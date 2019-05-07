package com.baozun.sdk.kafka.connection.test

import java.util.Properties
import java.util.concurrent.ExecutionException

import org.apache.kafka.clients.producer._
import org.apache.kafka.common.serialization.{IntegerSerializer, StringSerializer}


class Producer(val bootstrapServer: String, val topic: String, val isAsync: Boolean) {
  val props = new Properties
  props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer)
  props.put(ProducerConfig.CLIENT_ID_CONFIG, "Producer")
  props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, classOf[IntegerSerializer].getName)
  props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer].getName)
  props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, "5000")
  private val producer = new KafkaProducer[Integer, String](props)

  private val messageNo = 1
  private val messageStr = "Message_" + 1
  private val startTime = System.currentTimeMillis
  if (isAsync) { // Send asynchronously
    producer.send(new ProducerRecord[Integer, String](topic, messageNo, messageStr), new CallBack(startTime, messageNo, messageStr))
  }
  else { // Send synchronously
    try {
      producer.send(new ProducerRecord[Integer, String](topic, messageNo, messageStr)).get
      System.out.println("Sent message: (" + messageNo + ", " + messageStr + ")")
    } catch {
      case e@(_: InterruptedException | _: ExecutionException) =>
        e.printStackTrace()
    }
  }
}

class CallBack(val startTime: Long, val key: Int, val message: String) extends Callback {
  /**
    * A callback method the user can implement to provide asynchronous handling of request completion. This method will
    * be called when the record sent to the server has been acknowledged. When exception is not null in the callback,
    * metadata will contain the special -1 value for all fields except for topicPartition, which will be valid.
    *
    * @param metadata  The metadata for the record that was sent (i.e. the partition and offset). Null if an error
    *                  occurred.
    * @param exception The exception thrown during processing of this record. Null if no error occurred.
    */
  override def onCompletion(metadata: RecordMetadata, exception: Exception): Unit = {
    val elapsedTime = System.currentTimeMillis - startTime
    if (metadata != null) System.out.println("message(" + key + ", " + message + ") sent to partition(" + metadata.partition + "), " + "offset(" + metadata.offset + ") in " + elapsedTime + " ms")
    else System.err.println("连接超时！")
  }
}

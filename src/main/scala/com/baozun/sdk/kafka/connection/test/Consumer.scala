package com.baozun.sdk.kafka.connection.test

import java.util.{Collections, Properties}

import kafka.utils.ShutdownableThread
import org.apache.kafka.clients.consumer.{ConsumerConfig, KafkaConsumer}

/**
  * Kafka Consumer
  *
  * @param topic 要消费的topic
  */
class Consumer(val bootstrapServer: String, val topic: String) extends ShutdownableThread("KafkaConsumer", false) {
  val props = new Properties
  props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServer)
  props.put(ConsumerConfig.GROUP_ID_CONFIG, "Consumer")
  props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true")
  props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000")
  props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000")
  props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.IntegerDeserializer")
  props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
  val consumer = new KafkaConsumer[Integer, String](props)

  override def doWork(): Unit = {
    consumer.subscribe(Collections.singletonList(this.topic))
    val records = consumer.poll(1L)
    import scala.collection.JavaConversions._
    for (record <- records) {
      System.out.println("Received message: (" + record.key + ", " + record.value + ") at offset " + record.offset)
    }
  }

}

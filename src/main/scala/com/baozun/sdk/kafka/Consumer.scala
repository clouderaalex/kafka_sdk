package com.baozun.sdk.kafka

import java.util.{Collections, Properties}

import kafka.utils.ShutdownableThread
import org.apache.kafka.clients.consumer.{ConsumerConfig, KafkaConsumer}


class Consumer(val topic: String) extends ShutdownableThread("KafkaConsumerExample", false) {
  val props = new Properties
  props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaProperties.KAFKA_SERVER_URL + ":" + KafkaProperties.KAFKA_SERVER_PORT)
  props.put(ConsumerConfig.GROUP_ID_CONFIG, "DemoConsumer")
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

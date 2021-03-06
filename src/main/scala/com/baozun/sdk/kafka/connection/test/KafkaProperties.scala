package com.baozun.sdk.kafka.connection.test

/**
  * kafka 配置信息
  */
object KafkaProperties {
  val TOPIC = "baozun"
  val KAFKA_SERVER_URL = "192.168.1.100"
  val KAFKA_SERVER_PORT = 9092
  val KAFKA_PRODUCER_BUFFER_SIZE: Int = 4 * 1024
  val CONNECTION_TIMEOUT = 100000
  //val TOPIC2 = "topic2"
  //val TOPIC3 = "topic3"
  val CLIENT_ID = "SimpleConsumerClient"
}

class KafkaProperties private() {
}



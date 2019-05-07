package com.baozun.sdk.kafka.connection.test


/**
  * Kafka 连接
  */
object KafkaConnect {
  def main(args: Array[String]): Unit = {
    //Kafka 服务器地址
    val bootstrapServer = null
    //Kafka Topic
    val topic = null
    //同步还是异步
    val isAsync = true

    new Producer("localhost:9092",KafkaProperties.TOPIC, isAsync)
    //    val consumerThread = new Consumer(KafkaProperties.TOPIC)
    //    consumerThread.start
  }
}






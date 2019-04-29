package com.baozun.sdk.kafka

object KafkaConsumerProducerDemo {
  def main(args: Array[String]): Unit = {
    val isAsync = args.length == 0 || !args(0).trim.equalsIgnoreCase("sync")
    val producerThread = new Producer(KafkaProperties.TOPIC, isAsync)
    producerThread.start
//    val consumerThread = new Consumer(KafkaProperties.TOPIC)
//    consumerThread.start
  }
}



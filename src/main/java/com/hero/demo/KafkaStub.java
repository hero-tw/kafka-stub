package com.hero.demo;


import org.springframework.kafka.test.rule.KafkaEmbedded;

import java.util.stream.Stream;

public class KafkaStub {
    public KafkaEmbedded embeddedKafka = null;


    public void start() {
        embeddedKafka = new KafkaEmbedded(1, true, "TOPIC1");
        Stream.of(embeddedKafka.getBrokerAddresses()).forEach(System.out::println);
    }

    public void stop() {

    }
}

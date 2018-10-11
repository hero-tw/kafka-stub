package com.hero.demo.kafka.application;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class KafkaStub {
    public KafkaEmbedded embeddedKafka = null;

    @Value("${kafkastub.topics}")
    private String TOPICS;

    @Value("${kafkastub.ports}")
    private int PORTS;

    @PostConstruct
    public void start() {
        embeddedKafka = new KafkaEmbedded(1, true, TOPICS);
        embeddedKafka.setKafkaPorts(PORTS);
        try {
            embeddedKafka.before();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.hero.demo.kafka.client;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * Created by play on 10/10/18.
 */
//Just a utility to test our embeddedkafka
public class KafkaStubProducer {
    private String BOOTSTRAP_SERVERS = "127.0.0.1:9092";

    private static String TOPICS = "mytopic.t";

    public static void main(String[] args) {
        if (args.length > 0) {
            TOPICS = args[0];
        }
        new KafkaStubProducer().startProducer().startConsumer();
    }

    private KafkaStubProducer startProducer() {
        Properties producerProps = new Properties();
        producerProps.put("bootstrap.servers", BOOTSTRAP_SERVERS);
        producerProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producerProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> producer = new KafkaProducer(producerProps);

        try {
            RecordMetadata recordMetadata = producer.send(new ProducerRecord<String, String>(TOPICS, "1000", "2000")).get();
            System.out.println(recordMetadata);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return this;
    }

    private KafkaStubProducer startConsumer() {
        final Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG,
                "KafkaMockConsumer");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class.getName());
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);

        consumer.subscribe(Collections.singletonList(TOPICS));

        Timer timer = new Timer();
        timer.schedule(wrap(() -> {
            consumer
                    .poll(1000)
                    .forEach(c -> {
                        System.out.println(c.key() + "->" + c.value());
                    });
        }), 500, 500);

        return this;
    }

    static TimerTask wrap(Runnable r) {
        return new TimerTask() {

            @Override
            public void run() {
                r.run();
            }
        };
    }
}

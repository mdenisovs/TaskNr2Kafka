package com.example;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import static com.example.KafkaAvroProducer.BOOTSTRAP_SERVERS;

public class KafkaAvroConsumer {
    public static void main(String[] args) {
        var kafkaConsumer = prepareKafkaConsumer();
        kafkaConsumer.subscribe(Collections.singletonList(KafkaAvroProducer.TOPIC));

        while (true) {
            try {
                var records = kafkaConsumer.poll(Duration.ofMillis(1000));
                if (records.isEmpty()) {
                    System.out.println("no messages");
                    continue;
                }

                for (var record : records) {
                    System.out.println("received: " + record.value());
                }
                break;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        kafkaConsumer.close();
    }

    private static KafkaConsumer<String, Customer> prepareKafkaConsumer() {
        var props = new Properties();
        props.put("bootstrap.servers", BOOTSTRAP_SERVERS);
        props.put("group.id", "test");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put("schema.registry.url", "http://localhost:8081");

        return new KafkaConsumer<>(props);
    }
}

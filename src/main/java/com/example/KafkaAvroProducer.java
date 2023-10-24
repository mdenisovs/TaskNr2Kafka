package com.example;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaAvroProducer {
    public final static String TOPIC = "my-topic-2";
    public final static String BOOTSTRAP_SERVERS = "localhost:9092";

    public static void main(String[] args) {
        var kafkaProducer = prepareKafkaProducer();
        var customer = Customer.newBuilder().setFirstName("parenj").build();
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(TOPIC, "key", "ping");

        System.out.println("record sent...");
        try {
            kafkaProducer.send(producerRecord, (recordMetadata, e) -> {
                if (e != null) {
                    e.printStackTrace();
                }

                System.out.println("Sent - " + recordMetadata.toString());
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        kafkaProducer.flush();
        System.out.println("flushed");
        kafkaProducer.close();
        System.out.println("closed");
    }

    private static KafkaProducer<String, String> prepareKafkaProducer() {
        var props = new Properties();
        props.put("bootstrap.servers", BOOTSTRAP_SERVERS);
        props.put("acks", "all");
        props.put("retries", 10);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        props.put("schema.registry.url", "http://localhost:8081");

        return new KafkaProducer<>(props);
    }
}
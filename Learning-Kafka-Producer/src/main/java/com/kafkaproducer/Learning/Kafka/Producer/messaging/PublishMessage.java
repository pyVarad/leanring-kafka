package com.kafkaproducer.Learning.Kafka.Producer.messaging;

import com.kafkaproducer.Learning.Kafka.Producer.configs.PublisherConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class PublishMessage {

    @Autowired
    private final PublisherConfiguration publisherConfiguration;

    public PublishMessage(PublisherConfiguration publisherConfiguration) {
        this.publisherConfiguration = publisherConfiguration;
    }

    public void send(String topic, String data) {
        KafkaTemplate<Integer, String> template = this.publisherConfiguration.kafkaTemplate(
                this.publisherConfiguration.producerFactory()
        );
        template.setDefaultTopic(topic);
        template.sendDefault(1, data);
    }
}

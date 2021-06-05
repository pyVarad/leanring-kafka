package com.kafkaproducer.Learning.Kafka.Producer.models.request;

import lombok.Data;

@Data
public class AddUserInfo {

    private String username;
    private String firstName;
    private String lastName;
    private String message;
}

package com.kafkaproducer.Learning.Kafka.Producer.models.response;

import lombok.Data;

@Data
public class UserInfoResponse {

    private long id;
    private String username;
    private String firstName;
    private String lastName;
    private String message;
}

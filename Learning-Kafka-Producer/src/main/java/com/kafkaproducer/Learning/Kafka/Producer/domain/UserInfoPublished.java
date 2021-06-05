package com.kafkaproducer.Learning.Kafka.Producer.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "UserMessages")
public class UserInfoPublished {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "message")
    private String message;
}

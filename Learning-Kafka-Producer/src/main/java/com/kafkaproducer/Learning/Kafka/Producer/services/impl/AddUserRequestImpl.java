package com.kafkaproducer.Learning.Kafka.Producer.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafkaproducer.Learning.Kafka.Producer.domain.UserInfoPublished;
import com.kafkaproducer.Learning.Kafka.Producer.messaging.PublishMessage;
import com.kafkaproducer.Learning.Kafka.Producer.models.request.AddUserInfo;
import com.kafkaproducer.Learning.Kafka.Producer.models.response.UserInfoResponse;
import com.kafkaproducer.Learning.Kafka.Producer.repositories.UserInfoPublishedRepo;
import com.kafkaproducer.Learning.Kafka.Producer.services.AddUserRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
public class AddUserRequestImpl implements AddUserRequestService {

    @Autowired
    private final UserInfoPublishedRepo userInfoPublishedRepo;

    @Autowired
    private final PublishMessage publishMessage;

    public AddUserRequestImpl(UserInfoPublishedRepo userInfoPublishedRepo, PublishMessage publishMessage) {
        this.userInfoPublishedRepo = userInfoPublishedRepo;
        this.publishMessage = publishMessage;
    }

    @Override
    @Transactional
    public UserInfoResponse addUserRequest(AddUserInfo request) {
        log.info("Add a new user request.");
        UserInfoPublished userInfoPublished = new UserInfoPublished();
        userInfoPublished.setUsername(request.getUsername());
        userInfoPublished.setFirstName(request.getFirstName());
        userInfoPublished.setLastName(request.getLastName());
        userInfoPublished.setMessage(request.getMessage());
        userInfoPublished = this.userInfoPublishedRepo.save(userInfoPublished);
        try {
            this.publishMessage.send("first_topic", new ObjectMapper().writeValueAsString(userInfoPublished));
        } catch (Exception ex) {
            log.error("Failed to push the message to the topic.");
        }
        log.info("Successfully loaded the request.");
        return new ObjectMapper().convertValue(userInfoPublished, UserInfoResponse.class);
    }
}

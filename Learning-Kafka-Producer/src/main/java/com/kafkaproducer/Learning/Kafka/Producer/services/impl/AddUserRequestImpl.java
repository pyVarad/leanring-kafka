package com.kafkaproducer.Learning.Kafka.Producer.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafkaproducer.Learning.Kafka.Producer.domain.UserInfoPublished;
import com.kafkaproducer.Learning.Kafka.Producer.models.request.AddUserInfo;
import com.kafkaproducer.Learning.Kafka.Producer.models.response.UserInfoResponse;
import com.kafkaproducer.Learning.Kafka.Producer.repositories.UserInfoPublishedRepo;
import com.kafkaproducer.Learning.Kafka.Producer.services.AddUserRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AddUserRequestImpl implements AddUserRequestService {

    @Autowired
    private final UserInfoPublishedRepo userInfoPublishedRepo;

    public AddUserRequestImpl(UserInfoPublishedRepo userInfoPublishedRepo) {
        this.userInfoPublishedRepo = userInfoPublishedRepo;
    }

    @Override
    @Transactional
    public UserInfoResponse addUserRequest(AddUserInfo request) {
        UserInfoPublished userInfoPublished = new UserInfoPublished();
        userInfoPublished.setUsername(request.getUsername());
        userInfoPublished.setFirstName(request.getFirstName());
        userInfoPublished.setLastName(request.getLastName());
        userInfoPublished.setMessage(request.getMessage());
        userInfoPublished = this.userInfoPublishedRepo.save(userInfoPublished);
        return new ObjectMapper().convertValue(userInfoPublished, UserInfoResponse.class);
    }
}

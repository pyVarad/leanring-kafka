package com.kafkaproducer.Learning.Kafka.Producer.services;

import com.kafkaproducer.Learning.Kafka.Producer.models.request.AddUserInfo;
import com.kafkaproducer.Learning.Kafka.Producer.models.response.UserInfoResponse;

public interface AddUserRequestService {

UserInfoResponse addUserRequest(AddUserInfo request);
}

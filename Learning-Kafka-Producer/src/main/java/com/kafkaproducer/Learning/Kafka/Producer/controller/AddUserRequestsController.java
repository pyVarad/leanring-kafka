package com.kafkaproducer.Learning.Kafka.Producer.controller;

import com.kafkaproducer.Learning.Kafka.Producer.models.request.AddUserInfo;
import com.kafkaproducer.Learning.Kafka.Producer.models.response.UserInfoResponse;
import com.kafkaproducer.Learning.Kafka.Producer.services.AddUserRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;

@RestController
@RequestMapping("/userRequest")
public class AddUserRequestsController {

    @Autowired
    private final AddUserRequestService addUserRequestService;

    public AddUserRequestsController(AddUserRequestService addUserRequestService) {
        this.addUserRequestService = addUserRequestService;
    }

    @PostMapping(path="/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserInfoResponse> addUserRequest(@Valid @RequestBody AddUserInfo request) {
        UserInfoResponse userInfoResponse = this.addUserRequestService.addUserRequest(request);
        return new ResponseEntity<>(userInfoResponse, HttpStatus.CREATED);
    }
}

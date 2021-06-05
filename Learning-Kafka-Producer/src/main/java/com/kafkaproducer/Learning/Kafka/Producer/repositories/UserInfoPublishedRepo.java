package com.kafkaproducer.Learning.Kafka.Producer.repositories;

import com.kafkaproducer.Learning.Kafka.Producer.domain.UserInfoPublished;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoPublishedRepo extends JpaRepository<UserInfoPublished,Long> {
}

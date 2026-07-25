package com.example.tempconv.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.tempconv.model.ApiKey; 

public interface ApiKeyRepository extends MongoRepository<ApiKey, String> {
    
    boolean existsByKeyValue(String keyValue);
}
package com.example.tempconv.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.tempconv.model.TemperatureLog;

@Repository
public interface TemperatureRepository extends MongoRepository<TemperatureLog, String> {
    
   
    List<TemperatureLog> findByInputUnitIgnoreCase(String inputUnit);
}
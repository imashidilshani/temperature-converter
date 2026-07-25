package com.example.tempconv.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter; 

@Document(collection = "temperatureLog")
@Data
@Getter 
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TemperatureLog {
    @Id
    private String id;
    private double inputTemperature;
    private String inputUnit;
    private double outputTemperature;
    private String outputUnit;
    private String timestamp;
}
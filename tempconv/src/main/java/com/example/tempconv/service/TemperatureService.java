package com.example.tempconv.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.tempconv.model.TemperatureLog;
import com.example.tempconv.repository.ApiKeyRepository;
import com.example.tempconv.repository.TemperatureRepository;

@Service
public class TemperatureService {

    private final TemperatureRepository temperatureRepository;
    private final ApiKeyRepository apiKeyRepository;

    public TemperatureService(TemperatureRepository temperatureRepository, ApiKeyRepository apiKeyRepository) {
        this.temperatureRepository = temperatureRepository;
        this.apiKeyRepository = apiKeyRepository;
    }

    
    public TemperatureLog convertandsave(double value, String unit) {
        double outputValue = 0.0;
        String outputUnit = "";

        if (unit.equalsIgnoreCase("Celsius")) {
            outputValue = (value * 9 / 5) + 32; // Celsius -> Fahrenheit
            outputUnit = "Fahrenheit";
        } else if (unit.equalsIgnoreCase("Fahrenheit")) {
            outputValue = (value - 32) * 5 / 9; // Fahrenheit -> Celsius
            outputUnit = "Celsius";
        }

        
        String currentTimestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        TemperatureLog log = new TemperatureLog();
        log.setInputTemperature(value);
        log.setInputUnit(unit);
        log.setOutputTemperature(outputValue);
        log.setOutputUnit(outputUnit);
        log.setTimestamp(currentTimestamp);

        return temperatureRepository.save(log);
    }

   
    public List<TemperatureLog> getAllLogs() {
        return temperatureRepository.findAll();
    }

    
    public List<TemperatureLog> getLogsByUnit(String unit) {
        return temperatureRepository.findByInputUnitIgnoreCase(unit);
    }

   
    public void validateApiKey(String apiKey) {
    // if (apiKey == null || !apiKeyRepository.existsByKeyValue(apiKey)) {
    //     throw new UnauthorizedException("Invalid or missing API Key!");
    // }
} 

public String checkSafety(double value, String unit) {
    double tempInFahrenheit = unit.equalsIgnoreCase("C") ? (value * 9.0 / 5.0) + 32 : value;

    if (tempInFahrenheit >= 100) {
        return String.format("Warning: %.1f°%s is dangerously HOT! Stay hydrated.", value, unit.toUpperCase());
    } else if (tempInFahrenheit <= 32) {
        return String.format("Warning: %.1f°%s is dangerously COLD! Wear warm clothes.", value, unit.toUpperCase());
    } else {
        return "The temperature is comfortable and safe.";
    }
}

}       



package com.example.tempconv.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.tempconv.model.TemperatureLog;
import com.example.tempconv.service.TemperatureService;

@RestController
@RequestMapping("/api/temperatures")
public class TemperatureController {

    private final TemperatureService temperatureService;

    public TemperatureController(TemperatureService temperatureService) {
        this.temperatureService = temperatureService;
    }

    // 1. Temperature Conversion & DB Save Endpoint
    @PostMapping("/convert")
    public ResponseEntity<TemperatureLog> convertTemperature(
            @RequestHeader(value = "X-API-KEY", required = false) String apiKey,
            @RequestParam double value,                
            @RequestParam String unit                  
    ) {
        temperatureService.validateApiKey(apiKey);
        TemperatureLog savedLog = temperatureService.convertandsave(value, unit);
        return ResponseEntity.ok(savedLog);
    }

    // 2. Get All Logs Endpoint
    @GetMapping("/logs")                                     
    public ResponseEntity<List<TemperatureLog>> getAllLogs(
            @RequestHeader(value = "X-API-KEY", required = false) String apiKey  
    ) {
        temperatureService.validateApiKey(apiKey);
        List<TemperatureLog> logs = temperatureService.getAllLogs();
        return ResponseEntity.ok(logs);
    }

    // 3. Get History Endpoint
    @GetMapping("/history")                                     
    public ResponseEntity<List<TemperatureLog>> getHistory(
            @RequestHeader(value = "X-API-KEY", required = false) String apiKey  
    ) {
        temperatureService.validateApiKey(apiKey);
        List<TemperatureLog> history = temperatureService.getAllLogs();
        return ResponseEntity.ok(history);
    }

    // 4. Filter Logs Endpoint
    @GetMapping("/filter")                                     
    public ResponseEntity<List<TemperatureLog>> filterLogs(
            @RequestHeader(value = "X-API-KEY", required = false) String apiKey,
            @RequestParam String unit 
    ) {
        temperatureService.validateApiKey(apiKey);
        List<TemperatureLog> filteredLogs = temperatureService.getLogsByUnit(unit);
        return ResponseEntity.ok(filteredLogs);
    }

    // 5. Safety Check Endpoint
    @GetMapping(value = "/safety-check", produces = "text/plain")
    public ResponseEntity<String> checkSafety(
            @RequestParam("value") double value,
            @RequestParam("unit") String unit) {
        
        String responseMessage = temperatureService.checkSafety(value, unit);
        return ResponseEntity.ok(responseMessage);
    }
}
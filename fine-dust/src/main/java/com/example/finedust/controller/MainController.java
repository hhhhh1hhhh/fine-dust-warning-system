package com.example.finedust.controller;


import com.example.finedust.JsonData;
import com.example.finedust.service.JsonFileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/json-data") //http://localhost:8080/api/json-data/data
public class MainController {

    private final JsonFileService jsonFlieService;

    public MainController(JsonFileService jsonFlieService) {
        this.jsonFlieService = jsonFlieService;
    }

    @GetMapping("/data")
    public ResponseEntity<List<JsonData>> getJsonData() {
        try {
            List<JsonData> data = jsonFlieService.loadJsonData();
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}

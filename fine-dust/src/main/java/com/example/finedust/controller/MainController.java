package com.example.finedust.controller;


import com.example.finedust.data.JsonData;
import com.example.finedust.service.JsonFileService;
import com.example.finedust.service.StationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/json-data") // http://localhost:8080/api/json-data/
public class MainController {

    private final JsonFileService jsonFlieService;
    private final StationService stationService;

    public MainController(JsonFileService jsonFlieService, StationService stationService) {
        this.jsonFlieService = jsonFlieService;
        this.stationService = stationService;
    }

    @GetMapping("/read-data")
    public ResponseEntity<List<JsonData>> getJsonData() {
        try {
            List<JsonData> data = jsonFlieService.loadJsonData();
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/save-stations")
    public ResponseEntity<String> saveStations() throws IOException {
        List<JsonData> stations = jsonFlieService.loadJsonData();
        stationService.saveStations(stations);
        return ResponseEntity.ok("Stations save successfully");
    }
}

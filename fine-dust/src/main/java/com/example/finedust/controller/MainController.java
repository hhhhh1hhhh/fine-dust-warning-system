package com.example.finedust.controller;


import com.example.finedust.data.JsonData;
import com.example.finedust.entity.AllWarningEntity;
import com.example.finedust.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api") // http://localhost:8080/api/
public class MainController {

    private final JsonFileService jsonFlieService;
    private final CheckService checkService;
    private final AllWarningService allWarningService;
    private final FineDustService fineDustService;
    private final AlertService alertService;

    public MainController(JsonFileService jsonFlieService, CheckService checkService,
                          FineDustService fineDustService, AllWarningService allWarningService,
                          AlertService alertService) {
        this.jsonFlieService = jsonFlieService;
        this.checkService = checkService;
        this.allWarningService = allWarningService;
        this.fineDustService = fineDustService;
        this.alertService = alertService;
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

    @PostMapping("/save-json-data")
    public ResponseEntity<String> saveFindDust() throws IOException {
        List<JsonData> finedust = jsonFlieService.loadJsonData();
        fineDustService.saveFinedust(finedust);
        return ResponseEntity.ok("fine-dust save successfully");
    }

    @PostMapping("/save-checks")
    public ResponseEntity<String> saveChecks(){
        checkService.SaveCheckZeroPm();
        return ResponseEntity.ok("Checks save successfully");
    }

    @PostMapping("/save-all-warnings")
    public ResponseEntity<String> saveWarnings(){
        allWarningService.checkAllAdvisories();
        return ResponseEntity.ok("All-Warnings save successfully");
    }

    @PostMapping("/alert-warnings")
    public ResponseEntity<String> alertWarnings(){
        alertService.saveAlert();
        return ResponseEntity.ok("All-Warnings save successfully");
    }

}


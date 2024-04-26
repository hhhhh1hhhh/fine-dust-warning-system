package com.example.finedust.controller;


import com.example.finedust.data.JsonData;
import com.example.finedust.service.*;
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
    private final CheckService checkService;
    private final AllWarningService allWarningService;
    private final FineDustService fineDustService;

    public MainController(JsonFileService jsonFlieService,
                          CheckService checkService,
                          FineDustService fineDustService,
                          AllWarningService allWarningService) {
        this.jsonFlieService = jsonFlieService;
        this.checkService = checkService;
        this.allWarningService = allWarningService;
        this.fineDustService = fineDustService;
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

    @PostMapping("/save-finedust")
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

    @PostMapping("/save-warnings")
    public ResponseEntity<String> saveWarnings(){
        allWarningService.checkAllAdvisories();

        return ResponseEntity.ok("Warnings save successfully");
    }
}

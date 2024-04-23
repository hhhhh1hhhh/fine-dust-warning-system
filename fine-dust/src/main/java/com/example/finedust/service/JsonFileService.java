package com.example.finedust.service;

import com.example.finedust.JsonData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class JsonFileService {

    private final ObjectMapper objectMapper;

    public JsonFileService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<JsonData> loadJsonData() throws IOException {
        File file = new ClassPathResource("data.json").getFile();
        JsonData[] JsonDataArray = objectMapper.readValue(file, JsonData[].class);

        for (JsonData jsonData : JsonDataArray) {
            System.out.println("날짜: " + jsonData.getDate());
            System.out.println("측정소명:" + jsonData.getStationName());
            System.out.println("측정소코드:" + jsonData.getStationCode());
            System.out.println("PM10: " + jsonData.getPm10());
            System.out.println("PM2.5: " + jsonData.getPm25());
            System.out.println();

        }

        return Arrays.asList(JsonDataArray);
    }


}

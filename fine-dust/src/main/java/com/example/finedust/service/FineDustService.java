package com.example.finedust.service;

import com.example.finedust.data.JsonData;
import com.example.finedust.entity.FineDustEntity;
import com.example.finedust.repository.FineDustRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class FineDustService {

    private final FineDustRepository fineDustRepository;

    public FineDustService(FineDustRepository fineDustRepository) {
        this.fineDustRepository = fineDustRepository;
    }

//    @Transactional
//    public void saveFinedust(List<JsonData> finedustData) {
//        for(JsonData jsonData : finedustData) {
//            FineDustEntity fineDustEntity = new FineDustEntity();
//            fineDustEntity.setDateTime(LocalDateTime.parse(jsonData.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH")));
//            fineDustEntity.setStationName(jsonData.getStationName());
////            fineDustEntity.setPm10(jsonData.getPm10());
////            fineDustEntity.setPm25(jsonData.getPm25());
//
//            fineDustEntity.setPm10(jsonData.getPm10() != null ? jsonData.getPm10() : 0);
//            fineDustEntity.setPm25(jsonData.getPm25() != null ? jsonData.getPm25() : 0);
//
//            fineDustRepository.saveAll(fineDustEntity);
//        }
//    }

    @Transactional
    public void saveFinedust(List<JsonData> finedustData) {
        List<FineDustEntity> entities = new ArrayList<>();

        for (JsonData jsonData : finedustData) {
            FineDustEntity fineDustEntity = new FineDustEntity();
            fineDustEntity.setDateTime(LocalDateTime.parse(jsonData.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH")));
            fineDustEntity.setStationName(jsonData.getStationName());
            // fineDustEntity.setPm10(jsonData.getPm10());
            // fineDustEntity.setPm25(jsonData.getPm25());

            fineDustEntity.setPm10(jsonData.getPm10() != null ? jsonData.getPm10() : 0);
            fineDustEntity.setPm25(jsonData.getPm25() != null ? jsonData.getPm25() : 0);

            entities.add(fineDustEntity);
        }

        fineDustRepository.saveAll(entities);
    }


}

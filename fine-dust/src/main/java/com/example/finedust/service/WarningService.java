package com.example.finedust.service;

import com.example.finedust.entity.AllWarningEntity;
import com.example.finedust.repository.AllWarningRepository;
import com.example.finedust.repository.WarningLevelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarningService {

    private final AllWarningRepository allWarningRepository;
    private final WarningLevelRepository warningLevelRepository;


    public WarningService(AllWarningRepository allWarningRepository, WarningLevelRepository warningLevelRepository) {
        this.allWarningRepository = allWarningRepository;
        this.warningLevelRepository = warningLevelRepository;
    }


//    public List<AllWarningEntity> findAllWarningsWithLevels()
    public void findAllWarningsWithLevels(){

        List<AllWarningEntity> entityList = allWarningRepository.findAllWithWarningLevelOrderedByDateTime();

        for (AllWarningEntity allWarningEntity : entityList) {
            System.out.println("날짜: " + allWarningEntity.getDateTime());
            System.out.println("측정소: " + allWarningEntity.getStationName());
            System.out.println("(초)미세먼지 농도: " + allWarningEntity.getPmValue());
            System.out.println("경보단계: " + allWarningEntity.getWarningLevelEntity().getType());
            System.out.println();
        }
//        return allWarningRepository.findAllWithWarningLevelOrderedByDateTime();
    }
}

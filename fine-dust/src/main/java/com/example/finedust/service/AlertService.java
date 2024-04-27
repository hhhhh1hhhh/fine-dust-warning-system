package com.example.finedust.service;

import com.example.finedust.entity.AlertEntity;
import com.example.finedust.entity.AllWarningEntity;
import com.example.finedust.repository.AlertRepository;
import com.example.finedust.repository.AllWarningRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AlertService {

    private final AllWarningRepository allWarningRepository;
    private final AlertRepository alertRepository;


    public AlertService(AllWarningRepository allWarningRepository, AlertRepository alertRepository) {
        this.allWarningRepository = allWarningRepository;
        this.alertRepository = alertRepository;
    }

    public List<AllWarningEntity> findAllWarningsWithLevels() {
        return allWarningRepository.findAllWithWarningLevelOrderedByDateTime();

    }

    // 경보를 시간별로 그룹화하고 시간 오름차순으로 정렬
    public Map<LocalDateTime, List<AllWarningEntity>> groupWarningsByDateTime(List<AllWarningEntity> allWarningEntityList) {
        return allWarningEntityList.stream()
                .collect(Collectors.groupingBy(AllWarningEntity::getDateTime))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new
                ));
    }


    //    // 각 그룹 내에서 가장 높은 등급의 경보를 찾는 메서드 (여러 개를 리스트에 저장)
    private List<AllWarningEntity> findHighestLevelWarnings(List<AllWarningEntity> allWarningEntityList) {
        int lowestLevel = allWarningEntityList.stream()
                .mapToInt(warning -> warning.getWarningLevelEntity().getLevel())
                .min()
                .orElse(0);

        return allWarningEntityList.stream()
                .filter(warning -> warning.getWarningLevelEntity().getLevel() == lowestLevel)
                .collect(Collectors.toList());
    }

    // 각 그룹에서 가장 높은 등급의 경보 선택
    public Map<LocalDateTime, List<AllWarningEntity>> selectLowestLevelWarnings(Map<LocalDateTime, List<AllWarningEntity>> groupedWarningsByDateTime) {
        Map<LocalDateTime, List<AllWarningEntity>> selectedWarnings = new LinkedHashMap<>();
        for (Map.Entry<LocalDateTime, List<AllWarningEntity>> entry : groupedWarningsByDateTime.entrySet()) {
            List<AllWarningEntity> lowestLevelWarnings = findHighestLevelWarnings(entry.getValue());
            selectedWarnings.put(entry.getKey(), lowestLevelWarnings);
        }
        return selectedWarnings;
    }


    public void saveAlert() {
        List<AllWarningEntity> allWarningsWithLevels = findAllWarningsWithLevels();
        Map<LocalDateTime, List<AllWarningEntity>> groupedAndSortedWarnings = groupWarningsByDateTime(allWarningsWithLevels);
        Map<LocalDateTime, List<AllWarningEntity>> selectedHighestLevelWarnings = selectLowestLevelWarnings(groupedAndSortedWarnings);

        List<LocalDateTime> sortedDateTimes = new ArrayList<>(selectedHighestLevelWarnings.keySet());
        Collections.sort(sortedDateTimes);

        for (LocalDateTime dateTime : sortedDateTimes) {
            List<AllWarningEntity> allWarningEntityList = selectedHighestLevelWarnings.get(dateTime);
            for (AllWarningEntity allWarningEntity : allWarningEntityList) {
                AlertEntity alertEntity = new AlertEntity();
                alertEntity.setStationName(allWarningEntity.getStationName());
                alertEntity.setDateTime(dateTime);
                alertEntity.setWarningType(allWarningEntity.getWaringType());

                alertRepository.save(alertEntity);
            }
        }
    }
}
package com.example.finedust.service;

import com.example.finedust.data.JsonData;
import com.example.finedust.entity.StationEntity;
import com.example.finedust.entity.WarningEntity;
import com.example.finedust.entity.WarningLevelEntity;
import com.example.finedust.repository.StationRepository;
import com.example.finedust.repository.WarningLevelRepository;
import com.example.finedust.repository.WarningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
@RequiredArgsConstructor
public class WarningService {

    private final WarningRepository warningRepository;
    private final WarningLevelRepository warningLevelRepository;
    private final StationRepository stationRepository;


    public void saveWarnings(List<JsonData> warnings) {
        for (JsonData warning : warnings) {
            if (warning.getPm10() != null && (warning.getPm10() >= 300 || warning.getPm10() >= 150)) {
                WarningEntity warningEntity = createWarningEntity(warning);
                warningRepository.save(warningEntity);
            }
        }
    }

    private WarningEntity createWarningEntity(JsonData jsonData) {
        WarningEntity warningEntity = new WarningEntity();

        // 측정소 정보 설정
        StationEntity stationEntity = stationRepository.findByStationCode(jsonData.getStationCode()).orElse(null);
        warningEntity.setStationEntity(stationEntity);

        // 발령 시간 설정
        LocalDateTime issuedAt = LocalDateTime.parse(jsonData.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH"));
        warningEntity.setIssuedAt(issuedAt);

        // 경보 단계 설정
        String warningType = determineWarningType(jsonData);
        WarningLevelEntity warningLevelEntity = warningLevelRepository.findByType(warningType);
        warningEntity.setWarningLevelEntity(warningLevelEntity);

        return warningEntity;
    }



    private String determineWarningType(JsonData jsonData) {

        // PM10 값이 null인 경우 0으로 설정
        int pm10Value = jsonData.getPm10() != null ? jsonData.getPm10() : 0;
//       int pm25Value = jsonData.getPm25() != null ? jsonData.getPm25() : 0;

        if (pm10Value >= 300) {
            return "미세먼지 경보";
        } else if (pm10Value >= 150){
            return "미세먼지 주의보";
        } else {
            return null;
        }
    }
}

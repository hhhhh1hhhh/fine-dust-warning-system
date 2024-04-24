package com.example.finedust.service;

import com.example.finedust.data.JsonData;
import com.example.finedust.entity.CheckEntity;
import com.example.finedust.entity.StationEntity;
import com.example.finedust.repository.CheckRepository;
import com.example.finedust.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CheckService {

    private final CheckRepository checkRepository;
    private final StationRepository stationRepository;

    public void saveChecks(List<JsonData> checkData) {
        for(JsonData data : checkData) {
            Optional<StationEntity> optionStationEntity = stationRepository.findByStationCode(data.getStationCode());
            // 측정된 미세먼지, 초미세먼지가 모두 null --> 측정소 점검이 있던 날
            // 미세먼지만 null일 때, 초미세먼지만 null일 때 따로 처리?
            if(data.getPm10() == null && data.getPm25() == null){
                StationEntity stationEntity = optionStationEntity.orElseThrow(() -> new RuntimeException("Station not found"));
                CheckEntity checkentity = new CheckEntity();
                checkentity.setStationEntity(stationEntity);
                checkentity.setCheckDate(LocalDateTime.parse(data.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH")));
                checkRepository.save(checkentity);
            }
        }
    }
}

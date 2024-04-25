package com.example.finedust.service;

import com.example.finedust.data.JsonData;
import com.example.finedust.entity.StationEntity;
import com.example.finedust.repository.StationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StationService {

    private final StationRepository stationRepository;

    @Transactional
    public void saveStations(List<JsonData> stationData) {
        for (JsonData jsonData : stationData) {
            if (!stationRepository.existsByStationCode(jsonData.getStationCode())) {
                StationEntity stationEntity = new StationEntity();
                stationEntity.setStationName(jsonData.getStationName());
                stationEntity.setStationCode(jsonData.getStationCode());
                stationRepository.save(stationEntity);
            }
        }
    }

}

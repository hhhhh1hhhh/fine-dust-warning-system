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
    public void saveStations(List<JsonData> stationDatas) {
        for (JsonData data : stationDatas) {
            if (!stationRepository.existsByStationCode(data.getStationCode())) {
                StationEntity entity = new StationEntity();
                entity.setStationName(data.getStationName());
                entity.setStationCode(data.getStationCode());
                stationRepository.save(entity);
            }
        }
    }

}

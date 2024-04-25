package com.example.finedust.service;


import com.example.finedust.entity.CheckEntity;
import com.example.finedust.entity.FineDustEntity;
import com.example.finedust.repository.CheckRepository;
import com.example.finedust.repository.FineDustRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckService {

    private final CheckRepository checkRepository;
    private final FineDustRepository fineDustRepository;

    @Transactional
    public void SaveCheckZeroPm() {

        List<FineDustEntity> fineDustEntityList = fineDustRepository.findByPm10AndPm25(0, 0);

        for (FineDustEntity fineDustData : fineDustEntityList) {
            CheckEntity checkEntity = new CheckEntity();
            checkEntity.setStationName(fineDustData.getStationName());
            checkEntity.setDateTime(fineDustData.getDateTime());
            checkEntity.setPm10(fineDustData.getPm10());
            checkEntity.setPm25(fineDustData.getPm25());

            checkRepository.save(checkEntity);
        }
    }
}



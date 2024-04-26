package com.example.finedust.service;

import com.example.finedust.entity.AllWarningEntity;
import com.example.finedust.entity.FineDustEntity;
import com.example.finedust.entity.WarningLevelEntity;
import com.example.finedust.repository.AllWarningRepository;
import com.example.finedust.repository.FineDustRepository;
import com.example.finedust.repository.WarningLevelRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class AllWarningService {

    private final FineDustRepository fineDustRepository;
    private final AllWarningRepository allWarningRepository;
    private final WarningLevelRepository warningLevelRepository;

    public AllWarningService(FineDustRepository fineDustRepository, AllWarningRepository allWarningRepository, WarningLevelRepository warningLevelRepository) {
        this.fineDustRepository = fineDustRepository;
        this.allWarningRepository = allWarningRepository;
        this.warningLevelRepository = warningLevelRepository;
    }

    public List<FineDustEntity> checkContinueData(List<FineDustEntity> data, int minPm, Integer maxPm, String pmType) {
        List<FineDustEntity> continuousData = new ArrayList<>(); // 최종 리스트
        List<FineDustEntity> tempData = new ArrayList<>(); // 임시 저장 리스트

        for (int i = 0; i < data.size(); i++) { // data의 크기만큼 반복
            FineDustEntity record = data.get(i);
            boolean isContinuous = i > 0 && record.getDateTime().minusHours(1).equals(data.get(i - 1).getDateTime());

            // 조건 검사 부분에 동적 농도 타입 처리
            boolean isInPm = false;
            if (pmType.equals("PM10")) {
                isInPm = record.getPm10() >= minPm && (maxPm == null || record.getPm10() < maxPm);
            } else if (pmType.equals("PM2.5")) {
                isInPm = record.getPm25() >= minPm && (maxPm == null || record.getPm25() < maxPm);
            }

            if (isContinuous && isInPm) { // 이전 기록과 1시간 차이나고 Pm이 범위 내에 있을 때
                tempData.add(record); // 임시 저장 리스트에 record 추가
            } else {
                if (tempData.size() >= 2) { // 임시 저장 리스트의 크기가 2 이상
                    continuousData.addAll(new ArrayList<>(tempData)); // 최종 리스트에서 임시 저장 리스트 추가
                }
                tempData.clear(); // 임시 저장 리스트 초기화
                if (isInPm) { // Pm이 범위 내에 있을 때
                    tempData.add(record);
                }
            }
        }

        if (tempData.size() >= 2) { // 리스트의 마지막 부분 처리
            continuousData.addAll(new ArrayList<>(tempData));
        }

        return continuousData;
    }


    // 경보 데이터 처리를 위한 일반화된 메서드
    private void processWarning(String pmType, String levelType, int minPm, Integer maxPm, int warningLevelId) {
        List<FineDustEntity> advisoryData = checkContinueData(findDataByTypeAndLevel(pmType, levelType), minPm, maxPm, pmType);
        WarningLevelEntity warningLevel = warningLevelRepository.findById(warningLevelId).orElse(null);

        for (FineDustEntity data : advisoryData) {
            saveWarning(data, warningLevel, pmType);
        }
    }

    // 데이터 저장
    private void saveWarning(FineDustEntity data, WarningLevelEntity warningLevel, String pmType) {
        AllWarningEntity allWarning = new AllWarningEntity();
        allWarning.setStationName(data.getStationName());
        allWarning.setDateTime(data.getDateTime());
        allWarning.setPmValue(pmType.equals("PM10") ? data.getPm10() : data.getPm25());
        allWarning.setWarningLevelEntity(warningLevel);
        allWarningRepository.save(allWarning);
    }

    // pm종류, 주의보/경보 찾는 메서드
    private List<FineDustEntity> findDataByTypeAndLevel(String pmType, String levelType) {
        if (pmType.equals("PM10")) {
            return levelType.equals("Advisory") ? fineDustRepository.findPM10Advisory() : fineDustRepository.findPM10Warning();
        } else if (pmType.equals("PM2.5")) {
            return levelType.equals("Advisory") ? fineDustRepository.findPM25Advisory() : fineDustRepository.findPM25Warning();
        }
        return new ArrayList<>();
    }

    public void checkAllAdvisories() {
        processWarning("PM10", "Advisory", 150, 300, 4); // 미세먼지 주의보
        processWarning("PM10", "Warning", 300, null, 2); // 미세먼지 경보
        processWarning("PM2.5", "Advisory", 75, 150, 3); // 초미세먼지 주의보
        processWarning("PM2.5", "Warning", 150, null, 1); // 초미세먼지 경보
    }
}

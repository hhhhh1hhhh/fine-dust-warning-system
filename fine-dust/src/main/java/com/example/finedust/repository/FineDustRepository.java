package com.example.finedust.repository;

import com.example.finedust.entity.FineDustEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface FineDustRepository extends JpaRepository<FineDustEntity, Long> {

    List<FineDustEntity> findByPm10AndPm25(int pm10, int pm25);

        /*
    미세먼지 주의보
    SELECT stationName, dateTime, pm10
    FROM `fine-dust`.finedust_entity
    WHERE pm10 >= 150 and pm10 < 300

    미세먼지 경보
    SELECT stationName, dateTime, pm10
    FROM `fine-dust`.finedust_entity
    WHERE pm10 >= 300

    초미세먼지 주의보
    SELECT stationName, dateTime, pm25
    FROM `fine-dust`.finedust_entity
    WHERE pm25 >= 75 and pm25 < 150

    초미세먼저 경보
    SELECT stationName, dateTime, pm25
    FROM `fine-dust`.finedust_entity
    WHERE pm25 >= 150
     */

    @Query("SELECT f FROM FineDustEntity f WHERE f.pm10 >= 150 AND f.pm10 < 300")
    List<FineDustEntity> findPM10Advisory();

    @Query("SELECT f FROM FineDustEntity f WHERE f.pm10 >= 300")
    List<FineDustEntity> findPM10Warning();

    @Query("SELECT f FROM FineDustEntity f WHERE f.pm25 >= 75 AND f.pm25 < 150")
    List<FineDustEntity> findPM25Advisory();

    @Query("SELECT f FROM FineDustEntity f WHERE f.pm25 >= 150")
    List<FineDustEntity> findPM25Warning();
}

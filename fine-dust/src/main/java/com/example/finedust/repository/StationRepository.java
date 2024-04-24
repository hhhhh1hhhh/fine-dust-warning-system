package com.example.finedust.repository;

import com.example.finedust.entity.StationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StationRepository extends JpaRepository<StationEntity, Integer> {
    boolean existsByStationCode(String stationCode);

    Optional<StationEntity> findByStationCode(String stationCode);
}

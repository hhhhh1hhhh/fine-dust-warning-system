package com.example.finedust.repository;

import com.example.finedust.entity.FineDustEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FineDustRepository extends JpaRepository<FineDustEntity, Long> {

    List<FineDustEntity> findByPm10AndPm25(int pm10, int pm25);
}

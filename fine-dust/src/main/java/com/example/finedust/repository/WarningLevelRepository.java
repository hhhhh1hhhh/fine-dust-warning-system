package com.example.finedust.repository;

import com.example.finedust.entity.WarningLevelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarningLevelRepository extends JpaRepository<WarningLevelEntity, Integer> {
}

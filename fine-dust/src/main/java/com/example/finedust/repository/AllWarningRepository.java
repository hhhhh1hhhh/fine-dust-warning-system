package com.example.finedust.repository;

import com.example.finedust.entity.AllWarningEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AllWarningRepository extends JpaRepository<AllWarningEntity, Long> {
    @Query("SELECT a FROM AllWarningEntity a JOIN FETCH a.warningLevelEntity b ORDER BY a.dateTime ASC")
    List<AllWarningEntity> findAllWithWarningLevelOrderedByDateTime();
}

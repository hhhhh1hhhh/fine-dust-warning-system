package com.example.finedust.repository;

import com.example.finedust.entity.CheckEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CheckRepository extends JpaRepository<CheckEntity, Long> {
}

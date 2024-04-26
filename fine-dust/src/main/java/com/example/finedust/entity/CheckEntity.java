package com.example.finedust.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "check_entity")
public class CheckEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private int pm10;

    @Column(nullable = false)
    private int pm25;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(nullable = false)
    private String stationName;

}

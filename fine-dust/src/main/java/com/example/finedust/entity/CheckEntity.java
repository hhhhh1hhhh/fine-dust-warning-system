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

    @Column(name = "pm10")
    private int pm10;

    @Column(name = "pm25")
    private int pm25;

    @Column(name = "dateTime")
    private LocalDateTime dateTime;

    @Column(name = "stationName")
    private String stationName;

}

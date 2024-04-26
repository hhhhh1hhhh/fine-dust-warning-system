package com.example.finedust.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "allwaring_Entity")
public class AllWarningEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String stationName;

    private LocalDateTime dateTime;

    private Integer pmValue;

    /* Waring:warningLevel = N:1*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warninglevel_id", referencedColumnName = "id")
    private WarningLevelEntity warningLevelEntity;

}

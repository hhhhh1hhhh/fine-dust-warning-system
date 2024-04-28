package com.example.finedust.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "allwarning_entity")
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

    public String getWaringType() {
        return warningLevelEntity.getType(); }

}
package com.example.finedust.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "warninglevel_entity")
public class WarningLevelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private int level;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String Description;

}

package com.timkin.models.entity;

import javax.persistence.*;

@Entity
@Table(name = "motorcycles")
public class Motorcycle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    private String model;
    private double price;
    private boolean sold;
    private double engineVolume;
    private String engineType;
}

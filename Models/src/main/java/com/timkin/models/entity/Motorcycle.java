package com.timkin.models.entity;

import javax.persistence.*;

@Entity
@Table(name = "motorcycles")
public class Motorcycle {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 20, nullable = false)
    private String model;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private boolean sold;

    @Column(name = "engine_volume", nullable = false)
    private double engineVolume;

    @Column(name = "engine_type", length = 20, nullable = false)
    private String engineType;

    public Motorcycle() {
    }

    public Motorcycle(String model, double price, boolean sold, double engineVolume, String engineType) {
        this.model = model;
        this.price = price;
        this.sold = sold;
        this.engineVolume = engineVolume;
        this.engineType = engineType;
    }

    public int getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public double getEngineVolume() {
        return engineVolume;
    }

    public void setEngineVolume(double engineVolume) {
        this.engineVolume = engineVolume;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }
}

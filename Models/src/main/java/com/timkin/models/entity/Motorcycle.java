package com.timkin.models.entity;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "motorcycles")
public class Motorcycle {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 20, nullable = false)
    @Size(min = 4, max = 20, message = "Model name length should be in range from 4 to 20")
    @NotBlank(message = "Model name should not be null, not be empty and not consist of only space characters")
    private String model;

    @Column(nullable = false)
    @Positive(message = "Price should be positive")
    @Min(value = 10, message = "Minimum price is 10")
    @Max(value = 1000000000, message = "Maximum price is 1'000'000'000")
    @NotNull(message = "Price can't be null")
    private double price;

    @Column(nullable = false)
    @NotNull(message = "Flag 'Is sold' can't be null")
    private boolean sold;

    @Column(name = "engine_volume", nullable = false)
    @Positive(message = "Engine volume should be positive")
    @Min(value = 5, message = "Minimum volume is 5")
    @Max(value = 1000, message = "Maximum volume is 1000")
    @NotNull(message = "Engine volume can't be null")
    private double engineVolume;

    @Column(name = "engine_type", length = 20, nullable = false)
    @Size(min = 3, max = 20, message = "Model name length should be in range from 4 to 20")
    @NotBlank(message = "Model name should not be null, not be empty and not consist of only space characters")
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

    public void setId(int id) {
        this.id = id;
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

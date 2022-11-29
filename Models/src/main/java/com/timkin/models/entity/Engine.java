package com.timkin.models.entity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "engines")
public class Engine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column
    private String model;

    @Column(nullable = false)
    @Positive(message = "Engine volume should be positive")
    @Min(value = 5, message = "Minimum volume is 5")
    @Max(value = 1000, message = "Maximum volume is 1000")
    @NotNull(message = "Engine volume can't be null")
    private double volume;

    @ManyToOne
    @JoinColumn(name = "engine_type_id", foreignKey = @ForeignKey(name = "fk_engine_enginetype"))
    private EngineType type;

    public Engine() {
    }

    public Engine(double volume, EngineType type) {
        this.volume = volume;
        this.type = type;
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

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public EngineType getType() {
        return type;
    }

    public void setType(EngineType type) {
        this.type = type;
    }
}

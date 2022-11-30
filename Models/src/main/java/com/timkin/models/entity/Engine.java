package com.timkin.models.entity;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "engines")
public class Engine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column(length = 30, nullable = false)
    @Size(min = 3, max = 30,message = "Model name's length should be from 3 to 30 characters")
    @NotBlank(message = "Model name should not be null, empty or contain only space characters")
    private String model;

    @Column(nullable = false)
    @Positive(message = "Engine volume should be positive")
    @Min(value = 5, message = "Minimum volume is 5")
    @Max(value = 1000, message = "Maximum volume is 1000")
    @NotNull(message = "Engine volume can't be null")
    private double volume;

    @ManyToOne
    @JoinColumn(name = "engine_type_id", foreignKey = @ForeignKey(name = "fk_engine_enginetype"))
    @NotNull(message = "Engine type can't be null")
    private EngineType type;

    public Engine() {
    }

    public Engine(String model, double volume, EngineType type) {
        this.model = model;
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

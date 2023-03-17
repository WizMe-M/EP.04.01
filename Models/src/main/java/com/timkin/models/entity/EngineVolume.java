package com.timkin.models.entity;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "engine_volumes")
public class EngineVolume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column
    @NotBlank(message = "Name can't be empty")
    private String name;

    @Column(nullable = false)
    @Positive(message = "Engine volume should be positive")
    @Max(value = 1000, message = "Maximum volume is 1000")
    @NotNull(message = "Engine volume can't be null")
    private double value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}

package com.timkin.models.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "engine_types",
uniqueConstraints = @UniqueConstraint(name = "uk_enginetype_name", columnNames = "name"))
public class EngineType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column(length = 20, nullable = false)
    @Size(min = 3, max = 20, message = "Model name length should be in range from 4 to 20")
    @NotBlank(message = "Model name should not be null, not be empty and not consist of only space characters")
    private String name;

    public EngineType() {
    }

    public EngineType(String name) {
        this.name = name;
    }

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
}

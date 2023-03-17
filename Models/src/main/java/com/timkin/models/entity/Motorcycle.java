package com.timkin.models.entity;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

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
    @Range(min = 10, max = 1000000000, message = "Price must be in range from 10 to 1'000'000'000")
    @NotNull(message = "Price can't be null")
    private double price;

    @ManyToOne(optional = false)
    @JoinColumn(name = "engine_id", foreignKey = @ForeignKey(name = "fk_motorcycle_engine"))
    private Engine engine;

    @OneToMany(mappedBy = "motorcycle", fetch = FetchType.EAGER)
    private List<Purchase> purchases;

    public Motorcycle() {
    }

    public Motorcycle(String model, double price) {
        this.model = model;
        this.price = price;
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

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }
}

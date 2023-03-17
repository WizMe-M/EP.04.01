package com.timkin.models.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "contracts")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column(name = "process_date", nullable = false)
    private Date processDate = Date.from(Instant.now());

    @Column(name = "supply_date", nullable = false)
    @NotNull(message = "Supply date can't be null")
    private Date supplyDate;

    @ManyToOne
    @JoinColumn(name = "consumer_id", foreignKey = @ForeignKey(name = "fk_contract_consumer"))
    private Consumer consumer;

    @ManyToMany
    @JoinTable(name = "orders",
            joinColumns = @JoinColumn(name = "contract_id", referencedColumnName = "id"),
            foreignKey = @ForeignKey(name = "fk_orders_contract"),
            inverseJoinColumns = @JoinColumn(name = "motorcycle_id", referencedColumnName = "id"),
            inverseForeignKey = @ForeignKey(name = "fk_orders_motorcycle"))
    private List<Motorcycle> motorcycles;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getProcessDate() {
        return processDate;
    }

    public void setProcessDate(Date processDate) {
        this.processDate = processDate;
    }

    public Date getSupplyDate() {
        return supplyDate;
    }

    public void setSupplyDate(Date supplyDate) {
        this.supplyDate = supplyDate;
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

    public List<Motorcycle> getMotorcycles() {
        return motorcycles;
    }

    public void setMotorcycles(List<Motorcycle> motorcycles) {
        this.motorcycles = motorcycles;
    }
}

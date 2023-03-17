package com.timkin.models.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "contracts")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column(name = "process_date", nullable = false)
    private Date processDate = Date.valueOf(LocalDate.now());

    @Column(name = "supply_date", nullable = false)
    @NotNull(message = "Supply date can't be null")
    private Date supplyDate;

    @ManyToOne
    @JoinColumn(name = "consumer_id", foreignKey = @ForeignKey(name = "fk_contract_consumer"))
    @NotNull(message = "Consumer can't be null")
    private Consumer consumer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "motorcycle_id", foreignKey = @ForeignKey(name = "fk_contract_motorcycle"))
    @NotNull(message = "Motorcycle can't be null")
    private Motorcycle motorcycle;

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

    public Motorcycle getMotorcycle() {
        return motorcycle;
    }

    public void setMotorcycle(Motorcycle motorcycle) {
        this.motorcycle = motorcycle;
    }
}

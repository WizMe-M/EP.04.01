package com.timkin.models.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "counsumers")
public class Consumer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column(name = "legal_address", nullable = false)
    @NotBlank(message = "Legal address should not be blank")
    private String legalAddress;

    @Column(name = "postal_address", nullable = false)
    @NotBlank(message = "Legal address should not be blank")
    private String postalAddress;

    @Column(name = "correspondent", nullable = false)
    @Pattern(regexp = "^\\d{20}$", message = "Correspondent account should be 20-digits number")
    private String correspondentAccount;

    @Column(name = "settlement", nullable = false)
    @Pattern(regexp = "^\\d{20}$", message = "Settlement account should be 20-digits number")
    private String settlementAccount;

    @Column(name = "tin", nullable = false)
    @Pattern(regexp = "^\\d{12}$", message = "Taxpayer Identification Number should be 12-digits number")
    private String tin;

    @ManyToOne
    @JoinColumn(name = "country_id", foreignKey = @ForeignKey(name = "fk_consumer_country"))
    @NotNull(message = "Country can't be null")
    private Country country;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLegalAddress() {
        return legalAddress;
    }

    public void setLegalAddress(String legalAddress) {
        this.legalAddress = legalAddress;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public String getCorrespondentAccount() {
        return correspondentAccount;
    }

    public void setCorrespondentAccount(String correspondentAccount) {
        this.correspondentAccount = correspondentAccount;
    }

    public String getSettlementAccount() {
        return settlementAccount;
    }

    public void setSettlementAccount(String settlementAccount) {
        this.settlementAccount = settlementAccount;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}

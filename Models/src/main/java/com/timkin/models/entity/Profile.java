package com.timkin.models.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "profiles")
public class Profile {
    @Id
    @Column
    private UUID id;

    @Column
    @NotBlank(message = "First name should not be empty")
    private String firstName;

    @Column
    @NotBlank(message = "Last name should not be empty")
    private String lastName;

    @Column
    @NotBlank(message = "Patronymic should not be empty")
    private String patronymic;

    @OneToOne(fetch = FetchType.EAGER)
    @MapsId
    @JoinColumn(name = "id", foreignKey = @ForeignKey(name = "fk_profile_user"))
    private User user;

    @ManyToMany
    @JoinTable(name = "purchased_motorcycles",
            joinColumns = @JoinColumn(name = "profile_id", referencedColumnName = "id"),
            foreignKey = @ForeignKey(name = "fk_purchasedmotorcycles_profile"),
            inverseJoinColumns = @JoinColumn(name = "motorcycle_id", referencedColumnName = "id"),
            inverseForeignKey = @ForeignKey(name = "fk_purchasedmotorcycles_motorcycle"))
    private List<Motorcycle> purchases;

    public Profile() {
    }

    public Profile(User user) {
        this.user = user;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Motorcycle> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Motorcycle> purchases) {
        this.purchases = purchases;
    }
}

package com.timkin.models.entity;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "profiles")
public class Profile {

    // TODO: add data validation
    @Id
    @Column
    private UUID id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String patronymic;

    @Column
    private int age;

    @Column
    private String description;

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

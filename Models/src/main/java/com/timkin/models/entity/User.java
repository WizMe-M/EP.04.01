package com.timkin.models.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "uuid4", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(length = 25, nullable = false, unique = true)
    @Size(min = 3, max = 25, message = "Login's length should be in range from 3 to 25")
    @NotBlank(message = "Login should not be null, not be empty and not consist of only space characters")
    private String login;

    @Column(length = 16, nullable = false)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[~!@#$%^&*+\\-=])(?!.*\\s)$",
            message = "Password should contain at least one uppercase char, one lowercase char, one digit and one special character, and NOT contain space characters")
    @Size(min = 8, max = 16, message = "Password's length should be in range from 8 to 16")
    @NotEmpty(message = "Password can't be null or empty")
    private String password;

    @Column(name = "registration_date", nullable = false)
    @Null(message = "Registration date will be set after user's registration, you can't set it manually")
    private Date registrationDate = Date.from(Instant.now());

    @Column(nullable = false)
    @Min(value = 14, message = "It is permissible to use the application for users at least 14 years old")
    @Max(value = 120, message = "Set a realistic age (the most old people live to the age of 115-120 years)")
    @NotNull(message = "Age can't be null")
    private int age;

    public User() {
    }

    public User(String login, String password, int age) {
        this.login = login;
        this.password = password;
        this.age = age;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

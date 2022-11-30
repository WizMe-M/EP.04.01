package com.timkin.models.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(name = "uk_user_login", columnNames = "login"))
public class User {
    @Id
    @Column
    @GeneratedValue(generator = "guid")
    @GenericGenerator(name = "guid", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(length = 25, nullable = false)
    @Size(min = 3, max = 25, message = "Login's length should be in range from 3 to 25")
    @NotBlank(message = "Login should not be null, not be empty and not consist of only space characters")
    private String login;

    @Column(length = 16, nullable = false)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[~!@#$%^&*+\\-=])(?!.*\\s).+$",
            message = "Password should contain at least one uppercase char, one lowercase char, one digit and one special character, and NOT contain space characters")
    @Size(min = 8, max = 16, message = "Password's length should be in range from 8 to 16")
    @NotEmpty(message = "Password can't be null or empty")
    private String password;

    @Column(name = "registration_date", nullable = false)
    private Date registrationDate = Date.from(Instant.now());

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "fk_user_role"))
    @NotNull(message = "Role must be set for user")
    private Role role;

    @OneToOne(mappedBy = "user")
    private Profile profile;

    public boolean hasProfile() {
        return profile != null;
    }

    public User() {
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}

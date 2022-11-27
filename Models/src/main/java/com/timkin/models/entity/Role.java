package com.timkin.models.entity;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "uk_role_name", columnNames = "name"))
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column(length = 20)
    private String name;
}

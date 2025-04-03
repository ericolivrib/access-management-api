package com.erico.accessmanagement.business.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @Column(name = "role_id")
    private Integer id;

    @Column(name = "role_label")
    @Enumerated(EnumType.STRING)
    private RoleLabel label;

    @OneToMany(mappedBy = "role")
    private List<User> users;
}

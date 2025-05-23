package com.erico.accessmanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@Table(name = "accesses")
@NoArgsConstructor
@AllArgsConstructor
public class Access {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "access_id")
    private Integer id;

    @Column(name = "access_label")
    private String label;

    @OneToMany(mappedBy = "access")
    private List<Permission> permissions;
}

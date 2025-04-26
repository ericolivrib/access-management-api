package com.erico.accessmanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@Builder
@Table(name = "access_permissions")
@NoArgsConstructor
@AllArgsConstructor
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "permission_id")
    private UUID id;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne()
    @JoinColumn(name = "access_id")
    private Access access;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PermissionStatus status;

    @Column(name = "near_expiration")
    private Boolean nearExpiration;

    @Column(name = "expires_in")
    private Instant expiresIn;

    @Column(name = "granted_at")
    private Instant grantedAt;

    @Column(name = "revoked_at")
    private Instant revokedAt;
}

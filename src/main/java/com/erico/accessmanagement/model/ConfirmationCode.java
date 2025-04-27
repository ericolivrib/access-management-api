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
@Table(name = "confirmation_codes")
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "code_id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "expires_in")
    private Instant expiresIn;

    @Column(name = "confirmed")
    private boolean confirmed;
}

package com.erico.accessmanagement.repository;

import com.erico.accessmanagement.model.ConfirmationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ConfirmationCodeRepository extends JpaRepository<ConfirmationCode, UUID> {
}

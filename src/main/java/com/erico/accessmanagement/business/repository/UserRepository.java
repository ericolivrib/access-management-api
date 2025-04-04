package com.erico.accessmanagement.business.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.erico.accessmanagement.business.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);
}

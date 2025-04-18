package com.erico.accessmanagement.business.repository;

import com.erico.accessmanagement.business.model.Role;
import com.erico.accessmanagement.business.model.RoleLabel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role getReferenceByLabel(RoleLabel roleLabel);
}

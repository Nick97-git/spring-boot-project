package com.amazon.review.repository;

import com.amazon.review.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRoleName(Role.RoleName roleName);
}

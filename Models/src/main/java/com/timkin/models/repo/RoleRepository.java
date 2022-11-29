package com.timkin.models.repo;

import com.timkin.models.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    List<Role> findByNameContainsIgnoreCase(String name);
}

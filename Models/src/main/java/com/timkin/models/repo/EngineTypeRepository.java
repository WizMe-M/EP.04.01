package com.timkin.models.repo;

import com.timkin.models.entity.EngineType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EngineTypeRepository extends JpaRepository<EngineType, Integer> {
    List<EngineType> findByNameContainsIgnoreCase(String name);
}

package com.timkin.models.repo;

import com.timkin.models.entity.EngineType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EngineTypeRepository extends JpaRepository<EngineType, Integer> {
}

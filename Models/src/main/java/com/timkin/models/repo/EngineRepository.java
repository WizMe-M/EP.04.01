package com.timkin.models.repo;

import com.timkin.models.entity.Engine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EngineRepository extends JpaRepository<Engine, Integer> {
}

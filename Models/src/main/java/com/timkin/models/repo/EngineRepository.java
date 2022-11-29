package com.timkin.models.repo;

import com.timkin.models.entity.Engine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EngineRepository extends JpaRepository<Engine, Integer> {
    List<Engine> findByModelContainsIgnoreCase(String model);
}

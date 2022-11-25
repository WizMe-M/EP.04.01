package com.timkin.models.repo;

import com.timkin.models.entity.Motorcycle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MotorcycleRepository extends JpaRepository<Motorcycle, Integer> {
    List<Motorcycle> findByModelContainsIgnoreCase(String model);
}

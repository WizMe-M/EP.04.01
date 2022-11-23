package com.timkin.models.repo;

import com.timkin.models.entity.Motorcycle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MotorcycleRepository extends JpaRepository<Motorcycle, Integer> {
}

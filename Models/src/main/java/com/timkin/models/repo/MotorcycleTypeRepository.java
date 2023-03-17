package com.timkin.models.repo;

import com.timkin.models.entity.MotorcycleType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MotorcycleTypeRepository extends JpaRepository<MotorcycleType, Integer> {
}
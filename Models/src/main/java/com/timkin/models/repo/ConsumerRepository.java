package com.timkin.models.repo;

import com.timkin.models.entity.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsumerRepository extends JpaRepository<Consumer, Integer> {
}
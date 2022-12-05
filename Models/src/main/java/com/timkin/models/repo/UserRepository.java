package com.timkin.models.repo;

import com.timkin.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByLogin(String login);
    Optional<User> findByLogin(String login);
    List<User> findByLoginContainsIgnoreCase(String login);
}

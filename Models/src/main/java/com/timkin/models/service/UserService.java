package com.timkin.models.service;

import com.timkin.models.entity.User;
import com.timkin.models.exceptions.UserNotFoundException;
import com.timkin.models.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean existWithLogin(String login) {
        return userRepository.existsByLogin(login);
    }

    public User find(String login) throws UserNotFoundException {
        return userRepository.findByLogin(login).orElseThrow(UserNotFoundException::new);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> findAll(String searchString) {
        return userRepository.findByLoginContainsIgnoreCase(searchString);
    }

    public void add(User user) {
        if (user.getId() != null) {
            throw new IllegalStateException("User already has ID!");
        }

        userRepository.save(user);
    }

    public void edit(User user) {
        if (user.getId() == null) {
            throw new IllegalStateException("Edited user's ID was not set!");
        }

        userRepository.save(user);
    }

    public void delete(String login) throws UserNotFoundException {
        User deleting = find(login);
        userRepository.delete(deleting);
    }
}

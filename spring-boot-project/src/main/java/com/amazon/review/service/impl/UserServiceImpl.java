package com.amazon.review.service.impl;

import com.amazon.review.model.User;
import com.amazon.review.repository.UserRepository;
import com.amazon.review.service.UserService;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> saveAll(Set<User> users) {
        return userRepository.saveAll(users);
    }

    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }
}

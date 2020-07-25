package com.amazon.review.service.impl;

import com.amazon.review.model.UserAws;
import com.amazon.review.repository.UserAwsRepository;
import com.amazon.review.service.UserAwsService;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserAwsServiceImpl implements UserAwsService {
    private final UserAwsRepository userAwsRepository;

    @Override
    public UserAws save(UserAws user) {
        return userAwsRepository.save(user);
    }

    @Override
    public List<UserAws> saveAll(Set<UserAws> users) {
        return userAwsRepository.saveAll(users);
    }

    @Override
    public List<UserAws> getMostActiveUsers(int limit, int offset) {
        PageRequest pageRequest = PageRequest.of(offset, limit);
        return userAwsRepository.findAll(pageRequest);
    }
}

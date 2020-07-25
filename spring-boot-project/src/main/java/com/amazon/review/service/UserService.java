package com.amazon.review.service;

import com.amazon.review.model.User;

public interface UserService extends GenericService<User> {

    User findByLogin(String login);
}

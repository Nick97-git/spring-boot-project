package com.amazon.review.security;

import com.amazon.review.model.User;

public interface AuthenticationService {

    User register(User user);

    User login(String login, String password);
}

package com.amazon.review.security;

import com.amazon.review.exception.AuthenticationException;
import com.amazon.review.model.Role;
import com.amazon.review.model.User;
import com.amazon.review.service.RoleService;
import com.amazon.review.service.UserService;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final RoleService roleService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Set.of(roleService.findByRoleName(Role.RoleName.USER)));
        return userService.save(user);
    }

    @SneakyThrows
    @Override
    public User login(String login, String password) {
        User user = userService.findByLogin(login);
        String encodedPassword = passwordEncoder.encode(password);
        if (user == null || user.getPassword().equals(encodedPassword)) {
            throw new AuthenticationException("Incorrect username or password!!!");
        }
        return user;
    }
}

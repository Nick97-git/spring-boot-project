package com.amazon.review.repository;

import com.amazon.review.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT u FROM User u LEFT JOIN FETCH u.roles Role where u.login = :login")
    User findByLogin(@Param("login") String login);
}

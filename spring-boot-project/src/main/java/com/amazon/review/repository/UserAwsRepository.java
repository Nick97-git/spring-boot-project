package com.amazon.review.repository;

import com.amazon.review.model.UserAws;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserAwsRepository extends JpaRepository<UserAws, Long> {

    @Query("select new UserAws(u.profileName) from UserAws u order by size(u.reviews) desc")
    List<UserAws> findAll(PageRequest pageRequest);

    UserAws findByProfileName(String profileName);
}

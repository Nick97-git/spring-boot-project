package com.amazon.review.model;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class UserAws {
    @Id
    private String id;
    @NonNull
    private String profileName;
    @OneToMany(mappedBy = "userAws")
    private Set<Review> reviews;
}

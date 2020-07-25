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
public class Product {
    @Id
    @NonNull
    private String id;
    @OneToMany(mappedBy = "product")
    private Set<Review> reviews;
}

package com.amazon.review.service;

import com.amazon.review.model.Product;
import java.util.List;

public interface ProductService extends GenericService<Product> {

    List<Product> getMostCommentedProducts(int limit, int offset);

    Product findById(String productId);
}

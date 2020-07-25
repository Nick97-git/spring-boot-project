package com.amazon.review.service.impl;

import com.amazon.review.model.Product;
import com.amazon.review.repository.ProductRepository;
import com.amazon.review.service.ProductService;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> saveAll(Set<Product> products) {
        return productRepository.saveAll(products);
    }

    @Override
    public List<Product> getMostCommentedProducts(int limit, int offset) {
        PageRequest pageRequest = PageRequest.of(offset, limit);
        return productRepository.findAll(pageRequest);
    }

    @Override
    public Product findById(String productId) {
        return productRepository.findById(productId).get();
    }
}

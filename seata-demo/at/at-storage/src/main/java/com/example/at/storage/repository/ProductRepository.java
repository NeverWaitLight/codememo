package com.example.at.storage.repository;

import com.example.at.storage.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}

package com.example.xa.storage.repository;

import com.example.xa.storage.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}

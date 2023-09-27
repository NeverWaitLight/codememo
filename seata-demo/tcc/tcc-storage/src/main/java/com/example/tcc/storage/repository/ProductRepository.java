package com.example.tcc.storage.repository;

import com.example.tcc.storage.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}

package org.waitlight.codememo.seata.at.storage.repository;

import org.waitlight.codememo.seata.at.storage.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}

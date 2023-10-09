package org.waitlight.codememo.seata.tcc.storage.repository;

import org.waitlight.codememo.seata.tcc.storage.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}

package org.waitlight.codememo.seata.xa.storage.repository;

import org.waitlight.codememo.seata.xa.storage.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}

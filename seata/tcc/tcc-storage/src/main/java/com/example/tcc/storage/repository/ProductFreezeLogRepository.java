package com.example.tcc.storage.repository;

import com.example.tcc.storage.model.ProductFreezeLog;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductFreezeLogRepository extends CrudRepository<ProductFreezeLog, Long> {
    Optional<ProductFreezeLog> findByXid(String xid);
}

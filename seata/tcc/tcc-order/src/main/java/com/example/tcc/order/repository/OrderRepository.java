package com.example.tcc.order.repository;


import com.example.tcc.order.model.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OrderRepository extends CrudRepository<Order, Long> {
    Optional<Order> findByXid(String xid);
}

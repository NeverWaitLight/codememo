package org.waitlight.codememo.seata.at.order.repository;


import org.waitlight.codememo.seata.at.order.model.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}

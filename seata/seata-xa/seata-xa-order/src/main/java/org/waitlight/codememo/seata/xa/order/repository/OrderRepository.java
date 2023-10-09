package org.waitlight.codememo.seata.xa.order.repository;


import org.waitlight.codememo.seata.xa.order.model.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}

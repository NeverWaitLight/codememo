package com.example.at.order.controller;

import com.example.at.order.model.Order;
import com.example.at.order.repository.OrderRepository;
import org.example.xa.commons.Ordering;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    public void submit(@RequestBody Ordering ordering) {
        Order order = new Order();
        order.setProductId(ordering.getProductId());
        order.setBill(ordering.getBill());
        orderRepository.save(order);
    }

    @GetMapping
    public Iterable<Order> list() {
        return orderRepository.findAll();
    }
}

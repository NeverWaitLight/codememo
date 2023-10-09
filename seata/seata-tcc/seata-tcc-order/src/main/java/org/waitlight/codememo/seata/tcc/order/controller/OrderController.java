package org.waitlight.codememo.seata.tcc.order.controller;

import org.waitlight.codememo.seata.tcc.order.action.OrderingAction;
import org.waitlight.codememo.seata.tcc.order.model.Order;
import org.waitlight.codememo.seata.tcc.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.example.xa.commons.Ordering;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderRepository orderRepository;
    private final OrderingAction orderingAction;

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    public void submit(@RequestBody Ordering ordering) {
        orderingAction.ordering(null, ordering);
    }

    @GetMapping
    public Iterable<Order> list() {
        return orderRepository.findAll();
    }
}

package org.waitlight.codememo.seata.tcc.business.controller;

import org.waitlight.codememo.seata.tcc.business.client.OrderClient;
import org.waitlight.codememo.seata.tcc.business.client.StorageClient;
import org.waitlight.codememo.seata.tcc.business.client.WalletClient;
import io.seata.spring.annotation.GlobalTransactional;
import org.example.xa.commons.Ordering;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadLocalRandom;

@RestController
public class BusinessController {

    @Autowired
    private OrderClient orderClient;
    @Autowired
    private StorageClient storageClient;
    @Autowired
    private WalletClient walletClient;


    @GlobalTransactional
    @RequestMapping(path = "/ordering", method = {RequestMethod.POST, RequestMethod.PUT})
    public void order(@RequestBody Ordering ordering) {
        walletClient.pay(ordering.getUsername(), ordering.getBill());
        storageClient.reduce(ordering.getProductId(), ordering.getAmount());
        orderClient.create(ordering);
        if (ThreadLocalRandom.current().nextInt() % 2 == 0) {
            throw new RuntimeException("老子不干了");
        }
    }

}

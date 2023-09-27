package com.example.at.wallet.controller;

import com.example.at.wallet.model.Balance;
import com.example.at.wallet.repository.BalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/balance")
public class BalanceController {
    @Autowired
    private BalanceRepository balanceRepository;

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    public void submit(@RequestBody Balance balance) {
        balanceRepository.save(balance);
    }

    @GetMapping
    public Iterable<Balance> list() {
        return balanceRepository.findAll();
    }

    @Transactional
    @PostMapping("/pay")
    public void pay(@RequestParam("username") String username, @RequestParam("amount") BigDecimal amount) {
        balanceRepository.findByUsername(username)
                .map(b -> {
                    b.setBalance(b.getBalance().subtract(amount));
                    return b;
                })
                .ifPresent(b -> balanceRepository.save(b));
    }


}

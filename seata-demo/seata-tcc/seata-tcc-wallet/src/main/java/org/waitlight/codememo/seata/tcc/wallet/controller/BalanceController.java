package org.waitlight.codememo.seata.tcc.wallet.controller;

import org.waitlight.codememo.seata.tcc.wallet.action.BalancePayAction;
import org.waitlight.codememo.seata.tcc.wallet.model.Balance;
import org.waitlight.codememo.seata.tcc.wallet.repository.BalanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/balance")
@RequiredArgsConstructor
public class BalanceController {
    private final BalanceRepository balanceRepository;
    private final BalancePayAction balancePayAction;

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    public void submit(@RequestBody Balance balance) {
        balanceRepository.save(balance);
    }

    @GetMapping
    public Iterable<Balance> list() {
        return balanceRepository.findAll();
    }

    @PostMapping("/pay")
    public void pay(@RequestParam("username") String username, @RequestParam("amount") BigDecimal amount) {
        balancePayAction.pay(null, username, amount);
    }

}

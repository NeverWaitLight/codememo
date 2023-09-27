package com.example.tcc.wallet.repository;

import com.example.tcc.wallet.model.Balance;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BalanceRepository extends CrudRepository<Balance, Long> {
    Optional<Balance> findByUsername(String username);
}

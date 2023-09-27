package org.waitlight.codememo.seata.xa.wallet.repository;

import org.waitlight.codememo.seata.xa.wallet.model.Balance;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BalanceRepository extends CrudRepository<Balance, Long> {
    Optional<Balance> findByUsername(String username);
}

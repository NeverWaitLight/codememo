package com.example.tcc.wallet.repository;

import com.example.tcc.wallet.model.WalletFreezeLog;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BalanceFreezeLogRepository extends CrudRepository<WalletFreezeLog, Long> {
    Optional<WalletFreezeLog> findByXid(String xid);
}

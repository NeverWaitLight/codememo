package org.waitlight.codememo.seata.tcc.wallet.repository;

import org.waitlight.codememo.seata.tcc.wallet.model.WalletFreezeLog;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BalanceFreezeLogRepository extends CrudRepository<WalletFreezeLog, Long> {
    Optional<WalletFreezeLog> findByXid(String xid);
}

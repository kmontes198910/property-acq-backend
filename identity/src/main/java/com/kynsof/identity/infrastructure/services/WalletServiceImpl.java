package com.kynsof.identity.infrastructure.services;

import com.kynsof.identity.domain.dto.WalletDto;
import com.kynsof.identity.domain.interfaces.service.IWalletService;
import com.kynsof.identity.infrastructure.entities.Wallet;
import com.kynsof.identity.infrastructure.repository.command.WalletWriteDataJPARepository;
import com.kynsof.identity.infrastructure.repository.query.WalletReadDataJPARepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class WalletServiceImpl implements IWalletService {


    private final WalletReadDataJPARepository repositoryQuery;
    private final WalletWriteDataJPARepository repositoryWrite;

    public WalletServiceImpl(WalletReadDataJPARepository repositoryQuery, WalletWriteDataJPARepository repositoryWrite) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryWrite = repositoryWrite;
    }

    @Override
    public WalletDto findByCustomerId(UUID customerId) {
        Optional<Wallet> wallet = repositoryQuery.findByCustomerId(customerId);
        return wallet.map(Wallet::toAggregate).orElse(null);
    }

    @Override
    public UUID create(WalletDto object) {
        Wallet value = new Wallet(object);
        var wallet = repositoryWrite.save(value);
        return wallet.getId();
    }

}

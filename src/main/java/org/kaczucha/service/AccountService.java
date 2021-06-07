package org.kaczucha.service;

import org.kaczucha.controller.dto.AccountRequest;
import org.kaczucha.controller.dto.AccountResponse;
import org.kaczucha.repository.AccountRepository;
import org.kaczucha.repository.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final AccountRepository repository;
    private final AccountMapper mapper;

    @Autowired
    public AccountService(AccountRepository repository, AccountMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public AccountResponse findById(Long id) {
        final Account accountById = repository.findAccountById(id);
        return mapper.map(accountById);
    }

    public void save(AccountRequest accountRequest) {
        final Account account = mapper.map(accountRequest);
        repository.save(account);
    }
}

package org.kaczucha.service;

import lombok.RequiredArgsConstructor;
import org.kaczucha.controller.dto.AccountRequest;
import org.kaczucha.controller.dto.AccountResponse;
import org.kaczucha.repository.AccountRepository;
import org.kaczucha.repository.entity.Account;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository repository;

    public AccountResponse findById(final Long id) {
        return repository
                .findById(id)
                .map(account ->
                        AccountResponse.builder()
                                .balance(account.getBalance())
                                .currency(account.getCurrency())
                                .id(account.getId())
                                .userId(account.getUserId())
                                .build())
                .orElseThrow(() -> new IllegalArgumentException("Account with " + id + " not found"));
    }

    public void save(final AccountRequest accountRequest) {
        repository.save(
                Account.builder()
                        .balance(accountRequest.getBalance())
                        .userId(accountRequest.getUserId())
                        .currency(accountRequest.getCurrency())
                        .build()
        );
    }
}

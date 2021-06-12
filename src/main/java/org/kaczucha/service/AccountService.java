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

        public void transfer(
            long fromAccountId,
            long toAccountId,
            double amount
    ) {
        validateAmount(amount);
        if (fromAccountId == toAccountId) {
            throw new IllegalArgumentException("fromEmail and toEmail cannot be equal!");
        }
        Account fromAccount = repository.getOne(fromAccountId);
        Account toAccount = repository.getOne(toAccountId);
        if (fromAccount.getBalance() - amount >= 0) {
            fromAccount.setBalance(fromAccount.getBalance() - amount);
            toAccount.setBalance(toAccount.getBalance() + amount);
        } else {
            throw new NoSufficientFundsException("Not enough funds!");
        }
        repository.save(fromAccount);
        repository.save(toAccount);
    }
        private void validateAmount(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive!");
        }
    }
}

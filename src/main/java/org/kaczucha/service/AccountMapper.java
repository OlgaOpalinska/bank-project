package org.kaczucha.service;

import org.kaczucha.controller.dto.AccountRequest;
import org.kaczucha.controller.dto.AccountResponse;
import org.kaczucha.repository.entity.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    public AccountResponse map(Account account) {
        return AccountResponse.builder()
                .id(account.getId())
                .balance(account.getBalance())
                .currency(account.getCurrency())
                .userId(account.getUserId())
                .build();
    }
    public Account map(AccountRequest request) {
        return Account.builder()
                .balance(request.getBalance())
                .currency(request.getCurrency())
                .userId(request.getUserId())
                .build();
    }
}

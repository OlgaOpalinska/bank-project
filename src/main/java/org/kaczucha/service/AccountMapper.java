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
                .build();
    }
    public Account map(AccountRequest accountRequest) {
        return Account.builder()
                .balance(accountRequest.getBalance())
                .currency(accountRequest.getCurrency())
                .build();
    }
}

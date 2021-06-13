package org.kaczucha.service;

import lombok.RequiredArgsConstructor;
import org.kaczucha.controller.dto.TransactionRequest;
import org.kaczucha.repository.TransactionRepository;
import org.kaczucha.repository.entity.TransactionEntity;
import org.springframework.stereotype.Service;
import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository repository;
    private final AccountService accountService;

    public void createTransaction(TransactionRequest request) {
        accountService.transfer(
                request.getFromAccountId(),
                request.getToAccountId(),
                request.getCurrency(),
                request.getAmount());

        repository.save(
                TransactionEntity.builder()
                        .amount(request.getAmount())
                        .currency(request.getCurrency())
                        .fromAccountId(request.getFromAccountId())
                        .toAccountId(request.getToAccountId())
                        .transactionDate(OffsetDateTime.now())
                        .build()
        );
    }
}

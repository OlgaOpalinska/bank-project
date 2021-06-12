package org.kaczucha.controller;

import lombok.RequiredArgsConstructor;
import org.kaczucha.controller.dto.TransactionRequest;
import org.kaczucha.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService service;

    @PostMapping("/api/transaction")
    @ResponseStatus(HttpStatus.CREATED)
    public void createClient(@RequestBody TransactionRequest request) {
        service.createTransaction(request);
    }
}

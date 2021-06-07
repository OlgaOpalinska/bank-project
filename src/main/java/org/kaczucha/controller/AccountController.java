package org.kaczucha.controller;

import lombok.RequiredArgsConstructor;
import org.kaczucha.controller.dto.AccountRequest;
import org.kaczucha.controller.dto.AccountResponse;
import org.kaczucha.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AccountController {
    private final AccountService service;

    @GetMapping("/api/account")
    public ResponseEntity<AccountResponse> findById(@RequestParam Long id) {
        final AccountResponse accountResponse = service.findById(id);
        return new ResponseEntity<>(accountResponse, HttpStatus.ACCEPTED);
    }

    @PostMapping("/api/account")
    @ResponseStatus(HttpStatus.CREATED)
    public void createAccount (@RequestBody AccountRequest accountRequest) {
        service.save(accountRequest);
    }
}

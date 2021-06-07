package org.kaczucha.controller;

import lombok.RequiredArgsConstructor;
import org.kaczucha.repository.entity.Account;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController {

    public ResponseEntity<Account> findById(Long id) {
        return null;
    }
    public void createAccount (@RequestBody Account account) {

    }
}

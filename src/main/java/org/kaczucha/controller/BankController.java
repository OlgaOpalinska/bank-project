package org.kaczucha.controller;

import lombok.RequiredArgsConstructor;
import org.kaczucha.repository.entity.Client;
import org.kaczucha.service.BankService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BankController {
    private final BankService service;

    @GetMapping("/api/user")
    public Client findByEmail(@RequestParam String email) {
        return service.findByEmail(email);
    }
}

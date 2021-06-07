package org.kaczucha.service;

import org.kaczucha.controller.dto.ClientResponse;
import org.kaczucha.repository.ClientSpringJpaRepository;
import org.kaczucha.repository.entity.Account;
import org.kaczucha.repository.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class BankService {
    private final ClientSpringJpaRepository clientRepository;

    @Autowired
    public BankService (ClientSpringJpaRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void save(Client client){
        clientRepository.save(client);
    }

    public Client findByEmail(String email){
        return clientRepository.findByEmail(email);
    }
    public ClientResponse findResponseByEmail(String email) {
        Client client = findByEmail(email);
        final ClientResponse response = ClientResponse.builder()
                .id(client.getId())
                .name(client.getName())
                .email(client.getEmail())
                .accounts(client.getAccounts().stream()
                                .map(Account::getId)
                                .collect(Collectors.toList()))
                .build();
        return response;
    }

    public void transfer(
            String fromEmail,
            String toEmail,
            double amount
    ) {
        validateAmount(amount);
        if (fromEmail.equals(toEmail)) {
            throw new IllegalArgumentException("fromEmail and toEmail cannot be equal!");
        }
        Client fromClient = clientRepository.findByEmail(fromEmail);
        Client toClient = clientRepository.findByEmail(toEmail);
        if (fromClient.getBalance() - amount >= 0) {
            fromClient.setBalance(fromClient.getBalance() - amount);
            toClient.setBalance(toClient.getBalance() + amount);
        } else {
            throw new NoSufficientFundsException("Not enough funds!");
        }
        clientRepository.save(fromClient);
        clientRepository.save(toClient);
    }

    public void withdraw(
            final String email,
            final double amount
    ) {
        validateAmount(amount);
        if (Objects.isNull(email)) {
            throw new IllegalArgumentException("Email can't be null!");
        }
        final String lowerCaseEmail = email.toLowerCase();
        final Client client = clientRepository.findByEmail(lowerCaseEmail);
        if (amount > client.getBalance()) {
            throw new NoSufficientFundsException("Amount cannot be greater than balance!");
        }
        final double newBalance = client.getBalance() - amount;
        client.setBalance(newBalance);
        clientRepository.save(client);
    }

    private void validateAmount(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive!");
        }
    }
}

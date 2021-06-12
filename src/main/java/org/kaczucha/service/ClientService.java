package org.kaczucha.service;

import org.kaczucha.controller.dto.ClientRequest;
import org.kaczucha.controller.dto.ClientResponse;
import org.kaczucha.repository.ClientRepository;
import org.kaczucha.repository.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper mapper;

    @Autowired
    public ClientService(ClientRepository clientRepository, ClientMapper mapper) {
        this.clientRepository = clientRepository;
        this.mapper = mapper;
    }

    public void save(ClientRequest clientRequest){
        Client client = mapper.map(clientRequest);
        clientRepository.save(client);
    }

    public Client findByEmail(String email){
        return clientRepository.findByEmail(email);
    }
    public ClientResponse findResponseByEmail(String email) {
        Client client = findByEmail(email);
        return mapper.map(client);
    }
//    public void transfer(
//            String fromEmail,
//            String toEmail,
//            double amount
//    ) {
//        validateAmount(amount);
//        if (fromEmail.equals(toEmail)) {
//            throw new IllegalArgumentException("fromEmail and toEmail cannot be equal!");
//        }
//        Client fromClient = clientRepository.findByEmail(fromEmail);
//        Client toClient = clientRepository.findByEmail(toEmail);
//        if (fromClient.getBalance() - amount >= 0) {
//            fromClient.setBalance(fromClient.getBalance() - amount);
//            toClient.setBalance(toClient.getBalance() + amount);
//        } else {
//            throw new NoSufficientFundsException("Not enough funds!");
//        }
//        clientRepository.save(fromClient);
//        clientRepository.save(toClient);
//    }
//
//    public void withdraw(
//            final String email,
//            final double amount
//    ) {
//        validateAmount(amount);
//        if (Objects.isNull(email)) {
//            throw new IllegalArgumentException("Email can't be null!");
//        }
//        final String lowerCaseEmail = email.toLowerCase();
//        final Client client = clientRepository.findByEmail(lowerCaseEmail);
//        if (amount > client.getBalance()) {
//            throw new NoSufficientFundsException("Amount cannot be greater than balance!");
//        }
//        final double newBalance = client.getBalance() - amount;
//        client.setBalance(newBalance);
//        clientRepository.save(client);
//    }
//
//    private void validateAmount(double amount) {
//        if (amount <= 0) {
//            throw new IllegalArgumentException("Amount must be positive!");
//        }
//    }
}

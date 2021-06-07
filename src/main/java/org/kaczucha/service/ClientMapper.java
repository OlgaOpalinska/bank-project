package org.kaczucha.service;

import org.kaczucha.controller.dto.ClientRequest;
import org.kaczucha.controller.dto.ClientResponse;
import org.kaczucha.repository.entity.Account;
import org.kaczucha.repository.entity.Client;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ClientMapper {

    public ClientResponse map(Client client) {
        return ClientResponse.builder()
                .id(client.getId())
                .name(client.getName())
                .email(client.getEmail())
                .accounts(client.getAccounts()
                        .stream()
                        .map(Account::getId)
                        .collect(Collectors.toList()))
                .build();
    }
    public Client map(ClientRequest clientRequest) {
        return Client.builder()
                .name(clientRequest.getName())
                .email(clientRequest.getEmail())
                .build();
    }
}

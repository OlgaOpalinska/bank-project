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
}

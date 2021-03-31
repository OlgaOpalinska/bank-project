//package org.kaczucha.repository;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.kaczucha.repository.entity.Client;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class InMemoryClientRepositoryTest {
//    private InMemoryClientRepository repository;
//    private List<Client> clients;
//
//    @BeforeEach
//    public void setup() {
//        clients = new ArrayList<>();
//        repository = new InMemoryClientRepository(clients);
//    }
//    @Test
//    public void verifyIfUserIsAddingCorrectlyToRepository() {
//        //given
//        final Client client = new Client("Alek", "alek@wp.pl", 100);
//        final Client expectedClient = new Client("Alek", "alek@wp.pl", 100);
//        //when
//        repository.save(client);
//        //then
//        final Client actualClient = clients.get(0);
//        assertEquals(expectedClient, actualClient);
//    }
//}

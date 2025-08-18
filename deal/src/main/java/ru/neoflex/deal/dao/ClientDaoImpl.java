package ru.neoflex.deal.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.neoflex.deal.model.Client;
import ru.neoflex.deal.repository.ClientRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Slf4j
public class ClientDaoImpl implements DAO<Client, UUID> {
    private final ClientRepository clientRepository;

    @Autowired
    public ClientDaoImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Client> getAll() {
        log.trace("Starting the select for all clients in the database");
        List<Client> clients = clientRepository.findAll();
        log.debug("The data search was successful, records were found [{}]", clients.size());
        return clients;
    }

    @Override
    public Client findById(UUID uuid) {
        log.trace("Starting the select client by id = {} in the database", uuid);
        Client client = clientRepository.findById(uuid).orElseThrow(() -> {
            log.error("client data with uuid = {} not found", uuid);
            return new NoSuchElementException("No value present");
        });
        log.debug("the client's data is found. {}", client);
        return client;
    }

    @Override
    public void savaAndFlush(Client client) {
        log.trace("The beginning of adding client data {} to the database", client);
        clientRepository.saveAndFlush(client);
        log.debug("client data {} updated", client);
    }

    @Override
    public void delete(Client client) {
        log.trace("The beginning of deleting client data {} to the database", client);
        clientRepository.delete(client);
        log.debug("client data {} deleted", client);
    }
}

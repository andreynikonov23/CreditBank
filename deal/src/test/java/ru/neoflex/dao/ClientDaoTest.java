package ru.neoflex.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import ru.neoflex.model.Client;
import ru.neoflex.utils.TestData;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("/application-test.properties")
@Sql(value = {"/sql/init_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class ClientDaoTest {
    @Autowired
    private DAO<Client, UUID> clientDAO;

    @Test
    public void getAllTest() {
        List<Client> clients = clientDAO.getAll();
        assertEquals(4, clients.size());
    }

    @Test
    public void findByIdTest() {
        Client client = clientDAO.findById(UUID.fromString("e3d917fb-f851-4cad-ba07-6562c65a59cb"));
        assertEquals(UUID.fromString("e3d917fb-f851-4cad-ba07-6562c65a59cb"), client.getId());
        assertEquals(UUID.fromString("eb7ae8bf-aaf1-4e8b-ac69-88fd07efbe6e"), client.getPassportDto().getPassportUUID());
    }

    @Test
    public void findByIncorrectId() {
        assertThrows(NoSuchElementException.class, () ->  clientDAO.findById(UUID.randomUUID()));
    }

    @Test
    public void saveTest() {
        Client client = TestData.getValidClient();
        clientDAO.savaAndFlush(client);
        assertNotNull(client.getId());
        assertNotNull(clientDAO.findById(client.getId()));
    }
}

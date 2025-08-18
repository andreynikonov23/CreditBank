package ru.neoflex.deal.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import ru.neoflex.deal.model.Client;
import ru.neoflex.deal.model.Credit;
import ru.neoflex.deal.model.Statement;
import ru.neoflex.deal.utils.TestData;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource("/application-test.properties")
@Sql(value = "/sql/init_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class StatementDaoImplTest {
    @Autowired
    private DAO<Statement, UUID> statementDAO;

    @Test
    public void getAllTest() {
        List<Statement> clients = statementDAO.getAll();
        assertEquals(1, clients.size());
    }

    @Test
    public void findByIdTest() {
        Statement statement = statementDAO.findById(UUID.fromString("47936ce8-e8c6-496e-a33a-f8f1d4c26c74"));
        assertEquals(UUID.fromString("47936ce8-e8c6-496e-a33a-f8f1d4c26c74"), statement.getId());
        assertEquals(UUID.fromString("e3d917fb-f851-4cad-ba07-6562c65a59cb"), statement.getClient().getId());
        assertEquals(UUID.fromString("d917bf37-8786-4e14-9eac-0ef216425f2d"), statement.getCredit().getId());
    }

    @Test
    public void findByIncorrectId() {
        assertThrows(NoSuchElementException.class, () ->  statementDAO.findById(UUID.randomUUID()));
    }
}

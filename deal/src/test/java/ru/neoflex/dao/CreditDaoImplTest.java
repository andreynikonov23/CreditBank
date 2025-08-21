package ru.neoflex.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import ru.neoflex.model.Credit;
import ru.neoflex.utils.TestData;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource("/application-test.properties")
@Sql(value = {"/sql/init_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class CreditDaoImplTest {
    @Autowired
    private DAO<Credit, UUID> creditDAO;

    @Test
    public void getAllTest() {
        List<Credit> clients = creditDAO.getAll();
        assertEquals(1, clients.size());
    }

    @Test
    public void findByIdTest() {
        Credit credit = creditDAO.findById(UUID.fromString("d917bf37-8786-4e14-9eac-0ef216425f2d"));
        assertEquals(UUID.fromString("d917bf37-8786-4e14-9eac-0ef216425f2d"), credit.getId());
        assertEquals(120, credit.getPaymentSchedule().size());
    }

    @Test
    public void findByIncorrectId() {
        assertThrows(NoSuchElementException.class, () ->  creditDAO.findById(UUID.randomUUID()));
    }

    @Test
    public void saveTest() {
        Credit credit = TestData.getCredit();
        creditDAO.savaAndFlush(credit);
        assertNotNull(credit.getId());
        assertNotNull(creditDAO.findById(credit.getId()));
    }
}

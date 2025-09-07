package ru.neoflex.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.neoflex.model.Statement;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource("/application-test.properties")
@Sql(value = {"/sql/init_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@AutoConfigureMockMvc
public class AdminControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAllStatementsTest() throws Exception {
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.get("/deal/admin/statement")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseBody = result.getResponse().getContentAsString();
        List<Statement> statements = objectMapper.readValue(responseBody, objectMapper
                .getTypeFactory()
                .constructCollectionType(List.class, Statement.class));
        assertEquals(6, statements.size());
    }

    @Test
    public void getStatementTest() throws Exception {
        String statementId = "b084413b-9cbc-4791-9732-f827be4a827a";
        MvcResult requestResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/deal/admin/statement/" + statementId)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseBody = requestResult.getResponse().getContentAsString();
        Statement result = objectMapper.readValue(responseBody, Statement.class);

        assertEquals(statementId, result.getId().toString());
        assertEquals("1b18792f-c656-4f03-8823-4187e47ecc09", result.getClient().getId().toString());
        assertEquals("277f0cd9-1f2a-47b6-997f-6e6e2bb19f07", result.getCredit().getId().toString());
    }

    @Test
    public void getNonExistedStatementTest() throws Exception {
        String statementId = UUID.randomUUID().toString();
        MvcResult requestResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/deal/admin/statement/" + statementId)
                )
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
        String responseBody = requestResult.getResponse().getContentAsString();

        assertEquals("no data was found in the database: statement data with uuid = " + statementId + " not found", responseBody);
    }
}

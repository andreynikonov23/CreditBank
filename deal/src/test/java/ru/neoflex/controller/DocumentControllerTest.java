package ru.neoflex.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.neoflex.exceptions.SignDocumentException;
import ru.neoflex.service.DocumentService;

import java.util.UUID;

@SpringBootTest
@TestPropertySource("/application-test.properties")
@Sql(value = {"/sql/init_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@AutoConfigureMockMvc
public class DocumentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DocumentService documentService;

    @Test
    public void availabilityEndpoints() throws Exception {
        String statementId = UUID.randomUUID().toString();
        String sesCode = UUID.randomUUID().toString();

        mockMvc.perform(
                MockMvcRequestBuilders.post("/deal/document/" + statementId + "/send")
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Mockito.verify(documentService).sendDocument(statementId);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/deal/document/" + statementId + "/sign")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Mockito.verify(documentService).registerSigning(statementId);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/deal/document/" + statementId + "/" + sesCode)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Mockito.verify(documentService).signDocument(statementId, sesCode);
    }


}

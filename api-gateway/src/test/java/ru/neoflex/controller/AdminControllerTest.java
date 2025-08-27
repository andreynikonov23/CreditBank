package ru.neoflex.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.neoflex.client.DealClientService;
import ru.neoflex.utils.TestData;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
public class AdminControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private DealClientService dealClientService;

    @Test
    public void getAllStatementsTest() throws Exception {
        Mockito.when(dealClientService.getAllStatements()).thenReturn(TestData.getStatementListJson());
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/gateway/deal/admin/statement")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseBody = result.getResponse().getContentAsString();
        assertEquals(TestData.getStatementListJson(), responseBody);
    }

    @Test
    public void getStatementTest() throws Exception {
        String statementId = UUID.randomUUID().toString();
        Mockito.when(dealClientService.findStatementById(statementId)).thenReturn(TestData.getStatementJson());
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/gateway/deal/admin/statement/" + statementId)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseBody = result.getResponse().getContentAsString();
        assertEquals(TestData.getStatementJson(), responseBody);
    }
}

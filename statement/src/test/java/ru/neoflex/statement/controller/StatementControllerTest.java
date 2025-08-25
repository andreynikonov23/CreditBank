package ru.neoflex.statement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.neoflex.statement.client.DealApiClient;
import ru.neoflex.statement.dto.LoanOfferDto;
import ru.neoflex.statement.dto.LoanStatementRequestDto;
import ru.neoflex.statement.utils.TestData;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class StatementControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private DealApiClient dealApiClient;

    @Test
    public void calcLoanOffersTest() throws Exception {
        LoanStatementRequestDto loanStatementRequestDto = TestData.getValidLoanStatementRequestDto();

        Mockito.when(dealApiClient.statement(loanStatementRequestDto)).thenReturn(TestData.getTestLoanOffers());

        String jsonRequestBody = objectMapper.writeValueAsString(loanStatementRequestDto);
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.post("/statement")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequestBody)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String jsonResponseBody = result.getResponse().getContentAsString();
        List<LoanOfferDto> loanOffers = objectMapper.readValue(jsonResponseBody, objectMapper
                .getTypeFactory()
                .constructCollectionType(List.class, LoanOfferDto.class));

        assertEquals(4, loanOffers.size());
    }
}

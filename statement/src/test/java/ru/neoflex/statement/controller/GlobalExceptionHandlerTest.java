package ru.neoflex.statement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.HttpClientErrorException;
import ru.neoflex.statement.client.DealApiClient;
import ru.neoflex.statement.dto.LoanOfferDto;
import ru.neoflex.statement.dto.LoanStatementRequestDto;
import ru.neoflex.statement.exceptions.MicroserviceException;
import ru.neoflex.statement.exceptions.MicroserviceName;
import ru.neoflex.statement.exceptions.StatementStatusException;
import ru.neoflex.statement.utils.TestData;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
public class GlobalExceptionHandlerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private DealApiClient dealApiClient;


    @Test
    public void handleValidationExceptionsTest() throws Exception {
        List<String> expectedErrors = new ArrayList<>(List.of("invalid email","invalid passport series","invalid passport series","age must be over 18 years old.","lastname is empty"));
        LoanStatementRequestDto loanStatementRequestDto = TestData.getInvalidLoanStatementRequestDto();

        String jsonRequestBody = objectMapper.writeValueAsString(loanStatementRequestDto);
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.post("/statement")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequestBody)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        String jsonResponseBody = result.getResponse().getContentAsString();
        List<String> actualErrors = objectMapper.readValue(jsonResponseBody, objectMapper
                .getTypeFactory()
                .constructCollectionType(List.class, String.class)
        );

        assertEquals(expectedErrors.size(), actualErrors.size());
        expectedErrors.forEach(error -> assertTrue(actualErrors.contains(error)));
    }

    @Test
    public void handleMicroserviceExceptionsTest() throws Exception {
        LoanStatementRequestDto loanStatementRequestDto = TestData.getValidLoanStatementRequestDto();

        Mockito.when(dealApiClient.statement(loanStatementRequestDto)).thenThrow(new MicroserviceException(MicroserviceName.DEAL, HttpStatus.GATEWAY_TIMEOUT));

        String jsonRequestBody = objectMapper.writeValueAsString(loanStatementRequestDto);
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.post("/statement")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequestBody)
                )
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andReturn();

        String errorMessage = result.getResponse().getContentAsString();
        assertEquals("Unfortunately, this feature is currently unavailable.", errorMessage);
    }

    @Test
    public void handleNoSuchElementTest() throws Exception {
        UUID statementId = UUID.randomUUID();
        LoanOfferDto loanOfferDto = TestData.getTestLoanOffers().get(0);
        loanOfferDto.setStatementId(statementId);

        Mockito.doThrow(new NoSuchElementException("statement data with uuid = " + statementId + " not found")).when(dealApiClient).select(loanOfferDto);

        String jsonRequestBody = objectMapper.writeValueAsString(loanOfferDto);
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.post("/statement/offer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequestBody)
                )
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
        String errorMessage = result.getResponse().getContentAsString();
        assertEquals("no data was found in the database: statement data with uuid = " + statementId + " not found", errorMessage);
    }

    @Test
    public void handleStatementStatusExceptionTest() throws Exception {
        String statementId = UUID.randomUUID().toString();
        Mockito.doThrow(new StatementStatusException(String.format("the credit has already been issued for the statement {%s}", statementId))).when(dealApiClient).clientDenied(statementId);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/statement/offer")
                                .param("denied-statement", statementId)
                )
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();

        assertEquals("the statement status exception: the credit has already been issued for the statement {" + statementId + "}", body);
    }
}

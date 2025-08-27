package ru.neoflex.controller;

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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.HttpClientErrorException;
import ru.neoflex.client.CalculatorApiClient;
import ru.neoflex.dto.LoanOfferDto;
import ru.neoflex.dto.LoanStatementRequestDto;
import ru.neoflex.exceptions.SignDocumentException;
import ru.neoflex.service.DocumentService;
import ru.neoflex.utils.TestData;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestPropertySource("/application-test.properties")
@Sql(value = {"/sql/init_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@AutoConfigureMockMvc
public class GlobalExceptionHandlerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CalculatorApiClient calculatorApiClient;
    @MockBean
    private DocumentService documentService;

    @Test
    public void handleValidationExceptionsTest() throws Exception {
        List<String> expectedErrors = new ArrayList<>(List.of("invalid email","invalid passport series","invalid passport series","age must be over 18 years old.","lastname is empty"));
        LoanStatementRequestDto loanStatementRequestDto = TestData.getInvalidLoanStatementRequestDto();

        String jsonRequestBody = objectMapper.writeValueAsString(loanStatementRequestDto);
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.post("/deal/statement")
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
    public void handleHttpClientExceptionsTest() throws Exception {
        LoanStatementRequestDto loanStatementRequestDto = TestData.getValidLoanStatementRequestDto();

        Mockito.when(calculatorApiClient.offers(loanStatementRequestDto)).thenThrow(new HttpClientErrorException(HttpStatus.GATEWAY_TIMEOUT));

        String jsonRequestBody = objectMapper.writeValueAsString(loanStatementRequestDto);
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.post("/deal/statement")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequestBody)
                )
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andReturn();

        String errorMessage = result.getResponse().getContentAsString();
        assertEquals("Unfortunately, the loan calculation service is currently unavailable", errorMessage);
    }

    @Test
    public void handleNoSuchElementTest() throws Exception {
        UUID statementId = UUID.randomUUID();
        LoanOfferDto loanOfferDto = TestData.getTestLoanOffers().get(0);
        loanOfferDto.setStatementId(statementId);

        String jsonRequestBody = objectMapper.writeValueAsString(loanOfferDto);
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.post("/deal/select")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequestBody)
                )
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
        String errorMessage = result.getResponse().getContentAsString();
        assertEquals("no data was found in the database: statement data with uuid = " + statementId.toString() + " not found", errorMessage);
    }

    @Test
    public void handleSignDocumentExceptionTest() throws Exception {
        String statementId = UUID.randomUUID().toString();
        String sesCode = UUID.randomUUID().toString();

        Mockito.doThrow(new SignDocumentException("")).when(documentService).signDocument(statementId, sesCode);
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/deal/document/" + statementId + "/" + sesCode)
                )
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andReturn();
    }
}

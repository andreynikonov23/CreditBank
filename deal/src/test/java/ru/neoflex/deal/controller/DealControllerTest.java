package ru.neoflex.deal.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.HttpClientErrorException;
import ru.neoflex.deal.client.CalculatorApiClient;
import ru.neoflex.deal.dao.DAO;
import ru.neoflex.deal.exceptions.ScoringException;
import ru.neoflex.deal.model.Client;
import ru.neoflex.deal.model.Statement;
import ru.neoflex.deal.model.dto.FinishRegistrationRequestDto;
import ru.neoflex.deal.model.dto.LoanOfferDto;
import ru.neoflex.deal.model.dto.LoanStatementRequestDto;
import ru.neoflex.deal.model.dto.ScoringDataDto;
import ru.neoflex.deal.service.DealService;
import ru.neoflex.deal.utils.TestData;

import javax.swing.plaf.nimbus.State;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("/application-test.properties")
@Sql(value = {"/sql/init_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@AutoConfigureMockMvc
public class DealControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @SpyBean
    private DealService dealService;
    @MockBean
    private CalculatorApiClient calculatorApiClient;

    @Test
    public void calcLoanTermsTest() throws Exception {
        LoanStatementRequestDto loanStatementRequestDto = TestData.getValidLoanStatementRequestDto();

        Mockito.when(dealService.calcLoanTerms(loanStatementRequestDto)).thenReturn(TestData.getTestLoanOffers());

        String jsonRequestBody = objectMapper.writeValueAsString(loanStatementRequestDto);
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/deal/statement")
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

    @Test
    public void selectLoanOfferTest() throws Exception {
        LoanOfferDto loanOfferDto = TestData.getTestLoanOffers().get(0);
        loanOfferDto.setStatementId(UUID.randomUUID());

        Mockito.doNothing().when(dealService).selectLoanOffer(loanOfferDto);

        String jsonRequestBody = objectMapper.writeValueAsString(loanOfferDto);
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/deal/select")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequestBody)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Mockito.verify(dealService).selectLoanOffer(loanOfferDto);
    }

    @Test
    public void calculateTest() throws Exception {
        String testStatementId = UUID.randomUUID().toString();
        FinishRegistrationRequestDto finishRegistrationRequestDto = TestData.getFinishRegistrationRequestDto();

        Mockito.doNothing().when(dealService).calculate(testStatementId, finishRegistrationRequestDto);

        String jsonRequestBody = objectMapper.writeValueAsString(finishRegistrationRequestDto);
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/deal/calculate")
                                .param("statementId", testStatementId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequestBody)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Mockito.verify(dealService).calculate(testStatementId, finishRegistrationRequestDto);
    }

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

}

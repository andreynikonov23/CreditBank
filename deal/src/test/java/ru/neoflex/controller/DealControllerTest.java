package ru.neoflex.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import ru.neoflex.client.CalculatorApiClient;
import ru.neoflex.dto.FinishRegistrationRequestDto;
import ru.neoflex.dto.LoanOfferDto;
import ru.neoflex.dto.LoanStatementRequestDto;
import ru.neoflex.service.DealService;
import ru.neoflex.utils.TestData;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("/application-test.properties")
@Sql(value = {"/sql/init_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@AutoConfigureMockMvc
public class DealControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private DealService dealService;
    @MockBean
    private CalculatorApiClient calculatorApiClient;

    @Transactional
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
}

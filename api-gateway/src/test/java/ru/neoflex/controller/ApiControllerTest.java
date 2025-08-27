package ru.neoflex.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.neoflex.client.DealClientService;
import ru.neoflex.client.StatementClientService;
import ru.neoflex.dto.FinishRegistrationRequestDto;
import ru.neoflex.dto.LoanOfferDto;
import ru.neoflex.dto.LoanStatementRequestDto;
import ru.neoflex.utils.TestData;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
public class ApiControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private StatementClientService statementClientService;
    @MockitoBean
    private DealClientService dealClientService;

    @Test
    public void calcLoanOffersTest() throws Exception {
        LoanStatementRequestDto loanStatementRequestDto = TestData.getValidLoanStatementRequestDto();

        Mockito.when(statementClientService.calcLoanOffers(loanStatementRequestDto)).thenReturn(TestData.getTestLoanOffers());

        String jsonRequestBody = objectMapper.writeValueAsString(loanStatementRequestDto);
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/gateway/statement")
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
        LoanOfferDto loanOfferDto = TestData.getTestLoanOffers().get(1);
        loanOfferDto.setStatementId(UUID.randomUUID());

        String jsonRequestBody = objectMapper.writeValueAsString(loanOfferDto);
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/gateway/statement/offer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequestBody)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    public void calculateTest() throws Exception {
        String testStatementId = UUID.randomUUID().toString();
        FinishRegistrationRequestDto finishRegistrationRequestDto = TestData.getFinishRegistrationRequestDto();

        String jsonRequestBody = objectMapper.writeValueAsString(finishRegistrationRequestDto);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/gateway/deal/calculate")
                                .param("statementId", testStatementId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequestBody)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Mockito.verify(dealClientService).calculate(testStatementId, finishRegistrationRequestDto);
    }

    @Test
    public void requestSendDocumentsTest() throws Exception {
        String statementId = UUID.randomUUID().toString();

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/gateway/deal/document/" + statementId + "/send")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Mockito.verify(dealClientService).requestSendDocuments(statementId);
    }

    @Test
    public void requestSignDocuments() throws Exception {
        String statementId = UUID.randomUUID().toString();

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/gateway/deal/document/" + statementId + "/sign")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Mockito.verify(dealClientService).requestSignDocuments(statementId);
    }

    @Test
    public void signDocument() throws Exception {
        String statementId = UUID.randomUUID().toString();
        String sesCode = UUID.randomUUID().toString();

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/gateway/deal/document/" + statementId + "/" + sesCode)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Mockito.verify(dealClientService).signDocument(statementId, sesCode);
    }
}

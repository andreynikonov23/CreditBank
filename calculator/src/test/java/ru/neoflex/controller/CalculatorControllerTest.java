package ru.neoflex.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.neoflex.dto.*;
import ru.neoflex.utils.TestData;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CalculatorControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void offersTest() throws Exception {
        LoanStatementRequestDto loanStatementRequestDto = TestData.getValidLoanStatementRequestDto();

        String jsonRequestBody = objectMapper.writeValueAsString(loanStatementRequestDto);
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.post("/calculator/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String jsonResponseBody = result.getResponse().getContentAsString();
        List<LoanOfferDto> loanOfferDtoList = objectMapper.readValue(jsonResponseBody, objectMapper
                .getTypeFactory()
                .constructCollectionType(List.class, LoanOfferDto.class)
        );
        assertEquals(4, loanOfferDtoList.size());
    }

    @Test
    public void offersWithNotValidObjectTest() throws Exception {
        List<String> expectedErrors = new ArrayList<>(List.of("invalid email","invalid passport series","invalid passport series","age must be over 18 years old.","lastname is empty"));
        LoanStatementRequestDto loanStatementRequestDto = TestData.getInvalidLoanStatementRequestDto();

        String jsonRequestBody = objectMapper.writeValueAsString(loanStatementRequestDto);
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.post("/calculator/offers")
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
    public void calcTest() throws Exception {
        ScoringDataDto scoringDataDto = TestData.getValidScoringDataDto();

        String jsonRequestBody = objectMapper.writeValueAsString(scoringDataDto);
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.post("/calculator/calc")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String jsonResponseBody = result.getResponse().getContentAsString();
        assertEquals(CreditDto.class, objectMapper.readValue(jsonResponseBody, CreditDto.class).getClass());
    }

    @Test
    public void calcWithNotValidObjectTest() throws Exception {
        List<String> expectedErrors = new ArrayList<>(List.of("term must be greater than 0", "invalid passport number", "firstname is empty", "invalid passport issue date", "age must be over 18 years old.", "invalid passport series"));
        ScoringDataDto scoringDataDto = TestData.getInvalidScoringDataDto();

        String jsonRequestBody = objectMapper.writeValueAsString(scoringDataDto);
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.post("/calculator/calc")
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
    public void handleScoringExceptionTest() throws Exception {
        ScoringDataDto scoringDataDto = TestData.getValidScoringDataDto();
        scoringDataDto.getEmployment().setEmploymentStatus(EmploymentStatus.UNEMPLOYED);

        String jsonRequestBody = objectMapper.writeValueAsString(scoringDataDto);
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/calculator/calc")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody)
        )
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andReturn();

        String body = result.getResponse().getContentAsString();

        assertEquals("the request failed scoring: the client is unemployed", body);
    }
}

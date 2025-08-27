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
import ru.neoflex.dto.EmploymentStatus;
import ru.neoflex.dto.ScoringDataDto;
import ru.neoflex.utils.TestData;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class GlobalExceptionHandlerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

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

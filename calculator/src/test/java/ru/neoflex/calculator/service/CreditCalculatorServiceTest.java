package ru.neoflex.calculator.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.neoflex.calculator.dto.*;
import ru.neoflex.calculator.exceptions.ScoringException;
import ru.neoflex.calculator.utils.TestData;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CreditCalculatorServiceTest {
    private final CreditCalculatorService creditCalculatorService;

    @Autowired
    public CreditCalculatorServiceTest(CreditCalculatorService creditCalculatorService) {
        this.creditCalculatorService = creditCalculatorService;
    }

    @Test
    public void calculateLoanTermsTest() {
        LoanStatementRequestDto loanStatementRequestDto = TestData.getValidLoanStatementRequestDto();

        List<LoanOfferDto> result = creditCalculatorService.calculateLoanTerms(loanStatementRequestDto);

        assertEquals(4, result.size());
        for (int i = 0; i < result.size() - 1; i++) {
            assertTrue(result.get(i).getRate().compareTo(result.get(i + 1).getRate()) <= 0);
        }
    }

    @Test
    public void calcTest() throws ScoringException {
        ScoringDataDto scoringDataDto = TestData.getValidScoringDataDto();

        CreditDto credit = creditCalculatorService.calc(scoringDataDto);

        assertEquals(new BigDecimal("34639.8809523810"), credit.getMonthlyPayment());
        assertEquals(new BigDecimal("2909750.00000000260"), credit.getPsk());
        assertEquals(84, credit.getPaymentSchedule().size());

        BigDecimal actual = credit.getPaymentSchedule().get(credit.getPaymentSchedule().size()-1).getRemainingDebt().setScale(2, RoundingMode.HALF_UP);
        BigDecimal expected = new BigDecimal("0.0").setScale(2, RoundingMode.HALF_UP);
        assertEquals(0, expected.compareTo(actual));
    }
}

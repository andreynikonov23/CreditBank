package ru.neoflex.dto;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.neoflex.utils.TestData;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class LoanOfferDtoTest {
    private static UUID testStatementId;

    @BeforeAll
    public static void generateTestStatementId() {
        testStatementId = UUID.randomUUID();
    }

    @Test
    public void equalsAndHashCodeTest() {
        LoanOfferDto loanOfferDto1 = TestData.getTestLoanOfferDto();
        LoanOfferDto loanOfferDto2 = TestData.getTestLoanOfferDto();

        loanOfferDto1.setStatementId(testStatementId);
        loanOfferDto2.setStatementId(testStatementId);

        assertEquals(loanOfferDto1, loanOfferDto2);
    }

    @Test
    public void testEqualsWithDifferentObjects() {
        LoanOfferDto loanOfferDto1 = TestData.getTestLoanOfferDto();
        LoanOfferDto loanOfferDto2 = TestData.getTestLoanOfferDto();

        loanOfferDto1.setTerm(loanOfferDto2.getTerm() + 1);

        assertNotEquals(loanOfferDto1.hashCode(), loanOfferDto2.hashCode());
        assertNotEquals(loanOfferDto1, loanOfferDto2);
    }

    @Test
    public void testEqualsWithNull() {
        LoanOfferDto loanOfferDto = TestData.getTestLoanOfferDto();
        assertNotEquals(null, loanOfferDto);
    }

    @Test
    public void testEqualsWithDifferentClass() {
        LoanOfferDto loanOfferDto = TestData.getTestLoanOfferDto();
        assertNotEquals("some string", loanOfferDto);
    }
}

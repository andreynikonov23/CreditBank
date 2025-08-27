package ru.neoflex.statement.dto;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.neoflex.statement.utils.TestData;

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
        LoanOfferDto loanOfferDto1 = TestData.getTestLoanOffers().get(0);
        LoanOfferDto loanOfferDto2 = TestData.getTestLoanOffers().get(0);

        loanOfferDto1.setStatementId(testStatementId);
        loanOfferDto2.setStatementId(testStatementId);

        assertEquals(loanOfferDto1, loanOfferDto2);
    }

    @Test
    public void testEqualsWithDifferentObjects() {
        LoanOfferDto loanOfferDto1 = TestData.getTestLoanOffers().get(0);
        LoanOfferDto loanOfferDto2 = TestData.getTestLoanOffers().get(0);

        loanOfferDto1.setTerm(loanOfferDto2.getTerm() + 1);

        assertNotEquals(loanOfferDto1.hashCode(), loanOfferDto2.hashCode());
        assertNotEquals(loanOfferDto1, loanOfferDto2);
    }

    @Test
    public void testEqualsWithNull() {
        LoanOfferDto loanOfferDto = TestData.getTestLoanOffers().get(0);
        assertNotEquals(null, loanOfferDto);
    }

    @Test
    public void testEqualsWithDifferentClass() {
        LoanOfferDto loanOfferDto = TestData.getTestLoanOffers().get(0);
        assertNotEquals("some string", loanOfferDto);
    }
}

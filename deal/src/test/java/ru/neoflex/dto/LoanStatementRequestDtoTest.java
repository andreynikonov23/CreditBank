package ru.neoflex.dto;

import org.junit.jupiter.api.Test;
import ru.neoflex.utils.TestData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class LoanStatementRequestDtoTest {
    @Test
    public void equalsAndHashCodeTest() {
        LoanStatementRequestDto loanStatementRequestDto1 = TestData.getValidLoanStatementRequestDto();
        LoanStatementRequestDto loanStatementRequestDto2 = TestData.getValidLoanStatementRequestDto();

        assertEquals(loanStatementRequestDto1, loanStatementRequestDto2);
    }

    @Test
    public void testEqualsWithDifferentObjects() {
        LoanStatementRequestDto loanStatementRequestDto1 = TestData.getValidLoanStatementRequestDto();
        LoanStatementRequestDto loanStatementRequestDto2 = TestData.getValidLoanStatementRequestDto();

        loanStatementRequestDto1.setTerm(loanStatementRequestDto2.getTerm() + 1);

        assertNotEquals(loanStatementRequestDto1.hashCode(), loanStatementRequestDto2.hashCode());
        assertNotEquals(loanStatementRequestDto1, loanStatementRequestDto2);
    }

    @Test
    public void testEqualsWithNull() {
        LoanStatementRequestDto loanStatementRequestDto = TestData.getValidLoanStatementRequestDto();
        assertNotEquals(null, loanStatementRequestDto);
    }

    @Test
    public void testEqualsWithDifferentClass() {
        LoanStatementRequestDto loanStatementRequestDto = TestData.getValidLoanStatementRequestDto();
        assertNotEquals("some string", loanStatementRequestDto);
    }
}

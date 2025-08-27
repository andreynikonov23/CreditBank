package ru.neoflex.dto;

import org.junit.jupiter.api.Test;
import ru.neoflex.utils.TestData;


import static org.junit.jupiter.api.Assertions.*;

public class CreditDtoTest {
    @Test
    public void equalsAndHashCodeTest() {
        CreditDto creditDto1 = TestData.getTestCreditDto();
        CreditDto creditDto2 = TestData.getTestCreditDto();

        assertEquals(creditDto1, creditDto2);
    }

    @Test
    public void testEqualsWithDifferentObjects() {
        CreditDto creditDto1 = TestData.getTestCreditDto();
        CreditDto creditDto2 = TestData.getTestCreditDto();

        creditDto2.setTerm(creditDto2.getTerm() + 1);

        assertNotEquals(creditDto1.hashCode(), creditDto2.hashCode());
        assertNotEquals(creditDto1, creditDto2);
    }

    @Test
    public void testEqualsWithNull() {
        CreditDto creditDto = TestData.getTestCreditDto();
        assertNotEquals(null, creditDto);
    }

    @Test
    public void testEqualsWithDifferentClass() {
        CreditDto creditDto = TestData.getTestCreditDto();
        assertNotEquals("some string", creditDto);
    }
}

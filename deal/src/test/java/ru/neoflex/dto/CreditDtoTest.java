package ru.neoflex.dto;

import org.junit.jupiter.api.Test;
import ru.neoflex.utils.TestData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CreditDtoTest {
    @Test
    public void equalsAndHashCodeTest() {
        CreditDto creditDto1 = TestData.getCreditDto();
        CreditDto creditDto2 = TestData.getCreditDto();

        assertEquals(creditDto1, creditDto2);
    }

    @Test
    public void testEqualsWithDifferentObjects() {
        CreditDto creditDto1 = TestData.getCreditDto();
        CreditDto creditDto2 = TestData.getCreditDto();

        creditDto2.setTerm(creditDto2.getTerm() + 1);

        assertNotEquals(creditDto1.hashCode(), creditDto2.hashCode());
        assertNotEquals(creditDto1, creditDto2);
    }

    @Test
    public void testEqualsWithNull() {
        CreditDto creditDto = TestData.getCreditDto();
        assertNotEquals(null, creditDto);
    }

    @Test
    public void testEqualsWithDifferentClass() {
        CreditDto creditDto = TestData.getCreditDto();
        assertNotEquals("some string", creditDto);
    }
}

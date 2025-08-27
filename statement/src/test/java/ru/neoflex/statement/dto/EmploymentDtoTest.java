package ru.neoflex.statement.dto;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.neoflex.statement.utils.TestData;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class EmploymentDtoTest {
    private static UUID testEmploymentId;

    @BeforeAll
    public static void generateTestEmploymentId() {
        testEmploymentId = UUID.randomUUID();
    }

    @Test
    public void equalsAndHashCodeTest() {
        EmploymentDto employmentDto1 = TestData.getValidEmploymentDto();
        EmploymentDto employmentDto2 = TestData.getValidEmploymentDto();

        employmentDto1.setEmploymentUUID(testEmploymentId);
        employmentDto2.setEmploymentUUID(testEmploymentId);

        assertEquals(employmentDto1, employmentDto2);
    }

    @Test
    public void testEqualsWithDifferentObjects() {
        EmploymentDto employmentDto1 = TestData.getValidEmploymentDto();
        EmploymentDto employmentDto2 = TestData.getValidEmploymentDto();

        employmentDto2.setSalary(employmentDto2.getSalary().add(BigDecimal.ONE));

        assertNotEquals(employmentDto1.hashCode(), employmentDto2.hashCode());
        assertNotEquals(employmentDto1, employmentDto2);
    }

    @Test
    public void testEqualsWithNull() {
        EmploymentDto employmentDto = TestData.getValidEmploymentDto();
        assertNotEquals(null, employmentDto);
    }

    @Test
    public void testEqualsWithDifferentClass() {
        EmploymentDto employmentDto = TestData.getValidEmploymentDto();
        assertNotEquals("some string", employmentDto);
    }
}

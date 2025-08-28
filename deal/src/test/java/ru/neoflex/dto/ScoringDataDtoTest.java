package ru.neoflex.dto;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.neoflex.utils.TestData;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ScoringDataDtoTest {
    private static UUID testEmploymentId;

    @BeforeAll
    public static void generateTestEmploymentId() {
        testEmploymentId = UUID.randomUUID();
    }

    @Test
    public void equalsAndHashCodeTest() {
        ScoringDataDto scoringDataDto1 = TestData.getValidScoringDataDto();
        ScoringDataDto scoringDataDto2 = TestData.getValidScoringDataDto();

        scoringDataDto1.getEmployment().setEmploymentUUID(testEmploymentId);
        scoringDataDto2.getEmployment().setEmploymentUUID(testEmploymentId);

        assertEquals(scoringDataDto1, scoringDataDto2);
    }

    @Test
    public void testEqualsWithDifferentObjects() {
        ScoringDataDto scoringDataDto1 = TestData.getValidScoringDataDto();
        ScoringDataDto scoringDataDto2 = TestData.getValidScoringDataDto();

        scoringDataDto1.setTerm(scoringDataDto2.getTerm() + 1);

        assertNotEquals(scoringDataDto1.hashCode(), scoringDataDto2.hashCode());
        assertNotEquals(scoringDataDto1, scoringDataDto2);
    }

    @Test
    public void testEqualsWithNull() {
        ScoringDataDto scoringDataDto = TestData.getValidScoringDataDto();
        assertNotEquals(null, scoringDataDto);
    }

    @Test
    public void testEqualsWithDifferentClass() {
        ScoringDataDto scoringDataDto = TestData.getValidScoringDataDto();
        assertNotEquals("some string", scoringDataDto);
    }
}

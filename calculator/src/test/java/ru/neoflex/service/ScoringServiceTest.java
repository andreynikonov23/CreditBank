package ru.neoflex.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import ru.neoflex.calculator.dto.*;
import ru.neoflex.dto.*;
import ru.neoflex.exceptions.ScoringException;
import ru.neoflex.utils.TestData;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ScoringServiceTest {
    @Autowired
    private ScoringService scoringService;
    @Value("${credit.base_rate}")
    private BigDecimal baseRate;
    private ScoringDataDto validScoringDataDto;


    @BeforeEach
    public void initScoringDataDto() {
        validScoringDataDto = TestData.getValidScoringDataDto();
    }

    @Test
    public void test() {
        try {
            System.out.println(scoringService.scoreRate(baseRate, validScoringDataDto));
        } catch (ScoringException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Test
    void calcRateByUnemployment() {
        validScoringDataDto.getEmployment().setEmploymentStatus(EmploymentStatus.UNEMPLOYED);

        ScoringException ex = assertThrows(ScoringException.class,
                () -> scoringService.scoreRate(baseRate, validScoringDataDto));

        assertEquals("the client is unemployed", ex.getMessage());
    }

    @Test
    void calcRateBySelfEmploymentAndBusinessOwner() throws ScoringException {
        validScoringDataDto.getEmployment().setEmploymentStatus(EmploymentStatus.SELF_EMPLOYED);

        BigDecimal resultSelfEmployment = scoringService.scoreRate(baseRate, validScoringDataDto);
        BigDecimal actualSelfEmployment = resultSelfEmployment.setScale(2, RoundingMode.HALF_UP);
        BigDecimal expectedSelfEmployment = new BigDecimal("17.0").setScale(2, RoundingMode.HALF_UP);

        validScoringDataDto.getEmployment().setEmploymentStatus(EmploymentStatus.BUSINESS_OWNER);

        BigDecimal resultBusinessOwner = scoringService.scoreRate(baseRate, validScoringDataDto);
        BigDecimal actualBusinessOwner = resultBusinessOwner.setScale(2, RoundingMode.HALF_UP);
        BigDecimal expectedBusinessOwner = new BigDecimal("16.0").setScale(2, RoundingMode.HALF_UP);

        assertEquals(0, expectedSelfEmployment.compareTo(actualSelfEmployment));
        assertEquals(0, expectedBusinessOwner.compareTo(actualBusinessOwner));
    }

    @Test
    void calcRateByManagerPosition() throws ScoringException {
        validScoringDataDto.getEmployment().setPosition(EmploymentPosition.MID_MANAGER);

        BigDecimal resultMidManager = scoringService.scoreRate(baseRate, validScoringDataDto);
        BigDecimal actualMidManager = resultMidManager.setScale(2, RoundingMode.HALF_UP);
        BigDecimal expectedMidManager = new BigDecimal("13.0").setScale(2, RoundingMode.HALF_UP);

        validScoringDataDto.getEmployment().setPosition(EmploymentPosition.TOP_MANAGER);

        BigDecimal resultTopManager = scoringService.scoreRate(baseRate, validScoringDataDto);
        BigDecimal actualTopManager = resultTopManager.setScale(2, RoundingMode.HALF_UP);
        BigDecimal expectedTopManager = new BigDecimal("12.0").setScale(2, RoundingMode.HALF_UP);

        assertEquals(0, expectedMidManager.compareTo(actualMidManager));
        assertEquals(0, expectedTopManager.compareTo(actualTopManager));
    }

    @Test
    public void calcRateByInvalidSalarySum() {
        validScoringDataDto.getEmployment().setSalary(BigDecimal.valueOf(20000));

        ScoringException ex = assertThrows(ScoringException.class,
                () -> scoringService.scoreRate(baseRate, validScoringDataDto));

        assertEquals("the required amount is more than 24 salaries", ex.getMessage());
    }

    @Test
    public void calcRateByInvalidExperience() {
        validScoringDataDto.getEmployment().setWorkExperienceTotal(12);

        ScoringException ex = assertThrows(ScoringException.class,
                () -> scoringService.scoreRate(baseRate, validScoringDataDto));

        assertEquals("invalid work experience", ex.getMessage());
    }

    @Test
    public void calcRateByMaritalStatus() throws ScoringException {
        validScoringDataDto.setMaritalStatus(MaritalStatus.MARRIED);

        BigDecimal resultMarried = scoringService.scoreRate(baseRate, validScoringDataDto);
        BigDecimal actualMarried = resultMarried.setScale(2, RoundingMode.HALF_UP);
        BigDecimal expectedMarried = new BigDecimal("13.0").setScale(2, RoundingMode.HALF_UP);

        validScoringDataDto.setMaritalStatus(MaritalStatus.DIVORCED);

        BigDecimal resultDivorced = scoringService.scoreRate(baseRate, validScoringDataDto);
        BigDecimal actualDivorced = resultDivorced.setScale(2, RoundingMode.HALF_UP);
        BigDecimal expectedDivorced = new BigDecimal("16.0").setScale(2, RoundingMode.HALF_UP);

        assertEquals(0, expectedMarried.compareTo(actualMarried));
        assertEquals(0, expectedDivorced.compareTo(actualDivorced));
    }

    @Test
    public void calcRateByGender() throws ScoringException {
        validScoringDataDto.setGender(Gender.FEMALE);
        validScoringDataDto.setBirthdate(LocalDate.now().minusYears(33));

        BigDecimal resultWoman = scoringService.scoreRate(baseRate, validScoringDataDto);
        BigDecimal actualWoman = resultWoman.setScale(2, RoundingMode.HALF_UP);
        BigDecimal expectedWoman = new BigDecimal("15.0").setScale(2, RoundingMode.HALF_UP);

        validScoringDataDto.setGender(Gender.NON_BINARY);

        BigDecimal resultNonBinary = scoringService.scoreRate(baseRate, validScoringDataDto);
        BigDecimal actualNonBinary = resultNonBinary.setScale(2, RoundingMode.HALF_UP);
        BigDecimal expectedNonBinary = new BigDecimal("25.0").setScale(2, RoundingMode.HALF_UP);

        System.out.println(actualNonBinary);
        assertEquals(0, expectedWoman.compareTo(actualWoman));
        assertEquals(0, expectedNonBinary.compareTo(actualNonBinary));
    }

    @Test
    public void calcRateByInvalidAge() {
        validScoringDataDto.setBirthdate(LocalDate.now().minusYears(19));

        ScoringException ex = assertThrows(ScoringException.class,
                () -> scoringService.scoreRate(baseRate, validScoringDataDto));

        assertEquals("The age must be at least 20 and no more than 65", ex.getMessage());
    }

}

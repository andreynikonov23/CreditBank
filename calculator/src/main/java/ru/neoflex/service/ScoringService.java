package ru.neoflex.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.neoflex.dto.*;
import ru.neoflex.exceptions.ScoringException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Service
@Slf4j
public class ScoringService implements Scoring{
    @Override
    public BigDecimal scoreRate(BigDecimal rate, ScoringDataDto scoringDataDto) throws ScoringException {
        System.out.println(scoringDataDto);
        log.info("the beginning of data scoring: rate={}; scoringDataDto={}", rate, scoringDataDto);
        rate = calcRateByEmployment(rate, scoringDataDto.getAmount(), scoringDataDto.getEmployment());
        rate = calcRateByMaritalStatus(rate, scoringDataDto.getMaritalStatus());
        rate = calcRateByGenderAndAge(rate, scoringDataDto.getBirthdate(), scoringDataDto.getGender());

        return rate;
    }

    private BigDecimal calcRateByEmployment(BigDecimal rate, BigDecimal requiredAmount, EmploymentDto employment) throws ScoringException {
        log.debug("employment scoring: rate={}; requiredAmount={}, employment={}", rate, requiredAmount, employment);
        rate = calcRateByEmploymentStatus(rate, employment.getEmploymentStatus());
        rate = calcRateByEmploymentPosition(rate, employment.getPosition());

        if (isRequiredAmountMoreThen24Salaries(requiredAmount, employment.getSalary())) {
            String errorMessage = "the required amount is more than 24 salaries";
            log.error("employment scoring error: " + errorMessage);
            throw new ScoringException(errorMessage);
        }

        if (employment.getWorkExperienceTotal() < 547 || employment.getWorkExperienceCurrent() < 91) {
            String errorMessage = "invalid work experience";
            log.error("employment scoring error: " + errorMessage);
            throw new ScoringException(errorMessage);
        }

        log.debug("employment scoring was successful: result={}", rate);
        return rate;
    }

    private BigDecimal calcRateByMaritalStatus(BigDecimal rate, MaritalStatus maritalStatus) {
        log.debug("marital status scoring: rate={}; maritalStatus={}", rate, maritalStatus);
        switch (maritalStatus) {
            case MARRIED -> rate = rate.subtract(BigDecimal.valueOf(2));
            case DIVORCED -> rate = rate.add(BigDecimal.valueOf(1));
        }
        log.debug("result maritalStatus scoring = {}", rate);
        return rate;
    }

    private BigDecimal calcRateByGenderAndAge(BigDecimal rate, LocalDate birthdate, Gender gender) throws ScoringException {
        log.debug("gender and age scoring: rate={}; birthdate={}, gender={}", rate, birthdate, gender);
        Period period = Period.between(birthdate, LocalDate.now());
        int age = period.getYears();

        if (gender.equals(Gender.FEMALE) && age >= 32 && age <= 60) {
            rate = rate.subtract(BigDecimal.valueOf(3));
        } else if (gender.equals(Gender.MALE) && age >= 30 && age <= 55) {
            rate = rate.subtract(BigDecimal.valueOf(3));
        } else if (gender.equals(Gender.NON_BINARY)) {
            rate = rate.add(BigDecimal.valueOf(7));
        } else if (age < 20 || age > 65) {
            String errorMessage = "The age must be at least 20 and no more than 65";
            log.error("gender and age scoring error: " + errorMessage);
            throw new ScoringException(errorMessage);
        }

        return rate;
    }

    private BigDecimal calcRateByEmploymentStatus(BigDecimal rate, EmploymentStatus status) throws ScoringException {
        switch (status) {
            case UNEMPLOYED -> {
                throw new ScoringException("the client is unemployed");
            }
            case SELF_EMPLOYED -> rate = rate.add(BigDecimal.valueOf(2));
            case BUSINESS_OWNER -> rate = rate.add(BigDecimal.valueOf(1));
        }
        return rate;
    }

    private BigDecimal calcRateByEmploymentPosition(BigDecimal rate, EmploymentPosition position) {
        switch (position) {
            case MID_MANAGER -> rate = rate.subtract(BigDecimal.valueOf(2));
            case TOP_MANAGER -> rate = rate.subtract(BigDecimal.valueOf(3));
        }
        return rate;
    }

    private boolean isRequiredAmountMoreThen24Salaries(BigDecimal requiredAmount, BigDecimal salary) {
        BigDecimal salarySum = salary.multiply(BigDecimal.valueOf(24));
        return requiredAmount.compareTo(salarySum) > 0;
    }
}

package ru.neoflex.calculator.utils;

import ru.neoflex.calculator.dto.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TestData {
    public static ScoringDataDto getValidScoringDataDto() {
        ScoringDataDto scoringDataDto = new ScoringDataDto();
        scoringDataDto.setAmount(BigDecimal.valueOf(5000000));
        scoringDataDto.setTerm(10);
        scoringDataDto.setFirstname("John");
        scoringDataDto.setLastname("Doe");
        scoringDataDto.setMiddleName("Smith");
        scoringDataDto.setGender(Gender.MALE);
        scoringDataDto.setBirthdate(LocalDate.now().minusYears(30));
        scoringDataDto.setPassportSeries("1234");
        scoringDataDto.setPassportNumber("567890");
        scoringDataDto.setPassportIssueDate(LocalDate.now().plusMonths(10));
        scoringDataDto.setPassportIssueBranch("Some Branch");
        scoringDataDto.setMaritalStatus(MaritalStatus.SINGLE);
        scoringDataDto.setDependentAmount(0);

        EmploymentDto employmentDto = new EmploymentDto();
        employmentDto.setEmploymentStatus(EmploymentStatus.OFFICIAL_EMPLOYED);
        employmentDto.setEmployerINN("1234567890");
        employmentDto.setSalary(new BigDecimal("60000.00"));
        employmentDto.setPosition(Position.WORKER);
        employmentDto.setWorkExperienceTotal(5);
        employmentDto.setWorkExperienceCurrent(2);
        scoringDataDto.setEmployment(employmentDto);

        scoringDataDto.setAccountNumber("12345678901234567890");
        scoringDataDto.setInsuranceEnabled(true);
        scoringDataDto.setSalaryClient(false);

        return scoringDataDto;
    }

    public static ScoringDataDto getInvalidScoringDataDto() {
        ScoringDataDto scoringDataDto = new ScoringDataDto();
        scoringDataDto.setAmount(BigDecimal.valueOf(5000000));
        scoringDataDto.setTerm(-1);
        scoringDataDto.setLastname("Doe");
        scoringDataDto.setMiddleName("Smith");
        scoringDataDto.setGender(Gender.MALE);
        scoringDataDto.setBirthdate(LocalDate.now().minusYears(10));
        scoringDataDto.setPassportSeries("123434");
        scoringDataDto.setPassportNumber("56gk90");
        scoringDataDto.setPassportIssueDate(LocalDate.now().minusDays(18));
        scoringDataDto.setPassportIssueBranch("Some Branch");
        scoringDataDto.setMaritalStatus(MaritalStatus.SINGLE);
        scoringDataDto.setDependentAmount(0);

        EmploymentDto employmentDto = new EmploymentDto();
        employmentDto.setEmploymentStatus(EmploymentStatus.OFFICIAL_EMPLOYED);
        employmentDto.setEmployerINN("123456");
        employmentDto.setSalary(new BigDecimal("60000.00"));
        employmentDto.setPosition(Position.WORKER);
        employmentDto.setWorkExperienceTotal(5);
        employmentDto.setWorkExperienceCurrent(2);
        scoringDataDto.setEmployment(employmentDto);

        scoringDataDto.setAccountNumber("12345678901234567890");
        scoringDataDto.setInsuranceEnabled(true);
        scoringDataDto.setSalaryClient(false);

        return scoringDataDto;
    }

    public static LoanStatementRequestDto getValidLoanStatementRequestDto() {
        LoanStatementRequestDto loanStatementRequestDto = new LoanStatementRequestDto();
        loanStatementRequestDto.setAmount(BigDecimal.valueOf(5000000));
        loanStatementRequestDto.setTerm(10);
        loanStatementRequestDto.setFirstname("Andrey");
        loanStatementRequestDto.setLastname("Nikonov");
        loanStatementRequestDto.setMiddleName("Sergeevich");
        loanStatementRequestDto.setEmail("nikonov.as@mail.ru");
        loanStatementRequestDto.setBirthdate(LocalDate.of(2001, 9, 13));
        loanStatementRequestDto.setPassportSeries("2245");
        loanStatementRequestDto.setPassportNumber("234455");

        return loanStatementRequestDto;
    }

    public static LoanStatementRequestDto getInvalidLoanStatementRequestDto() {
        LoanStatementRequestDto loanStatementRequestDto = new LoanStatementRequestDto();
        loanStatementRequestDto.setAmount(BigDecimal.valueOf(5000000));
        loanStatementRequestDto.setTerm(10);
        loanStatementRequestDto.setFirstname("Andrey");
        loanStatementRequestDto.setMiddleName("Sergeevich");
        loanStatementRequestDto.setEmail("invalidemail.ru");
        loanStatementRequestDto.setBirthdate(LocalDate.now().minusYears(10));
        loanStatementRequestDto.setPassportNumber("224543");
        loanStatementRequestDto.setPassportSeries("56gk90");

        return loanStatementRequestDto;
    }
}

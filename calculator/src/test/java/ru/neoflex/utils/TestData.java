package ru.neoflex.utils;

import ru.neoflex.dto.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class TestData {
    public static ScoringDataDto getValidScoringDataDto() {
        ScoringDataDto scoringDataDto = new ScoringDataDto();
        scoringDataDto.setAmount(BigDecimal.valueOf(2500000));
        scoringDataDto.setTerm(7);
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

        EmploymentDto employmentDto = getValidEmploymentDto();
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
        employmentDto.setEmploymentStatus(EmploymentStatus.EMPLOYED);
        employmentDto.setEmployerINN("123456");
        employmentDto.setSalary(new BigDecimal("60000.00"));
        employmentDto.setPosition(EmploymentPosition.WORKER);
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

    public static EmploymentDto getValidEmploymentDto() {
        EmploymentDto employmentDto = new EmploymentDto();
        employmentDto.setEmploymentStatus(EmploymentStatus.EMPLOYED);
        employmentDto.setEmployerINN("1234567890");
        employmentDto.setSalary(new BigDecimal("120000.00"));
        employmentDto.setPosition(EmploymentPosition.WORKER);
        employmentDto.setWorkExperienceTotal(1825);
        employmentDto.setWorkExperienceCurrent(1095);

        return employmentDto;
    }

    public static LoanOfferDto getTestLoanOfferDto() {
        LoanOfferDto loanOfferDto = new LoanOfferDto();

        loanOfferDto.setRequestedAmount(BigDecimal.valueOf(5000000));
        loanOfferDto.setTotalAmount(BigDecimal.valueOf(5922500));
        loanOfferDto.setTerm(10);
        loanOfferDto.setMonthlyPayment(BigDecimal.valueOf(49354.166));
        loanOfferDto.setRate(BigDecimal.valueOf(15));
        loanOfferDto.setInsuranceEnabled(true);
        loanOfferDto.setInsuranceEnabled(true);
        return loanOfferDto;
    }

    public static CreditDto getTestCreditDto() {
        CreditDto creditDto = new CreditDto();
        creditDto.setAmount(BigDecimal.valueOf(5000000));
        creditDto.setTerm(8);
        creditDto.setRate(BigDecimal.valueOf(18));
        creditDto.setPsk(BigDecimal.valueOf(5900000));
        creditDto.setMonthlyPayment(BigDecimal.valueOf(61458.33));
        creditDto.setInsuranceEnabled(false);
        creditDto.setSalaryClient(false);

        List<PaymentScheduleElementDto> paymentSchedule = List.of(getTestPaymentScheduleElementDto());
        creditDto.setPaymentSchedule(paymentSchedule);

        return creditDto;
    }

    public static PaymentScheduleElementDto getTestPaymentScheduleElementDto() {
        PaymentScheduleElementDto paymentScheduleElementDto = new PaymentScheduleElementDto();
        paymentScheduleElementDto.setNumber(1);
        paymentScheduleElementDto.setDate(LocalDate.now());
        paymentScheduleElementDto.setTotalPayment(getBigDecimal(5900000.00));
        paymentScheduleElementDto.setInterestPayment(getBigDecimal(0.09));
        paymentScheduleElementDto.setDebtPayment(getBigDecimal(61458.33));
        paymentScheduleElementDto.setRemainingDebt(getBigDecimal(5838541.67));

        return paymentScheduleElementDto;
    }

    private static BigDecimal getBigDecimal(double value) {
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP);
    }
}

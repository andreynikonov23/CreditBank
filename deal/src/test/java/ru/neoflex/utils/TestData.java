package ru.neoflex.utils;

import ru.neoflex.dto.*;
import ru.neoflex.enums.*;
import ru.neoflex.model.Client;
import ru.neoflex.model.Credit;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestData {
    public static Client getValidClient() {
        Client client = new Client();
        client.setFirstname("John");
        client.setLastname("Snow");
        client.setMiddleName("Neddovich");
        client.setBirthdate(LocalDate.of(1998, 4, 15));
        client.setEmail("jsnow@win.va");

        PassportDto passportDto = new PassportDto();
        passportDto.setPassportUUID(UUID.randomUUID());
        passportDto.setSeries("2245");
        passportDto.setNumber("234455");
        passportDto.setIssueDate(LocalDate.now().plusYears(4));
        passportDto.setIssueBranch("ГУ МВД по Винтерфеллской обл.");
        client.setPassportDto(passportDto);

        EmploymentDto employmentDto = new EmploymentDto();
        employmentDto.setEmploymentUUID(UUID.randomUUID());
        employmentDto.setEmploymentStatus(EmploymentStatus.EMPLOYED);
        employmentDto.setEmployerINN("123456");
        employmentDto.setSalary(new BigDecimal("60000.00"));
        employmentDto.setPosition(EmploymentPosition.WORKER);
        employmentDto.setWorkExperienceTotal(5);
        employmentDto.setWorkExperienceCurrent(2);
        client.setEmploymentDto(employmentDto);

        client.setAccountNumber("1244322");
        return client;
    }

    public static Credit getCredit() {
        Credit credit = new Credit();
        credit.setAmount(BigDecimal.valueOf(4000000));
        credit.setTerm(7);
        credit.setMonthlyPayment(BigDecimal.valueOf(50952.380));
        credit.setRate(BigDecimal.valueOf(7.0));
        credit.setPsk(BigDecimal.valueOf(4280000));
        credit.setPaymentSchedule(new ArrayList<>());
        credit.setInsuranceEnable(true);
        credit.setSalaryClient(true);
        credit.setCreditStatus(CreditStatus.CALCULATED);

        return credit;
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
        loanStatementRequestDto.setEmail("nikonov.as");
        loanStatementRequestDto.setBirthdate(LocalDate.of(2020, 9, 13));
        loanStatementRequestDto.setPassportSeries("224435");
        loanStatementRequestDto.setPassportNumber("23gh55");

        return loanStatementRequestDto;
    }

    public static List<LoanOfferDto> getTestLoanOffers() {
        LoanOfferDto loanOfferDto1 = new LoanOfferDto();
        LoanOfferDto loanOfferDto2 = new LoanOfferDto();
        LoanOfferDto loanOfferDto3 = new LoanOfferDto();
        LoanOfferDto loanOfferDto4 = new LoanOfferDto();

        loanOfferDto1.setRequestedAmount(BigDecimal.valueOf(5000000));
        loanOfferDto1.setTotalAmount(BigDecimal.valueOf(5922500));
        loanOfferDto1.setTerm(10);
        loanOfferDto1.setMonthlyPayment(BigDecimal.valueOf(49354.166));
        loanOfferDto1.setRate(BigDecimal.valueOf(15));
        loanOfferDto1.setInsuranceEnabled(true);
        loanOfferDto1.setInsuranceEnabled(true);

        loanOfferDto2.setRequestedAmount(BigDecimal.valueOf(5000000));
        loanOfferDto2.setTotalAmount(BigDecimal.valueOf(5974000));
        loanOfferDto2.setTerm(10);
        loanOfferDto2.setMonthlyPayment(BigDecimal.valueOf(49783.333));
        loanOfferDto2.setRate(BigDecimal.valueOf(16));
        loanOfferDto2.setInsuranceEnabled(true);
        loanOfferDto2.setInsuranceEnabled(false);

        loanOfferDto3.setRequestedAmount(BigDecimal.valueOf(5000000));
        loanOfferDto3.setTotalAmount(BigDecimal.valueOf(5850000));
        loanOfferDto3.setTerm(10);
        loanOfferDto3.setMonthlyPayment(BigDecimal.valueOf(48750));
        loanOfferDto3.setRate(BigDecimal.valueOf(17));
        loanOfferDto3.setInsuranceEnabled(false);
        loanOfferDto3.setInsuranceEnabled(true);

        loanOfferDto4.setRequestedAmount(BigDecimal.valueOf(5000000));
        loanOfferDto4.setTotalAmount(BigDecimal.valueOf(5900000));
        loanOfferDto4.setTerm(10);
        loanOfferDto4.setMonthlyPayment(BigDecimal.valueOf(49166.666));
        loanOfferDto4.setRate(BigDecimal.valueOf(18));
        loanOfferDto4.setInsuranceEnabled(false);
        loanOfferDto4.setInsuranceEnabled(false);

        return List.of(loanOfferDto1, loanOfferDto2, loanOfferDto3, loanOfferDto4);
    }

    public static FinishRegistrationRequestDto getFinishRegistrationRequestDto() {
        FinishRegistrationRequestDto finishRegistrationRequestDto = new FinishRegistrationRequestDto();
        finishRegistrationRequestDto.setGender(Gender.FEMALE);
        finishRegistrationRequestDto.setMaritalStatus(MaritalStatus.MARRIED);
        finishRegistrationRequestDto.setDependentAmount(5290);
        finishRegistrationRequestDto.setPassportIssueDate(LocalDate.now().plusYears(12));
        finishRegistrationRequestDto.setPassportIssueBranch("ГУ МВД по Нижегородской области");
        finishRegistrationRequestDto.setAccountNumber("12482191");

        EmploymentDto employmentDto = getValidEmploymentDto();

        finishRegistrationRequestDto.setEmploymentDto(employmentDto);
        return finishRegistrationRequestDto;
    }

    public static CreditDto getCreditDto() {
        CreditDto creditDto = new CreditDto();
        creditDto.setAmount(BigDecimal.valueOf(2000000));
        creditDto.setTerm(4);
        creditDto.setMonthlyPayment(BigDecimal.valueOf(48750));
        creditDto.setRate(BigDecimal.valueOf(17));
        creditDto.setPsk(BigDecimal.valueOf(2340000));
        creditDto.setPaymentSchedule(new ArrayList<>());
        creditDto.setInsuranceEnabled(false);
        creditDto.setSalaryClient(true);

        return creditDto;
    }

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

    public static EmploymentDto getValidEmploymentDto() {
        EmploymentDto employmentDto = new EmploymentDto();
        employmentDto.setEmploymentUUID(UUID.randomUUID());
        employmentDto.setEmploymentStatus(EmploymentStatus.EMPLOYED);
        employmentDto.setEmployerINN("1234567890");
        employmentDto.setSalary(new BigDecimal("120000.00"));
        employmentDto.setPosition(EmploymentPosition.WORKER);
        employmentDto.setWorkExperienceTotal(1825);
        employmentDto.setWorkExperienceCurrent(1095);

        return employmentDto;
    }
}

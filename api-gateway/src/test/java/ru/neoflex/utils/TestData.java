package ru.neoflex.utils;

import ru.neoflex.dto.*;
import ru.neoflex.enums.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestData {

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

    public static String getStatementListJson() {
        return "[\n" +
                "    {\n" +
                "        \"id\": \"ff942ab8-817b-4fb9-98b6-2a10e478dd52\",\n" +
                "        \"status\": \"CC_DENIED\",\n" +
                "        \"creationDate\": \"2025-08-19\",\n" +
                "        \"appliedOffer\": {\n" +
                "            \"statementId\": \"ff942ab8-817b-4fb9-98b6-2a10e478dd52\",\n" +
                "            \"requestedAmount\": 2000000,\n" +
                "            \"totalAmount\": 2389599.999999997,\n" +
                "            \"term\": 9,\n" +
                "            \"monthlyPayment\": 22125.9259259259,\n" +
                "            \"rate\": 16,\n" +
                "            \"salaryClient\": false,\n" +
                "            \"insuranceEnabled\": true\n" +
                "        },\n" +
                "        \"signDate\": null,\n" +
                "        \"sesCode\": null,\n" +
                "        \"client\": {\n" +
                "            \"id\": \"179b91b6-5eb7-4d7b-a31f-56a98784e199\",\n" +
                "            \"firstname\": \"Igor\",\n" +
                "            \"lastname\": \"Popov\",\n" +
                "            \"middleName\": \"Viktorovich\",\n" +
                "            \"birthdate\": \"1975-01-25\",\n" +
                "            \"email\": \"popov.iv@gknpn.ru\",\n" +
                "            \"gender\": \"MALE\",\n" +
                "            \"maritalStatus\": \"SINGLE\",\n" +
                "            \"dependentAmount\": 5290,\n" +
                "            \"passportDto\": {\n" +
                "                \"passportUUID\": \"2ded68b0-0deb-4ced-82e4-56f094c203b9\",\n" +
                "                \"series\": \"2204\",\n" +
                "                \"number\": \"123456\",\n" +
                "                \"issueBranch\": \"ГУ МВД по Нижегородской области\",\n" +
                "                \"issueDate\": \"2037-10-13\"\n" +
                "            },\n" +
                "            \"employmentDto\": {\n" +
                "                \"employmentUUID\": \"ae26b028-b69b-453e-844d-dbe192e65d2b\",\n" +
                "                \"employmentStatus\": \"UNEMPLOYED\",\n" +
                "                \"employerINN\": \"214264958195\",\n" +
                "                \"salary\": 184000,\n" +
                "                \"position\": \"TOP_MANAGER\",\n" +
                "                \"workExperienceTotal\": 4380,\n" +
                "                \"workExperienceCurrent\": 730\n" +
                "            },\n" +
                "            \"accountNumber\": \"12482191\"\n" +
                "        },\n" +
                "        \"credit\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"8666f70c-5969-4b96-817b-d737dc223806\",\n" +
                "        \"status\": \"APPROVED\",\n" +
                "        \"creationDate\": \"2025-08-19\",\n" +
                "        \"appliedOffer\": {\n" +
                "            \"statementId\": \"8666f70c-5969-4b96-817b-d737dc223806\",\n" +
                "            \"requestedAmount\": 2000000,\n" +
                "            \"totalAmount\": 2389599.999999997,\n" +
                "            \"term\": 6,\n" +
                "            \"monthlyPayment\": 33188.8888888889,\n" +
                "            \"rate\": 16,\n" +
                "            \"salaryClient\": false,\n" +
                "            \"insuranceEnabled\": true\n" +
                "        },\n" +
                "        \"signDate\": \"2025-08-19\",\n" +
                "        \"sesCode\": null,\n" +
                "        \"client\": {\n" +
                "            \"id\": \"ef34947e-6315-4784-a17d-2bdc9d79125c\",\n" +
                "            \"firstname\": \"Olga\",\n" +
                "            \"lastname\": \"Mudrova\",\n" +
                "            \"middleName\": \"Viktorovna\",\n" +
                "            \"birthdate\": \"1988-06-19\",\n" +
                "            \"email\": \"mudrova.ov@gmail.com\",\n" +
                "            \"gender\": \"FEMALE\",\n" +
                "            \"maritalStatus\": \"MARRIED\",\n" +
                "            \"dependentAmount\": 5290,\n" +
                "            \"passportDto\": {\n" +
                "                \"passportUUID\": \"4fe9c5d5-5eff-4ea7-adfb-19f5676d7a6a\",\n" +
                "                \"series\": \"2204\",\n" +
                "                \"number\": \"123456\",\n" +
                "                \"issueBranch\": \"ГУ МВД по Нижегородской области\",\n" +
                "                \"issueDate\": \"2037-10-13\"\n" +
                "            },\n" +
                "            \"employmentDto\": {\n" +
                "                \"employmentUUID\": \"ae26b028-b69b-453e-844d-dbe192e65d2b\",\n" +
                "                \"employmentStatus\": \"EMPLOYED\",\n" +
                "                \"employerINN\": \"214264958195\",\n" +
                "                \"salary\": 184000,\n" +
                "                \"position\": \"TOP_MANAGER\",\n" +
                "                \"workExperienceTotal\": 4380,\n" +
                "                \"workExperienceCurrent\": 730\n" +
                "            },\n" +
                "            \"accountNumber\": \"12482191\"\n" +
                "        },\n" +
                "        \"credit\": {\n" +
                "            \"id\": \"afbe1d38-0dff-426d-bf09-207af63868f2\",\n" +
                "            \"amount\": 2000000,\n" +
                "            \"term\": 6,\n" +
                "            \"monthlyPayment\": 30900,\n" +
                "            \"rate\": 8,\n" +
                "            \"psk\": 2224800,\n" +
                "            \"creditStatus\": \"CALCULATED\",\n" +
                "            \"salaryClient\": false,\n" +
                "            \"insuranceEnable\": true\n" +
                "        }\n" +
                "    }\n" +
                "]";
    }

    public static String getStatementJson() {
        return "{\n" +
                "        \"id\": \"ff942ab8-817b-4fb9-98b6-2a10e478dd52\",\n" +
                "        \"status\": \"CC_DENIED\",\n" +
                "        \"creationDate\": \"2025-08-19\",\n" +
                "        \"appliedOffer\": {\n" +
                "            \"statementId\": \"ff942ab8-817b-4fb9-98b6-2a10e478dd52\",\n" +
                "            \"requestedAmount\": 2000000,\n" +
                "            \"totalAmount\": 2389599.999999997,\n" +
                "            \"term\": 9,\n" +
                "            \"monthlyPayment\": 22125.9259259259,\n" +
                "            \"rate\": 16,\n" +
                "            \"salaryClient\": false,\n" +
                "            \"insuranceEnabled\": true\n" +
                "        },\n" +
                "        \"signDate\": null,\n" +
                "        \"sesCode\": null,\n" +
                "        \"client\": {\n" +
                "            \"id\": \"179b91b6-5eb7-4d7b-a31f-56a98784e199\",\n" +
                "            \"firstname\": \"Igor\",\n" +
                "            \"lastname\": \"Popov\",\n" +
                "            \"middleName\": \"Viktorovich\",\n" +
                "            \"birthdate\": \"1975-01-25\",\n" +
                "            \"email\": \"popov.iv@gknpn.ru\",\n" +
                "            \"gender\": \"MALE\",\n" +
                "            \"maritalStatus\": \"SINGLE\",\n" +
                "            \"dependentAmount\": 5290,\n" +
                "            \"passportDto\": {\n" +
                "                \"passportUUID\": \"2ded68b0-0deb-4ced-82e4-56f094c203b9\",\n" +
                "                \"series\": \"2204\",\n" +
                "                \"number\": \"123456\",\n" +
                "                \"issueBranch\": \"ГУ МВД по Нижегородской области\",\n" +
                "                \"issueDate\": \"2037-10-13\"\n" +
                "            },\n" +
                "            \"employmentDto\": {\n" +
                "                \"employmentUUID\": \"ae26b028-b69b-453e-844d-dbe192e65d2b\",\n" +
                "                \"employmentStatus\": \"UNEMPLOYED\",\n" +
                "                \"employerINN\": \"214264958195\",\n" +
                "                \"salary\": 184000,\n" +
                "                \"position\": \"TOP_MANAGER\",\n" +
                "                \"workExperienceTotal\": 4380,\n" +
                "                \"workExperienceCurrent\": 730\n" +
                "            },\n" +
                "            \"accountNumber\": \"12482191\"\n" +
                "        },\n" +
                "        \"credit\": null\n" +
                "    }";
    }
}

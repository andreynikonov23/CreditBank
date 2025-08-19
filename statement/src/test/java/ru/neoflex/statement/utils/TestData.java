package ru.neoflex.statement.utils;

import ru.neoflex.statement.dto.LoanOfferDto;
import ru.neoflex.statement.dto.LoanStatementRequestDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class TestData {

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
}

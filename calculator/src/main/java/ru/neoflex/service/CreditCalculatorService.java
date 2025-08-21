package ru.neoflex.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.neoflex.dto.*;
import ru.neoflex.exceptions.ScoringException;
import ru.neoflex.utils.CreditAmountDto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class CreditCalculatorService implements CreditCalculator{
    private final Scoring scoring;
    @Value("${credit.base_rate}")
    private BigDecimal baseRate;
    @Value("${credit.insurance_cost_percent}")
    private BigDecimal insuranceCostPercent;
    @Value("${credit.salary_client_discount}")
    private BigDecimal salaryClientDiscount;
    @Value("${credit.insurance_discount}")
    private BigDecimal insuranceDiscount;

    public CreditCalculatorService(Scoring scoring) {
        this.scoring = scoring;
    }

    @Override
    public List<LoanOfferDto> calculateLoanTerms(LoanStatementRequestDto requestDto) {
        log.info("start calculate loan terms");
        List<LoanOfferDto> loanTerms = new ArrayList<>();
        boolean[] isSalaryClientCombinations = {false, true};
        boolean[] isInsuranceEnableCombinations = {false, true};

        for (boolean isSalaryClient : isSalaryClientCombinations) {
            for (boolean isInsuranceEnable : isInsuranceEnableCombinations) {
                BigDecimal requiredAmount = requestDto.getAmount();
                int term = requestDto.getTerm();

                log.debug("params: isSalaryClient = {}, isInsuranceEnable = {}, requiredAmount = {}, term = {}", isSalaryClient, isInsuranceEnable, requiredAmount, term);

                BigDecimal rate = calcRate(isInsuranceEnable, isSalaryClient);
                CreditAmountDto creditAmountDto = calcCreditAmount(requiredAmount, term, rate, isInsuranceEnable);

                LoanOfferDto loanOfferDto = new LoanOfferDto();
                loanOfferDto.setRequestedAmount(requiredAmount);
                loanOfferDto.setTotalAmount(creditAmountDto.getPsk());
                loanOfferDto.setTerm(term);
                loanOfferDto.setMonthlyPayment(creditAmountDto.getMonthlyPayment());
                loanOfferDto.setRate(rate);
                loanOfferDto.setInsuranceEnabled(isInsuranceEnable);
                loanOfferDto.setSalaryClient(isSalaryClient);
                loanTerms.add(loanOfferDto);

                log.debug("A loan offer has been formed: {}", loanOfferDto);
            }
        }
        Collections.sort(loanTerms);
        log.info("The loan terms have been calculated, and {} offers have been found.", loanTerms.size());
        return loanTerms;
    }

    @Override
    public CreditDto calc(ScoringDataDto dataDto) throws ScoringException {
        log.info("The beginning of the loan calculation based on scoring data: {}", dataDto);

        BigDecimal requiredAmount = dataDto.getAmount();
        int term = dataDto.getTerm();
        boolean isInsuranceEnabled = dataDto.isInsuranceEnabled();
        boolean isSalaryClient = dataDto.isSalaryClient();

        log.debug("Parameters for calculating the loan: requiredAmount = {}, term = {}, isInsuranceEnabled = {}, isSalaryClient = {}", requiredAmount, term, isInsuranceEnabled, isSalaryClient);

        BigDecimal rate = calcRate(isInsuranceEnabled, isSalaryClient);
        rate = scoring.scoreRate(rate, dataDto);

        CreditAmountDto creditAmountDto = calcCreditAmount(requiredAmount, term, rate, isInsuranceEnabled);
        BigDecimal psk = creditAmountDto.getPsk();
        BigDecimal monthlyPayment = creditAmountDto.getMonthlyPayment();

        List<PaymentScheduleElementDto> paymentSchedule = createPaymentSchedule(psk, term, monthlyPayment);

        CreditDto creditDto = new CreditDto(
                requiredAmount,
                term,
                monthlyPayment,
                rate,
                psk,
                isInsuranceEnabled,
                isSalaryClient,
                paymentSchedule
        );
        log.info("Credit calculation completed, result: {}", creditDto);
        return creditDto;
    }

    private BigDecimal calcRate(boolean isInsuranceEnable, boolean isSalaryClient) {
        log.debug("calculation of the interest rate with params: isInsuranceEnable = {}, isSalaryClient = {}", isInsuranceEnable, isSalaryClient);
        BigDecimal rate = baseRate;
        if (isInsuranceEnable) {
            rate = rate.subtract(insuranceDiscount);
            log.debug("a discount on the insurance service rate has been applied, isInsuranceEnable=true");
        }
        if(isSalaryClient) {
            rate = rate.subtract(salaryClientDiscount);
            log.debug("a discount on the salary client rate has been applied, isSalaryClient=true");
        }
        log.debug("total rate = {}", rate);
        return rate;
    }

    private CreditAmountDto calcCreditAmount(BigDecimal requiredAmount, int term, BigDecimal rate, boolean isInsuranceEnable) {
        log.debug("calc credit amount: requiredAmount = {}, term = {}, rate = {}, isInsuranceEnable = {}", requiredAmount, term, rate, isInsuranceEnable);
        BigDecimal annualAmount = requiredAmount.divide(BigDecimal.valueOf(term), 10, RoundingMode.HALF_UP);
        annualAmount = annualAmount.add(annualAmount.divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP).multiply(rate));

        if (isInsuranceEnable) {
            BigDecimal insuranceCost = annualAmount.divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP).multiply(insuranceCostPercent);
            annualAmount = annualAmount.add(insuranceCost);
            log.debug("added insurance cost = {}", insuranceCost);
        }

        BigDecimal psk = annualAmount.multiply(BigDecimal.valueOf(term));
        BigDecimal monthlyPayment = annualAmount.divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP);

        CreditAmountDto creditAmountDto = new CreditAmountDto(annualAmount, psk, monthlyPayment);
        log.debug("result creditAmountDto {}", creditAmountDto);

        return creditAmountDto;
    }

    private List<PaymentScheduleElementDto> createPaymentSchedule(BigDecimal psk, int term, BigDecimal monthlyPayment) {
        log.debug("creating a payment schedule. psk = {}, term = {}, monthlyPayment = {}", psk, term, monthlyPayment);
        List<PaymentScheduleElementDto> paymentSchedule = new ArrayList<>();
        LocalDate date = LocalDate.now();
        LocalDate endDate = date.plusYears(term);

        BigDecimal remainingDebt = psk;
        BigDecimal debtPayment = BigDecimal.ZERO;
        for (int num = 1; !(date.equals(endDate)); num++) {
            remainingDebt = remainingDebt.subtract(monthlyPayment);
            BigDecimal interestPaymentReverse = remainingDebt.divide(psk, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
            BigDecimal interestPayment = BigDecimal.valueOf(100).subtract(interestPaymentReverse);
            debtPayment = debtPayment.add(monthlyPayment);

            PaymentScheduleElementDto paymentScheduleElementDto = new PaymentScheduleElementDto(
                    num,
                    date,
                    psk,
                    interestPayment,
                    debtPayment,
                    remainingDebt
            );
            paymentSchedule.add(paymentScheduleElementDto);
            date = date.plusMonths(1);
        }

        log.debug("payment schedule has been created, number of items = {}", paymentSchedule.size());
        return paymentSchedule;
    }
}

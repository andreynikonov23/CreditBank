package ru.neoflex.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreditDto {
    private BigDecimal amount;
    private int term;
    private BigDecimal monthlyPayment;
    private BigDecimal rate;
    private BigDecimal psk;
    private boolean isInsuranceEnabled;
    private boolean isSalaryClient;
    private List<PaymentScheduleElementDto> paymentSchedule;

    @Override
    public String toString() {
        return String.format("CreditDto{amount: %f, term: %d, monthlyPayment: %f, rate: %f, psk: %f, isInsuranceEnabled: %b, isSalaryClient: %b, paymentSchedule: %s}",
                amount, term, monthlyPayment, rate, psk, isInsuranceEnabled, isSalaryClient, paymentSchedule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, term, monthlyPayment, rate, psk, isInsuranceEnabled, isSalaryClient, paymentSchedule);
    }

    @Override
    public boolean equals(Object obj) {
        if (this.hashCode() != obj.hashCode()) {
            return false;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        CreditDto otherCD = (CreditDto) obj;
        return amount.equals(otherCD.getAmount()) && term == otherCD.getTerm()
                && monthlyPayment.equals(otherCD.getMonthlyPayment()) && rate.equals(otherCD.getRate())
                && isInsuranceEnabled == otherCD.isInsuranceEnabled() && isSalaryClient == otherCD.isSalaryClient()
                && paymentSchedule.equals(otherCD.getPaymentSchedule());
    }
}

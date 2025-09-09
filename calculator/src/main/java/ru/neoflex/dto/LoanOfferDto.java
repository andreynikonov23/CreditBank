package ru.neoflex.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema
public class LoanOfferDto implements Comparable<LoanOfferDto> {
    private UUID statementId;
    private BigDecimal requestedAmount;
    private BigDecimal totalAmount;
    private int term;
    private BigDecimal monthlyPayment;
    private BigDecimal rate;
    private boolean isInsuranceEnabled;
    private boolean isSalaryClient;

    @Override
    public String toString() {
        return String.format("LoanOfferDto{statementId: %s, requestedAmount: %f, totalAmount: %f, term: %d, monthlyPayment: %f, rate: %f, isInsuranceEnabled: %b, isSalaryClient: %b}",
                statementId, requestedAmount, totalAmount, term, monthlyPayment, rate, isInsuranceEnabled, isSalaryClient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statementId, requestedAmount, totalAmount, term, monthlyPayment, rate, isInsuranceEnabled, isSalaryClient);
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
        LoanOfferDto otherLOD = (LoanOfferDto) obj;
        return statementId.equals(otherLOD.getStatementId()) && requestedAmount.equals(otherLOD.getRequestedAmount())
                && totalAmount.equals(otherLOD.getTotalAmount()) && term == otherLOD.getTerm()
                && monthlyPayment.equals(otherLOD.getMonthlyPayment()) && rate.equals(otherLOD.getRate())
                && isInsuranceEnabled == otherLOD.isInsuranceEnabled() && isSalaryClient == otherLOD.isSalaryClient();
    }

    @Override
    public int compareTo(LoanOfferDto o) {
        return this.rate.compareTo(o.getRate());
    }
}

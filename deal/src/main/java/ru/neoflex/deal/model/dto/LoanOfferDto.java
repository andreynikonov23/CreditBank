package ru.neoflex.deal.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
public class LoanOfferDto implements Comparable<LoanOfferDto> {
    @NotNull(message = "statementId is null")
    private UUID statementId;
    @Min(value = 20000, message = "the amount must be greater than or equal to 20000")
    private BigDecimal requestedAmount;
    @Min(value = 20000, message = "the amount must be greater than or equal to 20000")
    private BigDecimal totalAmount;
    @Min(value = 6, message = "the term must be greater than or equal to 6")
    private int term;
    @Min(value = 0, message = "totalAmount must be greater than 0")
    private BigDecimal monthlyPayment;
    @Min(value = 0, message = "totalAmount must be greater than 0")
    private BigDecimal rate;
    @NotNull(message = "isInsuranceEnable is null")
    private boolean isInsuranceEnabled;
    @NotNull(message = "isInsuranceEnable is null")
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

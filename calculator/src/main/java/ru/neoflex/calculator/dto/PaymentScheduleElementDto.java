package ru.neoflex.calculator.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentScheduleElementDto {
    private int number;
    private LocalDate date;
    private BigDecimal totalPayment;
    private BigDecimal interestPayment;
    private BigDecimal debtPayment;
    private BigDecimal remainingDebt;

    @Override
    public String toString() {
        return String.format("PaymentScheduleElementDto{number: %d, date: %s, totalPayment: %f, interestPayment: %f, debtPayment: %f, remainingDebt: %f}",
                number, date, totalPayment, interestPayment, debtPayment, remainingDebt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, date, totalPayment, interestPayment, debtPayment, remainingDebt);
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
        PaymentScheduleElementDto otherPSED = (PaymentScheduleElementDto) obj;
        return number == otherPSED.getNumber() && date.equals(otherPSED.getDate())
                && totalPayment.equals(otherPSED.getTotalPayment()) && interestPayment.equals(otherPSED.getInterestPayment())
                && debtPayment.equals(otherPSED.getDebtPayment()) && remainingDebt.equals(otherPSED.getRemainingDebt());
    }
}

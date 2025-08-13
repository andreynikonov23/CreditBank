package ru.neoflex.calculator.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CreditAmountDto {
    private BigDecimal annualAmount;
    private BigDecimal psk;
    private BigDecimal monthlyPayment;
}

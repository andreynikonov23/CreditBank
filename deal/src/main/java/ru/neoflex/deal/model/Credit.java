package ru.neoflex.deal.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ru.neoflex.deal.model.dto.PaymentScheduleElementDto;
import ru.neoflex.deal.model.enums.CreditStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
public class Credit {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @NotNull(message = "credit_id is empty")
    @Column(name = "credit_id")
    private UUID id;
    @NotNull(message = "amount is null")
    private BigDecimal amount;
    @Positive(message = "term must be greater than 0")
    private Integer term;
    @NotNull(message = "monthly_payment is null")
    @Min(value = 0, message = "monthly_payment must not be less than 0")
    @Column(name = "monthly_payment")
    private BigDecimal monthlyPayment;
    @NotNull(message = "rate is null")
    @Min(value = 0, message = "rate must not be less than 0")
    private BigDecimal rate;
    @NotNull(message = "psk is null")
    @Min(value = 0, message = "psk must not be less than 0")
    private BigDecimal psk;
    @NotNull(message = "paymentSchedule is null")
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "payment_schedule")
    private List<PaymentScheduleElementDto> paymentSchedule;
    @Column(name = "insurance_enable")
    private boolean isInsuranceEnable;
    @Column(name = "salary_client")
    private boolean isSalaryClient;
    @Enumerated(EnumType.STRING)
    @Column(name = "credit_status")
    private CreditStatus creditStatus;

    @OneToMany(mappedBy = "credit", fetch = FetchType.LAZY)
    private Set<Statement> statements;

    @Override
    public String toString() {
        return String.format("Credit{id: %s, amount: %f, term: %d, monthlyPayment: %f, rate: %f, psk: %f," +
                        " paymentSchedule: %s, isInsuranceEnabled: %b, isSalaryClient: %b, creditStatus: %s}",
                id, amount, term, monthlyPayment, rate, psk, paymentSchedule, isInsuranceEnable, isSalaryClient, creditStatus);
    }
}

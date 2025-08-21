package ru.neoflex.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import ru.neoflex.validator.Adult;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoanStatementRequestDto {
    @NotNull(message = "amount is empty")
    @Min(value = 20000, message = "the amount must be greater than or equal to 20000")
    private BigDecimal amount;
    @Min(value = 6, message = "the term must be greater than or equal to 6")
    private int term;
    @NotNull(message = "firstname is empty")
    @Size(min = 2, max = 20, message = "The firstname must be between 2 and 20 Latin characters long.")
    private String firstname;
    @NotNull(message = "lastname is empty")
    @Size(min = 2, max = 20, message = "The lastname must be between 2 and 20 Latin characters long.")
    private String lastname;
    @Size(min = 2, max = 20, message = "The middleName must be between 2 and 20 Latin characters long.")
    private String middleName;
    @Email(message = "invalid email")
    private String email;
    @NotNull(message = "amount is empty")
    @Adult(message = "age must be over 18 years old.")
    private LocalDate birthdate;
    @NotNull(message = "passport series is empty")
    @Size(min = 4, max = 4, message = "invalid passport series")
    @Pattern(regexp = "^\\d+$", message = "invalid passport series")
    private String passportSeries;
    @NotNull(message = "passport number is empty")
    @Size(min = 6, max = 6, message = "invalid passport number")
    @Pattern(regexp = "^\\d+$", message = "invalid passport number")
    private String passportNumber;

    @Override
    public String toString() {
        return String.format("LoanStatementRequestDto{amount: %f, term: %d, firstname: %s, lastname: %s, middleName: %s, email: %s, birthdate: %s, passwordSeries: %s, passwordNumber: %s}",
                amount, term, firstname, lastname, middleName, email, birthdate, passportSeries, passportNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, term, firstname, lastname, middleName, email, birthdate, passportSeries, passportNumber);
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
        LoanStatementRequestDto otherLSRD = (LoanStatementRequestDto) obj;
        return amount.equals(otherLSRD.getAmount()) && term == otherLSRD.getTerm() && firstname.equals(otherLSRD.getFirstname())
                && lastname.equals(otherLSRD.getLastname()) && middleName.equals(otherLSRD.getMiddleName())
                && email.equals(otherLSRD.getEmail()) && birthdate.equals(otherLSRD.getBirthdate())
                && passportSeries.equals(otherLSRD.getPassportSeries()) && passportNumber.equals(otherLSRD.getPassportNumber());
    }
}

package ru.neoflex.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.neoflex.validator.Adult;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ScoringDataDto {
    @NotNull(message = "amount is null")
    private BigDecimal amount;
    @Positive(message = "term must be greater than 0")
    private int term;
    @NotNull(message = "firstname is empty")
    private String firstname;
    @NotNull(message = "lastname is empty")
    private String lastname;
    @NotNull(message = "middleName is empty")
    private String middleName;
    @NotNull(message = "gender is empty")
    private Gender gender;
    @NotNull(message = "birthdate is empty")
    @Past(message = "birthday should not be in the future")
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
    @NotNull(message = "passport issue date is empty")
    @Future(message = "invalid passport issue date")
    private LocalDate passportIssueDate;
    @NotNull(message = "passportIssueBranch is empty")
    private String passportIssueBranch;
    @NotNull(message = "maritalStatus is empty")
    private MaritalStatus maritalStatus;
    @Min(value = 0, message = "dependentAmount must not be less than 0")
    private int dependentAmount;
    private EmploymentDto employment;
    @NotNull(message = "accountNumber is empty")
    private String accountNumber;
    private boolean isInsuranceEnabled;
    private boolean isSalaryClient;

    @Override
    public String toString() {
        return String.format("ScoringDataDto{amount: %f, term: %d, firstname: %s, lastname: %s, middleName: %s," +
                        " gender: %s, birthdate: %s, passportSeries: %s, passportNumber: %s, passportIssueDate: %s," +
                        "passportIssueBranch: %s, maritalStatus: %s, dependentAmount: %d, employment: %s, accountNumber: %s," +
                        "isInsuranceEnabled: %b, isSalaryClient: %b}",
                amount, term, firstname, lastname, middleName, gender, birthdate, passportSeries, passportNumber, passportIssueDate,
                passportIssueBranch, maritalStatus, dependentAmount, employment, accountNumber, isInsuranceEnabled, isSalaryClient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, term, firstname, lastname, middleName, gender, birthdate, passportSeries, passportNumber,
                passportIssueDate, passportIssueBranch, maritalStatus, dependentAmount, employment, accountNumber, isInsuranceEnabled, isSalaryClient);
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
        ScoringDataDto otherSDD = (ScoringDataDto) obj;
        return amount.equals(otherSDD.getAmount()) && term == otherSDD.getTerm() && firstname.equals(otherSDD.getFirstname())
                && lastname.equals(otherSDD.getLastname()) && middleName.equals(otherSDD.getMiddleName())
                && gender.equals(otherSDD.getGender()) && birthdate.equals(otherSDD.getBirthdate())
                && passportSeries.equals(otherSDD.getPassportSeries()) && passportNumber.equals(otherSDD.getPassportNumber())
                && passportIssueDate.equals(otherSDD.getPassportIssueDate()) && passportIssueBranch.equals(otherSDD.getPassportIssueBranch())
                && maritalStatus.equals(otherSDD.getMaritalStatus()) && dependentAmount == otherSDD.getDependentAmount()
                && employment.equals(otherSDD.getEmployment()) && accountNumber.equals(otherSDD.getAccountNumber())
                && isInsuranceEnabled == otherSDD.isInsuranceEnabled() && isSalaryClient == otherSDD.isSalaryClient();
    }
}

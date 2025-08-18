package ru.neoflex.deal.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.neoflex.deal.model.enums.Gender;
import ru.neoflex.deal.model.enums.MaritalStatus;

import java.time.LocalDate;

@Data
public class FinishRegistrationRequestDto {
    @NotNull(message = "gender is empty")
    private Gender gender;
    @NotNull(message = "maritalStatus is empty")
    private MaritalStatus maritalStatus;
    private int dependentAmount;
    @NotNull(message = "passport issue date is empty")
    @Future(message = "invalid passport issue date")
    private LocalDate passportIssueDate;
    @NotNull(message = "passportIssueBranch is empty")
    private String passportIssueBranch;
    @JsonProperty("employment")
    private EmploymentDto employmentDto;
    @NotNull(message = "accountNumber is empty")
    private String accountNumber;
}

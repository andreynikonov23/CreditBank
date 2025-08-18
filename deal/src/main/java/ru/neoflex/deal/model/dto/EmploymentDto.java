package ru.neoflex.deal.model.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.neoflex.deal.model.enums.EmploymentPosition;
import ru.neoflex.deal.model.enums.EmploymentStatus;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmploymentDto {
    @NotNull(message = "employmentUUID is empty")
    private UUID employmentUUID;
    @NotNull(message = "employmentStatus is empty")
    private EmploymentStatus employmentStatus;
    @NotNull(message = "employerINN is empty")
    @Pattern(regexp = "^(\\d{12})$", message = "not valid employer INN")
    private String employerINN;
    @NotNull(message = "salary is empty")
    private BigDecimal salary;
    @NotNull(message = "position is empty")
    private EmploymentPosition position;
    @NotNull(message = "workExperienceTotal is empty")
    private int workExperienceTotal;
    @NotNull(message = "workExperienceCurrent is empty")
    private int workExperienceCurrent;

    @Override
    public String toString() {
        return String.format("Employment{employmentUUID: %s, employmentStatus: %s, employerINN: %s, salary: %f, position: %s, workExperienceTotal: %d, workExperienceCurrent: %d}",
                employmentUUID, employmentStatus, employerINN, salary, position, workExperienceTotal, workExperienceCurrent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employmentUUID, employmentStatus, employerINN, salary, position, workExperienceTotal, workExperienceCurrent);
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
        EmploymentDto otherED = (EmploymentDto) obj;
        return employmentUUID.equals(otherED.getEmploymentUUID()) && employmentStatus.equals(otherED.getEmploymentStatus()) && employerINN.equals(otherED.getEmployerINN())
                && salary.equals(otherED.getSalary()) && position.equals(otherED.getPosition())
                && workExperienceTotal == otherED.getWorkExperienceTotal() && workExperienceCurrent == otherED.getWorkExperienceCurrent();
    }
}

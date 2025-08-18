package ru.neoflex.deal.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ru.neoflex.deal.model.dto.EmploymentDto;
import ru.neoflex.deal.model.dto.PassportDto;
import ru.neoflex.deal.model.enums.Gender;
import ru.neoflex.deal.model.enums.MaritalStatus;
import ru.neoflex.deal.validator.Adult;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @NotNull(message = "statementId is null")
    @Column(name = "client_id")
    private UUID id;
    @NotNull(message = "firstname is empty")
    @Column(name = "first_name")
    private String firstname;
    @NotNull(message = "lastname is empty")
    @Column(name = "last_name")
    private String lastname;
    @NotNull(message = "middleName is empty")
    @Column(name = "middle_name")
    private String middleName;
    @NotNull(message = "amount is empty")
    @Adult(message = "age must be over 18 years old.")
    @Column(name = "birth_date")
    private LocalDate birthdate;
    @Email(message = "invalid email")
    private String email;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    @Column(name = "marital_status")
    private MaritalStatus maritalStatus;
    @Min(value = 0, message = "dependentAmount cannot be negative")
    @Column(name = "dependent_amount")
    private Integer dependentAmount;
    @NotNull(message = "passport data is empty")
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "passport_id")
    private PassportDto passportDto;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "employment_id")
    private EmploymentDto employmentDto;
    @Column(name = "account_number")
    private String accountNumber;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private Set<Statement> statements;

    @Override
    public String toString() {
        return String.format("Client{id: %s, firstname: %s, lastname: %s, middleName: %s," +
                        " birthdate: %s, email: %s, gender: %s,  maritalStatus: %s, dependentAmount: %d," +
                        " passportDto: %s, employmentDto: %s, accountNumber: %s}",
                id, firstname, lastname, middleName, birthdate, email, gender,
                maritalStatus, dependentAmount, passportDto, employmentDto, accountNumber);
    }
}

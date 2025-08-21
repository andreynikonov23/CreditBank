package ru.neoflex.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class PassportDto {
    private UUID passportUUID;
    private String series;
    private String number;
    private String issueBranch;
    private LocalDate issueDate;

    @Override
    public String toString() {
        return String.format("PassportDto{passportUUID: %s, series: %s, number: %s, issueBranch: %s, issueDate: %s}",
                passportUUID, series, number, issueBranch, issueDate);
    }
}

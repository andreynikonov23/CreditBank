package ru.neoflex.dto;

import lombok.Data;
import ru.neoflex.enums.ChangeType;

import java.time.LocalDate;

@Data
public class StatementStatusHistoryDto {
    private String status;
    private LocalDate time;
    private ChangeType changeType;
}

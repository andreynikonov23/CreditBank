package ru.neoflex.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.neoflex.enums.MessageTheme;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailMessage {
    private String address;
    private MessageTheme theme;
    private String statementId;
    private String text;
}

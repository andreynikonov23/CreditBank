package ru.neoflex.utils;

import lombok.extern.slf4j.Slf4j;
import ru.neoflex.dto.FinishRegistrationRequestDto;
import ru.neoflex.model.Client;

@Slf4j
public class ClientUpdater {
    public static void setFinishDataForClient(Client client, FinishRegistrationRequestDto finishRegistrationRequestDto) {
        log.debug("completion of client registration {} with final data {}", client, finishRegistrationRequestDto);
        client.setGender(finishRegistrationRequestDto.getGender());
        client.setMaritalStatus(finishRegistrationRequestDto.getMaritalStatus());
        client.setDependentAmount(finishRegistrationRequestDto.getDependentAmount());
        client.getPassportDto().setIssueDate(finishRegistrationRequestDto.getPassportIssueDate());
        client.getPassportDto().setIssueBranch(finishRegistrationRequestDto.getPassportIssueBranch());
        client.setEmploymentDto(finishRegistrationRequestDto.getEmploymentDto());
        client.setAccountNumber(finishRegistrationRequestDto.getAccountNumber());
        log.debug("the client's registration data has been entered: {}", client);
    }
}

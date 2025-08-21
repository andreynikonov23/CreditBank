package ru.neoflex.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.neoflex.model.Credit;
import ru.neoflex.repository.CreditRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Slf4j
public class CreditDaoImpl implements DAO<Credit, UUID> {
    private final CreditRepository creditRepository;

    public CreditDaoImpl(CreditRepository creditRepository) {
        this.creditRepository = creditRepository;
    }

    @Override
    public List<Credit> getAll() {
        log.trace("Starting the select for all credits in the database");
        List<Credit> credits = creditRepository.findAll();
        log.debug("The data search was successful, records were found [{}]", credits.size());
        return credits;
    }

    @Override
    public Credit findById(UUID uuid) {
        log.trace("Starting the select client by id = {} in the database", uuid);
        Credit credit = creditRepository.findById(uuid).orElseThrow(() -> {
            String errorMessage = String.format("credit data with uuid = %s not found", uuid);
            log.error(errorMessage);
            return new NoSuchElementException(errorMessage);
        });
        log.debug("the credit's data is found. {}", credit);
        return credit;
    }

    @Override
    public void savaAndFlush(Credit credit) {
        log.trace("The beginning of adding credit data {} to the database", credit);
        creditRepository.saveAndFlush(credit);
        log.debug("credit data {} updated", credit);
    }

    @Override
    public void delete(Credit credit) {
        log.trace("The beginning of deleting credit data {} to the database", credit);
        creditRepository.delete(credit);
        log.debug("credit data {} deleted", credit);
    }
}

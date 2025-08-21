package ru.neoflex.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.neoflex.model.Statement;
import ru.neoflex.repository.StatementRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Slf4j
public class StatementDaoImpl implements DAO<Statement, UUID> {
    private final StatementRepository statementRepository;

    @Autowired
    public StatementDaoImpl(StatementRepository statementRepository) {
        this.statementRepository = statementRepository;
    }

    @Override
    public List<Statement> getAll() {
        log.trace("Starting the select for all credits in the database");
        List<Statement> statements = statementRepository.findAll();
        log.debug("The data search was successful, records were found [{}]", statements.size());
        return statements;
    }

    @Override
    public Statement findById(UUID uuid) {
        log.trace("Starting the select statement by id = {} in the database", uuid);
        Statement statement = statementRepository.findById(uuid).orElseThrow(() -> {
            log.error("statement data with uuid = {} not found", uuid);
            return new NoSuchElementException("No value present");
        });
        log.debug("the statement's data is found. {}", statement);
        return statement;
    }

    @Override
    public void savaAndFlush(Statement statement) {
        log.trace("The beginning of adding statement data {} to the database", statement);
        statementRepository.saveAndFlush(statement);
        log.debug("statement data {} updated", statement);
    }

    @Override
    public void delete(Statement statement) {
        log.trace("The beginning of deleting statement data {} to the database", statement);
        statementRepository.delete(statement);
        log.debug("statement data {} deleted", statement);
    }
}

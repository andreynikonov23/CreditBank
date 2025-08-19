package ru.neoflex.deal.exceptions;

public class ScoringException extends RuntimeException {
    public ScoringException(String statusText) {
        super(statusText);
    }
}

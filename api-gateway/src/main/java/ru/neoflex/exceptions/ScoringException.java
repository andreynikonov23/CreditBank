package ru.neoflex.exceptions;

public class ScoringException extends RuntimeException {
    public ScoringException(String statusText) {
        super(statusText);
    }
}

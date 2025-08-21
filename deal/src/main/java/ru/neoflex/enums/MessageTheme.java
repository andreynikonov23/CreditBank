package ru.neoflex.enums;

public enum MessageTheme {
    RESULT_CREDIT_CALC("Результат счета кредита"),
    CREDIT_AGREEMENT("Кредитный договор"),
    SIGNATURE_REGISTRATION("Регистрация подписи");

    private final String name;

    MessageTheme(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

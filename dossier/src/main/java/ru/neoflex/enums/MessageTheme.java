package ru.neoflex.enums;

public enum MessageTheme {
    FINISH_REGISTRATION("Завершение регистрации"),
    RESULT_CREDIT_CALC("Результат счета кредита"),
    CREDIT_AGREEMENT("Кредитный договор"),
    SIGNATURE_REGISTRATION("Регистрация подписи"),
    CREDIT_ISSUED("Кредит одобрен"),
    STATEMENT_DENIED("Отклонение по заявке");

    private final String name;

    MessageTheme(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

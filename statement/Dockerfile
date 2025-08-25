FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

RUN if ! getent group spring >/dev/null 2>&1; then \
        groupadd -r spring; \
    fi && \
    if ! id -u spring >/dev/null 2>&1; then \
        useradd -r -g spring spring; \
    fi

USER spring:spring

COPY target/*.jar app.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "app.jar"]
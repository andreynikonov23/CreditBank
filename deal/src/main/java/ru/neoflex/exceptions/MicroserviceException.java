package ru.neoflex.exceptions;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.HttpClientErrorException;
import ru.neoflex.enums.MicroserviceName;

public class MicroserviceException extends HttpClientErrorException {
    private MicroserviceName microserviceName;
    public MicroserviceException(MicroserviceName microserviceName, HttpStatusCode statusCode) {
        super(statusCode);
        this.microserviceName = microserviceName;
    }

    public MicroserviceException(MicroserviceName microserviceName, HttpStatusCode statusCode, String statusText) {
        super(statusCode, statusText);
        this.microserviceName = microserviceName;
    }

    public MicroserviceName getMicroserviceName() {
        return microserviceName;
    }

    public void setMicroserviceName(MicroserviceName microserviceName) {
        this.microserviceName = microserviceName;
    }
}

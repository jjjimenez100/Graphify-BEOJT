package io.toro.ojtbe.jimenez.Graphify.core.generators.service;

public final class ServiceGeneratorException extends RuntimeException {
    public ServiceGeneratorException() {
        super();
    }

    public ServiceGeneratorException(String message) {
        super(message);
    }

    public ServiceGeneratorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceGeneratorException(Throwable cause) {
        super(cause);
    }

    protected ServiceGeneratorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

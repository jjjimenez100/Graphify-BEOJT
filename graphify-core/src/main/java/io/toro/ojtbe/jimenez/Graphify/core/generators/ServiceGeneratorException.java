package io.toro.ojtbe.jimenez.Graphify.core.generators;

final class ServiceGeneratorException extends Exception {
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

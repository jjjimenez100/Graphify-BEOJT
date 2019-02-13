package io.toro.ojtbe.jimenez.Graphify.core.generators.repository;

public final class RepositoryGeneratorException extends RuntimeException{
    public RepositoryGeneratorException() {
        super();
    }

    public RepositoryGeneratorException(String message) {
        super(message);
    }

    public RepositoryGeneratorException(String message, Throwable cause) {
        super(message, cause);
    }

    public RepositoryGeneratorException(Throwable cause) {
        super(cause);
    }

    protected RepositoryGeneratorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

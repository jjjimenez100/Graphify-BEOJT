package io.toro.ojtbe.jimenez.Graphify.core.generators.resolver;

public final class QueryGeneratorException extends RuntimeException {
    public QueryGeneratorException() {
        super();
    }

    public QueryGeneratorException(String message) {
        super(message);
    }

    public QueryGeneratorException(String message, Throwable cause) {
        super(message, cause);
    }

    public QueryGeneratorException(Throwable cause) {
        super(cause);
    }

    protected QueryGeneratorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

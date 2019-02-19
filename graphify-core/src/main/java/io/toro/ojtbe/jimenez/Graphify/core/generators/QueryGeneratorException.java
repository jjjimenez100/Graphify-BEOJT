package io.toro.ojtbe.jimenez.Graphify.core.generators;

public class QueryGeneratorException extends Exception {
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

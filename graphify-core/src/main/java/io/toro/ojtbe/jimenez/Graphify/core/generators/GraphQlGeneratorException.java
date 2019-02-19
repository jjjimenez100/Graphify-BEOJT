package io.toro.ojtbe.jimenez.Graphify.core.generators;

public final class GraphQlGeneratorException extends Exception{
    public GraphQlGeneratorException() {
        super();
    }

    public GraphQlGeneratorException(String message) {
        super(message);
    }

    public GraphQlGeneratorException(String message, Throwable cause) {
        super(message, cause);
    }

    public GraphQlGeneratorException(Throwable cause) {
        super(cause);
    }

    protected GraphQlGeneratorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

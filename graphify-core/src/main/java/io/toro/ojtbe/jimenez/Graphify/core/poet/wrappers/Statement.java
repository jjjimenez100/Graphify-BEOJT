package io.toro.ojtbe.jimenez.Graphify.core.poet.wrappers;

import java.util.Arrays;
import java.util.Objects;

public final class Statement {
    private final String statement;
    private final Object[] arguments;

    public Statement(String statement, Object... arguments){
        this.statement = statement;
        this.arguments = arguments;
    }

    public String getStatement() {
        return statement;
    }

    public Object[] getArguments() {
        return arguments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Statement statement1 = (Statement) o;
        return Objects.equals(statement, statement1.statement) &&
                Arrays.equals(arguments, statement1.arguments);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(statement);
        result = 31 * result + Arrays.hashCode(arguments);
        return result;
    }

    @Override
    public String toString() {
        return "Statement{" +
                "statement='" + statement + '\'' +
                ", arguments=" + Arrays.toString(arguments) +
                '}';
    }
}

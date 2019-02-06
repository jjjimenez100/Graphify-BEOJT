package io.toro.ojtbe.jimenez.Graphify.core.poet.wrappers;

import javax.lang.model.element.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class Method {
    private final String name;
    private final List<Variable> parameters;
    private final List<Modifier> modifiers;
    private final List<ClassNameWrapper> annotations;
    private final List<Statement> statements;
    private final List<ClassNameWrapper> types;

    private Method(Builder builder){
        this.name = builder.name;
        this.parameters = Collections.unmodifiableList(builder.parameters);
        this.modifiers = Collections.unmodifiableList(builder.modifiers);
        this.annotations = Collections.unmodifiableList(builder.annotations);
        this.types = Collections.unmodifiableList(builder.types);
        this.statements = Collections.unmodifiableList(builder.statements);
    }

    public static class Builder{
        private final String name;
        private final List<Variable> parameters;
        private final List<Modifier> modifiers;
        private final List<ClassNameWrapper> annotations;
        private final List<Statement> statements;
        private final List<ClassNameWrapper> types;

        public Builder(String name, ClassNameWrapper types){
            this.name = name;
            this.parameters = new ArrayList<>();
            this.modifiers = new ArrayList<>();
            this.annotations = new ArrayList<>();
            this.statements = new ArrayList<>();
            this.types = new ArrayList<>();
            this.types.add(types);
        }

        public Builder addStatement(Statement statement){
            statements.add(statement);
            return this;
        }

        public Builder addParameter(Variable parameter){
            parameters.add(parameter);
            return this;
        }

        public Builder addModifier(String modifier){
            modifiers.add(Modifier.valueOf(modifier));
            return this;
        }

        public Builder addAnnotation(ClassNameWrapper annotation){
            annotations.add(annotation);
            return this;
        }

        public Builder addType(ClassNameWrapper type){
            types.add(type);
            return this;
        }

        public Method build(){
            return new Method(this);
        }
    }

    public String getName() {
        return name;
    }

    public List<Variable> getParameters() {
        return parameters;
    }

    public List<Modifier> getModifiers() {
        return modifiers;
    }

    public List<ClassNameWrapper> getAnnotations() {
        return annotations;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    public List<ClassNameWrapper> getTypes() {
        return types;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Method method = (Method) o;
        return Objects.equals(name, method.name) &&
                Objects.equals(parameters, method.parameters) &&
                Objects.equals(modifiers, method.modifiers) &&
                Objects.equals(annotations, method.annotations) &&
                Objects.equals(statements, method.statements) &&
                Objects.equals(types, method.types);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, parameters, modifiers, annotations, statements, types);
    }

    @Override
    public String toString() {
        return "Method{" +
                "name='" + name + '\'' +
                ", parameters=" + parameters +
                ", modifiers=" + modifiers +
                ", annotations=" + annotations +
                ", statements=" + statements +
                ", types=" + types +
                '}';
    }
}

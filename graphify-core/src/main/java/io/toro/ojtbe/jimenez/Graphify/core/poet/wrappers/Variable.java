package io.toro.ojtbe.jimenez.Graphify.core.poet.wrappers;

import javax.lang.model.element.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class Variable {
    private final List<ClassNameWrapper> types;
    private final List<ClassNameWrapper> annotations;
    private final List<Modifier> modifiers;
    private final String name;

    private Variable(Builder builder){
        this.name = builder.name;
        this.types = Collections.unmodifiableList(builder.types);
        this.annotations = Collections.unmodifiableList(builder.annotations);
        this.modifiers = Collections.unmodifiableList(builder.modifiers);
    }

    public static class Builder{
        private final List<ClassNameWrapper> types;
        private final List<ClassNameWrapper> annotations;
        private final List<Modifier> modifiers;
        private final String name;

        public Builder(String name, ClassNameWrapper dataType){
            this.name = name;
            this.types = new ArrayList<>();
            this.annotations = new ArrayList<>();
            this.modifiers = new ArrayList<>();
            addType(dataType);
        }

        public Builder addAnnotation(ClassNameWrapper annotation){
            annotations.add(annotation);
            return this;
        }

        public Builder addModifier(String modifier){
            modifiers.add(Modifier.valueOf(modifier));
            return this;
        }

        public Builder addType(ClassNameWrapper dataType){
            types.add(dataType);
            return this;
        }

        public Variable build(){
            return new Variable(this);
        }
    }

    public List<ClassNameWrapper> getTypes() {
        return types;
    }

    public List<ClassNameWrapper> getAnnotations() {
        return annotations;
    }

    public List<Modifier> getModifiers() {
        return modifiers;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variable variable = (Variable) o;
        return Objects.equals(types, variable.types) &&
                Objects.equals(annotations, variable.annotations) &&
                Objects.equals(modifiers, variable.modifiers) &&
                Objects.equals(name, variable.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(types, annotations, modifiers, name);
    }

    @Override
    public String toString() {
        return "Variable{" +
                "types=" + types +
                ", annotations=" + annotations +
                ", modifiers=" + modifiers +
                ", name='" + name + '\'' +
                '}';
    }
}

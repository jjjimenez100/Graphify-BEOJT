package io.toro.ojtbe.jimenez.Graphify.core.poet.wrappers;

import javax.lang.model.element.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class Interface {
    private final String name;
    private final List<Modifier> modifiers;
    private final List<ClassNameWrapper> annotations;
    private final List<ClassNameWrapper> parentTypes;

    private Interface(Builder builder){
        this.name = builder.name;
        this.modifiers = Collections.unmodifiableList(builder.modifiers);
        this.annotations = Collections.unmodifiableList(builder.annotations);
        this.parentTypes = Collections.unmodifiableList(builder.parentTypes);
    }

    public static class Builder{
        private final String name;
        private final List<Modifier> modifiers;
        private final List<ClassNameWrapper> annotations;
        private final List<ClassNameWrapper> parentTypes;

        public Builder(String name){
            this.name = name;
            this.modifiers = new ArrayList<>();
            this.annotations = new ArrayList<>();
            this.parentTypes = new ArrayList<>();
        }

        public Builder parent(ClassNameWrapper parent){
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

        public Builder addParentType(ClassNameWrapper parentType){
            parentTypes.add(parentType);
            return this;
        }

        public Interface build(){
            return new Interface(this);
        }
    }

    public String getName() {
        return name;
    }

    public List<Modifier> getModifiers() {
        return modifiers;
    }

    public List<ClassNameWrapper> getAnnotations() {
        return annotations;
    }
    public List<ClassNameWrapper> getParentTypes() {
        return parentTypes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Interface that = (Interface) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(modifiers, that.modifiers) &&
                Objects.equals(annotations, that.annotations) &&
                Objects.equals(parentTypes, that.parentTypes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, modifiers, annotations, parentTypes);
    }

    @Override
    public String toString() {
        return "Interface{" +
                "name='" + name + '\'' +
                ", modifiers=" + modifiers +
                ", annotations=" + annotations +
                ", parentTypes=" + parentTypes +
                '}';
    }
}

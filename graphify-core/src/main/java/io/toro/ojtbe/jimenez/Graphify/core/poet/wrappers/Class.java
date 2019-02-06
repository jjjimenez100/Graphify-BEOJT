package io.toro.ojtbe.jimenez.Graphify.core.poet.wrappers;

import javax.lang.model.element.Modifier;
import java.util.*;

public final class Class {
    private final String name;
    private final List<Modifier> modifiers;
    private final List<ClassNameWrapper> interfaces;
    private final List<Method> methods;
    private final List<ClassNameWrapper> annotations;
    private final List<Variable> fields;
    private final List<String> typeVariables;
    private final List<ClassNameWrapper> parentTypes;

    private Class(Builder builder){
        this.name = builder.name;
        this.parentTypes = Collections.unmodifiableList(builder.parentTypes);
        this.modifiers = Collections.unmodifiableList(builder.modifiers);
        this.interfaces = Collections.unmodifiableList(builder.interfaces);
        this.methods = Collections.unmodifiableList(builder.methods);
        this.annotations = Collections.unmodifiableList(builder.annotations);
        this.fields = Collections.unmodifiableList(builder.fields);
        this.typeVariables = Collections.unmodifiableList(builder.typeVariables);
    }

    public static class Builder{
        private final String name;
        private final List<Modifier> modifiers;
        private final List<ClassNameWrapper> interfaces;
        private final List<Method> methods;
        private final List<ClassNameWrapper> annotations;
        private final List<Variable> fields;
        private final List<String> typeVariables;
        private List<ClassNameWrapper> parentTypes;

        public Builder(String name){
            this.name = name;
            this.modifiers = new ArrayList<>();
            this.interfaces = new ArrayList<>();
            this.methods = new ArrayList<>();
            this.annotations = new ArrayList<>();
            this.fields = new ArrayList<>();
            this.typeVariables = new ArrayList<>();
            this.parentTypes = new ArrayList<>();
        }

        public Builder addParentType(ClassNameWrapper parentType){
            parentTypes.add(parentType);
            return this;
        }

        public Builder addModifier(String modifier){
            modifiers.add(Modifier.valueOf(modifier));
            return this;
        }

        public Builder addInterface(ClassNameWrapper interfaceRep){
            interfaces.add(interfaceRep);
            return this;
        }

        public Builder addMethod(Method method){
            methods.add(method);
            return this;
        }

        public Builder addAnnotation(ClassNameWrapper annotation){
            annotations.add(annotation);
            return this;
        }

        public Builder addField(Variable field){
            fields.add(field);
            return this;
        }

        public Builder addTypeParameter(String typeName){
            typeVariables.add(typeName);
            return this;
        }

        public Class build(){
            return new Class(this);
        }
    }

    public String getName() {
        return name;
    }

    public List<Modifier> getModifiers() {
        return modifiers;
    }

    public List<ClassNameWrapper> getInterfaces() {
        return interfaces;
    }

    public List<Method> getMethods() {
        return methods;
    }

    public List<ClassNameWrapper> getAnnotations() {
        return annotations;
    }

    public List<Variable> getFields() {
        return fields;
    }

    public List<String> getTypeVariables() {
        return typeVariables;
    }

    public List<ClassNameWrapper> getParentTypes() {
        return parentTypes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Class aClass = (Class) o;
        return Objects.equals(name, aClass.name) &&
                Objects.equals(modifiers, aClass.modifiers) &&
                Objects.equals(interfaces, aClass.interfaces) &&
                Objects.equals(methods, aClass.methods) &&
                Objects.equals(annotations, aClass.annotations) &&
                Objects.equals(fields, aClass.fields) &&
                Objects.equals(typeVariables, aClass.typeVariables) &&
                Objects.equals(parentTypes, aClass.parentTypes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, modifiers, interfaces, methods, annotations, fields, typeVariables, parentTypes);
    }

    @Override
    public String toString() {
        return "Class{" +
                "name='" + name + '\'' +
                ", modifiers=" + modifiers +
                ", interfaces=" + interfaces +
                ", methods=" + methods +
                ", annotations=" + annotations +
                ", fields=" + fields +
                ", typeVariables=" + typeVariables +
                ", parentTypes=" + parentTypes +
                '}';
    }
}

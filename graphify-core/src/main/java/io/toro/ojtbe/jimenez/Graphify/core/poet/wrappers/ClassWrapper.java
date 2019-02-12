package io.toro.ojtbe.jimenez.Graphify.core.poet.wrappers;

import javax.lang.model.element.Modifier;
import java.util.*;

public final class ClassWrapper {
    private final String name;
    private final String packageStatement;
    private final List<Modifier> modifiers;
    private final List<ClassNameWrapper> interfaces;
    private final List<Method> methods;
    private final List<ClassNameWrapper> annotations;
    private final List<Variable> fields;
    private final List<String> typeVariables;
    private final List<ClassNameWrapper> parentTypes;

    private ClassWrapper(Builder builder){
        this.name = builder.name;
        this.packageStatement = builder.packageStatement;
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
        private final String packageStatement;
        private final List<Modifier> modifiers;
        private final List<ClassNameWrapper> interfaces;
        private final List<Method> methods;
        private final List<ClassNameWrapper> annotations;
        private final List<Variable> fields;
        private final List<String> typeVariables;
        private final List<ClassNameWrapper> parentTypes;

        public Builder(String name, String packageStatement){
            this.name = name;
            this.packageStatement = packageStatement;
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

        public ClassWrapper build(){
            return new ClassWrapper(this);
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

    public String getPackageStatement() {
        return packageStatement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassWrapper aClassWrapper = (ClassWrapper) o;
        return name.equals(aClassWrapper.name) &&
                packageStatement.equals(aClassWrapper.packageStatement) &&
                modifiers.equals(aClassWrapper.modifiers) &&
                interfaces.equals(aClassWrapper.interfaces) &&
                methods.equals(aClassWrapper.methods) &&
                annotations.equals(aClassWrapper.annotations) &&
                fields.equals(aClassWrapper.fields) &&
                typeVariables.equals(aClassWrapper.typeVariables) &&
                parentTypes.equals(aClassWrapper.parentTypes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, packageStatement, modifiers, interfaces, methods, annotations, fields, typeVariables, parentTypes);
    }

    @Override
    public String toString() {
        return "ClassWrapper{" +
                "name='" + name + '\'' +
                ", packageStatement='" + packageStatement + '\'' +
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

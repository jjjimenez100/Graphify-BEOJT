package io.toro.ojtbe.jimenez.Graphify.core.poet.wrappers;

import java.util.Objects;

public final class ClassNameWrapper {
    private final String packageName;
    private final String className;

    public ClassNameWrapper(String packageName, String className){
        this.packageName = packageName;
        this.className = className;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getClassName() {
        return className;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassNameWrapper aClassNameWrapper = (ClassNameWrapper) o;
        return Objects.equals(packageName, aClassNameWrapper.packageName) &&
                Objects.equals(className, aClassNameWrapper.className);
    }

    @Override
    public int hashCode() {
        return Objects.hash(packageName, className);
    }

    @Override
    public String toString() {
        return "ClassNameWrapper{" +
                "packageName='" + packageName + '\'' +
                ", className='" + className + '\'' +
                '}';
    }
}

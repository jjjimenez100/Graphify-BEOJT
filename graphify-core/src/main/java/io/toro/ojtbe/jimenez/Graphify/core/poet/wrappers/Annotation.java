package io.toro.ojtbe.jimenez.Graphify.core.poet.wrappers;

import java.util.Objects;

public final class Annotation {
    public static final Annotation AUTOWIRED = new Annotation(
            "org.springframework.beans.factory.annotation",
            "Autowired"
    );

    public static final Annotation TRANSACTION = new Annotation(
            "javax.transaction",
            "Transactional"
    );

    public static final Annotation SERVICE = new Annotation(
            "org.springframework.stereotype",
            "Service"
    );

    public static final Annotation COMPONENT = new Annotation(
            "org.springframework.stereotype",
            "Component"
    );

    private final ClassNameWrapper annotation;

    private Annotation(String packageStatement,
                      String className){
        this.annotation = new ClassNameWrapper(
                packageStatement,
                className
        );
    }

    public ClassNameWrapper getAnnotation() {
        return annotation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Annotation that = (Annotation) o;
        return annotation.equals(that.annotation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(annotation);
    }

    @Override
    public String toString() {
        return "Annotation{" +
                "annotation=" + annotation +
                '}';
    }
}

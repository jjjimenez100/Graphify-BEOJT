package io.toro.ojtbe.jimenez.Graphify.core;

import com.squareup.javapoet.ClassName;

public enum Annotation {
    TRANSACTION(
            "javax.transaction",
            "Transactional"
    ),

    SERVICE(
            "org.springframework.beans.factory.annotation",
            "Autowired"
    ),

    AUTOWIRED(
            "org.springframework.beans.factory.annotation",
            "Autowired"
    ),

    COMPONENT(
            "org.springframework.stereotype",
            "Component"
    );

    private final ClassName annotation;

    private Annotation(String packageStatement,
                       String className){
        this.annotation = ClassName.get(
                packageStatement, className
        );
    }

    public ClassName getAnnotation(){
        return annotation;
    }
}

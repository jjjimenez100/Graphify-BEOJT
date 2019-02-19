package io.toro.ojtbe.jimenez.Graphify.core.poet;

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

    Annotation(String packageStatement,
                       String className){
        this.annotation = ClassName.get(
                packageStatement, className
        );
    }

    public ClassName getAnnotation(){
        return annotation;
    }
}

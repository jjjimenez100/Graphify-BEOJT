package io.toro.ojtbe.jimenez.Graphify.core.poet;

import com.squareup.javapoet.ClassName;

public enum ClassNames {
    JPA_REPOSITORY(
            "org.springframework.data.jpa.repository",
            "JpaRepository"
    ),

    CRUD_REPOSITORY(
        "org.springframework.data.repository",
        "CrudRepository"
    ),

    GRAPHQL_QUERY_RESOLVER(
            "com.coxautodev.graphql.tools",
            "GraphQLQueryResolver"
    ),

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

    ClassNames(String packageStatement,
               String className){
        this.annotation = ClassName.get(
                packageStatement, className
        );
    }

    public ClassName getAnnotation(){
        return annotation;
    }
}

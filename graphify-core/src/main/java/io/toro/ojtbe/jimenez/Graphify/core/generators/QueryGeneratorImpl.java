package io.toro.ojtbe.jimenez.Graphify.core.generators;

import com.squareup.javapoet.*;
import io.toro.ojtbe.jimenez.Graphify.core.GraphEntity;
import io.toro.ojtbe.jimenez.Graphify.core.poet.ClassNameUtil;
import io.toro.ojtbe.jimenez.Graphify.core.poet.ClassNames;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

final class QueryGeneratorImpl implements QueryGenerator{
    private final String name;
    private final Modifier access;
    private final ClassName parent;
    private final String serviceName;
    private final Path baseDirectory;

    private QueryGeneratorImpl(Builder builder){
        this.name = builder.name;
        this.access = builder.access;
        this.parent = builder.parent;
        this.serviceName = builder.serviceName;
        this.baseDirectory = builder.baseDirectory;
    }

    static class Builder{
        private String name;
        private Modifier access;
        private ClassName parent;
        private String serviceName;
        private Path baseDirectory;

        Builder(){
            this.name = "QueryResolver";
            this.access = Modifier.PUBLIC;
            this.parent = ClassNames.GRAPHQL_QUERY_RESOLVER
                    .getClassName();
            this.serviceName = "Service";
            this.baseDirectory = Paths.get("src/main/java");
        }

        Builder name(String name){
            this.name = name;
            return this;
        }

        Builder access(Modifier access){
            this.access = access;
            return this;
        }

        Builder parent(ClassName parent){
            this.parent = parent;
            return this;
        }

        Builder serviceName(String serviceName){
            this.serviceName = serviceName;
            return this;
        }

        Builder baseDirectory(Path baseDirectory){
            this.baseDirectory = baseDirectory;
            return this;
        }

        QueryGeneratorImpl build(){
            return new QueryGeneratorImpl(this);
        }
    }

    @Override
    public List<GraphEntity> process(List<GraphEntity> input) {
        try{
            generate(input);
        } catch(QueryGeneratorException e){
            e.printStackTrace();

            return Collections.emptyList();
        }

        return input;
    }

    @Override
    public void generate(List<GraphEntity> graphEntities)
            throws QueryGeneratorException {

        String fullyQualifiedName =
                graphEntities.get(0).getFullyQualifiedName();

        String packageStatement
                = fullyQualifiedName.substring(
                        0, fullyQualifiedName.lastIndexOf(".")
        );

        TypeSpec resolver = createQueryClass(graphEntities);

        JavaFile file = JavaFile.builder(packageStatement, resolver)
                .skipJavaLangImports(true)
                .indent("   ")
                .build();

        try {
            file.writeTo(baseDirectory);

        } catch (IOException e) {
            e.printStackTrace();

            throw new QueryGeneratorException(
                    "Failed to generate query resolver ");
        }
    }

    private TypeSpec createQueryClass(List<GraphEntity> graphEntities){
        TypeSpec.Builder queryClassBuilder = TypeSpec.classBuilder(name)
                .addAnnotation(ClassNames.COMPONENT.getClassName())
                .addSuperinterface(parent)
                .addModifiers(access, Modifier.FINAL);

        MethodSpec.Builder constructorBuilder = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC);

        for(GraphEntity graphEntity: graphEntities){
            String fullyQualifiedName =
                    graphEntity.getFullyQualifiedName();

            int nameBounds
                    = fullyQualifiedName.lastIndexOf(".");

            String packageStatement
                    = fullyQualifiedName.substring(0, nameBounds);

            String entityName
                    = fullyQualifiedName.substring(nameBounds + 1);

            String serviceType = entityName + serviceName;

            ClassName service = ClassName.get(
                    packageStatement,
                    serviceType
            );

            String serviceName = Character.toLowerCase(serviceType.charAt(0)) +
                    serviceType.substring(1);

            queryClassBuilder.addField(
                    createField(
                            serviceName,
                            service
                    )
            );

            constructorBuilder.addParameter(
                    createConstructorParam(
                            serviceName,
                            service
                    )
            );

            CodeBlock initializer = createInitializer(serviceName);
            constructorBuilder.addStatement(initializer);

            ClassName entity = ClassName.get(
                    packageStatement, entityName
            );

            queryClassBuilder.addMethod(
                    createGetAll(serviceName, entity)
            );

            queryClassBuilder.addMethod(
                    createGetById(
                            serviceName,
                            entity,
                            ClassName.get(
                                    "",
                                    graphEntity.getIdType()
                            )
                    )
            );
        }

        queryClassBuilder.addMethod(constructorBuilder.build());

        return queryClassBuilder.build();
    }

    private FieldSpec createField(String serviceName,
                                  ClassName serviceClass){
        return FieldSpec.builder(serviceClass, serviceName)
                .addModifiers(Modifier.PRIVATE, Modifier.FINAL)
                .addAnnotation(ClassNames.AUTOWIRED.getClassName())
                .build();
    }

    private ParameterSpec createConstructorParam(String serviceName,
                                                 ClassName serviceClass){
        return ParameterSpec.builder(serviceClass, serviceName)
                .build();
    }

    private CodeBlock createInitializer(String serviceName){
        return CodeBlock.builder()
                .add(
                        "this.$L = $L",
                        serviceName,
                        serviceName
                ).build();
    }

    private MethodSpec createGetAll(String serviceName,
                                    ClassName entityClassName){
        CodeBlock getAllEntity = CodeBlock.builder()
                .add(
                        "return $L.getAll();\n", serviceName
                )
                .build();

        String entityName = entityClassName.simpleName();
        String pluralEntityName = entityName.toLowerCase() + "s";

        ClassName iterable = ClassName.get(
                "", "Iterable"
        );

        return MethodSpec.methodBuilder(pluralEntityName)
                .returns(ParameterizedTypeName.get(
                        iterable, entityClassName
                ))
                .addCode(getAllEntity)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .build();
    }

    private MethodSpec createGetById(String serviceName,
                                     ClassName entityClassName,
                                     ClassName idType){
        //entity get individual
        CodeBlock getIndividual = CodeBlock.builder()
                .add(
                        "return $L.getById(id)" +
                                ".orElseThrow(RuntimeException::new);\n", serviceName
                ).build();

        ParameterSpec idParameter = ParameterSpec.builder(
                ClassNameUtil.INSTANCE.toBoxedType(idType), "id")
                .build();

        String singularEntityName = entityClassName.simpleName().toLowerCase();

        return MethodSpec.methodBuilder(singularEntityName)
                .returns(entityClassName)
                .addParameter(idParameter)
                .addCode(getIndividual)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .build();
    }
}
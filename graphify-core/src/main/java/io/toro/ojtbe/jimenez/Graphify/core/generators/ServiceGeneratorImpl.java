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

final class ServiceGeneratorImpl implements ServiceGenerator {
    private final String name;
    private final Modifier access;
    private final ClassName parent;
    private final String repositoryName;
    private final Path baseDirectory;

    private ServiceGeneratorImpl(Builder builder){
        this.name = builder.name;
        this.access = builder.access;
        this.parent = builder.parent;
        this.repositoryName = builder.repositoryName;
        this.baseDirectory = builder.baseDirectory;
    }

    static class Builder{
        private String name;
        private Modifier access;
        private ClassName parent;
        private String repositoryName;
        private Path baseDirectory;

        Builder(){
            this.name = "Service";
            this.parent = ClassName.get(
                    "io.toro.ojtbe.jimenez.Graphify.core.generators",
                    "ServiceImpl"
            );
            this.repositoryName = "Repository";
            this.access = Modifier.PUBLIC;
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

        Builder repositoryName(String repositoryName){
            this.repositoryName = repositoryName;
            return this;
        }

        Builder baseDirectory(Path baseDirectory){
            this.baseDirectory = baseDirectory;
            return this;
        }

        ServiceGeneratorImpl build(){
            return new ServiceGeneratorImpl(this);
        }
    }

    @Override
    public List<GraphEntity> process(List<GraphEntity> input) {
        try{
            for(GraphEntity graphEntity : input){
                generate(graphEntity);
            }

        } catch(ServiceGeneratorException e){
            e.printStackTrace();

            return Collections.emptyList();
        }

        return input;
    }

    @Override
    public void generate(GraphEntity graphEntity)
            throws ServiceGeneratorException {

        String fullyQualifiedName =
                graphEntity.getFullyQualifiedName();

        int nameBounds
                = fullyQualifiedName.lastIndexOf(".");

        String packageStatement
                = fullyQualifiedName.substring(0, nameBounds);

        String entityName
                = fullyQualifiedName.substring(nameBounds + 1);

        ClassName repository = ClassName.get(
                packageStatement,
                entityName + repositoryName
        );

        TypeSpec service = createServiceClass(
                graphEntity, repository, parent,
                entityName, packageStatement
        );

        JavaFile file = JavaFile.builder(packageStatement, service)
                .skipJavaLangImports(true)
                .indent("   ")
                .build();
        try {
            file.writeTo(baseDirectory);

        } catch (IOException e) {
            e.printStackTrace();

            throw new ServiceGeneratorException(
                    "Failed to generate service, at: " +
                            graphEntity);
        }
    }

    private TypeSpec createServiceClass(GraphEntity graphEntity,
                                        ClassName repository,
                                        ClassName parent,
                                        String entityName,
                                        String packageStatement){
        String serviceName = entityName + name;

        ClassName entityClass = ClassName.get(
                packageStatement,
                entityName
        );

        ClassName idClass = ClassNameUtil.INSTANCE
                .toBoxedType(
                        ClassName.get(
                                "",
                                graphEntity.getIdType()
                        )
                );

        return TypeSpec.classBuilder(serviceName)
                .addAnnotation(ClassNames.TRANSACTION.getClassName())
                .addAnnotation(ClassNames.SERVICE.getClassName())
                .superclass(
                        ParameterizedTypeName.get(
                                parent, entityClass, idClass
                        )
                )
                .addMethod(
                        createConstructor(
                                repository
                        )
                )
                .addModifiers(access)
                .build();
    }

    private MethodSpec createConstructor(ClassName repository) {
        String repositoryType = repository.simpleName();
        String repositoryName = Character.toLowerCase(
                repositoryType.charAt(0)) +
                repositoryType.substring(1);

        return MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(
                        createConstructorParameter(
                                repositoryName, repository
                        )
                )
                .addCode(
                        createSuperInit(repositoryName)
                )
                .addAnnotation(
                        ClassNames.AUTOWIRED.getClassName()
                )
                .build();
    }

    private ParameterSpec createConstructorParameter(String repositoryName,
                                                     ClassName repository) {
        return ParameterSpec.builder(repository, repositoryName)
                .build();
    }

    private CodeBlock createSuperInit(String repositoryName){
        return CodeBlock.builder()
                .add("super($L);\n", repositoryName)
                .build();
    }
}

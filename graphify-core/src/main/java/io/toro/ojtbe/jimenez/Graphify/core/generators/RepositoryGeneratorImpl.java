package io.toro.ojtbe.jimenez.Graphify.core.generators;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import io.toro.ojtbe.jimenez.Graphify.core.GraphEntity;
import io.toro.ojtbe.jimenez.Graphify.core.poet.ClassNameUtil;
import io.toro.ojtbe.jimenez.Graphify.core.poet.ClassNames;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

final class RepositoryGeneratorImpl implements RepositoryGenerator {
    private final String name;
    private final Modifier access;
    private final ClassName parent;
    private final Path baseDirectory;

    private RepositoryGeneratorImpl(Builder builder){
        this.name = builder.name;
        this.access = builder.access;
        this.parent = builder.parent;
        this.baseDirectory = builder.baseDirectory;
    }

    static class Builder{
        private String name;
        private Modifier access;
        private ClassName parent;
        private Path baseDirectory;

        Builder(){
            this.name = "Repository";
            this.baseDirectory = Paths.get("src/main/java");
            this.access = Modifier.PUBLIC;
            this.parent = ClassNames.CRUD_REPOSITORY
                    .getClassName();
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

        Builder baseDirectory(Path baseDirectory){
            this.baseDirectory = baseDirectory;
            return this;
        }

        RepositoryGeneratorImpl build(){
            return new RepositoryGeneratorImpl(this);
        }
    }

    @Override
    public List<GraphEntity> process(List<GraphEntity> input) {
        try{
            for(GraphEntity graphEntity: input){
                generate(graphEntity);
            }
        } catch(RepositoryGeneratorException e){
            e.printStackTrace();

            return Collections.emptyList();
        }

        return input;
    }

    @Override
    public void generate(GraphEntity graphEntity)
    throws RepositoryGeneratorException {
        String fullyQualifiedName =
                graphEntity.getFullyQualifiedName();

        int nameBounds
                = fullyQualifiedName.lastIndexOf(".");

        String packageStatement
                = fullyQualifiedName.substring(0, nameBounds);

        String entityName
                = fullyQualifiedName.substring(nameBounds + 1);

        TypeSpec repository = createRepositoryInterface(
                graphEntity, parent, entityName, packageStatement
        );

        JavaFile file = JavaFile.builder(packageStatement, repository)
                .skipJavaLangImports(true)
                .indent("   ")
                .build();

        try {
            file.writeTo(baseDirectory);

        } catch (IOException e) {
            e.printStackTrace();

            throw new RepositoryGeneratorException(
                    "Failed to generate repositories, at "
                    + graphEntity
            );
        }
    }

    private TypeSpec createRepositoryInterface(GraphEntity graphEntity,
                                               ClassName parent,
                                               String entityName,
                                               String packageStatement){
        String idType = graphEntity.getIdType();
        String repositoryName = entityName + name;

        ClassName model = ClassName.get(packageStatement, entityName);
        ClassName id = ClassNameUtil.INSTANCE
                .toBoxedType(
                        ClassName.get("", idType)
                );

        return TypeSpec.interfaceBuilder(repositoryName)
                .addModifiers(access)
                .addSuperinterface(
                        ParameterizedTypeName.get(
                                parent, model, id
                        )
                )
                .build();
    }
}

package io.toro.ojtbe.jimenez.Graphify.core.generators;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import io.toro.ojtbe.jimenez.Graphify.core.GraphEntity;
import io.toro.ojtbe.jimenez.Graphify.core.poet.ClassNames;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.nio.file.Paths;

final class RepositoryGeneratorImpl implements RepositoryGenerator {
    private String name;
    private Modifier access;
    private ClassName parent;

    RepositoryGeneratorImpl(){
        this.name = "Repository";
        this.access = Modifier.PUBLIC;
        this.parent = ClassNames.CRUD_REPOSITORY
                .getAnnotation();
    }

    @Override
    public boolean generate(GraphEntity graphEntity,
                            String path,
                            String packageName) {
        TypeSpec repository = createRepositoryInterface(
                graphEntity, parent
        );

        JavaFile file = JavaFile
                .builder(packageName, repository)
                .skipJavaLangImports(true)
                .indent("   ")
                .build();
        try {
            file.writeTo(Paths.get(path));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RepositoryGeneratorException();
            return false;
        }
        boolean writeSuccess = PoetUtil.INSTANCE
                .write(
                        graphEntity.getPackageName(),
                        graphEntity.getModelDirectory(),
                        repository
                );

        if(!writeSuccess){
            throw new RepositoryGeneratorException("Failed to generate" +
                    " repository file for: " + graphEntity);
        }

        return repository;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setParent(String className, String packageName) {
        this.parent = ClassName.get(
                className, packageName
        );
    }

    @Override
    public void setAccess(String access) {
        this.access = Modifier.valueOf(access);
    }

    @Override
    public TypeSpec generateRepository(GraphEntity graphEntity,
                                        ClassName parent) throws RepositoryGeneratorException{


    }


    private TypeSpec createRepositoryInterface(GraphEntity graphEntity,
                                                ClassName parent){
        String entityName = graphEntity.getClassName();
        String idType = graphEntity.getIdType();
        String packageName = graphEntity.getPackageName();
        String repositoryName = entityName + defaultSuffix;

        ClassName model = ClassName.get(packageName, entityName);
        ClassName id = PoetUtil.INSTANCE
                .toBoxedType(
                        ClassName.get("", idType)
                );

        return TypeSpec.interfaceBuilder(repositoryName)
                .addModifiers(defaultAccess)
                .addSuperinterface(
                        ParameterizedTypeName.get(
                                parent, model, id
                        )
                )
                .build();
    }
}

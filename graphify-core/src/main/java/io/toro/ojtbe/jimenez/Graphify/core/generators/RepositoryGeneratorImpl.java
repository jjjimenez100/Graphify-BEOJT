package io.toro.ojtbe.jimenez.Graphify.core.generators;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import io.toro.ojtbe.jimenez.Graphify.core.GraphEntity;
import io.toro.ojtbe.jimenez.Graphify.core.poet.PoetUtil;

import javax.lang.model.element.Modifier;

final class RepositoryGeneratorImpl implements RepositoryGenerator {
    private final String defaultSuffix;
    private final Modifier defaultAccess;

    RepositoryGeneratorImpl(String defaultSuffix,
                                   Modifier defaultAccess){
        this.defaultSuffix = defaultSuffix;
        this.defaultAccess = defaultAccess;
    }

    @Override
    public TypeSpec generateRepository(GraphEntity graphEntity,
                                        ClassName parent) throws RepositoryGeneratorException{

        TypeSpec repository = createRepositoryInterface(
                graphEntity, parent
        );

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

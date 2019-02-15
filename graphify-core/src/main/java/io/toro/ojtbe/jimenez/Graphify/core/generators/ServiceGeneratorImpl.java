package io.toro.ojtbe.jimenez.Graphify.core.generators;

import com.squareup.javapoet.*;
import io.toro.ojtbe.jimenez.Graphify.core.GraphEntity;
import io.toro.ojtbe.jimenez.Graphify.core.Annotation;
import io.toro.ojtbe.jimenez.Graphify.core.poet.PoetUtil;

import javax.lang.model.element.Modifier;

final class ServiceGeneratorImpl implements ServiceGenerator{
    private final String defaultSuffix;
    private final Modifier[] modifiers;

    ServiceGeneratorImpl(String defaultSuffix,
                                Modifier... modifiers){
        this.defaultSuffix = defaultSuffix;
        this.modifiers = modifiers;
    }

    @Override
    public TypeSpec generateService(GraphEntity graphEntity,
                                    TypeSpec repository,
                                    ClassName parent)
            throws ServiceGeneratorException{
        TypeSpec service = createServiceClass(
                graphEntity, repository, parent
        );

        boolean writeSuccess = PoetUtil.INSTANCE
                .write(
                        graphEntity.getPackageName(),
                        graphEntity.getModelDirectory(),
                        service
                );

        if(!writeSuccess){
            throw new ServiceGeneratorException("Failed to generate" +
                    " service file for: " + repository);
        }

        return service;
    }

    private TypeSpec createServiceClass(GraphEntity graphEntity,
                                        TypeSpec repository,
                                        ClassName parent){
        String serviceName = graphEntity.getClassName() + defaultSuffix;

        ClassName entityClass = ClassName.get(
                graphEntity.getPackageName(),
                graphEntity.getClassName()
        );

        ClassName repositoryClass = ClassName.get(
                graphEntity.getPackageName(),
                repository.name
        );

        ClassName idClass = PoetUtil.INSTANCE
                .toBoxedType(
                        ClassName.get(
                                "",
                                graphEntity.getIdType()
                        )
                );

        return TypeSpec.classBuilder(serviceName)
                .addAnnotation(Annotation.TRANSACTION.getAnnotation())
                .addAnnotation(Annotation.SERVICE.getAnnotation())
                .superclass(
                        ParameterizedTypeName.get(
                                parent, entityClass, idClass
                        )
                )
                .addMethod(
                        createConstructor(
                                repository,
                                repositoryClass
                        )
                )
                .addModifiers(modifiers)
                .build();
    }

    private MethodSpec createConstructor(TypeSpec repository,
                                         ClassName repositoryClass) {
        String repositoryType = repository.name;
        String repositoryName = Character.toLowerCase(
                repositoryType.charAt(0)) +
                repositoryType.substring(1);


        return MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(
                        createConstructorParameter(
                                repositoryName, repositoryClass
                        )
                )
                .addCode(
                        createSuperInit(repositoryName)
                )
                .addAnnotation(
                        Annotation.AUTOWIRED.getAnnotation()
                )
                .build();
    }

    private ParameterSpec createConstructorParameter(String repositoryName,
                                                     ClassName repositoryClass) {
        return ParameterSpec.builder(repositoryClass, repositoryName)
                .build();
    }

    private CodeBlock createSuperInit(String repositoryName){
        return CodeBlock.builder()
                .add("super($L);\n", repositoryName)
                .build();
    }
}

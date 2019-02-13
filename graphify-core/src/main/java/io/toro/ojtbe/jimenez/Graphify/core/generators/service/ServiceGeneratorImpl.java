package io.toro.ojtbe.jimenez.Graphify.core.generators.service;

import io.toro.ojtbe.jimenez.Graphify.core.generators.GraphEntity;
import io.toro.ojtbe.jimenez.Graphify.core.poet.PoetService;
import io.toro.ojtbe.jimenez.Graphify.core.poet.wrappers.*;

public final class ServiceGeneratorImpl implements ServiceGenerator{
    private final PoetService poetService;
    private final ClassNameWrapper parent;
    private final String defaultSuffix;

    public ServiceGeneratorImpl(PoetService poetService,
                                ClassNameWrapper parent,
                                String defaultSuffix){
        this.poetService = poetService;
        this.parent = parent;
        this.defaultSuffix = defaultSuffix;
    }

    public ClassWrapper generateService(GraphEntity graphEntity,
                                 Interface repository){
        ClassWrapper service = createServiceClass(
                graphEntity, repository
        );

        boolean writeSuccess = poetService.writeClass(
                "",
                service
        );

        if(!writeSuccess){
            throw new ServiceGeneratorException("Failed to generate" +
                    " service file for: " + repository);
        }

        return service;
    }

    private ClassWrapper createServiceClass(GraphEntity graphEntity,
                                     Interface repository){
        String serviceName = graphEntity.getClassName() + defaultSuffix;

        ClassNameWrapper entityClass = new ClassNameWrapper(
                graphEntity.getPackageName(),
                graphEntity.getClassName()
        );

        ClassNameWrapper repositoryClass = new ClassNameWrapper(
                repository.getPackageStatement(),
                repository.getName()
        );

        ClassNameWrapper idClass = new ClassNameWrapper(
                "",
                graphEntity.getIdType()
        );

        return new ClassWrapper.Builder(
                    serviceName, graphEntity.getPackageName()
                )
                .addAnnotation(Annotation.TRANSACTION.getAnnotation())
                .addAnnotation(Annotation.SERVICE.getAnnotation())
                .addParentType(parent)
                .addParentType(entityClass)
                .addParentType(idClass)
                .addMethod(
                        createConstructor(
                                repository,
                                serviceName,
                                repositoryClass
                        )
                )
                .addModifier("PUBLIC")
                .addModifier("FINAL")
                .build();
    }

    private Method createConstructor(Interface repository,
                                     String serviceName,
                                     ClassNameWrapper repositoryClass){
        String repositoryType = repository.getName();
        String repositoryName = Character
                .toLowerCase(repositoryType.charAt(0)) +
                repositoryType.substring(1);


        return new Method.Builder(serviceName)
                .addModifier("PUBLIC")
                .addParameter(
                        createConstructorParameter(repositoryName,
                        repositoryClass)
                )
                .addStatement(
                        createSuperInit(repositoryName)
                )
                .addAnnotation(
                        Annotation.AUTOWIRED.getAnnotation()
                )
                .build();
    }

    private Variable createConstructorParameter(String repositoryName,
                                               ClassNameWrapper repositoryClass){
        return new Variable
                .Builder(repositoryName, repositoryClass)
                .build();
    }

    private Statement createSuperInit(String repositoryName){
        return new Statement("super($L);\n", repositoryName);
    }
}

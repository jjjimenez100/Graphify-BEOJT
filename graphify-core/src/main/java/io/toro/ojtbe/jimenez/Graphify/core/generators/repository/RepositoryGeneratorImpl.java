package io.toro.ojtbe.jimenez.Graphify.core.generators.repository;

import io.toro.ojtbe.jimenez.Graphify.core.generators.GraphEntity;
import io.toro.ojtbe.jimenez.Graphify.core.poet.PoetService;
import io.toro.ojtbe.jimenez.Graphify.core.poet.wrappers.ClassNameWrapper;
import io.toro.ojtbe.jimenez.Graphify.core.poet.wrappers.Interface;

public final class RepositoryGeneratorImpl implements RepositoryGenerator{
    private final PoetService poetService;
    private final String defaultSuffix;
    private final String defaultAccess;

    public RepositoryGeneratorImpl(PoetService poetService,
                                   String defaultSuffix,
                                   String defaultAccess){
        this.poetService = poetService;
        this.defaultSuffix = defaultSuffix;
        this.defaultAccess = defaultAccess.toUpperCase();
    }

    public Interface generateRepository(GraphEntity graphEntity,
                                        ClassNameWrapper parent) {

        Interface repository = createRepositoryInterface(
                graphEntity, parent
        );

        boolean writeSuccess = poetService.writeInterface(
                graphEntity.getModelDirectory(),
                repository
        );

        if(!writeSuccess){
            throw new RepositoryGeneratorException("Failed to generate" +
                    " repository file for: " + graphEntity);
        }

        return repository;
    }

    private Interface createRepositoryInterface(GraphEntity graphEntity,
                                                ClassNameWrapper parent){
        String entityName = graphEntity.getClassName();
        String idType = graphEntity.getIdType();
        String packageName = graphEntity.getPackageName();
        String repositoryName = entityName + defaultSuffix;

        ClassNameWrapper model = new ClassNameWrapper(packageName, entityName);
        ClassNameWrapper id = new ClassNameWrapper("", idType);

        return new Interface.Builder(repositoryName, packageName)
                .addModifier(defaultAccess)
                .addParentType(parent)
                .addParentType(model)
                .addParentType(id)
                .build();
    }
}

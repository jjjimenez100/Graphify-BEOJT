package io.toro.ojtbe.jimenez.Graphify.core.generators.resolver;

import io.toro.ojtbe.jimenez.Graphify.core.generators.GraphEntity;
import io.toro.ojtbe.jimenez.Graphify.core.poet.PoetService;
import io.toro.ojtbe.jimenez.Graphify.core.poet.wrappers.*;
import java.util.List;

public final class QueryGeneratorImpl implements QueryGenerator{
    private final PoetService poetService;
    private final String defaultName;
    private final ClassNameWrapper resolverInterface;

    public QueryGeneratorImpl(PoetService poetService,
                              String defaultName,
                              ClassNameWrapper resolverInterface){
        this.poetService = poetService;
        this.defaultName = defaultName;
        this.resolverInterface = resolverInterface;
    }

    public ClassWrapper generateQuery(List<GraphEntity> graphEntities,
                                              List<ClassWrapper> services){
        if(!validateMappings(graphEntities.size(), services.size())){
            throw new QueryGeneratorException("Invalid graph entity to" +
                    "service mappings");
        }

        ClassWrapper resolver = createQueryClass(graphEntities, services);
        boolean writeSuccess = poetService.writeClass("", resolver);

        if(!writeSuccess){
            throw new QueryGeneratorException();
        }

        return resolver;
    }

    private boolean validateMappings(int entitiesSize,
                                     int servicesSize){
        if(entitiesSize == 0 || servicesSize == 0){
            return false;
        } else if(entitiesSize != servicesSize){
            return false;
        } else {
            return true;
        }
    }

    private ClassWrapper createQueryClass(List<GraphEntity> graphEntities,
                                         List<ClassWrapper> services){
        String packageStatement = services.get(0).getPackageStatement();
        ClassWrapper.Builder queryClassBuilder =
                new ClassWrapper.Builder(defaultName, packageStatement)
                .addAnnotation(Annotation.COMPONENT.getAnnotation())
                .addInterface(resolverInterface)
                .addModifier("PUBLIC");

        Method.Builder constructorBuilder = new Method.Builder(defaultName)
                .addModifier("PUBLIC");

        int max = graphEntities.size();

        for(int index=0; index<max; index++){
            ClassWrapper service = services.get(index);
            String serviceType = service.getName();
            String serviceName = Character
                    .toLowerCase(serviceType.charAt(0)) +
                    serviceType.substring(1);
            String servicePackage = service
                    .getPackageStatement();

            ClassNameWrapper serviceClass = new ClassNameWrapper(
                    servicePackage,
                    serviceType
            );

            queryClassBuilder.addField(
                    createField(
                            serviceName,
                            serviceClass
                    )
            );

            constructorBuilder.addParameter(
                    createConstructorParam(
                            serviceName,
                            serviceClass
                    )
            );

            Statement initializer = createInitializer(serviceName);
            constructorBuilder.addStatement(initializer);

            GraphEntity graphEntity = graphEntities.get(index);
            String entityPackage = graphEntity.getPackageName();

            ClassNameWrapper entity = new ClassNameWrapper(
                    entityPackage, graphEntity.getClassName()
            );

            queryClassBuilder.addMethod(
                    createGetAll(serviceName, entity)
            );

            queryClassBuilder.addMethod(
                    createGetById(serviceName, entity, graphEntity.getIdType())
            );
        }
        queryClassBuilder.addMethod(constructorBuilder.build());

        return queryClassBuilder.build();
    }

    private Variable createField(String serviceName,
                                 ClassNameWrapper serviceClass){
        return new Variable.Builder(serviceName, serviceClass)
                .addModifier("PUBLIC")
                .addModifier("FINAL")
                .addAnnotation(Annotation.AUTOWIRED.getAnnotation())
                .build();
    }

    private Variable createConstructorParam(String serviceName,
                                            ClassNameWrapper serviceClass){
        return new Variable.Builder(serviceName, serviceClass).build();
    }

    private Statement createInitializer(String serviceName){
        return new Statement(
                "this.$L = $L;\n", serviceName, serviceName
        );
    }

    private Method createGetAll(String serviceName,
                               ClassNameWrapper entityClassName){
        Statement getAllEntity = new Statement(
                "return $L.getAll();\n", serviceName
        );

        String entityName = entityClassName.getClassName();
        String pluralEntityName = entityName.toLowerCase() + "s";

        ClassNameWrapper iterable = new ClassNameWrapper(
                "", "Iterable"
        );

        return new Method.Builder(pluralEntityName)
                .addType(iterable)
                .addType(entityClassName)
                .addStatement(getAllEntity)
                .addModifier("PUBLIC")
                .build();
    }

    private Method createGetById(String serviceName,
                                ClassNameWrapper entityClassName,
                                String idType
                              ){
        //entity get individual
        Statement getIndividual = new Statement(
                "return $L.getById(id)" +
                        ".orElseThrow(RuntimeException::new);\n", serviceName
        );

        Variable idParameter = new Variable.Builder("id",
                new ClassNameWrapper("", idType))
                .build();

        String singularEntityName = entityClassName.getClassName().toLowerCase();

        return new Method.Builder(singularEntityName)
                .addType(entityClassName)
                .addParameter(idParameter)
                .addStatement(getIndividual)
                .addModifier("PUBLIC")
                .build();
    }
}

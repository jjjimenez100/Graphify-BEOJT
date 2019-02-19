package io.toro.ojtbe.jimenez.Graphify.core.generators;

import com.squareup.javapoet.*;
import io.toro.ojtbe.jimenez.Graphify.core.Annotation;
import io.toro.ojtbe.jimenez.Graphify.core.GraphEntity;
import io.toro.ojtbe.jimenez.Graphify.core.poet.PoetUtil;

import javax.lang.model.element.Modifier;
import java.util.List;

final class QueryGeneratorImpl implements QueryGenerator{
    private String name;
    private Modifier access;
    private ClassName parent;

    QueryGeneratorImpl(){
        this.name = "QueryResolver";
        this.access = Modifier.PUBLIC;
        this.parent =
    }

    public TypeSpec generateQuery(List<GraphEntity> graphEntities,
                                  List<TypeSpec> services){
        if(!validateMappings(graphEntities.size(), services.size())){
            throw new QueryGeneratorException("Invalid graph entity to" +
                    "service mappings");
        }

        TypeSpec resolver = createQueryClass(graphEntities, services);

        boolean writeSuccess = PoetUtil.INSTANCE
                .write(
                        graphEntities.get(0).getPackageName(),
                        graphEntities.get(0).getModelDirectory(),
                        resolver
                );

        if(!writeSuccess){
            throw new QueryGeneratorException("Failed to generate resolver" +
                    " file for: " + graphEntities.get(0));
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

    private TypeSpec createQueryClass(List<GraphEntity> graphEntities,
                                         List<TypeSpec> services){
        TypeSpec.Builder queryClassBuilder = TypeSpec.classBuilder(defaultName)
                .addAnnotation(Annotation.COMPONENT.getAnnotation())
                .addSuperinterface(resolverInterface)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL);

        MethodSpec.Builder constructorBuilder = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC);

        int max = graphEntities.size();

        for(int index=0; index<max; index++){
            TypeSpec service = services.get(index);
            String serviceType = service.name;
            String serviceName = Character
                    .toLowerCase(serviceType.charAt(0)) +
                    serviceType.substring(1);
            String servicePackage = graphEntities.get(index)
                    .getPackageName();

            ClassName serviceClass = ClassName.get(
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

            CodeBlock initializer = createInitializer(serviceName);
            constructorBuilder.addStatement(initializer);

            GraphEntity graphEntity = graphEntities.get(index);
            String entityPackage = graphEntity.getPackageName();

            ClassName entity = ClassName.get(
                    entityPackage, graphEntity.getClassName()
            );

            queryClassBuilder.addMethod(
                    createGetAll(serviceName, entity)
            );

            queryClassBuilder.addMethod(
                    createGetById(
                            serviceName,
                            entity,
                            ClassName.get("", graphEntity.getIdType())
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
                .addAnnotation(Annotation.AUTOWIRED.getAnnotation())
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
                                     ClassName idType
                              ){
        //entity get individual
        CodeBlock getIndividual = CodeBlock.builder()
            .add(
                "return $L.getById(id)" +
                        ".orElseThrow(RuntimeException::new);\n", serviceName
            ).build();

        ParameterSpec idParameter = ParameterSpec.builder(idType, "id")
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

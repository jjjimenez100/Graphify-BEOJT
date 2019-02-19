package io.toro.ojtbe.jimenez.Graphify.core.generators;

import com.squareup.javapoet.*;
import io.toro.ojtbe.jimenez.Graphify.core.Annotation;
import io.toro.ojtbe.jimenez.Graphify.core.GraphEntity;
import io.toro.ojtbe.jimenez.Graphify.core.poet.ClassNames;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

final class QueryGeneratorImpl implements QueryGenerator{
    private String name;
    private Modifier access;
    private ClassName parent;

    QueryGeneratorImpl(){
        this.name = "QueryResolver";
        this.access = Modifier.PUBLIC;
        this.parent = ClassNames.GRAPHQL_QUERY_RESOLVER
                .getClassName();
    }

    @Override
    public void generate(List<GraphEntity> graphEntities,
                         Map<String, String> services,
                         String packageName,
                         String path) throws QueryGeneratorException {
        TypeSpec resolver = createQueryClass(graphEntities, services);

        JavaFile file = JavaFile
                .builder(packageName, resolver)
                .skipJavaLangImports(true)
                .indent("   ")
                .build();

        try {
            file.writeTo(Paths.get(path));

        } catch (IOException e) {
            e.printStackTrace();

            throw new QueryGeneratorException();
        }
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

    private TypeSpec createQueryClass(List<GraphEntity> graphEntities,
                                      Map<String, String> services){
        TypeSpec.Builder queryClassBuilder = TypeSpec.classBuilder(name)
                .addAnnotation(Annotation.COMPONENT.getAnnotation())
                .addSuperinterface(parent)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL);

        MethodSpec.Builder constructorBuilder = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC);

        int index = 0;

        for(String servicePackage: services.keySet()){
            ClassName service = ClassName.get(
                    servicePackage, services.get(servicePackage)
            );

            String serviceType = service.simpleName();
            String serviceName = Character
                    .toLowerCase(serviceType.charAt(0)) +
                    serviceType.substring(1);

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

            index++;
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

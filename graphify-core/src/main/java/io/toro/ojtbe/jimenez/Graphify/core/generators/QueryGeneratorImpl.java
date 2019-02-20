package io.toro.ojtbe.jimenez.Graphify.core.generators;

import com.squareup.javapoet.*;
import io.toro.ojtbe.jimenez.Graphify.core.Annotation;
import io.toro.ojtbe.jimenez.Graphify.core.GraphEntity;
import io.toro.ojtbe.jimenez.Graphify.core.poet.ClassNames;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;

final class QueryGeneratorImpl implements QueryGenerator{
    private String name;
    private Modifier access;
    private ClassName parent;
    private String serviceName;

    QueryGeneratorImpl(){
        this.name = "QueryResolver";
        this.access = Modifier.PUBLIC;
        this.parent = ClassNames.GRAPHQL_QUERY_RESOLVER
                .getClassName();
        this.serviceName = "Service";
    }

    @Override
    public List<GraphEntity> process(List<GraphEntity> input) {
        try{
            generate(input);
        } catch(QueryGeneratorException e){
            e.printStackTrace();

            return Collections.emptyList();
        }

        return input;
    }

    @Override
    public void generate(List<GraphEntity> graphEntities)
            throws QueryGeneratorException {
        TypeSpec resolver = createQueryClass(graphEntities);

        // write main query resolver to
        // default package of first entity
        GraphEntity graphEntity = graphEntities.get(0);
        JavaFile file = JavaFile
                .builder(
                        graphEntity.getPackageName(),
                        resolver
                )
                .skipJavaLangImports(true)
                .indent("   ")
                .build();

        try {
            file.writeTo(Paths.get(
                    graphEntity.getModelDirectory()
            ));

        } catch (IOException e) {
            e.printStackTrace();

            throw new QueryGeneratorException();
        }
    }

    private TypeSpec createQueryClass(List<GraphEntity> graphEntities){
        TypeSpec.Builder queryClassBuilder = TypeSpec.classBuilder(name)
                .addAnnotation(Annotation.COMPONENT.getAnnotation())
                .addSuperinterface(parent)
                .addModifiers(access, Modifier.FINAL);

        MethodSpec.Builder constructorBuilder = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC);

        for(GraphEntity graphEntity: graphEntities){
            String serviceType = graphEntity.getClassName() + serviceName;
            String entityPackage = graphEntity.getPackageName();

            ClassName service = ClassName.get(
                    entityPackage,
                    serviceType
            );

            String serviceName = Character
                    .toLowerCase(serviceType.charAt(0)) +
                    serviceType.substring(1);

            queryClassBuilder.addField(
                    createField(
                            serviceName,
                            service
                    )
            );

            constructorBuilder.addParameter(
                    createConstructorParam(
                            serviceName,
                            service
                    )
            );

            CodeBlock initializer = createInitializer(serviceName);
            constructorBuilder.addStatement(initializer);

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
                            ClassName.get(
                                    "",
                                    graphEntity.getIdType()
                            )
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

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getParentClass() {
        return parent.simpleName();
    }

    @Override
    public String getParentPackage() {
        return parent.packageName();
    }

    @Override
    public void setServiceName(String name) {
        this.serviceName = name;
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
}

package io.toro.ojtbe.jimenez.Graphify.core.generators;

import com.squareup.javapoet.*;
import io.toro.ojtbe.jimenez.Graphify.core.GraphEntity;
import io.toro.ojtbe.jimenez.Graphify.core.Annotation;
import io.toro.ojtbe.jimenez.Graphify.core.poet.ClassNameUtil;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.nio.file.Paths;

final class ServiceGeneratorImpl implements ServiceGenerator{
    private String name;
    private Modifier access;
    private ClassName parent;

    ServiceGeneratorImpl(){
        this.name = "Service";
        this.access = Modifier.PUBLIC;
        this.parent = ClassName.get(
            "io.toro.ojtbe.jimenez.Graphify.core.generators",
                "ServiceImpl"
        );
    }

    @Override
    public void generate(GraphEntity graphEntity,
                         String path,
                         String packageName,
                         String repositoryName) throws ServiceGeneratorException {

        ClassName repository = ClassName.get(
                packageName, repositoryName
        );

        TypeSpec service = createServiceClass(
                graphEntity, repository, parent
        );

        JavaFile file = JavaFile
                .builder(packageName, service)
                .skipJavaLangImports(true)
                .indent("   ")
                .build();
        try {
            file.writeTo(Paths.get(path));

        } catch (IOException e) {
            e.printStackTrace();

            throw new ServiceGeneratorException();
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

    private TypeSpec createServiceClass(GraphEntity graphEntity,
                                        ClassName repository,
                                        ClassName parent){
        String serviceName = graphEntity.getClassName() + name;

        ClassName entityClass = ClassName.get(
                graphEntity.getPackageName(),
                graphEntity.getClassName()
        );

        ClassName idClass = ClassNameUtil.INSTANCE
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
                                repository
                        )
                )
                .addModifiers(access)
                .build();
    }

    private MethodSpec createConstructor(ClassName repository) {
        String repositoryType = repository.simpleName();
        String repositoryName = Character.toLowerCase(
                repositoryType.charAt(0)) +
                repositoryType.substring(1);


        return MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(
                        createConstructorParameter(
                                repositoryName, repository
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
                                                     ClassName repository) {
        return ParameterSpec.builder(repository, repositoryName)
                .build();
    }

    private CodeBlock createSuperInit(String repositoryName){
        return CodeBlock.builder()
                .add("super($L);\n", repositoryName)
                .build();
    }
}

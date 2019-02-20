package io.toro.ojtbe.jimenez.Graphify.core.generators;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import io.toro.ojtbe.jimenez.Graphify.core.GraphEntity;
import io.toro.ojtbe.jimenez.Graphify.core.poet.ClassNameUtil;
import io.toro.ojtbe.jimenez.Graphify.core.poet.ClassNames;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

final class RepositoryGeneratorImpl implements RepositoryGenerator {
    private String name;
    private Modifier access;
    private ClassName parent;

    RepositoryGeneratorImpl(){
        this.name = "Repository";
        this.access = Modifier.PUBLIC;
        this.parent = ClassNames.CRUD_REPOSITORY
                .getClassName();
    }

    @Override
    public List<GraphEntity> process(List<GraphEntity> input) {
        try{
            for(GraphEntity graphEntity: input){
                generate(graphEntity);
            }
        } catch(RepositoryGeneratorException e){
            e.printStackTrace();

            return Collections.emptyList();
        }

        return input;
    }

    @Override
    public void generate(GraphEntity graphEntity)
    throws RepositoryGeneratorException {
        TypeSpec repository = createRepositoryInterface(
                graphEntity, parent
        );

        // write repository to the same path and package
        // as the annotated entity model
        JavaFile file = JavaFile
                .builder(
                        graphEntity.getPackageName(),
                        repository
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

            throw new RepositoryGeneratorException();
        }
    }

    private TypeSpec createRepositoryInterface(GraphEntity graphEntity,
                                               ClassName parent){
        String entityName = graphEntity.getClassName();
        String idType = graphEntity.getIdType();
        String packageName = graphEntity.getPackageName();
        String repositoryName = entityName + name;

        ClassName model = ClassName.get(packageName, entityName);
        ClassName id = ClassNameUtil.INSTANCE
                .toBoxedType(
                        ClassName.get("", idType)
                );

        return TypeSpec.interfaceBuilder(repositoryName)
                .addModifiers(access)
                .addSuperinterface(
                        ParameterizedTypeName.get(
                                parent, model, id
                        )
                )
                .build();
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
    public void setAccess(String access) {
        this.access = Modifier.valueOf(access);
    }
}

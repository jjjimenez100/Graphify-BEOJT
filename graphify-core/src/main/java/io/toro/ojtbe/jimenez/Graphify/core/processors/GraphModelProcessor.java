package io.toro.ojtbe.jimenez.Graphify.core.processors;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import io.toro.ojtbe.jimenez.Graphify.annotations.GraphModel;
import io.toro.ojtbe.jimenez.Graphify.core.GraphEntity;
import io.toro.ojtbe.jimenez.Graphify.core.generators.GeneratorPipeline;
import io.toro.ojtbe.jimenez.Graphify.core.poet.ClassNameUtil;
import org.springframework.data.annotation.Id;

@SupportedAnnotationTypes("io.toro.ojtbe.jimenez.Graphify.annotations.GraphModel")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Process.class)
public final class GraphModelProcessor extends AbstractProcessor {

    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv){
        super.init(processingEnv);
        this.messager = processingEnv.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnvironment){
        if(!roundEnvironment.processingOver()){
            if(validateModelClasses(roundEnvironment)){
                List<GraphEntity> graphEntities
                        = initGraphEntities(roundEnvironment);
                debug(graphEntities);
                new GeneratorPipeline().execute(graphEntities);
            }

        }
        return true;
    }

    private boolean validateModelClasses(RoundEnvironment roundEnvironment){
        for(Element annotatedClass :
                roundEnvironment.getElementsAnnotatedWith(GraphModel.class)){
            if(!isValidModelClass(annotatedClass)){
                return false;
            }

            if(!hasOnlyOneId(annotatedClass)){
                return false;
            }
        }

        return true;
    }

    private boolean isValidModelClass(Element annotatedClass){
        // Annotated element should be a complete, model class. No abstract/interfaces/enums
        ElementKind elementKind = annotatedClass.getKind();
        Set<Modifier> modifiers = annotatedClass.getModifiers();
        if(elementKind == ElementKind.INTERFACE){
            raiseError(annotatedClass, "Annotated class should not be an interface");
            return false;

        } else if(elementKind == ElementKind.ENUM){
            raiseError(annotatedClass, "Annotated class should not be an enum");
            return false;

        } else if(modifiers.contains(Modifier.ABSTRACT)){
            raiseError(annotatedClass, "Annotated class should not be abstract");
            return false;

        } else if(modifiers.contains(Modifier.PRIVATE)){
            raiseError(annotatedClass, "Annotated class should not be of private access");
            return false;

        } else {
            return true;
        }
    }

    private boolean hasOnlyOneId(Element annotatedEntity){
        int idCount = 0;

        for(Element property: annotatedEntity.getEnclosedElements()){
            Id idProperty = property.getAnnotation(Id.class);
            if(Objects.nonNull(idProperty)){
                idCount += 1;
            }
        }

        if(idCount > 1){
            raiseError("Multiple id candidates for: " +
                    annotatedEntity.getSimpleName());
            return false;

        } else if(idCount == 0){
            raiseError("No property annotated with @Id");
            return false;
        }

        return true;
    }

    private List<GraphEntity> initGraphEntities(RoundEnvironment roundEnvironment){
        List<GraphEntity> graphEntities = new ArrayList<>();

        for(Element annotatedEntity : roundEnvironment
                .getElementsAnnotatedWith(GraphModel.class)){
            GraphEntity graphEntity = initGraphEntity(annotatedEntity);
            graphEntities.add(graphEntity);
        }

        return graphEntities;
    }

    private GraphEntity initGraphEntity(Element annotatedEntity){
        String className = getName(annotatedEntity);
        String packageName = getPackageName(annotatedEntity);
        String idName = getIdName(annotatedEntity);

        GraphEntity.Builder entityBuilder = new GraphEntity
                .Builder()
                .className(className)
                .packageName(packageName)
                .idName(idName)
                .modelDirectory(getModelDirectory(className +
                        ".java", packageName));
        initGraphProperties(idName, annotatedEntity, entityBuilder);

        return entityBuilder.build();
    }

    private String getIdName(Element annotatedEntity){
        for(Element property: annotatedEntity.getEnclosedElements()){
            Id idProperty = property.getAnnotation(Id.class);
            if(Objects.nonNull(idProperty)){
                return property.getSimpleName().toString();
            }
        }

        return "";
    }

    private String getModelDirectory(String lookupName,
                                     String packageStatement){

        List<Path> candidates = Collections.emptyList();
        try{
            candidates = Files.walk(Paths.get(""))
                    .filter(Files::isRegularFile)
                    .filter((filePath) -> {
                        return filePath.toString()
                                .endsWith(lookupName);
                    })
                    .collect(Collectors.toList());
        } catch(IOException e){
            raiseError("IO Exception at: " + e.getMessage());
        }

        for(Path candidate: candidates){
            if(isPackageMatched(packageStatement,
                    candidate.toAbsolutePath().toString())){

                String packageDir = packageStatement.replace(
                        ".", "/"
                );

                return candidate.getParent()
                        .toString()
                        .replace(packageDir, "");
            }
        }

        raiseError("All candidates are not under package: " +
                packageStatement + " or you might not be using " +
                "the filesystem to store your classes");
        return "";
    }

    private boolean isPackageMatched(String packageStatement,
                                     String path){
        File candidate = new File(path);

        if(!packageStatement.isEmpty()){
            try(Scanner reader = new Scanner(candidate)){
                String declaredPackage = reader.nextLine();

                while(declaredPackage.isEmpty()){
                    declaredPackage = reader.nextLine();
                }

                return declaredPackage.contains(packageStatement);

            } catch(FileNotFoundException e){
                raiseError("File not found at: " + e.getMessage());

                return false;
            }
        } else {
            return true;
        }
    }

    private void initGraphProperties(String idName,
                                     Element annotatedEntity,
                                     GraphEntity.Builder entityBuilder){
        for(Element property: annotatedEntity.getEnclosedElements()){
            String type = getType(property).toString();

            if(isValidType(type)){
                String name = getName(property);
                ClassName typeEquivalent
                        = ClassName.get("", type);

                if(idName.equals(name)){
                    entityBuilder.idType(
                            typeEquivalent.simpleName()
                    );
                }

                entityBuilder.property(name, type);
            }
        }
    }

    private void debug(List<GraphEntity> graphEntities){

        for(int index=0; index<graphEntities.size(); index++){
            System.out.println("Entity " + index + ": "
                    + graphEntities.get(index));

            Map<String, String> properties = graphEntities
                    .get(index)
                    .getProperties();

            for(String propertyName: properties.keySet()){
                System.out.println("propertyType: " +
                        properties.get(propertyName));
                System.out.println("propertyName: " +
                        propertyName);
                System.out.println();
            }
        }
        System.out.println("Size: " + graphEntities.size());
    }

    /**
     * Logs a compiler error
     * @param causeElement the last element being processed
     * @param message to be logged with the compiler
     */
    private void raiseError(Element causeElement, String message){
        messager.printMessage(Diagnostic.Kind.ERROR, message, causeElement);
    }

    /**
     * Logs a compiler error
     * @param message to be logged with the compiler
     */
    private void raiseError(String message){
        messager.printMessage(Diagnostic.Kind.ERROR, message);
    }

    private TypeMirror getType(Element property){
        return property.asType();
    }

    private String getName(Element annotatedClass){
        return annotatedClass.getSimpleName().toString();
    }

    private String getPackageName(Element annotatedClass){
        return annotatedClass.getEnclosingElement().toString();
    }

    /**
     * Checks if the property is a valid type, not a method
     * @param type the extracted type
     * @return true if type is not a method, else false
     */
    private boolean isValidType(String type){
        return type.charAt(0) != '(' ;
    }
}

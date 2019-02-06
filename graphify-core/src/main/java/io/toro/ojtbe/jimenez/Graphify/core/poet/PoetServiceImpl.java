package io.toro.ojtbe.jimenez.Graphify.core.poet;

import com.squareup.javapoet.*;
import io.toro.ojtbe.jimenez.Graphify.core.poet.wrappers.*;
import io.toro.ojtbe.jimenez.Graphify.core.poet.wrappers.Class;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

final class PoetServiceImpl implements PoetService{
    private final List<String> logs;

    PoetServiceImpl(){
        logs = new ArrayList<>();
        addLog("Instance created");
    }

    @Override
    public boolean debug(String path){
        File logFile = new File(path + "/debug.txt");
        logFile.getParentFile().mkdirs();

        PrintWriter writer;
        try {
            writer = new PrintWriter(logFile);
        } catch (FileNotFoundException e) {
            addLog(e.getMessage());
            e.printStackTrace();
            return false;
        }

        for(String log: logs){
            writer.println(log);
        }
        writer.close();

        return true;
    }

    private void addLog(String message){
        long time = System.currentTimeMillis();
        logs.add(time + " - " + message);
    }

    @Override
    public boolean writeClass(String packageStatement,
                              String path,
                              Class classRep) {
        addLog(
                String.format(
                        "Writing class: %s at path: %s " +
                        "under package: %s",
                        classRep.getName(),
                        path,
                        packageStatement
                )
        );
        return write(packageStatement, path, createClass(classRep));
    }

    @Override
    public boolean writeInterface(String packageStatement,
                                  String path,
                                  Interface interfaceRep) {
        addLog(
                String.format(
                        "Writing interface: %s at path: %s " +
                                "under package: %s",
                        interfaceRep.getName(),
                        path,
                        packageStatement
                )
        );
        return write(packageStatement, path, createInterface(interfaceRep));
    }

    private boolean write(String packageStatement,
                          String path,
                          TypeSpec typeSpec){
        JavaFile file = JavaFile
                .builder(packageStatement, typeSpec)
                .build();

        try {
            file.writeTo(Paths.get(path));
            addLog("Write success");

            return true;
        } catch (IOException e) {
            addLog("Encountered IOException: " + e.getMessage());
            e.printStackTrace();

            return false;
        }
    }

    private TypeSpec createInterface(Interface interfaceRep){
        TypeSpec.Builder interfaceBuilder
                = TypeSpec.interfaceBuilder(interfaceRep.getName());

        List<Modifier> modifiers = interfaceRep.getModifiers();
        if(modifiers.size() != 0){
            interfaceBuilder.addModifiers(
                    modifiers.toArray(new Modifier[]{})
            );
        }

        List<ClassNameWrapper> superInterface
                = interfaceRep.getParentTypes();

        if(superInterface.size() > 1){ // has type parameters
            interfaceBuilder.addSuperinterface(
                    toParameterizedTypeName(superInterface)
            );
        } else if(superInterface.size() == 1){
            interfaceBuilder.addSuperinterface(
                    toClassName(superInterface.get(0))
            );
        }

        for(ClassNameWrapper annotation: interfaceRep.getAnnotations()){
            interfaceBuilder.addAnnotation(toClassName(annotation));
        }

        return interfaceBuilder.build();
    }

    private TypeSpec createClass(Class classRep){
       TypeSpec.Builder classBuilder
               = TypeSpec.classBuilder(classRep.getName());

       List<Modifier> modifiers = classRep.getModifiers();
       if(modifiers.size() != 0){
           classBuilder.addModifiers(
                   classRep.getModifiers().toArray(new Modifier[]{})
           );
       }

       for(ClassNameWrapper interfaceRep : classRep.getInterfaces()){
           classBuilder.addSuperinterface(toClassName(interfaceRep));
       }

       for(Method method: classRep.getMethods()){
           classBuilder.addMethod(createMethod(method));
       }

       for(ClassNameWrapper annotation: classRep.getAnnotations()){
           classBuilder.addAnnotation(toClassName(annotation));
       }

       for(Variable field: classRep.getFields()){
           classBuilder.addField(createField(field));
       }

       for(String typeName: classRep.getTypeVariables()){
           classBuilder.addTypeVariable(
                   TypeVariableName.get(typeName)
           );
       }

       List<ClassNameWrapper> parentTypes
               = classRep.getParentTypes();

       if (parentTypes.size() > 1) {
            classBuilder.superclass(
                    toParameterizedTypeName(parentTypes)
            );
       } else if(parentTypes.size() == 1){
           classBuilder.superclass(
                   toClassName(parentTypes.get(0))
           );
       }

       return classBuilder.build();
    }

    private MethodSpec createMethod(Method method){
        MethodSpec.Builder methodBuilder
                = MethodSpec.methodBuilder(method.getName());

        List<ClassNameWrapper> type = method.getTypes();

        if(type.size() > 1){
            methodBuilder.returns(toParameterizedTypeName(type));
        } else if(type.size() == 1){
            methodBuilder.returns(toClassName(type.get(0)));
        } else {
            RuntimeException e = new RuntimeException("At least a " +
                    "single type is required for method: " +
                    method.getName());
            addLog(e.getMessage());
            throw e;
        }

        for(Variable parameter: method.getParameters()){
            methodBuilder.addParameter(createParameter(parameter));
        }

        for(ClassNameWrapper annotation: method.getAnnotations()){
            methodBuilder.addAnnotation(toClassName(annotation));
        }

        List<Modifier> modifiers = method.getModifiers();
        if(modifiers.size() != 0){
            methodBuilder.addModifiers(modifiers);
        }

        List<Statement> statements = method.getStatements();
        if(statements.size() != 0){
            methodBuilder.addCode(
                    createBlock(statements.toArray(new Statement[]{}))
            );
        }

        return methodBuilder.build();
    }

    private CodeBlock createBlock(Statement... statements){
        CodeBlock.Builder blockBuilder = CodeBlock.builder();

        for(Statement statement: statements){
            blockBuilder.add(
                    statement.getStatement(),
                    statement.getArguments()
            );
        }

        return blockBuilder.build();
    }

    private ParameterSpec createParameter(Variable variable){
        ParameterSpec.Builder parameterBuilder;
        List<ClassNameWrapper> types = variable.getTypes();
        String fieldName = variable.getName();

        if(types.size() > 1){
            parameterBuilder = ParameterSpec.builder(
                    toParameterizedTypeName(types), fieldName
            );
        } else if(types.size() == 1){
            parameterBuilder = ParameterSpec.builder(
                                    toClassName(types.get(0)),
                                    fieldName
            );
        } else {
            RuntimeException e = new RuntimeException("At least a " +
                    "single type is required for parameter: " +
                    variable.getName());
            addLog(e.getMessage());
            throw e;
        }

        List<Modifier> modifiers = variable.getModifiers();
        if(modifiers.size() != 0){
            parameterBuilder.addModifiers(
                    variable.getModifiers().toArray(new Modifier[]{})
            );
        }

        for(ClassNameWrapper annotation: variable.getAnnotations()){
            parameterBuilder.addAnnotation(toClassName(annotation));
        }

        return parameterBuilder.build();
    }

    private FieldSpec createField(Variable variable){
        FieldSpec.Builder fieldBuilder;
        List<ClassNameWrapper> types = variable.getTypes();
        String fieldName = variable.getName();

        if(types.size() > 1){
            fieldBuilder = FieldSpec.builder(
                    toParameterizedTypeName(types), fieldName
            );
        } else if(types.size() == 1){
            fieldBuilder = FieldSpec.builder(
                    toClassName(types.get(0)), fieldName
            );
        } else {
            throw new RuntimeException("At least a single type is required");
        }

        List<Modifier> modifiers = variable.getModifiers();
        if(modifiers.size() != 0){
            fieldBuilder.addModifiers(
                    variable.getModifiers().toArray(new Modifier[]{})
            );
        }

        for(ClassNameWrapper annotation: variable.getAnnotations()){
            fieldBuilder.addAnnotation(toClassName(annotation));
        }

        return fieldBuilder.build();
    }

    private ClassName toClassName(ClassNameWrapper classNameWrapperRep){
        return ClassName.get(
                classNameWrapperRep.getPackageName(),
                classNameWrapperRep.getClassName()
        );
    }

    private ParameterizedTypeName toParameterizedTypeName(
            List<ClassNameWrapper> classNameWrapperReps){
       ClassName rawType = toClassName(classNameWrapperReps.get(0));

       classNameWrapperReps = classNameWrapperReps.subList(
               1, classNameWrapperReps.size()
       );

       return ParameterizedTypeName.get( // unwrap each object and convert them to ClassName[]
               rawType, classNameWrapperReps.stream()
                       .map(this::toClassName)
                       .toArray(ClassName[]::new)
       );
    }
}

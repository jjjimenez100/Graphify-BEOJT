package io.toro.ojtbe.jimenez.Graphify.core.poet;

import com.squareup.javapoet.*;
import io.toro.ojtbe.jimenez.Graphify.core.poet.wrappers.*;
import io.toro.ojtbe.jimenez.Graphify.core.poet.wrappers.ClassWrapper;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

final class PoetServiceImpl implements PoetService{
    private final List<String> logs;

    private final List<TypeName> primitives;

    PoetServiceImpl(){
        this.logs = new ArrayList<>();
        this.primitives = new ArrayList<>();
        addLog("Instance created");
        initPrimitives();
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
    public boolean writeClass(String path,
                              ClassWrapper classWrapperRep) {
        String packageStatement = classWrapperRep.getPackageStatement();
        addLog(
                String.format(
                        "Writing class: %s at path: %s " +
                        "under package: %s",
                        classWrapperRep.getName(),
                        path,
                        packageStatement
                )
        );
        return write(packageStatement, path, createClass(classWrapperRep));
    }

    @Override
    public boolean writeInterface(String path,
                                  Interface interfaceRep) {
        String packageStatement = interfaceRep.getPackageStatement();
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
                .skipJavaLangImports(true)
                .indent("   ")
                .build();
        try {
            file.writeTo(Paths.get(path));
            addLog("Write success");

            return true;
        } catch (IOException e) {
            addLog("Encountered IOException: " + e.getMessage()
            + ", Path: " + path + ", TypeSpec: " + typeSpec);
            debug(path);
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

    private TypeSpec createClass(ClassWrapper classWrapperRep){
       TypeSpec.Builder classBuilder
               = TypeSpec.classBuilder(classWrapperRep.getName());

       List<Modifier> modifiers = classWrapperRep.getModifiers();
       if(modifiers.size() != 0){
           classBuilder.addModifiers(
                   classWrapperRep.getModifiers().toArray(new Modifier[]{})
           );
       }

       for(ClassNameWrapper interfaceRep : classWrapperRep.getInterfaces()){
           classBuilder.addSuperinterface(toClassName(interfaceRep));
       }

       for(Method method: classWrapperRep.getMethods()){
           classBuilder.addMethod(createMethod(method));
       }

       for(ClassNameWrapper annotation: classWrapperRep.getAnnotations()){
           classBuilder.addAnnotation(toClassName(annotation));
       }

       for(Variable field: classWrapperRep.getFields()){
           classBuilder.addField(createField(field));
       }

       for(String typeName: classWrapperRep.getTypeVariables()){
           classBuilder.addTypeVariable(
                   TypeVariableName.get(typeName)
           );
       }

       List<ClassNameWrapper> parentTypes
               = classWrapperRep.getParentTypes();

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
        MethodSpec.Builder methodBuilder;
        List<ClassNameWrapper> type = method.getTypes();

        if(type.isEmpty()){
            methodBuilder = MethodSpec.constructorBuilder();
        } else {
            methodBuilder = MethodSpec.methodBuilder(method.getName());
        }

        if(type.size() > 1){
            methodBuilder.returns(toParameterizedTypeName(type));
        } else if(type.size() == 1){
            methodBuilder.returns(toClassName(type.get(0)));
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

    private void initPrimitives(){
        primitives.add(TypeName.BYTE);
        primitives.add(TypeName.BOOLEAN);
        primitives.add(TypeName.SHORT);
        primitives.add(TypeName.INT);
        primitives.add(TypeName.LONG);
        primitives.add(TypeName.CHAR);
        primitives.add(TypeName.FLOAT);
        primitives.add(TypeName.DOUBLE);
    }

    private ClassName toBoxedType(ClassName name){
        for(TypeName primitive: primitives){

            if(name.toString().equals(primitive.toString())){

                return (ClassName) primitive.box();
            }
        }
        return name;
    }

    private ClassName toClassName(ClassNameWrapper classNameWrapperRep){
        ClassName className = ClassName.get(
                classNameWrapperRep.getPackageName(),
                classNameWrapperRep.getClassName()
        );


        return toBoxedType(className);
    }


    private ParameterizedTypeName toParameterizedTypeName(
            List<ClassNameWrapper> classNameWrapperReps){
       ClassName rawType = toClassName(classNameWrapperReps.get(0));
        System.out.println("Raw type: " + rawType);
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

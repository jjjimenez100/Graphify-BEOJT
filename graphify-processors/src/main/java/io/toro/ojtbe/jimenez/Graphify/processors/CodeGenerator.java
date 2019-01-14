package io.toro.ojtbe.jimenez.Graphify.processors;

import com.squareup.javapoet.*;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

class CodeGenerator {

    private final String defaultPackage;

    CodeGenerator(String defaultPackage){
        this.defaultPackage = defaultPackage;
    }

    public Parameter createParameter(Type type, String name, Modifier modifier){
        return new Parameter(type, name, modifier);
    }

    public List<ParameterSpec> buildParameters(Parameter... parameters){
        List<ParameterSpec> parameterSpecList = new ArrayList<>();

        for(Parameter parameter: parameters){
            ParameterSpec parameterSpec = ParameterSpec
                    .builder(parameter.getType(), parameter.getName(), parameter.getModifier())
                    .build();
            parameterSpecList.add(parameterSpec);
        }

        return parameterSpecList;
    }

    static class Parameter {
        private final Type type;
        private final String name;
        private final Modifier modifier;

        private Parameter(Type type, String name){
            this.type = type;
            this.name = name;
            this.modifier = Modifier.PUBLIC;
        }

        private Parameter(Type type, String name, Modifier modifier){
            this.type = type;
            this.name = name;
            this.modifier = modifier;
        }

        private Type getType() {
            return type;
        }

        private String getName() {
            return name;
        }

        private Modifier getModifier() {
            return modifier;
        }
    }

    MethodSpec buildMethod(String name, Class<?> returnType, Modifier[] modifiers, CodeBlock[] blocks){
        MethodSpec.Builder methodSpecBuilder = MethodSpec
                .methodBuilder(name)
                .addModifiers(modifiers)
                .returns(returnType);

        for(CodeBlock block: blocks){
            methodSpecBuilder.addStatement(block);
        }

        return methodSpecBuilder.build();
    }

    CodeBlock buildCodeBlock(String... statements){
        CodeBlock.Builder codeBlockBuilder = CodeBlock.builder();

        for(String statement: statements){
            codeBlockBuilder.addStatement(statement);
        }

        return codeBlockBuilder.build();
    }

    TypeSpec buildClass(String name, Modifier[] modifiers, MethodSpec[] methods){
        TypeSpec.Builder newClass = TypeSpec.classBuilder(name).addModifiers(modifiers);

        for(MethodSpec method: methods){
            newClass.addMethod(method);
        }

        return newClass.build();
    }

    JavaFile createJavaFile(TypeSpec newClass, String name){
        return JavaFile
                .builder(defaultPackage + name, newClass)
                .build();
    }

    public JavaFile createJavaFile(TypeSpec newClass, String name, String packageName){
        return JavaFile
                .builder(packageName, newClass)
                .build();
    }

    void writeJavaFile(JavaFile javaFile, Path saveLocation){
        try{
            javaFile.writeTo(saveLocation);

        } catch(IOException e){
            throw new RuntimeException(e);
        }
    }
}

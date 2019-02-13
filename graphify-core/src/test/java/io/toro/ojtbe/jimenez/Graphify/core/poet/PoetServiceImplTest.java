package io.toro.ojtbe.jimenez.Graphify.core.poet;

import io.toro.ojtbe.jimenez.Graphify.core.poet.PoetFactory;
import io.toro.ojtbe.jimenez.Graphify.core.poet.PoetService;
import io.toro.ojtbe.jimenez.Graphify.core.poet.wrappers.*;
import io.toro.ojtbe.jimenez.Graphify.core.poet.wrappers.Class;
import org.junit.Test;

import java.nio.file.Paths;

import static org.junit.Assert.*;

public class PoetServiceImplTest {
    @Test
    public void givenPlainClass_whenWriteToClass_thenReturnTrue(){
        PoetFactory factory = new PoetFactory();
        PoetService codeGenerator = factory.getPoetService();

        Class testClass = new Class
                .Builder("TestClass")
                .build();

        assertTrue(
                codeGenerator.writeClass("com.test", "", testClass)
        );
    }

    @Test
    public void givenClassAndAnnotation_whenWriteToClass_thenReturnTrue(){
        PoetFactory factory = new PoetFactory();
        PoetService codeGenerator = factory.getPoetService();

        ClassNameWrapper testAnnotation = new ClassNameWrapper(
                "com.test.d", "Annotation"
        );

        Class testClass = new Class
                .Builder("TestClass")
                .addAnnotation(testAnnotation)
                .build();

        assertTrue(
                codeGenerator.writeClass("com.test", "", testClass)
        );
    }

    @Test
    public void givenClassAndParent_whenWriteToClass_thenReturnTrue(){
        PoetFactory factory = new PoetFactory();
        PoetService codeGenerator = factory.getPoetService();

        ClassNameWrapper parentClass = new ClassNameWrapper(
                "com.test.d", "ParentClass"
        );

        Class testClass = new Class
                .Builder("TestClass")
                .addParentType(parentClass)
                .build();

        assertTrue(
                codeGenerator.writeClass("com.test", "", testClass)
        );
    }

    @Test
    public void givenClassAndParentWithTypeParameters_whenWriteToClass_thenReturnTrue(){
        PoetFactory factory = new PoetFactory();
        PoetService codeGenerator = factory.getPoetService();

        ClassNameWrapper parentClass = new ClassNameWrapper(
                "com.test.d", "ParentClass"
        );

        Class testClass = new Class
                .Builder("TestClass")
                .addTypeParameter("T")
                .addTypeParameter("U")
                .addParentType(parentClass)
                .addParentType(parentClass)
                .addParentType(parentClass)
                .build();

        assertTrue(
                codeGenerator.writeClass("com.test", "", testClass)
        );
    }

    @Test
    public void givenClassAndField_whenWriteToClass_thenReturnTrue(){
        PoetFactory factory = new PoetFactory();
        PoetService codeGenerator = factory.getPoetService();

        ClassNameWrapper dataType =
                new ClassNameWrapper("", "int");

        ClassNameWrapper dataTypeTwo = new ClassNameWrapper(
                "io.toro.ojtbe.jimenez.Graphify.core.poet",
                "PoetService"
        );

        Variable testField = new Variable
                .Builder("testField", dataType)
                .build();

        Variable testFieldTwo = new Variable
                .Builder("poetService", dataTypeTwo)
                .build();

        Class testClass = new Class
                .Builder("TestClass")
                .addField(testField)
                .addField(testFieldTwo)
                .build();

        assertTrue(
                codeGenerator.writeClass("com.test", "", testClass)
        );
    }

    @Test
    public void givenClassWithMethodAnnotationAndParameter_whenWriteToClass_thenReturnTrue(){
        PoetFactory factory = new PoetFactory();
        PoetService codeGenerator = factory.getPoetService();

        ClassNameWrapper returnType =
                new ClassNameWrapper("", "void");

        ClassNameWrapper dataType =
                new ClassNameWrapper("", "int");

        ClassNameWrapper dataTypeTwo = new ClassNameWrapper(
                "io.toro.ojtbe.jimenez.Graphify.core.poet",
                "PoetService"
        );

        Variable testField = new Variable
                .Builder("testField", dataType)
                .build();

        Variable testFieldTwo = new Variable
                .Builder("poetService", dataTypeTwo)
                .build();

        Method testMethod = new Method
                .Builder("main", returnType)
                .addModifier("PUBLIC")
                .addModifier("STATIC")
                .addAnnotation(dataTypeTwo)
                .addParameter(testField)
                .addParameter(testFieldTwo)
                .build();

        Class testClass = new Class
                .Builder("TestClass")
                .addMethod(testMethod)
                .build();

        assertTrue(
                codeGenerator.writeClass("com.test", "", testClass)
        );
    }

    @Test
    public void givenClassWithMethodAndStatements_whenWriteToClass_thenReturnTrue(){
        PoetFactory factory = new PoetFactory();
        PoetService codeGenerator = factory.getPoetService();

        ClassNameWrapper returnType =
                new ClassNameWrapper("", "void");

        int testNum = 3;
        Statement testStatement =
                new Statement("System.out.println($L);\n", testNum);

        String testString = "s";
        Statement testStatementTwo
                = new Statement("System.out.println($S);\n", testString);

        Method testMethod = new Method
                .Builder("main", returnType)
                .addModifier("PUBLIC")
                .addModifier("STATIC")
                .addStatement(testStatement)
                .addStatement(testStatementTwo)
                .build();

        Class testClass = new Class
                .Builder("TestClass")
                .addMethod(testMethod)
                .build();

        assertTrue(
                codeGenerator.writeClass("com.test", "", testClass)
        );
    }

    @Test
    public void givenPlainInterface_whenWriteToInterface_thenReturnTrue(){
        PoetFactory factory = new PoetFactory();
        PoetService codeGenerator = factory.getPoetService();

        Interface testInterface = new Interface.Builder("TestRepo")
                .addModifier("PUBLIC")
                .build();

        assertTrue(
                codeGenerator.writeInterface("com.test",
                        "", testInterface)
        );
    }

    @Test
    public void givenAnnotatedInterfaceWithParentType_whenWriteToInterface_thenReturnTrue(){
        PoetFactory factory = new PoetFactory();
        PoetService codeGenerator = factory.getPoetService();

        ClassNameWrapper type = new ClassNameWrapper(
                "io.toro.ojtbe.jimenez.Graphify.core.poet",
                "PoetService"
        );

        ClassNameWrapper typeTwo = new ClassNameWrapper(
                "io.toro.ojtbe.jimenez.Graphify.core.poet",
                "Poet"
        );

        ClassNameWrapper typeThree = new ClassNameWrapper(
                "io.toro.ojtbe.jimenez.Graphify.core.poet",
                "PoetModel"
        );

        ClassNameWrapper typeFour = new ClassNameWrapper(
                "io.toro.ojtbe.jimenez.Graphify.core.poet",
                "PoetType"
        );

        Interface testInterface = new Interface.Builder("TestRepo")
                .addModifier("PUBLIC")
                .addAnnotation(type)
                .addParentType(typeTwo)
                .addParentType(typeThree)
                .addParentType(typeFour)
                .build();

        assertTrue(
                codeGenerator.writeInterface("com.test",
                        "", testInterface)
        );
    }

    @Test
    public void givenInstanceOfPoetService_whenDebug_thenReturnTrue(){
        PoetFactory factory = new PoetFactory();
        PoetService codeGenerator = factory.getPoetService();

        Class testClass = new Class
                .Builder("TestClass")
                .build();

        codeGenerator.writeClass("com.test", "", testClass);
        assertTrue(
                codeGenerator.debug(
                        Paths.get("").toAbsolutePath().toString()
                )
        );
    }
}

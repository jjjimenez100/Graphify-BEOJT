package io.toro.ojtbe.jimenez.Graphify.core.processors;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import io.toro.ojtbe.jimenez.Graphify.core.processors.GraphModelProcessor;
import org.junit.Test;

import javax.tools.JavaFileObject;

import static com.google.testing.compile.CompilationSubject.*;
import static com.google.testing.compile.Compiler.javac;


public class GraphModelProcessorTest {
    @Test
    public void givenAnnotatedClassWithValidConditions_whenAnnotationProcessing_thenProceedWithNoError(){
        GraphModelProcessor processor = new GraphModelProcessor();

        JavaFileObject modelTest = JavaFileObjects.forSourceLines("ModelTest",
                "package io.toro.ojtbe.jimenez.Graphify.core;",
                "import io.toro.ojtbe.jimenez.Graphify.annotations.GraphModel;",
                "@GraphModel(idName = \"id\")",
                "public class ModelTest {",
                "private int id;",
                "private String name;",
                "public int getId() {",
                "return id;",
                "}}");

        Compilation compilation = javac().withProcessors(processor).compile(modelTest);
        assertThat(compilation).succeeded();
    }

    @Test
    public void givenAnnotatedClassWithInvalidNameValue_whenAnnotationProcessing_thenThrowError(){
        GraphModelProcessor processor = new GraphModelProcessor();

        JavaFileObject modelTest = JavaFileObjects.forSourceLines("ModelTest",
                "package io.toro.ojtbe.jimenez.Graphify.core;",
                "import io.toro.ojtbe.jimenez.Graphify.annotations.GraphModel;",
                "@GraphModel(idName = \"notMatchingName\")",
                "public class ModelTest {",
                "private int id;",
                "private String name;",
                "public int getId() {",
                "return id;",
                "}}");

        Compilation compilation = javac().withProcessors(processor).compile(modelTest);
        assertThat(compilation).failed();
        assertThat(compilation).hadErrorCount(1);
    }

    @Test
    public void givenAnnotatedAbstractClass_whenAnnotationProcessing_thenThrowError(){
        GraphModelProcessor processor = new GraphModelProcessor();

        JavaFileObject modelTest = JavaFileObjects.forSourceLines("ModelTest",
                "package io.toro.ojtbe.jimenez.Graphify.core;",
                "import io.toro.ojtbe.jimenez.Graphify.annotations.GraphModel;",
                "@GraphModel(idName = \"id\")",
                "public abstract class ModelTest {",
                "private int id;",
                "private String name;",
                "public int getId() {",
                "return id;",
                "}}");

        Compilation compilation = javac().withProcessors(processor).compile(modelTest);
        assertThat(compilation).failed();
        assertThat(compilation).hadErrorCount(1);
        assertThat(compilation).hadErrorContaining("Annotated class should not be abstract");
    }

    @Test
    public void givenAnnotatedInterface_whenAnnotationProcessing_thenThrowError(){
        GraphModelProcessor processor = new GraphModelProcessor();

        JavaFileObject modelTest = JavaFileObjects.forSourceLines("ModelTest",
                "package io.toro.ojtbe.jimenez.Graphify.core;",
                "import io.toro.ojtbe.jimenez.Graphify.annotations.GraphModel;",
                "@GraphModel(idName = \"id\")",
                "public interface ModelTest {",
                "public int getId() {",
                "return 1;",
                "}}");

        Compilation compilation = javac().withProcessors(processor).compile(modelTest);

        assertThat(compilation).failed();
        assertThat(compilation).hadErrorCount(1);
        assertThat(compilation).hadErrorContaining("Annotated class should not be an interface");
    }

    @Test
    public void givenAnnotatedEnum_whenAnnotationProcessing_thenThrowError(){
        GraphModelProcessor processor = new GraphModelProcessor();

        JavaFileObject modelTest = JavaFileObjects.forSourceLines("ModelTest",
                "package io.toro.ojtbe.jimenez.Graphify.core;",
                "import io.toro.ojtbe.jimenez.Graphify.annotations.GraphModel;",
                "@GraphModel(idName = \"id\")",
                "public enum ModelTest {",
                "THIS, IS, A, TEST, ENUM}");

        Compilation compilation = javac().withProcessors(processor).compile(modelTest);

        assertThat(compilation).failed();
        assertThat(compilation).hadErrorCount(1);
        assertThat(compilation).hadErrorContaining("Annotated class should not be an enum");
    }

    @Test
    public void givenAnnotatedPrivateClassWithValidConditions_whenAnnotationProcessing_thenProceedWithNoError(){
        GraphModelProcessor processor = new GraphModelProcessor();

        JavaFileObject modelTest = JavaFileObjects.forSourceLines("ModelTest",
                "package io.toro.ojtbe.jimenez.Graphify.core;",
                "import io.toro.ojtbe.jimenez.Graphify.annotations.GraphModel;",
                "@GraphModel(idName = \"id\")",
                "private class ModelTest {",
                "private int id;",
                "private String name;",
                "public int getId() {",
                "return id;",
                "}}");

        Compilation compilation = javac().withProcessors(processor).compile(modelTest);
        assertThat(compilation).failed();
        assertThat(compilation).hadErrorCount(1);
        assertThat(compilation).hadErrorContaining("modifier private not allowed here");
    }
}


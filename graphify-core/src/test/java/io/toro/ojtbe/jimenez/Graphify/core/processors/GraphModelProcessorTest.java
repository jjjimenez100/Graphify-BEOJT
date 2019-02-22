package io.toro.ojtbe.jimenez.Graphify.core.processors;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import org.junit.Test;

import javax.tools.JavaFileObject;

import static com.google.testing.compile.CompilationSubject.*;
import static com.google.testing.compile.Compiler.javac;


public class GraphModelProcessorTest {
    @Test
    public void givenAnnotatedClassWithValidConditions_whenAnnotationProcessing_thenProceedWithNoError(){
        GraphModelProcessor processor = new GraphModelProcessor();

        JavaFileObject modelTest = JavaFileObjects.forResource("CaseOne.java");

        Compilation compilation = javac().withProcessors(processor).compile(modelTest);
        assertThat(compilation).succeeded();
    }

    @Test
    public void givenAnnotatedAbstractClass_whenAnnotationProcessing_thenThrowError(){
        GraphModelProcessor processor = new GraphModelProcessor();

        JavaFileObject modelTest = JavaFileObjects.forResource("CaseThree.java");

        Compilation compilation = javac().withProcessors(processor).compile(modelTest);
        assertThat(compilation).failed();
        assertThat(compilation).hadErrorCount(1);
        assertThat(compilation).hadErrorContaining("Annotated class should not be abstract");
    }

    @Test
    public void givenAnnotatedInterface_whenAnnotationProcessing_thenThrowError(){
        GraphModelProcessor processor = new GraphModelProcessor();

        JavaFileObject modelTest = JavaFileObjects.forResource("CaseFour.java");

        Compilation compilation = javac().withProcessors(processor).compile(modelTest);

        assertThat(compilation).failed();
        assertThat(compilation).hadErrorCount(1);
        assertThat(compilation).hadErrorContaining("Annotated class should not be an interface");
    }

    @Test
    public void givenAnnotatedEnum_whenAnnotationProcessing_thenThrowError(){
        GraphModelProcessor processor = new GraphModelProcessor();

        JavaFileObject modelTest = JavaFileObjects.forResource("CaseFive.java");

        Compilation compilation = javac().withProcessors(processor).compile(modelTest);

        assertThat(compilation).failed();
        assertThat(compilation).hadErrorCount(1);
        assertThat(compilation).hadErrorContaining("Annotated class should not be an enum");
    }

    @Test
    public void givenAnnotatedPrivateClassWithValidConditions_whenAnnotationProcessing_thenThrowError(){
        GraphModelProcessor processor = new GraphModelProcessor();

        JavaFileObject modelTest = JavaFileObjects.forResource("CaseSix.java");

        Compilation compilation = javac().withProcessors(processor).compile(modelTest);
        assertThat(compilation).failed();
        assertThat(compilation).hadErrorCount(1);
        assertThat(compilation).hadErrorContaining("modifier private not allowed here");
    }

    @Test
    public void givenAnnotatedClassWithMultipleIdAnnotation_whenAnnotationProcessing_thenThrowError(){
        GraphModelProcessor processor = new GraphModelProcessor();

        JavaFileObject modelTest = JavaFileObjects.forResource("CaseSeven.java");

        Compilation compilation = javac().withProcessors(processor).compile(modelTest);
        assertThat(compilation).failed();
        assertThat(compilation).hadErrorCount(1);
        assertThat(compilation).hadErrorContaining("Multiple id candidates for: CaseSeven");
    }

    @Test
    public void givenAnnotatedClassWithNoIdAnnotation_whenAnnotationProcessing_thenThrowError(){
        GraphModelProcessor processor = new GraphModelProcessor();

        JavaFileObject modelTest = JavaFileObjects.forResource("CaseEight.java");

        Compilation compilation = javac().withProcessors(processor).compile(modelTest);
        assertThat(compilation).failed();
        assertThat(compilation).hadErrorCount(1);
        assertThat(compilation).hadErrorContaining("No property annotated with @Id");
    }
}


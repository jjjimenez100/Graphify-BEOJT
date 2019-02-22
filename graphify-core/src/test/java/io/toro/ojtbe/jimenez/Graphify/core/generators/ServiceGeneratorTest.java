package io.toro.ojtbe.jimenez.Graphify.core.generators;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeSpec;
import io.toro.ojtbe.jimenez.Graphify.core.GraphEntity;
import org.junit.Test;
import org.mockito.Mockito;

import javax.lang.model.element.Modifier;

import static org.junit.Assert.*;

public class ServiceGeneratorTest {
    @Test(expected = NullPointerException.class)
    // generator directly accesses repository's attributes, so
    // an NPE is thrown given a null spec.
    public void givenNullSpecAndEntityWithClassName_whenCallingGenerateService_thenThrowNPE() throws ServiceGeneratorException{
        ServiceGenerator serviceGenerator =
                new ServiceGeneratorImpl(
                        "", Modifier.PUBLIC, Modifier.FINAL
                );

        GraphEntity graphEntity = new GraphEntity.Builder()
                .property("id", "int")
                .packageName("io.test")
                .modelDirectory("")
                .idType("int")
                .idName("id")
                .className("Person")
                .build();

        ClassName parent = ClassName.get("io.spring.test", "WillieRepository");

        Object result = serviceGenerator.generateService(
                graphEntity,
                Mockito.mock(TypeSpec.class),
                parent
        );

        assertNotNull(
                result
        );

        assertTrue(result instanceof TypeSpec);
    }

    @Test(expected = NullPointerException.class)
    // ParameterizedTypeName can't handle null class names
    public void givenEntitySpecAndNullClassName_whenCallingGenerateService_thenThrowNPE() throws ServiceGeneratorException{
        ServiceGenerator serviceGenerator =
                new ServiceGeneratorImpl(
                        "", Modifier.PUBLIC, Modifier.FINAL
                );

        GraphEntity graphEntity = new GraphEntity.Builder()
                .property("id", "int")
                .packageName("io.test")
                .modelDirectory("")
                .idType("int")
                .idName("id")
                .className("Person")
                .build();

        TypeSpec repo = TypeSpec.interfaceBuilder("PersonRepository").build();

        Object result = serviceGenerator.generateService(
                graphEntity,
                repo,
                Mockito.mock(ClassName.class)
        );

        assertNotNull(
                result
        );

        assertTrue(result instanceof TypeSpec);
    }

    @Test
    public void givenEntitySpecAndClassName_whenCallingGenerateService_thenReturnTypeSpec() throws ServiceGeneratorException{
        ServiceGenerator serviceGenerator =
                new ServiceGeneratorImpl(
                        "", Modifier.PUBLIC, Modifier.FINAL
                );

        GraphEntity graphEntity = new GraphEntity.Builder()
                .property("id", "int")
                .packageName("io.test")
                .modelDirectory("")
                .idType("int")
                .idName("id")
                .className("Person")
                .build();

        TypeSpec repo = TypeSpec.interfaceBuilder("PersonRepository").build();
        ClassName parent = ClassName.get("io.spring.test", "WillieRepository");

        Object result = serviceGenerator.generateService(
                graphEntity,
                repo,
                parent
        );

        assertNotNull(
                result
        );
        assertTrue(result instanceof TypeSpec);
    }

    @Test
    public void givenEntityWithNoPropertySpecAndClassName_whenCallingGenerateService_thenReturnTypeSpec() throws ServiceGeneratorException{
        ServiceGenerator serviceGenerator =
                new ServiceGeneratorImpl(
                        "", Modifier.PUBLIC, Modifier.FINAL
                );

        GraphEntity graphEntity = new GraphEntity.Builder()
                .packageName("io.test")
                .modelDirectory("")
                .idType("int")
                .idName("id")
                .className("Person")
                .build();

        TypeSpec repo = TypeSpec.interfaceBuilder("PersonRepository").build();
        ClassName parent = ClassName.get("io.spring.test", "WillieRepository");

        Object result = serviceGenerator.generateService(
                graphEntity,
                repo,
                parent
        );

        assertNotNull(
                result
        );

        assertTrue(result instanceof TypeSpec);
    }

    @Test(expected = NullPointerException.class)
    public void givenEntityWithNoPackageNameSpecAndClassName_whenCallingGenerateService_thenThrowNPE() throws ServiceGeneratorException{
        ServiceGenerator serviceGenerator =
                new ServiceGeneratorImpl(
                        "", Modifier.PUBLIC, Modifier.FINAL
                );

        GraphEntity graphEntity = new GraphEntity.Builder()
                .property("id", "int")
                .modelDirectory("")
                .idType("int")
                .idName("id")
                .className("Person")
                .build();

        TypeSpec repo = TypeSpec.interfaceBuilder("PersonRepository").build();
        ClassName parent = ClassName.get("io.spring.test", "WillieRepository");

        Object result = serviceGenerator.generateService(
                graphEntity,
                repo,
                parent
        );

        assertNotNull(
                result
        );

        assertTrue(result instanceof TypeSpec);
    }

    @Test(expected = NullPointerException.class)
    public void givenEntityWithNoModelDirectorySpecAndClassName_whenCallingGenerateService_thenThrowNPE() throws ServiceGeneratorException{
        ServiceGenerator serviceGenerator =
                new ServiceGeneratorImpl(
                        "", Modifier.PUBLIC, Modifier.FINAL
                );

        GraphEntity graphEntity = new GraphEntity.Builder()
                .property("id", "int")
                .packageName("io.test")
                .idType("int")
                .idName("id")
                .className("Person")
                .build();

        TypeSpec repo = TypeSpec.interfaceBuilder("PersonRepository").build();
        ClassName parent = ClassName.get("io.spring.test", "WillieRepository");

        Object result = serviceGenerator.generateService(
                graphEntity,
                repo,
                parent
        );

        assertNotNull(
                result
        );

        assertTrue(result instanceof TypeSpec);
    }

    @Test(expected = NullPointerException.class)
    public void givenEntityWithNoIdTypeSpecAndClassName_whenCallingGenerateService_thenThrowNPE() throws ServiceGeneratorException{
        ServiceGenerator serviceGenerator =
                new ServiceGeneratorImpl(
                        "", Modifier.PUBLIC, Modifier.FINAL
                );

        GraphEntity graphEntity = new GraphEntity.Builder()
                .property("id", "int")
                .packageName("io.test")
                .modelDirectory("")
                .idName("id")
                .className("Person")
                .build();

        TypeSpec repo = TypeSpec.interfaceBuilder("PersonRepository").build();
        ClassName parent = ClassName.get("io.spring.test", "WillieRepository");

        Object result = serviceGenerator.generateService(
                graphEntity,
                repo,
                parent
        );

        assertNotNull(
                result
        );

        assertTrue(result instanceof TypeSpec);
    }

    @Test
    public void givenEntityWithNoIdNameSpecAndClassName_whenCallingGenerateService_thenReturnTypeSpec() throws ServiceGeneratorException{
        ServiceGenerator serviceGenerator =
                new ServiceGeneratorImpl(
                        "", Modifier.PUBLIC, Modifier.FINAL
                );

        GraphEntity graphEntity = new GraphEntity.Builder()
                .property("id", "int")
                .packageName("io.test")
                .modelDirectory("")
                .idType("int")
                .className("Person")
                .build();

        TypeSpec repo = TypeSpec.interfaceBuilder("PersonRepository").build();
        ClassName parent = ClassName.get("io.spring.test", "WillieRepository");

        Object result = serviceGenerator.generateService(
                graphEntity,
                repo,
                parent
        );

        assertNotNull(
                result
        );

        assertTrue(result instanceof TypeSpec);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenEntityWithNoClassNameSpecAndClassName_whenCallingGenerateService_thenReturnTypeSpec() throws ServiceGeneratorException{
        ServiceGenerator serviceGenerator =
                new ServiceGeneratorImpl(
                        "", Modifier.PUBLIC, Modifier.FINAL
                );

        GraphEntity graphEntity = new GraphEntity.Builder()
                .property("id", "int")
                .packageName("io.test")
                .modelDirectory("")
                .idType("int")
                .idName("id")
                .build();

        TypeSpec repo = TypeSpec.interfaceBuilder("PersonRepository").build();
        ClassName parent = ClassName.get("io.spring.test", "WillieRepository");

        Object result = serviceGenerator.generateService(
                graphEntity,
                repo,
                parent
        );

        assertNotNull(
                result
        );

        assertTrue(result instanceof TypeSpec);
    }
}

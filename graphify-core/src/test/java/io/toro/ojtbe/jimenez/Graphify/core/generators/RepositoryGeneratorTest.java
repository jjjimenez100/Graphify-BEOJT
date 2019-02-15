package io.toro.ojtbe.jimenez.Graphify.core.generators;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeSpec;
import io.toro.ojtbe.jimenez.Graphify.core.GraphEntity;
import static org.junit.Assert.*;
import org.junit.Test;
import org.mockito.Mockito;

import javax.lang.model.element.Modifier;

public class RepositoryGeneratorTest {

    @Test
    public void givenGraphEntity_whenCallingGenerateRepository_thenReturnTypeSpec() throws RepositoryGeneratorException{
        RepositoryGenerator repositoryGenerator =
                new RepositoryGeneratorImpl(Mockito.anyString(), Modifier.PUBLIC);

        GraphEntity graphEntity = new GraphEntity.Builder()
                .property("id", "int")
                .packageName("io.test")
                .modelDirectory("")
                .idType("int")
                .idName("id")
                .className("Person")
                .build();
        ClassName parent = ClassName.get(Mockito.anyString(), Mockito.anyString());

        Object result = repositoryGenerator.generateRepository(graphEntity, parent);

        assertNotNull(result);
        assertTrue(result instanceof TypeSpec);
    }

    @Test
    public void givenGraphEntityWithNoProperties_whenCallingGenerateRepository_thenReturnTypeSpec() throws RepositoryGeneratorException{
        RepositoryGenerator repositoryGenerator =
                new RepositoryGeneratorImpl(Mockito.anyString(), Modifier.PUBLIC);

        GraphEntity graphEntity = new GraphEntity.Builder()
                .packageName("io.test")
                .modelDirectory("")
                .idType("int")
                .idName("id")
                .className("Person")
                .build();

        ClassName parent = ClassName.get(Mockito.anyString(), Mockito.anyString());

        Object result = repositoryGenerator.generateRepository(graphEntity, parent);

        assertNotNull(result);
        assertTrue(result instanceof TypeSpec);
    }

    @Test(expected = NullPointerException.class)
    // Java poet tries to access a null class name
    public void givenGraphEntityWithNoPackageName_whenCallingGenerateRepository_thenThrowNPE() throws RepositoryGeneratorException{
        RepositoryGenerator repositoryGenerator =
                new RepositoryGeneratorImpl(Mockito.anyString(), Modifier.PUBLIC);

        GraphEntity graphEntity = new GraphEntity.Builder()
                .property("id", "int")
                .modelDirectory("")
                .idType("int")
                .idName("id")
                .className("Person")
                .build();
        ClassName parent = ClassName.get(Mockito.anyString(), Mockito.anyString());

        Object result = repositoryGenerator.generateRepository(graphEntity, parent);

        assertNotNull(result);
        assertTrue(result instanceof TypeSpec);
    }

    @Test(expected = NullPointerException.class)
    //java poet tries to access a path that is null
    public void givenGraphEntityWithNoModelDirectory_whenCallingGenerateRepository_thenThrowNPE() throws RepositoryGeneratorException{
        RepositoryGenerator repositoryGenerator =
                new RepositoryGeneratorImpl(Mockito.anyString(), Modifier.PUBLIC);

        GraphEntity graphEntity = new GraphEntity.Builder()
                .property("id", "int")
                .packageName("io.test")
                .idType("int")
                .idName("id")
                .className("Person")
                .build();
        ClassName parent = ClassName.get(Mockito.anyString(), Mockito.anyString());

        Object result = repositoryGenerator.generateRepository(graphEntity, parent);

        assertNotNull(result);
        assertTrue(result instanceof TypeSpec);
    }

    @Test(expected = NullPointerException.class)
    // Poet throws an NPE because of accessing a null class name
    public void givenGraphEntityWithNoIdType_whenCallingGenerateRepository_thenThrowNPE() throws RepositoryGeneratorException{
        RepositoryGenerator repositoryGenerator =
                new RepositoryGeneratorImpl(Mockito.anyString(), Modifier.PUBLIC);

        GraphEntity graphEntity = new GraphEntity.Builder()
                .property("id", "int")
                .packageName("io.test")
                .modelDirectory("")
                .idName("id")
                .className("Person")
                .build();
        ClassName parent = ClassName.get(Mockito.anyString(), Mockito.anyString());

        Object result = repositoryGenerator.generateRepository(graphEntity, parent);

        assertNotNull(result);
        assertTrue(result instanceof TypeSpec);
    }

    @Test
    // Repository generator does not make use of id name, so this should pass
    public void givenGraphEntityWithNoIdName_whenCallingGenerateRepository_thenReturnTypeSpec() throws RepositoryGeneratorException{
        RepositoryGenerator repositoryGenerator =
                new RepositoryGeneratorImpl(Mockito.anyString(), Modifier.PUBLIC);

        GraphEntity graphEntity = new GraphEntity.Builder()
                .property("id", "int")
                .packageName("io.test")
                .modelDirectory("")
                .idType("int")
                .className("Person")
                .build();
        ClassName parent = ClassName.get(Mockito.anyString(), Mockito.anyString());

        Object result = repositoryGenerator.generateRepository(graphEntity, parent);

        assertNotNull(result);
        assertTrue(result instanceof TypeSpec);
    }

    @Test(expected = IllegalArgumentException.class)
    // poet forbids a class with a name of null
    public void givenGraphEntityWithNoClassName_whenCallingGenerateRepository_thenThrowNPE() throws RepositoryGeneratorException{
        RepositoryGenerator repositoryGenerator =
                new RepositoryGeneratorImpl(Mockito.anyString(), Modifier.PUBLIC);

        GraphEntity graphEntity = new GraphEntity.Builder()
                .property("id", "int")
                .packageName("io.test")
                .modelDirectory("")
                .idType("int")
                .idName("id")
                .build();
        ClassName parent = ClassName.get(Mockito.anyString(), Mockito.anyString());

        Object result = repositoryGenerator.generateRepository(graphEntity, parent);

        assertNotNull(result);
        assertTrue(result instanceof TypeSpec);
    }
}

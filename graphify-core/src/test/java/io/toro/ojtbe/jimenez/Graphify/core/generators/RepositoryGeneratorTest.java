package io.toro.ojtbe.jimenez.Graphify.core.generators;

import io.toro.ojtbe.jimenez.Graphify.core.generators.GraphEntity;
import io.toro.ojtbe.jimenez.Graphify.core.generators.repository.RepositoryGenerator;
import io.toro.ojtbe.jimenez.Graphify.core.generators.repository.RepositoryGeneratorImpl;
import io.toro.ojtbe.jimenez.Graphify.core.poet.PoetFactory;
import io.toro.ojtbe.jimenez.Graphify.core.poet.PoetService;
import io.toro.ojtbe.jimenez.Graphify.core.poet.wrappers.ClassNameWrapper;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class RepositoryGeneratorTest {
    @Test
    public void givenGraphEntityWithEmptyModelDirectory_whenGeneratingRepositories_thenReturnClassWrapper(){
        GraphEntity graphEntity = new GraphEntity.Builder()
                .className("TestClass")
                .idName("id")
                .idType("int")
                .modelDirectory("")
                .packageName("io.toro")
                .property("Test", "TestType")
                .build();

        PoetService codeGen
                = new PoetFactory().getPoetService();
        RepositoryGenerator repositoryGenerator = new RepositoryGeneratorImpl(
                        codeGen, "Repository", "PUBLIC"
        );
        ClassNameWrapper crud = new ClassNameWrapper(
                "org.springframework.data.repository", "CrudRepository"
        );
        assertNotNull(repositoryGenerator.generateRepository(graphEntity, crud));
    }

    @Test
    public void givenGraphEntityWithModelDirectory_whenGeneratingRepositories_thenReturnClassWrapper(){
        GraphEntity graphEntity = new GraphEntity.Builder()
                .className("TestClass")
                .idName("id")
                .idType("int")
                .modelDirectory("src/test/java")
                .packageName("io.toro")
                .property("Test", "TestType")
                .build();

        PoetService codeGen = new PoetFactory().getPoetService();
        RepositoryGenerator repositoryGenerator = new RepositoryGeneratorImpl(
                codeGen, "Repository", "PUBLIC"
        );
        ClassNameWrapper crud = new ClassNameWrapper(
                "org.springframework.data.repository", "CrudRepository"
        );
        assertNotNull(repositoryGenerator.generateRepository(graphEntity, crud));
    }

    @Test
    public void givenGraphEntityWithNoIdName_whenGeneratingRepositories_thenReturnClassWrapper(){
        GraphEntity graphEntity = new GraphEntity.Builder()
                .className("TestClass")
                .idType("int")
                .modelDirectory("src/test/java")
                .packageName("io.toro")
                .property("Test", "TestType")
                .build();

        PoetService codeGen = new PoetFactory().getPoetService();
        RepositoryGenerator repositoryGenerator = new RepositoryGeneratorImpl(
                codeGen, "Repository", "PUBLIC"
        );
        ClassNameWrapper crud = new ClassNameWrapper(
                "org.springframework.data.repository", "CrudRepository"
        );
        assertNotNull(repositoryGenerator.generateRepository(graphEntity, crud));
    }

    @Test(expected = NullPointerException.class)
    // GraphModelProcessor should do validation
    public void givenGraphEntityWithNoIdType_whenGeneratingRepositories_thenThrowNullPointerException(){
        GraphEntity graphEntity = new GraphEntity.Builder()
                .className("TestClass")
                .idName("id")
                .modelDirectory("src/test/java")
                .packageName("io.toro")
                .property("Test", "TestType")
                .build();

        PoetService codeGen = new PoetFactory().getPoetService();
        RepositoryGenerator repositoryGenerator = new RepositoryGeneratorImpl(
                codeGen, "Repository", "PUBLIC"
        );
        ClassNameWrapper crud = new ClassNameWrapper(
                "org.springframework.data.repository", "CrudRepository"
        );
        // should fail and throw NPE
        assertNotNull(repositoryGenerator.generateRepository(graphEntity, crud));
    }

    @Test
    public void givenGraphEntityWithNoClassName_whenGeneratingRepositories_thenReturnClassWrapper(){
        GraphEntity graphEntity = new GraphEntity.Builder()
                .idName("id")
                .idType("int")
                .modelDirectory("src/test/java")
                .packageName("io.toro")
                .property("Test", "TestType")
                .build();

        PoetService codeGen = new PoetFactory().getPoetService();
        RepositoryGenerator repositoryGenerator = new RepositoryGeneratorImpl(
                codeGen, "Repository", "PUBLIC"
        );
        ClassNameWrapper crud = new ClassNameWrapper(
                "org.springframework.data.repository", "CrudRepository"
        );
        assertNotNull(repositoryGenerator.generateRepository(graphEntity, crud));
    }

    @Test(expected = NullPointerException.class)
    public void givenGraphEntityWithNoPackageName_whenGeneratingRepositories_thenThrowNullPointerException(){
        GraphEntity graphEntity = new GraphEntity.Builder()
                .idName("id")
                .idType("int")
                .modelDirectory("src/test/java")
                .className("TestClass")
                .property("Test", "TestType")
                .build();

        PoetService codeGen = new PoetFactory().getPoetService();
        RepositoryGenerator repositoryGenerator = new RepositoryGeneratorImpl(
                codeGen, "Repository", "PUBLIC"
        );
        ClassNameWrapper crud = new ClassNameWrapper(
                "org.springframework.data.repository", "CrudRepository"
        );
        assertNotNull(repositoryGenerator.generateRepository(graphEntity, crud));
    }

    @Test(expected = NullPointerException.class)
    public void givenEmptyGraphEntity_whenGeneratingRepositories_thenThrowNullPointerException(){
        GraphEntity graphEntity = new GraphEntity.Builder()
                .build();

        PoetService codeGen = new PoetFactory().getPoetService();
        RepositoryGenerator repositoryGenerator = new RepositoryGeneratorImpl(
                codeGen, "Repository", "PUBLIC"
        );
        ClassNameWrapper crud = new ClassNameWrapper(
                "org.springframework.data.repository", "CrudRepository"
        );
        assertNotNull(repositoryGenerator.generateRepository(graphEntity, crud));
    }
}

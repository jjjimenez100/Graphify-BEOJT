
package io.toro.ojtbe.jimenez.Graphify.core.generators;

import io.toro.ojtbe.jimenez.Graphify.core.GraphEntity;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class RepositoryGeneratorTest {

    @After
    public void deleteGeneratedFiles() throws IOException{
        Files.walk(Paths.get("src/test/java/io/query/"))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .forEach(File::delete);
    }

    @Test
    public void givenPrimitiveAsPrimaryKey_whenCallingGenerateRepository_thenGenerateRepositoryFile()
    throws RepositoryGeneratorException, IOException {
        RepositoryGenerator repositoryGenerator =
                new RepositoryGeneratorImpl();

        GraphEntity graphEntity = new GraphEntity.Builder()
                .property("id", "int")
                .packageName("io.query")
                .modelDirectory("src/test/java/")
                .idType("int")
                .idName("id")
                .className("OneR")
                .build();

        repositoryGenerator.generate(graphEntity);

        assertTrue(
                Arrays.equals(
                        Files.readAllBytes(Paths.get("src/test/java/io/query/OneRRepository.java")),
                                Files.readAllBytes(Paths.get("src/test/resources/OneRRepository.java"))
                )
        );
    }

    @Test
    public void givenStringAsPrimaryKey_whenCallingGenerateRepository_thenGenerateRepositoryFile()
        throws RepositoryGeneratorException, IOException{
        RepositoryGenerator repositoryGenerator =
                new RepositoryGeneratorImpl();

        GraphEntity graphEntity = new GraphEntity.Builder()
                .property("id", "int")
                .packageName("io.query")
                .modelDirectory("src/test/java/")
                .idType("java.lang.String")
                .idName("id")
                .className("TwoR")
                .build();

        repositoryGenerator.generate(graphEntity);

        assertTrue(
                Arrays.equals(
                        Files.readAllBytes(Paths.get("src/test/java/io/query/TwoRRepository.java")),
                        Files.readAllBytes(Paths.get("src/test/resources/TwoRRepository.java"))
                )
        );
    }

    @Test
    public void givenGraphEntity_whenCallingGenerateRepository_thenGenerateRepositoryFile()
            throws RepositoryGeneratorException{
        RepositoryGenerator repositoryGenerator =
                new RepositoryGeneratorImpl();

        GraphEntity graphEntity = new GraphEntity.Builder()
                .property("id", "int")
                .packageName("io.query")
                .modelDirectory("src/test/java/")
                .idType("int")
                .idName("id")
                .className("Person")
                .build();

        repositoryGenerator.generate(graphEntity);

        assertTrue(
                Files.exists(
                        Paths.get(
                                "src/test/java/io/query/PersonRepository.java"
                        )
                )
        );
    }

    @Test
    public void givenGraphEntityWithNoProperties_whenCallingGenerateRepository_GenerateRepositoryFile()
            throws RepositoryGeneratorException{
        RepositoryGenerator repositoryGenerator =
                new RepositoryGeneratorImpl();

        GraphEntity graphEntity = new GraphEntity.Builder()
                .packageName("io.query")
                .modelDirectory("src/test/java/")
                .idType("int")
                .idName("id")
                .className("Cat")
                .build();

        repositoryGenerator.generate(graphEntity);

        assertTrue(
                Files.exists(
                        Paths.get(
                                "src/test/java/io/query/CatRepository.java"
                        )
                )
        );
    }

    @Test(expected = NullPointerException.class)
    // Java poet tries to access a null class name
    public void givenGraphEntityWithNoPackageName_whenCallingGenerateRepository_thenThrowNPE()
            throws RepositoryGeneratorException{
        RepositoryGenerator repositoryGenerator =
                new RepositoryGeneratorImpl();

        GraphEntity graphEntity = new GraphEntity.Builder()
                .property("id", "int")
                .modelDirectory("src/test/java/")
                .idType("int")
                .idName("id")
                .className("Person")
                .build();

        repositoryGenerator.generate(graphEntity);
    }

    @Test(expected = NullPointerException.class)
    //java poet tries to access a path that is null
    public void givenGraphEntityWithNoModelDirectory_whenCallingGenerateRepository_thenThrowNPE()
            throws RepositoryGeneratorException {
        RepositoryGenerator repositoryGenerator =
                new RepositoryGeneratorImpl();

        GraphEntity graphEntity = new GraphEntity.Builder()
                .property("id", "int")
                .packageName("io.query")
                .idType("int")
                .idName("id")
                .className("Person")
                .build();

        repositoryGenerator.generate(graphEntity);
    }

    @Test(expected = NullPointerException.class)
    // Poet throws an NPE because of accessing a null class name
    public void givenGraphEntityWithNoIdType_whenCallingGenerateRepository_thenThrowNPE()
            throws RepositoryGeneratorException {
        RepositoryGenerator repositoryGenerator =
                new RepositoryGeneratorImpl();

        GraphEntity graphEntity = new GraphEntity.Builder()
                .property("id", "int")
                .packageName("io.query")
                .modelDirectory("src/test/java/")
                .idName("id")
                .className("Person")
                .build();

        repositoryGenerator.generate(graphEntity);
    }

    @Test
    // Repository generator does not make use of id name, so this should pass
    public void givenGraphEntityWithNoIdName_whenCallingGenerateRepository_thenGenerateRepositoryFile()
            throws RepositoryGeneratorException{
        RepositoryGenerator repositoryGenerator =
                new RepositoryGeneratorImpl();

        GraphEntity graphEntity = new GraphEntity.Builder()
                .property("id", "int")
                .packageName("io.query")
                .modelDirectory("src/test/java/")
                .idType("int")
                .className("Dog")
                .build();

        repositoryGenerator.generate(graphEntity);

        assertTrue(
                Files.exists(
                        Paths.get(
                                "src/test/java/io/query/DogRepository.java"
                        )
                )
        );
    }

    @Test
    public void givenGraphEntityWithNoClassName_whenCallingGenerateRepository_thenGenerateRepositoryFile()
            throws RepositoryGeneratorException {
        RepositoryGenerator repositoryGenerator =
                new RepositoryGeneratorImpl();

        GraphEntity graphEntity = new GraphEntity.Builder()
                .property("id", "int")
                .packageName("io.query")
                .modelDirectory("src/test/java/")
                .idType("int")
                .idName("id")
                .build();

        repositoryGenerator.generate(graphEntity);

        assertTrue(
                Files.exists(
                        Paths.get(
                                "src/test/java/io/query/nullRepository.java"
                        )
                )
        );
    }
}


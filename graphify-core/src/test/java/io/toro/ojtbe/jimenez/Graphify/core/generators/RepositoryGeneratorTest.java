
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
        Path testingPath = Paths.get("src/test/java/io/query/");

        if(Files.exists(testingPath)){
            Files.walk(testingPath)
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .forEach(File::delete);
        }
    }

    // Failing test case ; repository generator makes use of lastIndexOf
    // on "." to get the class name from the FQN
    @Test(expected = StringIndexOutOfBoundsException.class)
    public void givenInvalidFQN_whenCallingGenerateRepository_thenThrowStringIndexOutOfBoundsException()
            throws RepositoryGeneratorException {
        RepositoryGenerator repositoryGenerator =
                new RepositoryGeneratorImpl.Builder()
                        .baseDirectory(Paths.get("src/test/java"))
                        .build();

        GraphEntity graphEntity = new GraphEntity.Builder()
                .property("id", "int")
                .idType("int")
                .idName("id")
                .fullyQualifiedName("io/query/Primitive")
                .build();

        repositoryGenerator.generate(graphEntity);
    }

    @Test
    public void givenPrimitiveAsPrimaryKey_whenCallingGenerateRepository_thenGenerateRepositoryFile()
            throws RepositoryGeneratorException, IOException {
        RepositoryGenerator repositoryGenerator =
                new RepositoryGeneratorImpl.Builder()
                        .baseDirectory(Paths.get("src/test/java"))
                        .build();

        GraphEntity graphEntity = new GraphEntity.Builder()
                .property("id", "int")
                .idType("int")
                .idName("id")
                .fullyQualifiedName("io.query.Primitive")
                .build();

        repositoryGenerator.generate(graphEntity);

        assertTrue(
                Arrays.equals(
                        Files.readAllBytes(Paths.get("src/test/java/io/query/PrimitiveRepository.java")),
                                Files.readAllBytes(Paths.get("src/test/resources/PrimitiveRepository.java"))
                )
        );
    }

    @Test
    public void givenNonPrimitiveAsPrimaryKey_whenCallingGenerateRepository_thenGenerateRepositoryFile()
        throws RepositoryGeneratorException, IOException{
        RepositoryGenerator repositoryGenerator =
                new RepositoryGeneratorImpl.Builder()
                        .baseDirectory(Paths.get("src/test/java"))
                        .build();

        GraphEntity graphEntity = new GraphEntity.Builder()
                .property("id", "int")
                .idType("java.lang.String")
                .idName("id")
                .fullyQualifiedName("io.query.NonPrimitive")
                .build();

        repositoryGenerator.generate(graphEntity);

        assertTrue(
                Arrays.equals(
                        Files.readAllBytes(Paths.get("src/test/java/io/query/NonPrimitiveRepository.java")),
                        Files.readAllBytes(Paths.get("src/test/resources/NonPrimitiveRepository.java"))
                )
        );
    }

    @Test
    public void givenGraphEntity_whenCallingGenerateRepository_thenGenerateRepositoryFile()
            throws RepositoryGeneratorException{
        RepositoryGenerator repositoryGenerator =
                new RepositoryGeneratorImpl.Builder()
                        .baseDirectory(Paths.get("src/test/java"))
                        .build();

        GraphEntity graphEntity = new GraphEntity.Builder()
                .property("id", "int")
                .idType("int")
                .idName("id")
                .fullyQualifiedName("io.query.Person")
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
                new RepositoryGeneratorImpl.Builder()
                        .baseDirectory(Paths.get("src/test/java"))
                        .build();

        GraphEntity graphEntity = new GraphEntity.Builder()
                .idType("int")
                .idName("id")
                .fullyQualifiedName("io.query.Cat")
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
    public void givenGraphEntityWithNoFQN_whenCallingGenerateRepository_thenThrowNPE()
            throws RepositoryGeneratorException{
        RepositoryGenerator repositoryGenerator =
                new RepositoryGeneratorImpl.Builder()
                        .baseDirectory(Paths.get("src/test/java"))
                        .build();

        GraphEntity graphEntity = new GraphEntity.Builder()
                .property("id", "int")
                .idType("int")
                .idName("id")
                .build();

        repositoryGenerator.generate(graphEntity);
    }

    @Test(expected = NullPointerException.class)
    // Poet throws an NPE because of accessing a null class name
    public void givenGraphEntityWithNoIdType_whenCallingGenerateRepository_thenThrowNPE()
            throws RepositoryGeneratorException {
        RepositoryGenerator repositoryGenerator =
                new RepositoryGeneratorImpl.Builder()
                        .baseDirectory(Paths.get("src/test/java"))
                        .build();

        GraphEntity graphEntity = new GraphEntity.Builder()
                .property("id", "int")
                .idName("id")
                .fullyQualifiedName("io.query.Cat")
                .build();

        repositoryGenerator.generate(graphEntity);
    }

    @Test
    // Repository generator does not make use of id name, so this should pass
    public void givenGraphEntityWithNoIdName_whenCallingGenerateRepository_thenGenerateRepositoryFile()
            throws RepositoryGeneratorException{
        RepositoryGenerator repositoryGenerator =
                new RepositoryGeneratorImpl.Builder()
                        .baseDirectory(Paths.get("src/test/java"))
                        .build();

        GraphEntity graphEntity = new GraphEntity.Builder()
                .property("id", "int")
                .idType("int")
                .fullyQualifiedName("io.query.Dog")
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
}


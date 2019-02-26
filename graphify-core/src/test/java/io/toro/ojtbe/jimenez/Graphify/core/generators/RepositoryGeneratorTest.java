
package io.toro.ojtbe.jimenez.Graphify.core.generators;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import io.toro.ojtbe.jimenez.Graphify.core.GraphEntity;
import static org.junit.Assert.*;
import org.junit.Test;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class RepositoryGeneratorTest {
    // Failing test case ; repository generator makes use of lastIndexOf
    // on "." to get the class name from the FQN
    @Test(expected = StringIndexOutOfBoundsException.class)
    public void givenInvalidFQN_whenCallingGenerateRepository_thenThrowStringIndexOutOfBoundsException()
            throws RepositoryGeneratorException {

        FileSystem fileSystem = Jimfs.newFileSystem(Configuration.unix());

        RepositoryGenerator repositoryGenerator =
                new RepositoryGeneratorImpl.Builder()
                        .baseDirectory(fileSystem.getPath("src/test/java"))
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

        FileSystem fileSystem = Jimfs.newFileSystem(Configuration.unix());

        RepositoryGenerator repositoryGenerator =
                new RepositoryGeneratorImpl.Builder()
                        .baseDirectory(fileSystem.getPath("src/test/java"))
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
                        Files.readAllBytes(fileSystem.getPath("src/test/java/io/query/PrimitiveRepository.java")),
                                Files.readAllBytes(Paths.get("src/test/resources/PrimitiveRepository.java"))
                )
        );
    }

    @Test
    public void givenNonPrimitiveAsPrimaryKey_whenCallingGenerateRepository_thenGenerateRepositoryFile()
        throws RepositoryGeneratorException, IOException{

        FileSystem fileSystem = Jimfs.newFileSystem(Configuration.unix());

        RepositoryGenerator repositoryGenerator =
                new RepositoryGeneratorImpl.Builder()
                        .baseDirectory(fileSystem.getPath("src/test/java"))
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
                        Files.readAllBytes(fileSystem.getPath("src/test/java/io/query/NonPrimitiveRepository.java")),
                        Files.readAllBytes(Paths.get("src/test/resources/NonPrimitiveRepository.java"))
                )
        );
    }

    @Test
    public void givenGraphEntity_whenCallingGenerateRepository_thenGenerateRepositoryFile()
            throws RepositoryGeneratorException{

        FileSystem fileSystem = Jimfs.newFileSystem(Configuration.unix());

        RepositoryGenerator repositoryGenerator =
                new RepositoryGeneratorImpl.Builder()
                        .baseDirectory(fileSystem.getPath("src/test/java"))
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
                        fileSystem.getPath(
                                "src/test/java/io/query/PersonRepository.java"
                        )
                )
        );
    }

    @Test
    public void givenGraphEntityWithNoProperties_whenCallingGenerateRepository_GenerateRepositoryFile()
            throws RepositoryGeneratorException{

        FileSystem fileSystem = Jimfs.newFileSystem(Configuration.unix());

        RepositoryGenerator repositoryGenerator =
                new RepositoryGeneratorImpl.Builder()
                        .baseDirectory(fileSystem.getPath("src/test/java"))
                        .build();

        GraphEntity graphEntity = new GraphEntity.Builder()
                .idType("int")
                .idName("id")
                .fullyQualifiedName("io.query.Cat")
                .build();

        repositoryGenerator.generate(graphEntity);

        assertTrue(
                Files.exists(
                        fileSystem.getPath(
                                "src/test/java/io/query/CatRepository.java"
                        )
                )
        );
    }

    @Test(expected = NullPointerException.class)
    // Java poet tries to access a null class name
    public void givenGraphEntityWithNoFQN_whenCallingGenerateRepository_thenThrowNPE()
            throws RepositoryGeneratorException{

        FileSystem fileSystem = Jimfs.newFileSystem(Configuration.unix());

        RepositoryGenerator repositoryGenerator =
                new RepositoryGeneratorImpl.Builder()
                        .baseDirectory(fileSystem.getPath("src/test/java"))
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

        FileSystem fileSystem = Jimfs.newFileSystem(Configuration.unix());

        RepositoryGenerator repositoryGenerator =
                new RepositoryGeneratorImpl.Builder()
                        .baseDirectory(fileSystem.getPath("src/test/java"))
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

        FileSystem fileSystem = Jimfs.newFileSystem(Configuration.unix());

        RepositoryGenerator repositoryGenerator =
                new RepositoryGeneratorImpl.Builder()
                        .baseDirectory(fileSystem.getPath("src/test/java"))
                        .build();

        GraphEntity graphEntity = new GraphEntity.Builder()
                .property("id", "int")
                .idType("int")
                .fullyQualifiedName("io.query.Dog")
                .build();

        repositoryGenerator.generate(graphEntity);

        assertTrue(
                Files.exists(
                        fileSystem.getPath(
                                "src/test/java/io/query/DogRepository.java"
                        )
                )
        );
    }
}


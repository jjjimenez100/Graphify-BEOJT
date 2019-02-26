package io.toro.ojtbe.jimenez.Graphify.core.generators;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import io.toro.ojtbe.jimenez.Graphify.core.GraphEntity;
import org.junit.Test;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class QueryGeneratorTest {

    // Failing test case ; query generator makes use of lastIndexOf
    // on "." to get the class name from the FQN
    @Test(expected = StringIndexOutOfBoundsException.class)
    public void givenInvalidFQN_whenCallingGenerateQuery_thenThrowStringIndexOutOfBoundsException()
            throws ServiceGeneratorException {

        FileSystem fileSystem = Jimfs.newFileSystem(Configuration.unix());

        ServiceGenerator serviceGenerator =
                new ServiceGeneratorImpl.Builder()
                        .baseDirectory(fileSystem.getPath("src/test/java"))
                        .build();

        GraphEntity graphEntity = new GraphEntity.Builder()
                .property("id", "int")
                .idType("int")
                .idName("id")
                .fullyQualifiedName("io/query/Primitive")
                .build();

        serviceGenerator.generate(graphEntity);
    }

    @Test
    public void givenSingleGraphEntity_whenGeneratingResolver_thenGenerateResolver()
    throws QueryGeneratorException{

        FileSystem fileSystem = Jimfs.newFileSystem(Configuration.unix());

        QueryGenerator generator = new QueryGeneratorImpl.Builder()
                .baseDirectory(fileSystem.getPath("src/test/java"))
                .build();

        List<GraphEntity> graphEntities = new ArrayList<>();
        GraphEntity graphEntity = new GraphEntity.Builder()
                .property("id", "int")
                .idType("int")
                .idName("id")
                .fullyQualifiedName("io.query.Person")
                .build();
        graphEntities.add(graphEntity);

        generator.generate(graphEntities);

        assertTrue(
                Files.exists(
                        fileSystem.getPath(
                                "src/test/java/io/query/QueryResolver.java"
                        )
                )
        );
    }

    @Test
    public void givenSingleGraphEntity_whenGeneratingResolver_thenGenerateAppropriateResolver()
            throws QueryGeneratorException, IOException {

        FileSystem fileSystem = Jimfs.newFileSystem(Configuration.unix());

        QueryGenerator generator = new QueryGeneratorImpl.Builder()
                .baseDirectory(fileSystem.getPath("src/test/java"))
                .build();

        List<GraphEntity> graphEntities = new ArrayList<>();
        GraphEntity graphEntity = new GraphEntity.Builder()
                .property("id", "int")
                .idType("int")
                .idName("id")
                .fullyQualifiedName("io.query.Person")
                .build();
        graphEntities.add(graphEntity);

        generator.generate(graphEntities);

        assertTrue(
                Arrays.equals(
                        Files.readAllBytes(fileSystem.getPath("src/test/java/io/query/QueryResolver.java")),
                        Files.readAllBytes(Paths.get("src/test/resources/QueryResolver.java"))
                )
        );
    }

    @Test
    public void givenGraphEntitiesInDifferentPackages_whenGeneratingResolver_thenGenerateResolver()
    throws QueryGeneratorException{

        FileSystem fileSystem = Jimfs.newFileSystem(Configuration.unix());

        QueryGenerator generator = new QueryGeneratorImpl.Builder()
                .baseDirectory(fileSystem.getPath("src/test/java"))
                .build();

        List<GraphEntity> graphEntities = new ArrayList<>();
        GraphEntity graphEntity = new GraphEntity.Builder()
                .property("id", "int")
                .idType("int")
                .idName("id")
                .fullyQualifiedName("io.query.Doggo")
                .build();

        //disregards second entitiy's package, defaults
        //into first entity
        GraphEntity graphEntityTwo = new GraphEntity.Builder()
                .property("id", "int")
                .idType("java.lang.String")
                .idName("id")
                .fullyQualifiedName("com.pusa.Catto")
                .build();
        graphEntities.add(graphEntity);
        graphEntities.add(graphEntityTwo);

        generator.generate(graphEntities);

        assertTrue(
                Files.exists(
                        fileSystem.getPath(
                                "src/test/java/io/query/QueryResolver.java"
                        )
                )
        );
    }

    @Test
    public void givenGraphEntitiesInDifferentPackages_whenGeneratingResolver_thenGenerateAppropriateResolver()
            throws QueryGeneratorException, IOException{

        FileSystem fileSystem = Jimfs.newFileSystem(Configuration.unix());

        QueryGenerator generator = new QueryGeneratorImpl.Builder()
                .baseDirectory(fileSystem.getPath("src/test/java"))
                .build();

        List<GraphEntity> graphEntities = new ArrayList<>();
        GraphEntity graphEntity = new GraphEntity.Builder()
                .property("id", "int")
                .idType("int")
                .idName("id")
                .fullyQualifiedName("io.query.Doggo")
                .build();

        //disregards second entitiy's package, defaults
        //into first entity
        GraphEntity graphEntityTwo = new GraphEntity.Builder()
                .property("id", "int")
                .idType("java.lang.String")
                .idName("id")
                .fullyQualifiedName("com.aso.Catto")
                .build();

        graphEntities.add(graphEntity);
        graphEntities.add(graphEntityTwo);

        generator.generate(graphEntities);

        assertTrue(
                Arrays.equals(
                        Files.readAllBytes(fileSystem.getPath("src/test/java/io/query/QueryResolver.java")),
                        Files.readAllBytes(Paths.get("src/test/resources/QueryResolver2.java"))
                )
        );
    }
}

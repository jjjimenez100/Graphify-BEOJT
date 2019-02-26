package io.toro.ojtbe.jimenez.Graphify.core.generators;

import io.toro.ojtbe.jimenez.Graphify.core.GraphEntity;
import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class QueryGeneratorTest {

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

    @Test
    public void givenSingleGraphEntity_whenGeneratingResolver_thenGenerateResolver()
    throws QueryGeneratorException{
        QueryGenerator generator = new QueryGeneratorImpl.Builder()
                .baseDirectory(Paths.get("src/test/java"))
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
                        Paths.get(
                                "src/test/java/io/query/QueryResolver.java"
                        )
                )
        );
    }

    @Test
    public void givenSingleGraphEntity_whenGeneratingResolver_thenGenerateAppropriateResolver()
            throws QueryGeneratorException, IOException {
        QueryGenerator generator = new QueryGeneratorImpl.Builder()
                .baseDirectory(Paths.get("src/test/java"))
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
                        Files.readAllBytes(Paths.get("src/test/java/io/query/QueryResolver.java")),
                        Files.readAllBytes(Paths.get("src/test/resources/QueryResolver.java"))
                )
        );
    }

    @Test
    public void givenGraphEntitiesInDifferentPackages_whenGeneratingResolver_thenGenerateResolver()
    throws QueryGeneratorException{
        QueryGenerator generator = new QueryGeneratorImpl.Builder()
                .baseDirectory(Paths.get("src/test/java"))
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
                        Paths.get(
                                "src/test/java/io/query/QueryResolver.java"
                        )
                )
        );
    }

    @Test
    public void givenGraphEntitiesInDifferentPackages_whenGeneratingResolver_thenGenerateAppropriateResolver()
            throws QueryGeneratorException, IOException{
        QueryGenerator generator = new QueryGeneratorImpl.Builder()
                .baseDirectory(Paths.get("src/test/java"))
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
                        Files.readAllBytes(Paths.get("src/test/java/io/query/QueryResolver.java")),
                        Files.readAllBytes(Paths.get("src/test/resources/QueryResolver2.java"))
                )
        );
    }
}

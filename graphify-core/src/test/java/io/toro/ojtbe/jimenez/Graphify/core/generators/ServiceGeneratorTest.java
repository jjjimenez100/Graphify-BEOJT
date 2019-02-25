package io.toro.ojtbe.jimenez.Graphify.core.generators;

import io.toro.ojtbe.jimenez.Graphify.core.GraphEntity;
import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.Assert.*;

public class ServiceGeneratorTest {

    @After
    public void deleteGeneratedFiles() throws IOException{
        Files.walk(Paths.get("src/test/java/io/query/"))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .forEach(File::delete);
    }

    @Test
    public void givenPrimitiveKey_whenCallingGenerateService_thenGenerateService() throws ServiceGeneratorException, IOException {
        ServiceGenerator serviceGenerator =
                new ServiceGeneratorImpl();

        GraphEntity graphEntity = new GraphEntity.Builder()
                .property("id", "int")
                .packageName("io.query")
                .modelDirectory("src/test/java/")
                .idType("int")
                .idName("id")
                .className("OneS")
                .build();

        serviceGenerator.generate(graphEntity);

        assertTrue(
                Arrays.equals(
                        Files.readAllBytes(Paths.get("src/test/java/io/query/OneSService.java")),
                        Files.readAllBytes(Paths.get("src/test/resources/OneSService.java"))
                )
        );
    }

    @Test
    public void givenStringOrObjectKey_whenCallingGenerateService_thenGenerateService()
            throws ServiceGeneratorException, IOException {
        ServiceGenerator serviceGenerator =
                new ServiceGeneratorImpl();

        GraphEntity graphEntity = new GraphEntity.Builder()
                .property("id", "int")
                .packageName("io.query")
                .modelDirectory("src/test/java/")
                .idType("java.lang.String")
                .idName("id")
                .className("TwoS")
                .build();

        serviceGenerator.generate(graphEntity);

        assertTrue(
                Arrays.equals(
                        Files.readAllBytes(Paths.get("src/test/java/io/query/TwoSService.java")),
                        Files.readAllBytes(Paths.get("src/test/resources/TwoSService.java"))
                )
        );
    }

    @Test
    public void givenEntitySpecAndClassName_whenCallingGenerateService_thenGenerateService() throws ServiceGeneratorException{
        ServiceGenerator serviceGenerator =
                new ServiceGeneratorImpl();

        GraphEntity graphEntity = new GraphEntity.Builder()
                .property("id", "int")
                .packageName("io.query")
                .modelDirectory("src/test/java/")
                .idType("int")
                .idName("id")
                .className("Person")
                .build();

        serviceGenerator.generate(graphEntity);

        assertTrue(
                Files.exists(
                        Paths.get(
                                "src/test/java/io/query/PersonService.java"
                        )
                )
        );
    }

    @Test
    public void givenEntityWithNoPropertySpecAndClassName_whenCallingGenerateService_thenGenerateService() throws ServiceGeneratorException{
        ServiceGenerator serviceGenerator =
                new ServiceGeneratorImpl();

        GraphEntity graphEntity = new GraphEntity.Builder()
                .property("id", "int")
                .packageName("io.query")
                .modelDirectory("src/test/java/")
                .idType("int")
                .idName("id")
                .className("Doggo")
                .build();

        serviceGenerator.generate(graphEntity);

        assertTrue(
                Files.exists(
                        Paths.get(
                                "src/test/java/io/query/DoggoService.java"
                        )
                )
        );
    }

    @Test(expected = NullPointerException.class)
    public void givenEntityWithNoPackageNameSpecAndClassName_whenCallingGenerateService_thenThrowNPE() throws ServiceGeneratorException{
        ServiceGenerator serviceGenerator =
                new ServiceGeneratorImpl();

        GraphEntity graphEntity = new GraphEntity.Builder()
                .property("id", "int")
                .modelDirectory("")
                .idType("int")
                .idName("id")
                .className("Person")
                .build();

        serviceGenerator.generate(
                graphEntity
        );
    }

    @Test(expected = NullPointerException.class)
    public void givenEntityWithNoModelDirectorySpecAndClassName_whenCallingGenerateService_thenThrowNPE() throws ServiceGeneratorException{
        ServiceGenerator serviceGenerator =
                new ServiceGeneratorImpl();

        GraphEntity graphEntity = new GraphEntity.Builder()
                .property("id", "int")
                .packageName("io.test")
                .idType("int")
                .idName("id")
                .className("Person")
                .build();

        serviceGenerator.generate(graphEntity);
    }

    @Test(expected = NullPointerException.class)
    public void givenEntityWithNoIdTypeSpecAndClassName_whenCallingGenerateService_thenThrowNPE() throws ServiceGeneratorException{
        ServiceGenerator serviceGenerator =
                new ServiceGeneratorImpl();

        GraphEntity graphEntity = new GraphEntity.Builder()
                .property("id", "int")
                .packageName("io.test")
                .modelDirectory("")
                .idName("id")
                .className("Person")
                .build();

        serviceGenerator.generate(
                graphEntity
        );
    }

    @Test
    public void givenEntityWithNoIdNameSpecAndClassName_whenCallingGenerateService_thenGenerateService()
            throws ServiceGeneratorException{
        ServiceGenerator serviceGenerator =
                new ServiceGeneratorImpl();

        GraphEntity graphEntity = new GraphEntity.Builder()
                .property("id", "int")
                .packageName("io.query")
                .modelDirectory("src/test/java/")
                .idType("int")
                .className("Catty")
                .build();

        serviceGenerator.generate(graphEntity);

        assertTrue(
                Files.exists(
                        Paths.get(
                                "src/test/java/io/query/CattyService.java"
                        )
                )
        );
    }

    @Test
    public void givenEntityWithNoClassNameSpecAndClassName_whenCallingGenerateService_thenGenerateService() throws ServiceGeneratorException{
        ServiceGenerator serviceGenerator =
                new ServiceGeneratorImpl();

        GraphEntity graphEntity = new GraphEntity.Builder()
                .property("id", "int")
                .packageName("io.query")
                .modelDirectory("src/test/java/")
                .idType("int")
                .idName("id")
                .build();

        serviceGenerator.generate(graphEntity);

        assertTrue(
                Files.exists(
                        Paths.get(
                                "src/test/java/io/query/nullService.java"
                        )
                )
        );
    }
}

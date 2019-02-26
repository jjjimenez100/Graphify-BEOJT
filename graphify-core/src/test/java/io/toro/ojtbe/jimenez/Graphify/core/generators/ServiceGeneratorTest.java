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
        Path testingPath = Paths.get("src/test/java/io/query/");

        if(Files.exists(testingPath)){
            Files.walk(testingPath)
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .forEach(File::delete);
        }
    }

    @Test
    public void givenPrimitiveKey_whenCallingGenerateService_thenGenerateService() throws ServiceGeneratorException, IOException {
        ServiceGenerator serviceGenerator =
                new ServiceGeneratorImpl.Builder()
                        .baseDirectory(Paths.get("src/test/java"))
                        .build();

        GraphEntity graphEntity = new GraphEntity.Builder()
                .property("id", "int")
                .idType("int")
                .idName("id")
                .fullyQualifiedName("io.query.PrimitiveServ")
                .build();

        serviceGenerator.generate(graphEntity);

        assertTrue(
                Arrays.equals(
                        Files.readAllBytes(Paths.get("src/test/java/io/query/PrimitiveServService.java")),
                        Files.readAllBytes(Paths.get("src/test/resources/PrimitiveServService.java"))
                )
        );
    }

    @Test
    public void givenNonPrimitiveKey_whenCallingGenerateService_thenGenerateService()
            throws ServiceGeneratorException, IOException {
        ServiceGenerator serviceGenerator =
                new ServiceGeneratorImpl.Builder()
                        .baseDirectory(Paths.get("src/test/java"))
                        .build();

        GraphEntity graphEntity = new GraphEntity.Builder()
                .property("id", "int")
                .idType("java.lang.String")
                .idName("id")
                .fullyQualifiedName("io.query.NonPrimitiveServ")
                .build();

        serviceGenerator.generate(graphEntity);

        assertTrue(
                Arrays.equals(
                        Files.readAllBytes(Paths.get("src/test/java/io/query/NonPrimitiveServService.java")),
                        Files.readAllBytes(Paths.get("src/test/resources/NonPrimitiveServService.java"))
                )
        );
    }

    @Test
    public void givenEntityAndFQNWhenCallingGenerateService_thenGenerateService() throws ServiceGeneratorException{
        ServiceGenerator serviceGenerator =
                new ServiceGeneratorImpl.Builder()
                        .baseDirectory(Paths.get("src/test/java"))
                        .build();

        GraphEntity graphEntity = new GraphEntity.Builder()
                .property("id", "int")
                .idType("int")
                .idName("id")
                .fullyQualifiedName("io.query.Person")
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

    @Test(expected = NullPointerException.class)
    public void givenEntityWithNoFQN_whenCallingGenerateService_thenThrowNPE() throws ServiceGeneratorException{
        ServiceGenerator serviceGenerator =
                new ServiceGeneratorImpl.Builder()
                        .baseDirectory(Paths.get("src/test/java"))
                        .build();

        GraphEntity graphEntity = new GraphEntity.Builder()
                .property("id", "int")
                .idType("int")
                .idName("id")
                .build();

        serviceGenerator.generate(
                graphEntity
        );
    }

    @Test(expected = NullPointerException.class)
    public void givenEntityWithNoIdTypeSpec_whenCallingGenerateService_thenThrowNPE() throws ServiceGeneratorException{
        ServiceGenerator serviceGenerator =
                new ServiceGeneratorImpl.Builder()
                        .baseDirectory(Paths.get("src/test/java"))
                        .build();

        GraphEntity graphEntity = new GraphEntity.Builder()
                .property("id", "int")
                .idName("id")
                .fullyQualifiedName("io.query.Person")
                .build();

        serviceGenerator.generate(
                graphEntity
        );
    }

    @Test
    public void givenEntityWithNoIdNameSpec_whenCallingGenerateService_thenGenerateService()
            throws ServiceGeneratorException{
        ServiceGenerator serviceGenerator =
                new ServiceGeneratorImpl.Builder()
                        .baseDirectory(Paths.get("src/test/java"))
                        .build();

        GraphEntity graphEntity = new GraphEntity.Builder()
                .property("id", "int")
                .idType("int")
                .fullyQualifiedName("io.query.Catty")
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
}

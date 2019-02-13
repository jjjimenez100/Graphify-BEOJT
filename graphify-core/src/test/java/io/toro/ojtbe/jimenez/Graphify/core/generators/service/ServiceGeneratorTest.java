package io.toro.ojtbe.jimenez.Graphify.core.generators.service;

import io.toro.ojtbe.jimenez.Graphify.core.generators.GraphEntity;
import io.toro.ojtbe.jimenez.Graphify.core.poet.PoetFactory;
import io.toro.ojtbe.jimenez.Graphify.core.poet.PoetService;
import io.toro.ojtbe.jimenez.Graphify.core.poet.wrappers.ClassNameWrapper;
import io.toro.ojtbe.jimenez.Graphify.core.poet.wrappers.Interface;

import static org.junit.Assert.*;
import org.junit.Test;

public class ServiceGeneratorTest {
    @Test
    public void givenInterfaceAndGraphEntity_whenGeneratingService_thenReturnTrue(){
        PoetFactory factory = new PoetFactory();
        PoetService codeGenerator = factory.getPoetService();

        ClassNameWrapper parent = new ClassNameWrapper(
                "gaga.z",
                "ServiceImpl"
        );

        Interface testInterface = new Interface.Builder("TestRepo", "com.test")
                .build();

        ServiceGenerator generator = new ServiceGeneratorImpl(
                codeGenerator,
                parent,
                "Service"
        );

        GraphEntity entity = new GraphEntity.Builder()
                .className("Person")
                .idName("id")
                .idType("int")
                .modelDirectory("src/test/java")
                .packageName("io.test")
                .build();

        assertNotNull(
                generator.generateService(
                        entity,
                        testInterface
                )
        );
    }

    @Test
    public void givenParentWithEmptyPropertiesInterfaceAndGraphEntity_whenGeneratingService_thenReturnTrue(){
        PoetFactory factory = new PoetFactory();
        PoetService codeGenerator = factory.getPoetService();

        ClassNameWrapper parent = new ClassNameWrapper(
                "",
                ""
        );

        Interface testInterface = new Interface.Builder("TestRepo", "com.test")
                .addModifier("PUBLIC")
                .build();

        ServiceGenerator generator = new ServiceGeneratorImpl(
                codeGenerator,
                parent,
                "Service"
        );

        GraphEntity entity = new GraphEntity.Builder()
                .className("Person")
                .idName("id")
                .idType("int")
                .modelDirectory("src/test/java")
                .packageName("io.test")
                .build();

        assertNotNull(
                generator.generateService(
                        entity,
                        testInterface
                )
        );
    }

    @Test
    public void givenParentWithEmptyPropertiesAndInterfaceWithShortClassNameAndGraphEntity_whenGeneratingService_thenReturnTrue(){
        PoetFactory factory = new PoetFactory();
        PoetService codeGenerator = factory.getPoetService();

        ClassNameWrapper parent = new ClassNameWrapper(
                "",
                ""
        );

        Interface testInterface = new Interface.Builder("s", "")
                .build();

        ServiceGenerator generator = new ServiceGeneratorImpl(
                codeGenerator,
                parent,
                "Service"
        );

        GraphEntity entity = new GraphEntity.Builder()
                .className("Person")
                .idName("id")
                .idType("int")
                .modelDirectory("src/test/java")
                .packageName("io.test")
                .build();

        assertNotNull(
                generator.generateService(
                        entity,
                        testInterface
                )
        );
    }

    @Test(expected = StringIndexOutOfBoundsException.class)
    public void givenParentAndInterfaceWithEmptyPropertiesAndGraphEntity_whenGeneratingService_thenThrowIndexOutOfBoundsException(){
        PoetFactory factory = new PoetFactory();
        PoetService codeGenerator = factory.getPoetService();

        ClassNameWrapper parent = new ClassNameWrapper(
                "",
                ""
        );

        Interface testInterface = new Interface.Builder("", "")
                .build();

        ServiceGenerator generator = new ServiceGeneratorImpl(
                codeGenerator,
                parent,
                "Service"
        );

        GraphEntity entity = new GraphEntity.Builder()
                .className("Person")
                .idName("id")
                .idType("int")
                .modelDirectory("src/test/java")
                .packageName("io.test")
                .build();

        assertNotNull(
                generator.generateService(
                        entity,
                        testInterface
                )
        );
    }

    @Test(expected = IllegalArgumentException.class)
    // cant convert to uppercase a numeric char
    // Interface naming to be checked by the GraphModelProcessor
    public void givenParentAndInterfaceWithNumericNameAndGraphEntity_whenGeneratingService_thenThrowIllegalArgumentException(){
        PoetFactory factory = new PoetFactory();
        PoetService codeGenerator = factory.getPoetService();

        ClassNameWrapper parent = new ClassNameWrapper(
                "",
                ""
        );

        Interface testInterface = new Interface.Builder("1", "111")
                .build();

        ServiceGenerator generator = new ServiceGeneratorImpl(
                codeGenerator,
                parent,
                "Service"
        );

        GraphEntity entity = new GraphEntity.Builder()
                .className("Person")
                .idName("id")
                .idType("int")
                .modelDirectory("src/test/java")
                .packageName("io.test")
                .build();

        assertNotNull(
                generator.generateService(
                        entity,
                        testInterface
                )
        );
    }

    @Test
    public void givenParentAndInterfaceAndGraphEntityWithNoClassName_whenGeneratingService_thenReturnTrue(){
        PoetFactory factory = new PoetFactory();
        PoetService codeGenerator = factory.getPoetService();

        ClassNameWrapper parent = new ClassNameWrapper(
                "",
                ""
        );

        Interface testInterface = new Interface.Builder("s", "s")
                .build();

        ServiceGenerator generator = new ServiceGeneratorImpl(
                codeGenerator,
                parent,
                "Service"
        );

        GraphEntity entity = new GraphEntity.Builder()
                .idName("id")
                .idType("int")
                .modelDirectory("src/test/java")
                .packageName("io.test")
                .build();

        assertNotNull(
                generator.generateService(
                        entity,
                        testInterface
                )
        );
    }

    @Test
    public void givenParentAndInterfaceAndGraphEntityWithNoIdName_whenGeneratingService_thenReturnTrue(){
        PoetFactory factory = new PoetFactory();
        PoetService codeGenerator = factory.getPoetService();

        ClassNameWrapper parent = new ClassNameWrapper(
                "",
                ""
        );

        Interface testInterface = new Interface.Builder("s", "s")
                .build();

        ServiceGenerator generator = new ServiceGeneratorImpl(
                codeGenerator,
                parent,
                "Service"
        );

        GraphEntity entity = new GraphEntity.Builder()
                .className("Ds")
                .idType("int")
                .modelDirectory("src/test/java")
                .packageName("io.test")
                .build();

        assertNotNull(
                generator.generateService(
                        entity,
                        testInterface
                )
        );
    }

    @Test(expected = NullPointerException.class)
    public void givenParentAndInterfaceAndGraphEntityWithNoModelDirectory_whenGeneratingService_thenReturnTrue(){
        PoetFactory factory = new PoetFactory();
        PoetService codeGenerator = factory.getPoetService();

        ClassNameWrapper parent = new ClassNameWrapper(
                "",
                ""
        );

        Interface testInterface = new Interface.Builder("s", "s")
                .build();

        ServiceGenerator generator = new ServiceGeneratorImpl(
                codeGenerator,
                parent,
                "Service"
        );

        GraphEntity entity = new GraphEntity.Builder()
                .className("Ds")
                .packageName("io.test")
                .build();

        assertNotNull(
                generator.generateService(
                        entity,
                        testInterface
                )
        );
    }

    @Test(expected = NullPointerException.class)
    public void givenParentAndInterfaceAndGraphEntityWithNoPackageName_whenGeneratingService_thenReturnTrue(){
        PoetFactory factory = new PoetFactory();
        PoetService codeGenerator = factory.getPoetService();

        ClassNameWrapper parent = new ClassNameWrapper(
                "",
                ""
        );

        Interface testInterface = new Interface.Builder("s", "s")
                .build();

        ServiceGenerator generator = new ServiceGeneratorImpl(
                codeGenerator,
                parent,
                "Service"
        );

        GraphEntity entity = new GraphEntity.Builder()
                .className("Ds")
                .idName("id")
                .idType("int")
                .modelDirectory("src/test/java")
                .build();

        assertNotNull(
                generator.generateService(
                        entity,
                        testInterface
                )
        );
    }
}

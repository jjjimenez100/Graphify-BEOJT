
package io.toro.ojtbe.jimenez.Graphify.core.generators;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeSpec;
import io.toro.ojtbe.jimenez.Graphify.core.GraphEntity;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class QueryGeneratorTest {
    @Test
    public void givenSingleGraphEntityAndSingleService_whenGeneratingResolver_thenReturnTypeSpec(){
        QueryGenerator generator = new QueryGeneratorImpl("QueryResolver",
                Mockito.mock(ClassName.class));

        List<GraphEntity> graphEntities = new ArrayList<>();
        GraphEntity graphEntity = new GraphEntity.Builder()
                .property("id", "int")
                .packageName("io.test")
                .modelDirectory("")
                .idType("int")
                .idName("id")
                .className("Person")
                .build();
        graphEntities.add(graphEntity);

        List<TypeSpec> services = new ArrayList<>();
        services.add(TypeSpec.classBuilder("PeronService").build());

        Object result = generator.generateQuery(graphEntities, services);
        assertNotNull(result);
        assertTrue(result instanceof TypeSpec);
    }

    @Test
    public void givenGraphEntitiesAndServices_whenGeneratingResolver_thenReturnTypeSpec(){
        QueryGenerator generator = new QueryGeneratorImpl("QueryResolver",
                Mockito.mock(ClassName.class));

        List<GraphEntity> graphEntities = new ArrayList<>();
        GraphEntity graphEntity = new GraphEntity.Builder()
                .property("id", "int")
                .packageName("io.test")
                .modelDirectory("")
                .idType("int")
                .idName("id")
                .className("Person")
                .build();
        GraphEntity graphEntityTwo = new GraphEntity.Builder()
                .property("id", "int")
                .packageName("io.test")
                .modelDirectory("")
                .idType("int")
                .idName("id")
                .className("Dog")
                .build();
        graphEntities.add(graphEntity);
        graphEntities.add(graphEntityTwo);

        List<TypeSpec> services = new ArrayList<>();
        services.add(TypeSpec.classBuilder("PeronService").build());
        services.add(TypeSpec.classBuilder("DogService").build());

        Object result = generator.generateQuery(graphEntities, services);
        assertNotNull(result);
        assertTrue(result instanceof TypeSpec);
    }

    @Test(expected = QueryGeneratorException.class)
    public void givenMismatchGraphEntity_whenGeneratingResolver_thenThrowQueryGeneratorException(){
        QueryGenerator generator = new QueryGeneratorImpl("QueryResolver",
                Mockito.mock(ClassName.class));
        List<GraphEntity> graphEntities = new ArrayList<>();
        List<TypeSpec> services = new ArrayList<>();
        services.add(TypeSpec.classBuilder("PeronService").build());

        Object result = generator.generateQuery(graphEntities, services);
        assertNotNull(result);
        assertTrue(result instanceof TypeSpec);
    }

    @Test(expected = QueryGeneratorException.class)
    public void givenMismatchService_whenGeneratingResolver_thenThrowQueryGeneratorException(){
        QueryGenerator generator = new QueryGeneratorImpl("QueryResolver",
                Mockito.mock(ClassName.class));
        List<GraphEntity> graphEntities = new ArrayList<>();
        GraphEntity graphEntity = new GraphEntity.Builder()
                .property("id", "int")
                .packageName("io.test")
                .modelDirectory("")
                .idType("int")
                .idName("id")
                .className("Person")
                .build();
        graphEntities.add(graphEntity);

        List<TypeSpec> services = new ArrayList<>();

        Object result = generator.generateQuery(graphEntities, services);
        assertNotNull(result);
        assertTrue(result instanceof TypeSpec);
    }
}


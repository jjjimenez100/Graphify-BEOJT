package io.toro.ojtbe.jimenez.Graphify.core.generators.resolver;

import io.toro.ojtbe.jimenez.Graphify.core.generators.GraphEntity;
import io.toro.ojtbe.jimenez.Graphify.core.poet.PoetFactory;
import io.toro.ojtbe.jimenez.Graphify.core.poet.PoetService;
import io.toro.ojtbe.jimenez.Graphify.core.poet.wrappers.ClassNameWrapper;
import io.toro.ojtbe.jimenez.Graphify.core.poet.wrappers.ClassWrapper;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;

public class QueryGeneratorTest {
    @Test
    public void givenServicesAndGraphEntity_whenGeneratingService_thenReturnTrue(){
        PoetFactory factory = new PoetFactory();
        PoetService codeGenerator = factory.getPoetService();

        GraphEntity entity = new GraphEntity.Builder()
                .className("Person")
                .idName("id")
                .idType("int")
                .modelDirectory("/s")
                .packageName("io.test")
                .build();

        List<GraphEntity> graphEntityList = new ArrayList<>();
        graphEntityList.add(entity);

        List<ClassWrapper> services = new ArrayList<>();
        services.add(new ClassWrapper.Builder("test", "io.test").build());

        ClassNameWrapper resolverInt
                = new ClassNameWrapper("com.coxautodev.graphql.tools", "GraphQLQueryResolver");
        QueryGenerator queryGenerator = new QueryGeneratorImpl(codeGenerator, "QueryResolver", resolverInt);
        assertNotNull(queryGenerator.generateQuery(graphEntityList, services));
    }

    @Test(expected = QueryGeneratorException.class)
    public void givenEmptyServicesAndGraphEntity_whenGeneratingService_thenThrowQueryGeneratorException(){
        PoetFactory factory = new PoetFactory();
        PoetService codeGenerator = factory.getPoetService();

        GraphEntity entity = new GraphEntity.Builder()
                .className("Person")
                .idName("id")
                .idType("int")
                .modelDirectory("/s")
                .packageName("io.test")
                .build();

        List<GraphEntity> graphEntityList = new ArrayList<>();

        List<ClassWrapper> services = new ArrayList<>();

        ClassNameWrapper resolverInt
                = new ClassNameWrapper("com.coxautodev.graphql.tools", "GraphQLQueryResolver");
        QueryGenerator queryGenerator = new QueryGeneratorImpl(codeGenerator, "QueryResolver", resolverInt);
        assertNotNull(queryGenerator.generateQuery(graphEntityList, services));
    }

    @Test(expected = QueryGeneratorException.class)
    public void givenMismatchGraphEntitiesAndServicesSize_whenGeneratingService_thenThrowQueryGeneratorException(){
        PoetFactory factory = new PoetFactory();
        PoetService codeGenerator = factory.getPoetService();

        GraphEntity entity = new GraphEntity.Builder()
                .className("Person")
                .idName("id")
                .idType("int")
                .modelDirectory("/s")
                .packageName("io.test")
                .build();

        List<GraphEntity> graphEntityList = new ArrayList<>();

        List<ClassWrapper> services = new ArrayList<>();
        services.add(new ClassWrapper.Builder("test", "io.test").build());

        ClassNameWrapper resolverInt
                = new ClassNameWrapper("com.coxautodev.graphql.tools", "GraphQLQueryResolver");
        QueryGenerator queryGenerator = new QueryGeneratorImpl(codeGenerator, "QueryResolver", resolverInt);
        assertNotNull(queryGenerator.generateQuery(graphEntityList, services));
    }

    @Test(expected = NullPointerException.class)
    public void givenNullGraphEntitiesAndServices_whenGeneratingService_thenReturnThrowNullPointerException(){
        PoetFactory factory = new PoetFactory();
        PoetService codeGenerator = factory.getPoetService();

        GraphEntity entity = new GraphEntity.Builder()
                .className("Person")
                .idName("id")
                .idType("int")
                .modelDirectory("/s")
                .packageName("io.test")
                .build();

        ClassNameWrapper resolverInt
                = new ClassNameWrapper("com.coxautodev.graphql.tools", "GraphQLQueryResolver");
        QueryGenerator queryGenerator = new QueryGeneratorImpl(codeGenerator, "QueryResolver", resolverInt);
        assertNotNull(queryGenerator.generateQuery(null, null));
    }
}

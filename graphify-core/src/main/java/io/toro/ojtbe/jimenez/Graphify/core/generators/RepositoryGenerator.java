package io.toro.ojtbe.jimenez.Graphify.core.generators;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeSpec;
import io.toro.ojtbe.jimenez.Graphify.core.GraphEntity;

interface RepositoryGenerator {
    TypeSpec generateRepository(GraphEntity graphEntity,
                                 ClassName parent)
            throws RepositoryGeneratorException;
}

package io.toro.ojtbe.jimenez.Graphify.core.generators;

import com.squareup.javapoet.TypeSpec;
import io.toro.ojtbe.jimenez.Graphify.core.GraphEntity;

import java.util.List;

interface QueryGenerator {
    TypeSpec generateQuery(List<GraphEntity> graphEntities,
                           List<TypeSpec> services);
}

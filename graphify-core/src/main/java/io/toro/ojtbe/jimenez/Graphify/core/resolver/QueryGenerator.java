package io.toro.ojtbe.jimenez.Graphify.core.generators.resolver;

import io.toro.ojtbe.jimenez.Graphify.core.generators.GraphEntity;
import io.toro.ojtbe.jimenez.Graphify.core.poet.wrappers.ClassWrapper;

import java.util.List;

public interface QueryGenerator {
    ClassWrapper generateQuery(List<GraphEntity> graphEntities,
                               List<ClassWrapper> services);
}

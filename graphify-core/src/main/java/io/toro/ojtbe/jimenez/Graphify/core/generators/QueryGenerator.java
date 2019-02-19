package io.toro.ojtbe.jimenez.Graphify.core.generators;

import io.toro.ojtbe.jimenez.Graphify.core.GraphEntity;

import java.util.List;

interface QueryGenerator extends Generator {
    void generate(List<GraphEntity> graphEntities,
                     String serviceClass,
                     String servicePackage)
            throws QueryGeneratorException;
}

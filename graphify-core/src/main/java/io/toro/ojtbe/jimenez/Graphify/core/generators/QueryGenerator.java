package io.toro.ojtbe.jimenez.Graphify.core.generators;

import io.toro.ojtbe.jimenez.Graphify.core.GraphEntity;

import java.util.List;
import java.util.Map;

interface QueryGenerator extends Generator {
    void generate(List<GraphEntity> graphEntities,
                  Map<String, String> services,
                  String packageName,
                  String path)
            throws QueryGeneratorException;
}

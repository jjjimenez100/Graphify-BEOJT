package io.toro.ojtbe.jimenez.Graphify.core.generators;

import io.toro.ojtbe.jimenez.Graphify.core.GraphEntity;
import io.toro.ojtbe.jimenez.Graphify.core.Handler;

import java.util.List;

interface QueryGenerator extends Handler<List<GraphEntity>> {
    void generate(List<GraphEntity> graphEntities)
            throws QueryGeneratorException;
}

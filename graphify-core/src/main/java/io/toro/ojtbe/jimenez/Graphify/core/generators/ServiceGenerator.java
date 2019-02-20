package io.toro.ojtbe.jimenez.Graphify.core.generators;

import io.toro.ojtbe.jimenez.Graphify.core.GraphEntity;
import io.toro.ojtbe.jimenez.Graphify.core.Handler;

import java.util.List;

interface ServiceGenerator extends Handler<List<GraphEntity>> {
    void generate(GraphEntity graphEntity)
            throws ServiceGeneratorException;
}

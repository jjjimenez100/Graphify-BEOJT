package io.toro.ojtbe.jimenez.Graphify.core.generators;

import io.toro.ojtbe.jimenez.Graphify.core.GraphEntity;

import java.util.List;

interface ServiceGenerator extends Generator {
    void generate(List<GraphEntity> graphEntities)
            throws ServiceGeneratorException;
}

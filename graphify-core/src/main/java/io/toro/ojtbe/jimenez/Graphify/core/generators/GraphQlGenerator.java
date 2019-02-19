package io.toro.ojtbe.jimenez.Graphify.core.generators;

import io.toro.ojtbe.jimenez.Graphify.core.GraphEntity;

import java.util.List;

public interface GraphQlGenerator {
    void generate(List<GraphEntity> graphEntities)
            throws GraphQlGeneratorException;
}

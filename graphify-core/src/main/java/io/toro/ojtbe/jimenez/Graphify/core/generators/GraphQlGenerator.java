package io.toro.ojtbe.jimenez.Graphify.core.generators;

import io.toro.ojtbe.jimenez.Graphify.core.GraphEntity;

import java.util.List;

public interface GraphQlGenerator {
    boolean generate(List<GraphEntity> graphEntities);
}

package io.toro.ojtbe.jimenez.Graphify.core.generators.service;

import io.toro.ojtbe.jimenez.Graphify.core.generators.GraphEntity;
import io.toro.ojtbe.jimenez.Graphify.core.poet.wrappers.ClassWrapper;
import io.toro.ojtbe.jimenez.Graphify.core.poet.wrappers.Interface;

public interface ServiceGenerator {
    ClassWrapper generateService(GraphEntity graphEntity,
                                 Interface repository);
}

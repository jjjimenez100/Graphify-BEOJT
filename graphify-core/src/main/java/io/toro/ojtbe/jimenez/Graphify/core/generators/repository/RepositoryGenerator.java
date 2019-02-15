package io.toro.ojtbe.jimenez.Graphify.core.generators.repository;

import io.toro.ojtbe.jimenez.Graphify.core.generators.GraphEntity;
import io.toro.ojtbe.jimenez.Graphify.core.poet.wrappers.ClassNameWrapper;
import io.toro.ojtbe.jimenez.Graphify.core.poet.wrappers.Interface;

public interface RepositoryGenerator {
    Interface generateRepository(GraphEntity graphEntity,
                                 ClassNameWrapper parent);
}

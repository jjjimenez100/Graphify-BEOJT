package io.toro.ojtbe.jimenez.Graphify.core.generators;

import io.toro.ojtbe.jimenez.Graphify.core.GraphEntity;

interface RepositoryGenerator extends Generator {
    void generate(GraphEntity graphEntity)
            throws RepositoryGeneratorException;
}

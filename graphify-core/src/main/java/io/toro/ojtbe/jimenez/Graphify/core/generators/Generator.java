package io.toro.ojtbe.jimenez.Graphify.core.generators;

import io.toro.ojtbe.jimenez.Graphify.core.GraphEntity;
import io.toro.ojtbe.jimenez.Graphify.core.Handler;

import java.util.List;

interface Generator extends Handler<List<GraphEntity>> {
    void setName(String name);

    void setParent(String className,
                   String packageName);

    void setAccess(String access);

    String getName();

    String getParentClass();

    String getParentPackage();
}
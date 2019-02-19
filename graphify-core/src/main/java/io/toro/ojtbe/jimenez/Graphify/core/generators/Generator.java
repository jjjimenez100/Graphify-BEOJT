package io.toro.ojtbe.jimenez.Graphify.core.generators;

interface Generator {
    void setName(String name);

    void setParent(String className,
                   String packageName);

    void setAccess(String access);
}

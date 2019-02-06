package io.toro.ojtbe.jimenez.Graphify.core.poet;

import io.toro.ojtbe.jimenez.Graphify.core.poet.wrappers.Class;
import io.toro.ojtbe.jimenez.Graphify.core.poet.wrappers.Interface;

public interface PoetService {
    public boolean writeClass(String packageStatement,
                              String path,
                              Class classRep);
    public boolean writeInterface(String packageStatement,
                                  String path,
                                  Interface interfaceRep);
    public boolean debug(String path);
}

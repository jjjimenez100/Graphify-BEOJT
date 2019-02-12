package io.toro.ojtbe.jimenez.Graphify.core.poet;

import io.toro.ojtbe.jimenez.Graphify.core.poet.wrappers.ClassWrapper;
import io.toro.ojtbe.jimenez.Graphify.core.poet.wrappers.Interface;

public interface PoetService {
    public boolean writeClass(String path,
                              ClassWrapper classWrapperRep);
    public boolean writeInterface(String path,
                                  Interface interfaceRep);
    public boolean debug(String path);
}

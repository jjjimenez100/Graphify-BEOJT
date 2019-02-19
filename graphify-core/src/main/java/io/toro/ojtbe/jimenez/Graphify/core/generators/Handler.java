package io.toro.ojtbe.jimenez.Graphify.core.generators;

public interface Handler<T> {
    T process(T input);
}
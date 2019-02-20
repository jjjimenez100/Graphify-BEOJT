package io.toro.ojtbe.jimenez.Graphify.core;

public interface Handler<T> {
    T process(T input);
}
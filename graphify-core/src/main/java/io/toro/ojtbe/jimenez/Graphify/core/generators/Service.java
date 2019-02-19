package io.toro.ojtbe.jimenez.Graphify.core.generators;

import java.util.Optional;

public interface Service<T, S> {
    Iterable<T> getAll();
    Optional<T> getById(S id);
    T save(T newEntity);
    void delete(S id);
}

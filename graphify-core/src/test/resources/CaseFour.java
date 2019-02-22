package io.toro.ojtbe.jimenez.Graphify.core;

import io.toro.ojtbe.jimenez.Graphify.annotations.GraphModel;
import org.springframework.data.annotation.Id;
@GraphModel

public interface CaseFour {
    @Id
    public int id = 1;
    public String name = "s";
}
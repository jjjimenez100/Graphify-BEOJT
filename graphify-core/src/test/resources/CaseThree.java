package io.toro.ojtbe.jimenez.Graphify.core;

import io.toro.ojtbe.jimenez.Graphify.annotations.GraphModel;
import org.springframework.data.annotation.Id;

@GraphModel
public abstract class CaseThree {
    @Id
    private int id;
    private String name;

    public int getId() {
        return id;
    }
}
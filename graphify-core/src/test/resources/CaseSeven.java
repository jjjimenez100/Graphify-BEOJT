package io.toro.ojtbe.jimenez.Graphify.core;

import io.toro.ojtbe.jimenez.Graphify.annotations.GraphModel;
import org.springframework.data.annotation.Id;

@GraphModel
public class CaseSeven {
    @Id
    private int id;
    @Id
    private String name;

    public int getId() {
        return id;
    }
}
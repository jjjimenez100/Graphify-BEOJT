package io.toro.ojtbe.jimenez.Graphify.core;

import io.toro.ojtbe.jimenez.Graphify.annotations.GraphModel;
import org.springframework.data.annotation.Id;

@GraphModel
public class CaseNine {
    @Id
    private String id;
    private String name;

    public String getId() {
        return id;
    }
}
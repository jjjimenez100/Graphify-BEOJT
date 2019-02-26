package io.toro.ojtbe.jimenez.Graphify.core;

import io.toro.ojtbe.jimenez.Graphify.annotations.GraphModel;
import org.springframework.data.annotation.Id;

@GraphModel
public enum Enum {
    @Id
    THIS, IS, AN, ENUM;
}
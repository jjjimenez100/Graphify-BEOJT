package io.toro.ojtbe.jimenez.Graphify.core;

import io.toro.ojtbe.jimenez.Graphify.annotations.GraphModel;
import org.springframework.data.annotation.Id;

/**
 * Class resource to be used by the
 * givenAnnotatedClassWithIntId_whenAnnotationProcessing_thenProceedWithNoError()
 * test case
 */
@GraphModel
public class IntId {
    @Id
    private int id;
    private String name;

    public int getId() {
        return id;
    }
}
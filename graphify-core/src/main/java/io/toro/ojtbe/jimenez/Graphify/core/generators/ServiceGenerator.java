package io.toro.ojtbe.jimenez.Graphify.core.generators;


import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeSpec;
import io.toro.ojtbe.jimenez.Graphify.core.GraphEntity;

public interface ServiceGenerator {
    TypeSpec generateService(GraphEntity graphEntity,
                             TypeSpec repository,
                             ClassName parent)
            throws ServiceGeneratorException;
}

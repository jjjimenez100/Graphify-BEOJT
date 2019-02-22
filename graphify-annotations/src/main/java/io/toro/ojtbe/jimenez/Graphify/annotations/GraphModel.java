package io.toro.ojtbe.jimenez.Graphify.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation interface that will trigger the graph model annotation
 * processing for GraphQL schema, repository, service, and resolver
 * generation respectively.
 * @author Joshua Jimenez
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface GraphModel {
}
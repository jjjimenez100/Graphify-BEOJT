package io.toro.ojtbe.jimenez.Graphify.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a GraphModel and holds specific properties to
 * be used by the annotation processor for schema generation
 *
 * @author Joshua Jimenez
 */
public final class GraphEntity {
    private final String fullyQualifiedName;
    private final String idName;
    private final String idType;
    private final Map<String, String> properties;

    private GraphEntity(Builder builder){
        this.fullyQualifiedName = builder.fullyQualifiedName;
        this.idName = builder.idName;
        this.idType = builder.idType;
        this.properties = Collections.unmodifiableMap(builder.properties);
    }

    public static class Builder{
        private String fullyQualifiedName;
        private String idName;
        private String idType;
        private Map<String, String> properties;

        public Builder(){
            properties = new HashMap<>();
        }

        public Builder fullyQualifiedName(String fullyQualifiedName){
            this.fullyQualifiedName = fullyQualifiedName;
            return this;
        }

        public Builder idName(String idName){
            this.idName = idName;
            return this;
        }

        public Builder property(String name, String type){
            properties.put(name, type);
            return this;
        }

        public Builder idType(String idType){
            this.idType = idType;
            return this;
        }

        public GraphEntity build(){
            return new GraphEntity(this);
        }
    }

    public String getFullyQualifiedName() {
        return fullyQualifiedName;
    }

    public String getIdName() {
        return idName;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public String getIdType() {
        return idType;
    }

    @Override
    public String toString() {
        return "GraphEntity{" +
                "fullyQualifiedName='" + fullyQualifiedName + '\'' +
                ", idName='" + idName + '\'' +
                ", idType='" + idType + '\'' +
                ", properties=" + properties +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GraphEntity that = (GraphEntity) o;
        return Objects.equals(fullyQualifiedName, that.fullyQualifiedName) &&
                Objects.equals(idName, that.idName) &&
                Objects.equals(idType, that.idType) &&
                Objects.equals(properties, that.properties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullyQualifiedName, idName, idType, properties);
    }
}

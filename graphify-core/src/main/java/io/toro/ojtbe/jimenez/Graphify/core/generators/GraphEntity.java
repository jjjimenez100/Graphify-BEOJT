package io.toro.ojtbe.jimenez.Graphify.core.generators;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a GraphModel and holds specific properties to
 * be used by the annotation processor for
 * repository, service, schema, and query resolver generation
 *
 * @author Joshua Jimenez
 *
 */
public final class GraphEntity {

    private final String className;
    private final String packageName;
    private final String idName;
    private final String idType;
    private final String modelDirectory;
    private final Map<String, String> properties;

    private GraphEntity(Builder builder){
        this.className = builder.className;
        this.idName = builder.idName;
        this.packageName = builder.packageName;
        this.idType = builder.idType;
        this.modelDirectory = builder.modelDirectory;
        this.properties = Collections.unmodifiableMap(builder.properties);
    }

    public static class Builder{
        private String className;
        private String packageName;
        private String idName;
        private String idType;
        private Map<String, String> properties;
        private String modelDirectory;

        public Builder(){
            properties = new HashMap<>();
        }

        public Builder className(String className){
            this.className = className;
            return this;
        }

        public Builder packageName(String packageName){
            this.packageName = packageName;
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

        public Builder modelDirectory(String modelDirectory){
            this.modelDirectory = modelDirectory;
            return this;
        }

        public GraphEntity build(){
            return new GraphEntity(this);
        }
    }

    public String getClassName() {
        return className;
    }

    public String getPackageName() {
        return packageName;
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

    public String getModelDirectory() {
        return modelDirectory;
    }

    @Override
    public String toString() {
        return "GraphEntity{" +
                "className='" + className + '\'' +
                ", packageName='" + packageName + '\'' +
                ", idName='" + idName + '\'' +
                ", idType='" + idType + '\'' +
                ", modelDirectory='" + modelDirectory + '\'' +
                ", properties=" + properties +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GraphEntity that = (GraphEntity) o;
        return Objects.equals(className, that.className) &&
                Objects.equals(packageName, that.packageName) &&
                Objects.equals(idName, that.idName) &&
                Objects.equals(idType, that.idType) &&
                Objects.equals(modelDirectory, that.modelDirectory) &&
                Objects.equals(properties, that.properties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(className, packageName, idName,
                idType, modelDirectory, properties);
    }
}

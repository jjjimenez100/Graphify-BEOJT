package io.toro.ojtbe.jimenez.Graphify.core.poet;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import java.util.ArrayList;
import java.util.List;

public enum ClassNameUtil {
    INSTANCE;

    public final List<TypeName> primitives;

    ClassNameUtil(){
        primitives = new ArrayList<>();
        initPrimitives();
    }

    private void initPrimitives(){
        primitives.add(TypeName.VOID);
        primitives.add(TypeName.BOOLEAN);
        primitives.add(TypeName.BYTE);
        primitives.add(TypeName.SHORT);
        primitives.add(TypeName.INT);
        primitives.add(TypeName.LONG);
        primitives.add(TypeName.CHAR);
        primitives.add(TypeName.FLOAT);
        primitives.add(TypeName.DOUBLE);
    }

    public ClassName toBoxedType(ClassName name){
        for(TypeName primitive: primitives){
            if(name.toString().equals(primitive.toString())){
                return (ClassName) primitive.box();
            }
        }
        return name;
    }
}

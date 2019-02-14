package io.toro.ojtbe.jimenez.Graphify.core.poet;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public enum PoetUtil {
    INSTANCE;

    public final List<TypeName> primitives;

    private PoetUtil(){
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

    public boolean write(String packageStatement,
                         String path,
                         TypeSpec typeSpec){
        JavaFile file = JavaFile
                .builder(packageStatement, typeSpec)
                .skipJavaLangImports(true)
                .indent("   ")
                .build();
        try {
            file.writeTo(Paths.get(path));
            return true;
        } catch (IOException e) {
            e.printStackTrace();

            return false;
        }
    }
}

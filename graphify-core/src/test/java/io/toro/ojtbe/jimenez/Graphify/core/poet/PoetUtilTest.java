package io.toro.ojtbe.jimenez.Graphify.core.poet;


import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import org.junit.Test;
import static org.junit.Assert.*;

public class PoetUtilTest {
    @Test
    public void whenCallingPrimitiveTypeNameList_thenReturnAllTypeNamePrimitives(){
        assertTrue(PoetUtil.INSTANCE.primitives.contains(TypeName.VOID));
        assertTrue(PoetUtil.INSTANCE.primitives.contains(TypeName.BOOLEAN));
        assertTrue(PoetUtil.INSTANCE.primitives.contains(TypeName.BYTE));
        assertTrue(PoetUtil.INSTANCE.primitives.contains(TypeName.SHORT));
        assertTrue(PoetUtil.INSTANCE.primitives.contains(TypeName.INT));
        assertTrue(PoetUtil.INSTANCE.primitives.contains(TypeName.LONG));
        assertTrue(PoetUtil.INSTANCE.primitives.contains(TypeName.CHAR));
        assertTrue(PoetUtil.INSTANCE.primitives.contains(TypeName.FLOAT));
        assertTrue(PoetUtil.INSTANCE.primitives.contains(TypeName.DOUBLE));
    }
    @Test
    public void givenPrimitiveClassName_whenCallingToBoxedType_thenReturnBoxedType(){
        ClassName primitiveInt = ClassName.get("", "int");

        assertEquals(
                PoetUtil.INSTANCE.toBoxedType(primitiveInt).simpleName(),
                "Integer"
        );
    }

    @Test
    public void givenBoxedClassName_whenCallingToBoxedType_thenReturnBoxedType(){
        ClassName primitiveInt = ClassName.get("", "Double");

        assertEquals(
                PoetUtil.INSTANCE.toBoxedType(primitiveInt).simpleName(),
                "Double"
        );
    }

    @Test
    public void givenNonBoxedAndPrimitiveClassName_whenCallingToBoxedType_thenReturnItself(){
        ClassName primitiveInt = ClassName.get("io.toro.ojtbe", "TestClass");

        assertEquals(
                PoetUtil.INSTANCE.toBoxedType(primitiveInt).simpleName(),
                "TestClass"
        );
    }

    @Test
    public void givenTypeSpec_whenCallingWrite_thenReturnTrue(){
        TypeSpec testClass = TypeSpec.classBuilder("s").build();
        assertTrue(PoetUtil.INSTANCE.write("", "", testClass));
    }
}

package io.toro.ojtbe.jimenez.Graphify.core.poet;


import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import org.junit.Test;
import static org.junit.Assert.*;

public class ClassNameUtilTest {
    @Test
    public void whenCallingPrimitiveTypeNameList_thenReturnAllTypeNamePrimitives(){
        assertTrue(ClassNameUtil.INSTANCE.primitives.contains(TypeName.VOID));
        assertTrue(ClassNameUtil.INSTANCE.primitives.contains(TypeName.BOOLEAN));
        assertTrue(ClassNameUtil.INSTANCE.primitives.contains(TypeName.BYTE));
        assertTrue(ClassNameUtil.INSTANCE.primitives.contains(TypeName.SHORT));
        assertTrue(ClassNameUtil.INSTANCE.primitives.contains(TypeName.INT));
        assertTrue(ClassNameUtil.INSTANCE.primitives.contains(TypeName.LONG));
        assertTrue(ClassNameUtil.INSTANCE.primitives.contains(TypeName.CHAR));
        assertTrue(ClassNameUtil.INSTANCE.primitives.contains(TypeName.FLOAT));
        assertTrue(ClassNameUtil.INSTANCE.primitives.contains(TypeName.DOUBLE));
    }
    @Test
    public void givenPrimitiveClassName_whenCallingToBoxedType_thenReturnBoxedType(){
        ClassName primitiveInt = ClassName.get("", "int");

        assertEquals(
                ClassNameUtil.INSTANCE.toBoxedType(primitiveInt).simpleName(),
                "Integer"
        );
    }

    @Test
    public void givenBoxedClassName_whenCallingToBoxedType_thenReturnBoxedType(){
        ClassName primitiveInt = ClassName.get("", "Double");

        assertEquals(
                ClassNameUtil.INSTANCE.toBoxedType(primitiveInt).simpleName(),
                "Double"
        );
    }

    @Test
    public void givenNonBoxedAndPrimitiveClassName_whenCallingToBoxedType_thenReturnItself(){
        ClassName primitiveInt = ClassName.get("io.toro.ojtbe", "TestClass");

        assertEquals(
                ClassNameUtil.INSTANCE.toBoxedType(primitiveInt).simpleName(),
                "TestClass"
        );
    }
}

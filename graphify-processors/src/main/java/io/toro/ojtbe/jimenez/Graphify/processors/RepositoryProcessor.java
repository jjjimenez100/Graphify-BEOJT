package io.toro.ojtbe.jimenez.Graphify.processors;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

@SupportedAnnotationTypes("*")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class RepositoryProcessor extends AbstractProcessor {

    private Messager messager;
    private Filer filer;

    @Override public synchronized void init(
            ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.messager = processingEnv.getMessager();
        this.filer = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment){
        messager.printMessage(Diagnostic.Kind.NOTE, "HELLO THERE.!!!!");
        System.out.println("HIHDIAHDIAHDIAHDAI!!!!!!!!!!");
        CodeGenerator codeGen = new CodeGenerator(this.getClass().getPackage().getName());
        TypeSpec testClass = codeGen.buildClass("JOSHUA", new Modifier[]{}, new MethodSpec[]{});
        JavaFile javaFile = codeGen.createJavaFile(testClass, "das");

        Path workingDirectory = Paths.get(".");
        Path executingClassDirectory = Paths.get(workingDirectory.toAbsolutePath() + "/src/test/java/");
        codeGen.writeJavaFile(javaFile, executingClassDirectory);

        return true;
    }
}

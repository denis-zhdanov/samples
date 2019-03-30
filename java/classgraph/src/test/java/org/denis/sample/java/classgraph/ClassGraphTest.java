package org.denis.sample.java.classgraph;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ClassGraphTest {

    @Test
    public void whenTypeAnnotationIsUsed_thenItIsRespected() {
        try (ScanResult scanResult = new ClassGraph().enableAllInfo()
                                                     .whitelistPackages(getClass().getPackage().getName())
                                                     .scan())
        {
            ClassInfoList classInfos = scanResult.getClassesWithAnnotation(TestAnnotation.class.getName());
            assertThat(classInfos).extracting(ClassInfo::getName).contains(ClassWithAnnotation.class.getName());
        }
    }
}

package com.pc.spring.importannotation;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 使用ImportSelector加载bean
 * @author pc
 * @Date 2020/10/20
 **/
public class TestImportSelector implements ImportSelector {

    /**
     *    返回需要被加载的类
     *   AnnotationMetadata表示当前被@Import注解给标注的所有注解信息
     * @param annotationMetadata
     * @return
     */
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        System.out.println(annotationMetadata);
        return new String[] {"com.pc.spring.importannotation.Bean2"};
    }

}

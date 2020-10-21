package com.pc.spring.importannotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * import引导导入
 * @author pc
 * @Date 2020/10/20
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({Bean1.class, TestImportSelector.class})
public @interface ImportLoader {
}

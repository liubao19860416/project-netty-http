package com.saic.ebiz.vbox.conf;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 描述信息
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2015年10月29日
 * 
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Descriptor {

    String key() default "";

    String value();

}

package com.injectlibrary.injecttype;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by xiaoqing.zhou
 * on  2019/4/1
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnClickEvent {
    Class<?> listenerType();
    String listenerSetter();
    String methodName();
}

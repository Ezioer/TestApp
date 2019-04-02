package com.injectlibrary.injecttype;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import android.view.View;
import java.lang.annotation.Target;

/**
 * Created by xiaoqing.zhou
 * on  2019/4/1
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@OnClickEvent(listenerType = View.OnClickListener.class,listenerSetter = "setOnClickListener",methodName = "onClick")
public @interface InjectOnClick {
    int[] value();
}

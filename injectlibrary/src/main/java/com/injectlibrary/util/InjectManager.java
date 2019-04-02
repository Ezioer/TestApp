package com.injectlibrary.util;

import android.app.Activity;
import android.view.View;
import com.injectlibrary.injecttype.ContentView;
import com.injectlibrary.injecttype.InjectView;
import com.injectlibrary.injecttype.MyInjectHandle;
import com.injectlibrary.injecttype.OnClickEvent;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by xiaoqing.zhou
 * on  2019/4/1
 */
public class InjectManager {
    public static final String ACTIVITY_SETCONTENTVIEW = "setContentView";
    public static final String ACTIVITY_FINDVIEWBYID = "findViewById";

    //注解contentView
    public static void injectContentView(Activity activity) {
        Class<? extends Activity> aClass = activity.getClass();
        ContentView annotation = aClass.getAnnotation(ContentView.class);
        if (annotation != null) {
            int contentId = annotation.value();
            try {
                Method method = aClass.getMethod(ACTIVITY_SETCONTENTVIEW, int.class);
                method.setAccessible(true);
                method.invoke(activity, contentId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //注解view
    public static void injectView(Activity activity) {
        Class<? extends Activity> aClass = activity.getClass();
        //可以获取到私有成员变量，getfields只能获取共有
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field field : declaredFields) {
            InjectView annotation = field.getAnnotation(InjectView.class);
            if (annotation != null) {
                int viewId = annotation.value();
                try {
                    Method method = aClass.getMethod(ACTIVITY_FINDVIEWBYID, int.class);
                    field.setAccessible(true);
                    method.setAccessible(true);
                    Object invoke = method.invoke(activity, viewId);
                    field.set(activity, invoke);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //注解onclicklistener方法
    public static void injectOnClickListener(Activity activity) {
        Class<? extends Activity> aClass = activity.getClass();
        Method[] methods = aClass.getMethods();
        for (Method method : methods) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                Class<? extends Annotation> annotationType = annotation.annotationType();
                OnClickEvent onClickEvent = annotationType.getAnnotation(OnClickEvent.class);
                if (onClickEvent != null) {
                    try {
                        Method value = annotationType.getDeclaredMethod("value");
                        int[] viewIds = (int[]) value.invoke(annotation, null);
                        Class<?> type = onClickEvent.listenerType();
                        String setter = onClickEvent.listenerSetter();
                        String name = onClickEvent.methodName();
                        MyInjectHandle myInjectHandle = new MyInjectHandle(activity);
                        myInjectHandle.setMethodMap(name, method);
                        Object proxy = Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[]{type}, myInjectHandle);
                        for (int id : viewIds) {
                            View view = activity.findViewById(id);
                            Method viewMethod = view.getClass().getMethod(setter, type);
                            viewMethod.invoke(view, proxy);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

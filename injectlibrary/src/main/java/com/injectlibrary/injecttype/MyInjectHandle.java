package com.injectlibrary.injecttype;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiaoqing.zhou
 * on  2019/4/1
 */
public class MyInjectHandle implements InvocationHandler {
    private Object object;
    private Map<String, Method> methodMap = new HashMap<>(1);

    public MyInjectHandle(Object object) {
        this.object = object;
    }

    public void setMethodMap(String name, Method method) {
        this.methodMap.put(name, method);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (object != null) {
            String name = method.getName();
            method = this.methodMap.get(name);
            if (method != null) {
                return method.invoke(object, args);
            }
        }
        return null;
    }
}

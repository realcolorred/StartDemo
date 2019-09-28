package com.example.demo.util;

import java.lang.reflect.Field;

/**
 * Created by lenovo on 2019/9/26.
 */
public class BeanUtil {

    public static <T> T fillEmptyBean(T obj) {
        Class userCla = (Class) obj.getClass();
        Field[] fs = userCla.getDeclaredFields();
        for (Field f : fs) {
            // 设置些属性是可以访问的
            f.setAccessible(true);
            Object val;
            try {
                // 得到此属性的值
                val = f.get(obj);
                if (val == null) {
                    if (Long.class == f.getType()) {
                        f.set(obj, 0L);
                        continue;
                    } else if (String.class == f.getType()) {
                        f.set(obj, StringUtil.EMPTY);
                        continue;
                    } else if (java.util.Date.class.isAssignableFrom(f.getType())) {
                        f.set(obj, DateUtil.NULL);
                        continue;
                    } else if (Byte.class == f.getType()) {
                        f.set(obj, (byte) 0);
                        continue;
                    } else if (Integer.class == f.getType()) {
                        f.set(obj, 0);
                        continue;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return obj;
    }
}

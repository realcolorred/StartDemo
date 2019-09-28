package com.example.demo.util;

import java.util.*;

/**
 * Created by lenovo on 2019/9/25.
 */
public class CollectionUtil {

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !CollectionUtil.isEmpty(collection);
    }

    public static <T> Set<T> singleToSet(T object) {
        Set<T> set = new HashSet<>();
        if (object != null) {
            set.add(object);
        }
        return set;
    }

    public static <T> List<T> singleToList(T object) {
        List<T> set = new ArrayList<>();
        if (object != null) {
            set.add(object);
        }
        return set;
    }
}

package com.example.demo.util;

import java.util.Collection;

/**
 * Created by lenovo on 2019/9/25.
 */
public class CollectionHelper {

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !CollectionHelper.isEmpty(collection);
    }
}

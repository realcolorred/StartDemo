package com.example.demo.service.impl;

import com.example.pub.util.BeanHelper;
import com.example.pub.util.CollectionHelper;
import com.example.pub.util.NumberHelper;
import com.example.pub.util.StringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * Created by lenovo on 2019/9/26.
 */
public abstract class BaseService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected boolean isEmpty(String str) {
        return StringHelper.isEmpty(str);
    }

    protected boolean isNotEmpty(String str) {
        return StringHelper.isNotEmpty(str);
    }

    protected boolean isNotEmpty(Collection<?> collection) {
        return CollectionHelper.isNotEmpty(collection);
    }

    protected boolean isVaildNum(Integer num) {
        return NumberHelper.isVaildNum(num);
    }

    protected boolean isVaildNum(Long num) {
        return NumberHelper.isVaildNum(num);
    }

    protected void fillEmptyBean(Object obj) {
        BeanHelper.fillEmptyBean(obj);
    }

}

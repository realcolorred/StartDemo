package com.example.demo.service.impl;

import com.example.common.util.BeanUtil;
import com.example.common.util.CollectionUtil;
import com.example.common.util.NumberUtil;
import com.example.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * Created by lenovo on 2019/9/26.
 */
public abstract class BaseService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected boolean isEmpty(String str) {
        return StringUtil.isEmpty(str);
    }

    protected boolean isNotEmpty(String str) {
        return StringUtil.isNotEmpty(str);
    }

    protected boolean isNotEmpty(Collection<?> collection) {
        return CollectionUtil.isNotEmpty(collection);
    }

    protected boolean isVaildNum(Integer num) {
        return NumberUtil.isValidNum(num);
    }

    protected boolean isVaildNum(Long num) {
        return NumberUtil.isValidNum(num);
    }

    protected void fillEmptyBean(Object obj) {
        BeanUtil.fillEmptyBean(obj);
    }

}

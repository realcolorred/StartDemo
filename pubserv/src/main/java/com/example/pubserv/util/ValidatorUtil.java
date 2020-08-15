package com.example.pubserv.util;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by lenovo on 2019/9/25.
 */
public class ValidatorUtil {

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private static <T> Map<String, StringBuffer> validateObj(T obj) {
        Map<String, StringBuffer> errorMap = null;
        Set<ConstraintViolation<T>> set = validator.validate(obj);
        if (set != null && set.size() > 0) {
            errorMap = new HashMap<>();
            String property;
            for (ConstraintViolation<T> cv : set) {
                //这里循环获取错误信息，可以自定义格式
                property = cv.getPropertyPath().toString();
                if (errorMap.get(property) != null) {
                    errorMap.get(property).append("," + cv.getMessage());
                } else {
                    StringBuffer sb = new StringBuffer();
                    sb.append(cv.getMessage());
                    errorMap.put(property, sb);
                }
            }
        }
        return errorMap;
    }

    public static <T> String validate(T obj) {
        Map<String, StringBuffer> msg = validateObj(obj);
        if (msg == null) {
            return null;
        } else {
            return msg.toString();
        }
    }

    public static <T> boolean validatePass(T obj) {
        return validateObj(obj) == null;
    }
}

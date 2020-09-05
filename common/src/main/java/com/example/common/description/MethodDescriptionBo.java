package com.example.common.description;

import com.example.common.util.CollectionUtil;
import com.example.common.util.StringUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by new on 2020/9/4.
 */
@Getter
@Setter
public class MethodDescriptionBo {

    private String                       name;
    private String                       description;
    private String                       url;
    private List<ParameterDescriptionBo> paramDeList;

    public MethodDescriptionBo() {
        paramDeList = new ArrayList<>();
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(name).append("(");
        if (CollectionUtil.isNotEmpty(paramDeList)) {
            str.append(paramDeList);
        }
        str.append(")");
        if (StringUtil.isNotEmpty(url)) {
            str.append("[").append(url).append("]");
        }
        if (StringUtil.isNotEmpty(description)) {
            str.append("[").append(description).append("]");
        }
        return str.toString();
    }
}

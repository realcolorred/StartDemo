package com.example.common.description;

import com.example.common.util.StringUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by new on 2020/9/4.
 */
@Getter
@Setter
public class ParameterDescriptionBo {

    private String name;
    private String description;
    private String type;

    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(name).append("[").append(type);
        if (StringUtil.isNotEmpty(description)) {
            str.append(",").append(description);
        }
        str.append("]");
        return str.toString();
    }
}

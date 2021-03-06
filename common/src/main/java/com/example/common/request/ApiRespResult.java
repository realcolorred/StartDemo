package com.example.common.request;

import com.example.common.exception.ErrorMessage;
import com.example.common.util.StringUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.text.MessageFormat;

/**
 * Created by new on 2020/8/28.
 */
@ApiModel("api通用返回")
@Getter
@Setter
@ToString
public class ApiRespResult<T> {

    @ApiModelProperty("0代表成功，非0代表失败")
    private String code = "0";
    @ApiModelProperty("错误信息")
    private String message;
    @ApiModelProperty("错误请求标识")
    private String requestId;
    @ApiModelProperty("数据")
    private T      data;

    public static ApiRespResult<?> success() {
        return new ApiRespResult<>();
    }

    public static <T> ApiRespResult<T> success(T data) {
        ApiRespResult<T> result = new ApiRespResult<T>();
        result.setData(data);
        return result;
    }

    public boolean isSuccess() {
        return "0".equals(code);
    }

    public static <T> ApiRespResult<T> fail(String code, String message) {
        ApiRespResult<T> result = new ApiRespResult<T>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static <T> ApiRespResult<T> fail(String code, String requestId, String message) {
        ApiRespResult<T> result = new ApiRespResult<T>();
        result.setCode(code);
        result.setMessage(message);
        result.setRequestId(requestId);
        return result;
    }

    public static <T> ApiRespResult<T> fail(ErrorMessage errorMessage, String... paramValue) {
        ApiRespResult<T> result = new ApiRespResult<T>();
        result.setCode(errorMessage.getCode());
        result.setMessage(MessageFormat.format(errorMessage.getMsg(), paramValue));
        return result;
    }

}

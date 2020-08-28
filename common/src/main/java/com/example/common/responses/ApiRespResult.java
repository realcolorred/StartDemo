package com.example.common.responses;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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

    public static <T> ApiRespResult<T> fail(int code) {
        return fail(String.valueOf(code));
    }

    public static <T> ApiRespResult<T> fail(int code, String message) {
        return fail(String.valueOf(code), message);
    }

    public static <T> ApiRespResult<T> fail(int code, T data, String message) {
        return fail(String.valueOf(code), data, message);
    }

    public static <T> ApiRespResult<T> fail(String code) {
        return fail(code, null);
    }

    public static <T> ApiRespResult<T> fail(String code, String message) {
        ApiRespResult<T> result = new ApiRespResult<T>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static <T> ApiRespResult<T> fail(String code, T data, String message) {
        ApiRespResult<T> result = new ApiRespResult<T>();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }
}

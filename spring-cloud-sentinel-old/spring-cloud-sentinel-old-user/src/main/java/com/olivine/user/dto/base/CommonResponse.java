package com.olivine.user.dto.base;

import com.olivine.user.enums.ApiCode;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author jphao
 * @date 2021/11/25 23:53
 * @description
 */
@Data
@NoArgsConstructor
public class CommonResponse<T> implements Serializable {

    private static final long serialVersionUID = 841227412131502247L;

    private Integer code;
    private String message;
    private String requestId;
    private T data;

    public CommonResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public CommonResponse(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public CommonResponse(Integer code, String message, String requestId, T data) {
        this.code = code;
        this.message = message;
        this.requestId = requestId;
        this.data = data;
    }

    public CommonResponse(ApiCode apiCode){
        this(apiCode.code(), apiCode.message());
    }

    public CommonResponse(ApiCode apiCode, T data){
        this(apiCode.code(), apiCode.message(), data);
    }

    public CommonResponse(ApiCode apiCode, String requestId , T data){
        this(apiCode.code(), apiCode.message(), requestId, data);
    }

    public static <T> CommonResponse<T> success() {
        return new CommonResponse<>(ApiCode.SUCCESS);
    }

    public static <T> CommonResponse<T> success(T data) {
        return new CommonResponse<>(ApiCode.SUCCESS, data);
    }
}

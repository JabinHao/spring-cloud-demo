package com.olivine.user.enums;

/**
 * @Author jphao
 * @Date 2021/11/26 16:26
 * @Description
 */
public enum ApiCode {

    SUCCESS(200, "successful"),

    /**
     * 用户不存在
     */
    FAILED(-400, "user doesn't exist"),

    EMPTY(-400, "No address found");

    private final Integer code;
    private final String message;

    ApiCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }
}

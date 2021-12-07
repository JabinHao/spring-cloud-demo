package com.olivine.order.enums;

/**
 * @Author jphao
 * @Date 2021/11/26 16:26
 * @Description
 */
public enum ApiCode {

    SUCCESS(200, "successful"),

    FAILED(-400, "order doesn't exist");

    private Integer code;
    private String message;

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

package com.olivine.user.enums;

/**
 * @Author jphao
 * @Date 2021/11/27 20:28
 * @Description
 */
public enum Gender {
    
    MALE("male"),
    FEMALE("female");
    
    private final String gender;

    Gender(String gender) {
        this.gender = gender;
    }
    public String gender(){
        return this.gender;
    }
}

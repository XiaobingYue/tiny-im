package com.yxb.tinylove.common.bean;

import java.util.Arrays;

/**
 * @author yxb
 * @since 2022.1.7
 */
public enum Gender {

    MAN(1, "男"),
    WOMEN(0, "女");

    private Integer type;

    private String gender;

    public Integer getType() {
        return type;
    }

    public String getGender() {
        return gender;
    }

    Gender(Integer type, String gender) {
        this.type = type;
        this.gender = gender;
    }

    public static Gender getGender(Integer type) {
        return Arrays.stream(Gender.values()).filter(item -> item.getType().equals(type)).findFirst().orElse(null);
    }
}

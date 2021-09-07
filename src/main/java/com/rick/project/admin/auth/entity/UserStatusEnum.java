package com.rick.project.admin.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Rick
 * @createdAt 2021-09-06 11:54:00
 */
@AllArgsConstructor
@Getter
public enum UserStatusEnum {

    NORMAL("正常"),
    EXPIRED("过期"),
    DISABLE("停用");

    public String getName() {
        return this.name();
    }

    private final String label;

    public static UserStatusEnum of(String name) {
        return Enum.valueOf(UserStatusEnum.class, name);
    }
}
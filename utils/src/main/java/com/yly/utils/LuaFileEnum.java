package com.yly.utils;

/**
 * lua脚本枚举类
 * @author xuzhipeng
 * @date 2021/01/31
 */
public enum LuaFileEnum {
    ID("id");

    private String name;

    LuaFileEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

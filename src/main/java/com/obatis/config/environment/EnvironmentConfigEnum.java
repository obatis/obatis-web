package com.obatis.config.environment;

public enum EnvironmentConfigEnum {

    /**
     * 数据库查询是否开启驼峰转化
     */
    MYBATIS_MAP_UNDERSCORE("mybatis.configuration.map-underscore-to-camel-case", "true");

    private String key;
    private String value;


    EnvironmentConfigEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}

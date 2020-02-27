package com.obatis.config.environment;

public enum  EnvironmentConfigEnum {

        /**
         * 是否配置访问404进入系统级拦截，不进行页面错误显示
         */
        SPRING_NOT_FOUND("spring.mvc.throw-exception-if-no-handler-found", "true"),
        /**
         * 是否开启静态资源映射
         */
        RESOURCES_ADD_MAPPING("spring.resources.add-mappings", "false"),
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

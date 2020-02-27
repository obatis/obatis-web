package com.obatis.config.environment;

public class EnvironmentConstant {

    protected static boolean ENVIRONMENT_LOAD_FLAG = false;

    protected enum SpringEn {
        SPRING_NOT_FOUND("spring.mvc.throw-exception-if-no-handler-found", "true"),
        RESOURCES_ADD_MAPPING("spring.resources.add-mappings", "false"),
        MYBATIS_MAP_UNDERSCORE("mybatis.configuration.map-underscore-to-camel-case", "true");

        private String key;
        private String value;

        SpringEn(String key, String value) {
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
}

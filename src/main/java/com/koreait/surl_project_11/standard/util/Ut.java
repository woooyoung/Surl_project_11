package com.koreait.surl_project_11.standard.util;

import com.koreait.surl_project_11.global.app.AppConfig;
import lombok.SneakyThrows;

public class Ut {
    public static class str {
        public static boolean isBlank(String str) {
            return str == null || str.isBlank();
        }

        //public static boolean isNotBlank(String str) {}
        public static boolean hasLength(String str) {
            return !isBlank(str);
        }
    }

    public static class json {
        @SneakyThrows
        public static String toString(Object obj) {
            return AppConfig.getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        }
    }
}


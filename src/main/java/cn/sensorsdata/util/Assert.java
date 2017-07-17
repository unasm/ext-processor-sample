package cn.sensorsdata.util;

import java.util.List;

/**
 * Created by tianyi on 10/07/2017.
 */
public class Assert {
    protected Assert() {
    }

    public static void notNull(Object obj, String message) {
        if(obj == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isTrue(Boolean obj, String message) {
        if(!obj.booleanValue()) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notBlank(String obj, String message) {
        if(obj == null || "".equals(obj.trim())) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notEmpty(List<?> objList, String message) {
        if(objList == null || objList.size() == 0) {
            throw new IllegalArgumentException(message);
        }
    }
}

package cn.sensorsdata.util;

import java.util.ResourceBundle;

/**
 * Created by tianyi on 10/07/2017.
 */
public class Context {
    public static String HOST;
    private  static String get(String key, ResourceBundle resourceBundle) {
        String value = null;
        if (resourceBundle.containsKey(key)) {
            value = resourceBundle.getString(key);
        }
        Assert.notNull(value, "启动失败 , 参数[" +  key + "]没有配置");
        return value;
    }

    public static void init(ResourceBundle resourceBundle) {
        HOST = get("host", resourceBundle);
    }
}

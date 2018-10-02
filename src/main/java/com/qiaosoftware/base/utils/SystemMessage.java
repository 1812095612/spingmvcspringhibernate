package com.qiaosoftware.base.utils;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 读取文件名为system.properties的配置文件
 * 
 * @author Administrator
 *
 */
public class SystemMessage {

    private static final String BUNDLE_NAME = "system";

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
}

package com.example.demo.util;

import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Created by lenovo on 2019/3/13.
 */
public class PropertyUtil {

    public static Properties getProperties(String propName) {
        InputStream is = null;
        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(propName);
            if (is != null) {
                Properties props = new Properties();
                props.load(is);
                return props;
            }
        } catch (IOException ignore) {
        } finally {
            IOUtils.closeQuietly(is);
        }
        return null;
    }

    public static Properties getProperties(Properties props, String prefix) {
        Properties newProps = new Properties();
        Enumeration<Object> keys = props.keys();
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            if (key.startsWith(prefix)) {
                newProps.setProperty(key.substring(prefix.length()), props.getProperty(key));
            }
        }
        return newProps;
    }

    /**
     * 获取key的值
     *
     * @param key
     *            键名
     * @param defaultValue
     *            默认值
     * @return 如果键存在返回键值，否则返回默认值(string)
     */
    public static String getProperty(Properties props, String key, String defaultValue) {
        String value = props.getProperty(key, defaultValue);
        return value != null ? value.trim() : null;
    }

    /**
     * 获取key的值
     *
     * @param key
     *            键名
     * @param defaultValue
     *            默认值
     * @return 如果键存在返回键值，否则返回默认值(int)
     */
    public static int getProperty(Properties props, String key, int defaultValue) {
        return NumberHelper.toInt(props.getProperty(key), defaultValue);
    }

    /**
     * 获取key的值
     *
     * @param key
     *            键名
     * @param defaultValue
     *            默认值
     * @return 如果键存在返回键值，否则返回默认值(boolean)
     */
    public static boolean getProperty(Properties props, String key, boolean defaultValue) {
        return "true".equalsIgnoreCase(props.getProperty(key, String.valueOf(defaultValue)).trim());
    }
}

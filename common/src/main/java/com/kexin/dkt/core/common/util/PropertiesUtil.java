package com.kexin.dkt.core.common.util;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;
import java.util.Properties;

/**
 * @auther shr
 * @date 2017/11/2
 * @time 18:53
 * @desc
 **/
@Slf4j
public class PropertiesUtil {

    private static Properties propertie = null;    // 配置的加载

    static {
        propertie = new Properties();
    }

    public static void initFile(String fileName) {
        propertie.clear();
        PropertiesUtil.loadProperties(PropertiesUtil.propertie, fileName);
    }

    public static void initDataSource(Map<String, String> dataMap) {
        propertie.clear();
        for (Map.Entry<String, String> entry : dataMap.entrySet()) {
            propertie.put(entry.getKey(), entry.getValue());
        }
    }

    private static void loadProperties(Properties properties, String fileName) {
        try {
            InputStream in = null;
            if (fileName.startsWith("classpath:")) {
                in = ClassHelper.getResourceAsStream(fileName.substring(10));
            } else if (fileName.startsWith("file:")) {
                in = new FileInputStream(fileName.substring(5));
            } else {
                throw new RuntimeException("资源路径格式不正确:" + fileName);
            }
            properties.load(in);
            in.close();
        } catch (InvalidPropertiesFormatException e) {
            log.error(e.getMessage(), e);
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public static String getProperty(String key) {
        return propertie.getProperty(key);
    }

    public static void setProperty(String key, String value) {
        propertie.setProperty(key, value);
    }
}

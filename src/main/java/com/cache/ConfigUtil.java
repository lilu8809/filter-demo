package com.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * ConfigUtil
 * 读取configFile.properties配置文件路径,返回配置文件对象
 */
public class ConfigUtil {

    private Properties config = new Properties();

    private ConfigUtil(String configFile) {
        try {
            InputStream is = getConfigFileStream(configFile);
            if (is != null) {
                config.load(new InputStreamReader(is));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private InputStream getConfigFileStream(String configFile) {
        InputStream is = null;
        try {
            File file = new File(configFile);
            if (file.exists()) {
                is = new FileInputStream(file);
            } else {
                file = new File(ConfigUtil.class.getResource("/").getPath() + configFile);
                if (file.exists()) {
                    is = new FileInputStream(file);
                } else {
                    is = ConfigUtil.class.getResourceAsStream(configFile);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return is;
    }

    public static ConfigUtil load(String configFile) {
        return new ConfigUtil(configFile);
    }

    public String get(String key) {
        return config.getProperty(key);
    }

    public int getInt(String key) {
        String value = get(key);
        if (value == null) {
            return 0;
        }
        return Integer.parseInt(value);
    }

    public boolean getBool(String key) {
        String value = get(key);
        if (value == null) {
            return false;
        }
        return Boolean.parseBoolean(value);
    }
    
    public long getLong(String key){
    	String value = get(key);
        if (value == null) {
            return -1L;
        }
        return Long.parseLong(value);
    }
}

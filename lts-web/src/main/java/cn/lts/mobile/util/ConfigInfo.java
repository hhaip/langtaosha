package cn.lts.mobile.util;

import java.util.Properties;
import java.util.Random;

/**
 * 读取properties相关配置
 * @author zhoujianyong
 * 2015年4月20日-上午10:24:36
 */
public class ConfigInfo {
    private static Properties cache = new Properties();
    static {
        try {
            cache.load(ConfigInfo.class.getClassLoader().getResourceAsStream("paramConfig.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getValue(String key) {
        return cache.getProperty(key);
    }

    /**
     * 生成随机数，位数由size决定
     *
     * @param size
     * @return
     */
    public static String getRand(int size) {

        Random random = new Random();
        String result = "";
        for (int i = 0; i < size; i++) {
            result += random.nextInt(10);
        }
        return result;
    }
}
package cn.lts.mobile.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.lts.memcachedclient.MemcachedClient;


public class LtsUtil {

	private static Logger logger = LoggerFactory.getLogger(LtsUtil.class);

	private static MemcachedClient memcachedClient;

	public void setMemcachedClient(MemcachedClient memcachedClient) {
		LtsUtil.memcachedClient = memcachedClient;
	}

	public static boolean pause(long seconds) {
		try {
			Thread.sleep(seconds * 1000);
			return true;
		} catch (InterruptedException e) {
			return false;
		}
	}

	public static boolean lock(String key, int seconds) {
		try {
			return memcachedClient.add(key, seconds, System.currentTimeMillis());
		} catch (Exception e) {
			logger.error("加锁异常:{}", e.getMessage());
		}
		return false;
	}

	public static void unlock(String key) {
		try {
			memcachedClient.delete(key);
		} catch (Exception e) {
			logger.error("解锁异常:{}", e.getMessage());
		}
	}

	public static Object get(String key) {
		try {
			return memcachedClient.get(key);
		} catch (Exception e) {
		}
		return null;
	}
	
	public static boolean set(String key, int seconds, Object value) {
		try {
			return memcachedClient.set(key, seconds, value);
		} catch (Exception e) {

		}
		return false;
	}

	public static String encode(String str) {
		return encodeDefaultIfFail(str, "");
	}

	public static String encodeDefaultIfFail(String str, String defaultStr) {
		if (str == null) {
			return "";
		}
		try {
			return URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
			return defaultStr;
		}
	}

	public static String replacePhone(String phone) {
		return StringUtils.replace(phone, ";", "\r\n\t\t\t\t\t\t");
	}

	public static String buildQuery(HttpServletRequest request) {
		StringBuffer buffer = new StringBuffer();
		Map<String, String[]> map = request.getParameterMap();
		int count = 0;
		for (Entry<String, String[]> entry : map.entrySet()) {
			try {
				String key = URLEncoder.encode(entry.getKey(), "UTF-8");
				String[] values = entry.getValue();
				if (values == null) {
					if (count != 0) {
						buffer.append('&');
					}
					buffer.append(key);
					count++;
				} else {
					for (String value : values) {
						if (count != 0) {
							buffer.append('&');
						}
						buffer.append(key);
						count++;
						buffer.append('=');
						String valueString = URLEncoder.encode(value, "UTF-8");
						buffer.append(valueString);
					}
				}
			} catch (UnsupportedEncodingException e) {

			}
		}
		return buffer.toString();
	}
}
package cn.lts.common.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.SecureRandom;

/**
 * 构建http请求
 * @author zhoujianyong
 * 2015年4月25日-上午11:31:56
 */
public class HttpRequestUtil {
	private static transient final Logger logger = LoggerFactory.getLogger(HttpRequestUtil.class);
	
	public static JSONObject post(String url,String content){
		return httpRequest(url, "POST", content);
	}
	
	public static JSONObject get(String url){
		return httpRequest(url, "GET", null);
	}
	
	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl 请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr 提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		HttpsURLConnection httpUrlConn = null;
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			URL url = new URL(requestUrl);
			httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);
			httpUrlConn.setConnectTimeout(60000);
			httpUrlConn.setReadTimeout(60000);
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			httpUrlConn.setRequestMethod(requestMethod);
			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();
			if (null != outputStr) {
				outputStream = httpUrlConn.getOutputStream();
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.flush();
			}
			// 将返回的输入流转换成字符串
			inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			jsonObject = JSONObject.parseObject(buffer.toString());
		} catch (Throwable e) {
			logger.error("异常："+e.getMessage());
		} finally {
			IOUtils.closeQuietly(inputStream);
			IOUtils.closeQuietly(outputStream);
			if(httpUrlConn != null) {
				httpUrlConn.disconnect();
			}
		}
		return jsonObject;
	}
	
	/**
	 * 发起https请求并获取字节数组结果
	 * @param requestUrl
	 * @param requestMethod
	 * @param data
	 */
	public static byte[] httpRequest_byte(String requestUrl, String requestMethod, byte[] data) throws Exception {
		if(StringUtils.isBlank(requestMethod) || "GET".equalsIgnoreCase(requestMethod) && "POST".equalsIgnoreCase(requestMethod)) {
			throw new IllegalArgumentException("requestUrl, requestMethod can't null");
		}
		HttpURLConnection httpUrlConn = null;
		OutputStream outputStream = null;
		InputStream inputStream = null;
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			if (requestMethod.equalsIgnoreCase("GET") && data != null && data.length > 0) {
				if (requestUrl.indexOf('?') > 0) {
					requestUrl += '&';
				} else {
					requestUrl += '?';
				}
				requestUrl += new String(data, Charset.forName("UTF-8"));
			}
			URL url = new URL(requestUrl);
			httpUrlConn = (HttpURLConnection) url.openConnection();
			if (httpUrlConn instanceof HttpsURLConnection) {
				TrustManager[] tm = {new MyX509TrustManager()};
				SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
				sslContext.init(null, tm, new SecureRandom());
				SSLSocketFactory ssf = sslContext.getSocketFactory();
				((HttpsURLConnection) httpUrlConn).setSSLSocketFactory(ssf);
			}
			httpUrlConn.setConnectTimeout(60000);
			httpUrlConn.setReadTimeout(60000);
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			httpUrlConn.setRequestMethod(requestMethod);
			if (requestMethod.equalsIgnoreCase("GET")) {
				httpUrlConn.connect();
			} else {
				if(data != null && data.length > 0) {
					outputStream = httpUrlConn.getOutputStream();
					outputStream.write(data);
					outputStream.flush();
				}
			}
			inputStream = httpUrlConn.getInputStream();
			byte[] buf = new byte[1024 * 2];
			int len;
			while ((len = inputStream.read(buf)) != -1) {
				out.write(buf, 0, len);
			}
			return out.toByteArray();
		} catch (Throwable t) {
			logger.error("URL:{}, 通信异常:{}", requestUrl, t.getMessage(), t);
			throw new Exception(t);
		} finally {
			IOUtils.closeQuietly(inputStream);
			IOUtils.closeQuietly(outputStream);
			if(httpUrlConn != null) {
				httpUrlConn.disconnect();
			}
		}
	}
}
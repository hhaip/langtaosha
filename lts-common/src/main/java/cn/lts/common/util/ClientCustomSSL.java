package cn.lts.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import cn.lts.common.constant.WeiXinConstants;

/**
 * 证书请求处理
 * author: huanghaiping
 * created on 16-7-13.
 */
public class ClientCustomSSL {

	/**
	 * 执行带证书的请求
	 * @param data 请求数据 
	 * @return 返回的数据
	 * @throws Exception
	 */
    public static String doWithCert(String data, String url) throws Exception {
        KeyStore keyStore  = KeyStore.getInstance("PKCS12");
        //FileInputStream instream = new FileInputStream(new File("D:/apiclient_cert.p12"));//P12文件目录（测试环境）
        FileInputStream instream = new FileInputStream(new File(WeiXinConstants.CERT_PARTH));//P12文件目录
        try {
            keyStore.load(instream, WeiXinConstants.MERCHANT_KEY.toCharArray());//这里写密码..默认是你的MCHID
        } finally {
            instream.close();
        }
        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, WeiXinConstants.MERCHANT_KEY.toCharArray())//这里也是写密码的
                .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[] { "TLSv1" },
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();
        try {
        	HttpPost httpost = new HttpPost(url); // 设置响应头信息
        	httpost.addHeader("Connection", "keep-alive");
        	httpost.addHeader("Accept", "*/*");
        	httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        	httpost.addHeader("Host", "api.mch.weixin.qq.com");
        	httpost.addHeader("X-Requested-With", "XMLHttpRequest");
        	httpost.addHeader("Cache-Control", "max-age=0");
        	httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
    		httpost.setEntity(new StringEntity(data, "UTF-8"));
            CloseableHttpResponse response = httpclient.execute(httpost);
            try {
                HttpEntity entity = response.getEntity();
                String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
                EntityUtils.consume(entity);
                return jsonStr;
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }
}
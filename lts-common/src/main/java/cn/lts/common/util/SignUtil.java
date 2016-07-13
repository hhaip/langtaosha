package cn.lts.common.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.lts.common.exception.AesException;

/**
 * 请求校验工具类
 * author: huanghaiping
 * created on 16-7-13.
 */
public class SignUtil {
	private static transient final Logger logger = LoggerFactory.getLogger(SignUtil.class);
	/**
	 * 根据map生成字典排序串
	 * @param paramMap
	 * @return
	 */
	public static String dicSort(Map<String, String> paramMap){
		if(paramMap == null || paramMap.isEmpty()) return null;
		String[] arr = new String[paramMap.size()];
		int i = 0;
		for(String key : paramMap.keySet()){
			arr[i] = key;
			i++;
		}
		Arrays.sort(arr);
		StringBuilder sb = new StringBuilder();
		for (int j = 0; j < arr.length; j++) {
			sb.append(arr[j]).append("=").append(paramMap.get(arr[j])).append("&");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}
	
	/**
	 * 根据实体生成字典排序串
	 * @param t
	 * @return
	 */
	public static <T>String dicSort(T t){
		if(t == null) return null;
		try{
			Field[] fields = t.getClass().getDeclaredFields();
			if(fields != null && fields.length > 0){
				List<String> retList = new ArrayList<String>();
				for(Field field : fields){
					Method fieldGetMet = t.getClass().getMethod("get"+field.getName().substring(0, 1).toUpperCase()+field.getName().substring(1), new Class[]{});
	                Object fieldVal = fieldGetMet.invoke(t, new Object[]{});
					if(fieldVal != null){
						retList.add(field.getName());
					}
				}
				String[] arr = new String[retList.size()];
				for(int i=0; i< arr.length; i++){
					arr[i] = retList.get(i);
				}
				Arrays.sort(arr);
				StringBuilder content = new StringBuilder();
				for (int i = 0; i < arr.length; i++) {
					Method fieldGetMet = t.getClass().getMethod("get"+arr[i].substring(0, 1).toUpperCase()+arr[i].substring(1), new Class[]{});
					content.append(arr[i]).append("=").append(fieldGetMet.invoke(t, new Object[]{})).append("&");
				}
				content.deleteCharAt(content.length() - 1);
				return content.toString();
			}else{
				return null;
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			return null;
		}
	}
	
	/**
	 * 验证签名
	 * 
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static boolean checkSignature(String signature, String token, String timestamp, String nonce) {
		String[] arr = new String[] { token, timestamp, nonce };
		// 将token、timestamp、nonce三个参数进行字典序排序
		Arrays.sort(arr);
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		MessageDigest md = null;
		String tmpStr = null;

		try {
			md = MessageDigest.getInstance("SHA-1");
			// 将三个参数字符串拼接成一个字符串进行sha1加密
			byte[] digest = md.digest(content.toString().getBytes());
			tmpStr = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		content = null;
		// 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
		return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
	}
	
	/**
	 * 用SHA1算法生成安全签名
	 * @param token 票据
	 * @param timestamp 时间戳
	 * @param nonce 随机字符串
	 * @param encrypt 密文
	 * @return 安全签名
	 * @throws AesException 
	 */
	public static String getSHA1(String token, String timestamp, 
		String nonce, String encrypt) throws AesException {
		try {
			String[] array = new String[] { token, timestamp, nonce};
			if(encrypt != null){
				array = new String[] { token, timestamp, nonce, encrypt};
			}
			StringBuffer sb = new StringBuffer();
			// 字符串排序
			Arrays.sort(array);
			for (int i = 0; i < array.length; i++) {
				sb.append(array[i]);
			}
			String str = sb.toString();
			// SHA1签名生成
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(str.getBytes());
			byte[] digest = md.digest();

			StringBuffer hexstr = new StringBuffer();
			String shaHex = "";
			for (int i = 0; i < digest.length; i++) {
				shaHex = Integer.toHexString(digest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexstr.append(0);
				}
				hexstr.append(shaHex);
			}
			return hexstr.toString();
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AesException(AesException.ComputeSignatureError);
		}
	}
	
	public static Map<String, String> sign(String jsapi_ticket, String url) {
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = createNonceStr();
        String timestamp = createTimestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                  "&noncestr=" + nonce_str +
                  "&timestamp=" + timestamp +
                  "&url=" + url;
        System.out.println(string1);

        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
        	logger.error(e.getMessage());
        }
        catch (UnsupportedEncodingException e)
        {
        	logger.error(e.getMessage());
        }

        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);
        return ret;
    }

	/**
	 * 将字节数组转换为十六进制字符串
	 * 
	 * @param byteArray
	 * @return
	 */
	private static String byteToStr(byte[] byteArray) {
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++) {
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}

	/**
	 * 将字节转换为十六进制字符串
	 * 
	 * @param mByte
	 * @return
	 */
	private static String byteToHexStr(byte mByte) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];

		String s = new String(tempArr);
		return s;
	}
	
	private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    public static String createNonceStr() {
        return UUID.randomUUID().toString().substring(0, 31);
    }

    public static String createTimestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
}
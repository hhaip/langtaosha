package cn.lts.common.util;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import cn.lts.common.constant.WeiXinConstants;
import cn.lts.common.enums.EnumMethod;
import cn.lts.common.vo.AccessToken;

/**
 * 公众平台通用接口工具类
 * 
 */
public class WechatAccessToken {
	
	private static Logger logger = LoggerFactory.getLogger(WechatAccessToken.class);
	
	/**
	 * 获取access_token
	 * 
	 * @param appid
	 *            凭证
	 * @param appsecret
	 *            密钥
	 * @param type
	 * 			  token类型
	 * @param code
	 * 			  授权code
	 * @return
	 */
	public static AccessToken getAccessToken(String appid, String appsecret, int type, String code) {
		AccessToken accessToken = null;
		String requestUrl = WeiXinConstants.ACCESS_TOKEN_URL.replace("APPID", appid).replace("APPSECRET", appsecret);
		if (type == WeiXinConstants.TOKEN_AUTH) {
			requestUrl = WeiXinConstants.AUTH_ACCESS_TOKEN_URL.replace("APPID", appid).replace("SECRET", appsecret).replace("CODE", code);
		}
		JSONObject jsonObject = HttpRequestUtil.httpRequest(requestUrl, EnumMethod.GET.name(), null);
		// 如果请求成功
		if (null == jsonObject) {
			return null;
		}

		if (NumberUtils.toInt(jsonObject.getString("errcode")) != 0) {
			logger.error("获取access token异常:{}", jsonObject.getString("errmsg"));
			return null;
		}

		try {
			accessToken = new AccessToken();
			accessToken.setToken(jsonObject.getString("access_token"));
			accessToken.setExpiresIn(jsonObject.getIntValue("expires_in"));
			if (type == WeiXinConstants.TOKEN_AUTH) {
				accessToken.setFreshToken(jsonObject.getString("refresh_token"));
				accessToken.setOpenId(jsonObject.getString("openid"));
			}
		} catch (JSONException e) {
			accessToken = null;
			// 获取token失败
			logger.error("获取access token异常:{}", e.getMessage());
		}
		return accessToken;
	}
}
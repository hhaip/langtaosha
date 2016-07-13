package cn.lts.mobile.util;

import com.alibaba.fastjson.JSONObject;

import cn.lts.common.constant.WeiXinConstants;
import cn.lts.common.enums.EnumMethod;
import cn.lts.common.util.HttpRequestUtil;

public class WechatOAuth2 {
	/**
	 * 根据token、code和openId获取用户授权信息
	 * 注意：此方法只有在scope=snsapi_userinfo的情况下才能使用
	 * @param token
	 * @param code
	 * @param openId
	 * @return
	 */
	public static JSONObject getUserInfo(String token, String code, String openId) {
		String menuUrl = WeiXinConstants.GET_USERINFO_URL.replace("ACCESS_TOKEN", token).replace("OPENID", openId);
		JSONObject jo = HttpRequestUtil.httpRequest(menuUrl, EnumMethod.GET.name(), null);
		System.out.println("jo=" + jo);
		return jo;
	}
}
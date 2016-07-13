package cn.lts.common.wx;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.lts.common.constant.CacheKeyConstants;
import cn.lts.common.constant.WeiXinConstants;
import cn.lts.common.util.WechatAccessToken;
import cn.lts.common.vo.AccessToken;
import cn.lts.memcachedclient.MemcachedClient;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;

public class WechatAPI {

	public static final String CARD_QRCODE_CREATE_URL = "https://api.weixin.qq.com/card/qrcode/create?access_token=ACCESS_TOKEN";

	public static final String CARD_GETTICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=wx_card";

	private static MemcachedClient memcachedClient;

	private static WxConfig wxConfig;

	public void setMemcachedClient(MemcachedClient memcachedClient) {
		WechatAPI.memcachedClient = memcachedClient;
	}

	public static void setWxConfig(WxConfig wxConfig) {
		WechatAPI.wxConfig = wxConfig;
	}

	public static String getAccessToken() {
		AccessToken accessToken = null;
		if (memcachedClient != null) {
			accessToken = (AccessToken) memcachedClient.get(CacheKeyConstants.UPARTY_ACCESS_TOKEN_KEY);
		}
		if (accessToken != null) {
			return accessToken.getToken();
		}
		accessToken = WechatAccessToken.getAccessToken(wxConfig.getAppId(), wxConfig.getSecret(), WeiXinConstants.TOKEN_PPUBLIC, null);
		if (accessToken == null) {
			return null;
		}
		memcachedClient.set(CacheKeyConstants.UPARTY_ACCESS_TOKEN_KEY, CacheKeyConstants.ACCESS_TOKEN_EXPIRE_TIME, accessToken);
		return accessToken.getToken();
	}

	public static String getTicket(int type) {
		String url = null, key = null;
		if (type == 0) {
			key = CacheKeyConstants.UPARTY_JSAPI_TICKET_KEY;
		} else if (type == 1) {
			url = CARD_GETTICKET_URL;
			key = CacheKeyConstants.UPARTY_CARD_TICKET_KEY;
		}

		if (memcachedClient.get(key) != null) {
			return memcachedClient.get(key);
		}

		HttpResponse response = HttpRequest.get(url.replaceFirst("ACCESS_TOKEN", getAccessToken())).send();
		if (response.statusCode() == 200) {
			JSONObject jsonObject = JSON.parseObject(response.charset("UTF-8").unzip().bodyText());
			if (jsonObject != null && jsonObject.getIntValue("errcode") == 0) {
				String ticket = jsonObject.getString("ticket");
				memcachedClient.set(key, CacheKeyConstants.ACCESS_TOKEN_EXPIRE_TIME, ticket);
				return ticket;
			}
		}
		return null;
	}

	public static void createCardqrcode() {
		HttpRequest.post(CARD_QRCODE_CREATE_URL.replaceFirst("ACCESS_TOKEN", getAccessToken()));

	}
}

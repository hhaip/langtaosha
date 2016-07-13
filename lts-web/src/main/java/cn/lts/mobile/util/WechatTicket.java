package cn.lts.mobile.util;

import com.alibaba.fastjson.JSONObject;

import cn.lts.common.constant.WeiXinConstants;
import cn.lts.common.enums.EnumMethod;
import cn.lts.common.util.HttpRequestUtil;

public class WechatTicket {
	/**
	 * 根据code获取成员信息
	 * 
	 * @param token
	 * @param code
	 * @param AgentID
	 * @return
	 */
	public static JSONObject getTicket(String token) {
		String ticketUrl = WeiXinConstants.GET_JSAPI_TICKET_URL.replace("ACCESS_TOKEN", token);
		JSONObject jo = HttpRequestUtil.httpRequest(ticketUrl, EnumMethod.GET.name(), null);
		System.out.println("jo=" + jo);
		return jo;
	}

}

package cn.lts.mobile.web.core;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.alibaba.fastjson.JSONObject;

import cn.lts.mobile.util.WechatTicket;
import cn.uparty.memcachedclient.MemcachedClient;
import cn.uparty.common.constant.CacheKeyConstants;
import cn.uparty.common.constant.WeiXinConstants;
import cn.uparty.common.vo.AccessToken;
import cn.uparty.common.util.SignUtil;
import cn.uparty.common.util.WechatAccessToken;
import cn.uparty.server.third.wx.WxConfig;

public abstract class BaseController {
	private transient final Logger logger = LoggerFactory.getLogger(this.getClass());
	protected static final String ERRPR_PAGE = "error_page";
	protected static final String PRODUCT_NAME_REGEX = "[(a-z0-9)]";

	private static final String JSON_TYPE = "application/json;charset=utf-8";
	private ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	protected MemcachedClient memcachedClient;

	@Autowired
	protected WxConfig wxConfig;

	/**
	 * 
	 * @date 2015年12月3日 下午5:47:47
	 * @author chengyadong
	 * @param request
	 * @return
	 */
	protected String getOpenIdByCode(HttpServletRequest request) {
		String code = request.getParameter("code");
		return getOpenId(memcachedClient, wxConfig, code);
	}

	/**
	 * 获取openId
	 * @param memcachedClient
	 * @param wxConfig
	 * @param code
	 * @return
	 */
	protected String getOpenId(MemcachedClient memcachedClient, WxConfig wxConfig, String code) {
		AccessToken accessToken = null;
		try {
			accessToken = getToken(memcachedClient, wxConfig, WeiXinConstants.TOKEN_AUTH, code);
			if (accessToken != null && accessToken.getToken() != null) {
				return accessToken.getOpenId();
			} else {
				logger.warn("accessToken为空");
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 设置jsapi页面相关参数
	 * 
	 * @param memcachedClient
	 * @param wxConfig
	 * @param url
	 * @param model
	 */
	protected void setParamForJSApi(MemcachedClient memcachedClient, WxConfig wxConfig, String url, Model model) {
		// 设置微信扫码相关JSAPI参数
		AccessToken accessToken = getToken(memcachedClient, wxConfig, WeiXinConstants.TOKEN_PPUBLIC, null);
		if (accessToken != null) {
			Map<String, String> ret = SignUtil.sign(getTicket(memcachedClient, accessToken.getToken()), url);
			model.addAttribute("appid", wxConfig.getAppId());
			model.addAttribute("jsapi_ticket", ret.get("jsapi_ticket"));
			model.addAttribute("timestamp", ret.get("timestamp"));
			model.addAttribute("nonceStr", ret.get("nonceStr"));
			model.addAttribute("signature", ret.get("signature"));
		}
	}

	/**
	 * 请求方的ip地址
	 * @param request
	 * @return
	 */
	protected static String getRemoteAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {// nginx
			ip = request.getHeader("x-forwarded-for");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 解析键值对形式的参数
	 * @param param
	 * @return
	 */
	protected String parseRequestParam(HttpServletRequest request) {
		StringBuilder builder = new StringBuilder();
		Map<String, String[]> paramMap = request.getParameterMap();
		if (paramMap != null && paramMap.size() > 0) {
			for (String key : paramMap.keySet()) {
				try {
					builder.append(key).append("=").append(java.net.URLEncoder.encode(paramMap.get(key)[0], "UTF-8")).append("&");
				} catch (UnsupportedEncodingException e) {
					logger.error("解析参数：" + paramMap.get(key)[0] + "错误!");
					continue;
				}
			}
			builder.deleteCharAt(builder.length() - 1);
		}
		return builder.toString();
	}
	
	protected void parseToModel(HttpServletRequest request, Model model) {
		Enumeration<String> enumeration = request.getParameterNames();
		while (enumeration != null && enumeration.hasMoreElements()) {
			String name = enumeration.nextElement();
			model.addAttribute(name, request.getParameter(name));
		}
	}

	/**
	 * 获取accessToken
	 * @param memcachedClient
	 * @param wxConfig wxConfig
	 * @param tokenType 基础token和授权token
	 * @param code 获取授权token，code值不能为null
	 */
	protected AccessToken getToken(MemcachedClient memcachedClient, WxConfig wxConfig, int tokenType, String code) {
		if (memcachedClient == null) {
			AccessToken accessToken = WechatAccessToken.getAccessToken(wxConfig.getAppId(), wxConfig.getSecret(), tokenType, code);
			return accessToken;
		}
		AccessToken accessToken;
		String cacheKey;
		if (tokenType == WeiXinConstants.TOKEN_PPUBLIC) {
			cacheKey = CacheKeyConstants.UPARTY_ACCESS_TOKEN_KEY;
		} else if (tokenType == WeiXinConstants.TOKEN_AUTH) {
			if(StringUtils.isBlank(code)){
				logger.error("无法获取用户openId,code为空");
			}
			cacheKey = CacheKeyConstants.AUTH_ACCESS_TOKEN_KEY + "_" + code;
		} else {
			throw new RuntimeException("无效的tokenType=" + tokenType);
		}
		accessToken = memcachedClient.get(cacheKey);
		if (accessToken != null) {
			return accessToken;
		} else {
			accessToken = WechatAccessToken.getAccessToken(wxConfig.getAppId(), wxConfig.getSecret(), tokenType, code);
			if (accessToken != null) {
				int expiresTime = accessToken.getExpiresIn();
				if (expiresTime > CacheKeyConstants.ACCESS_TOKEN_EXPIRE_TIME) {
					expiresTime = CacheKeyConstants.ACCESS_TOKEN_EXPIRE_TIME;
				}
				memcachedClient.set(cacheKey, expiresTime, accessToken);
			}
		}
		return accessToken;
	}

	/**
	 * 获取jsapi ticket
	 * @param memcachedClient
	 * @param token accessToken
	 * @return
	 */
	protected String getTicket(MemcachedClient memcachedClient, String token) {
		if (memcachedClient.get(CacheKeyConstants.UPARTY_JSAPI_TICKET_KEY) != null) {
			return memcachedClient.get(CacheKeyConstants.UPARTY_JSAPI_TICKET_KEY);
		} else {
			JSONObject jo = WechatTicket.getTicket(token);
			String ticket = jo.getString("ticket");
			if (StringUtils.isNotBlank(ticket)) {
				memcachedClient.set(CacheKeyConstants.UPARTY_JSAPI_TICKET_KEY, CacheKeyConstants.ACCESS_TOKEN_EXPIRE_TIME, ticket);
				return ticket;
			} else {
				return null;
			}
		}
	}

	public void writer(Object obj, String jsoncallback, HttpServletResponse response) throws Exception {
		if (StringUtils.isEmpty(jsoncallback)) {
			writeJSON(obj, response);
			return;
		}
		writeJSONP(obj, jsoncallback, response);
	}

	public void writeJSONP(Object obj, String jsoncallback, HttpServletResponse response) throws Exception {
		writer(jsoncallback + "(" + objectMapper.writeValueAsString(obj) + ")", response, JSON_TYPE);
	}

	private void writer(String string, HttpServletResponse response, String type) throws IOException, Exception {
		response.setContentType(type);
		PrintWriter writer = response.getWriter();
		writer.print(string);
		writer.flush();
		writer.close();
	}

	public void writer(byte[] data, HttpServletResponse response) throws IOException, Exception {
		response.setContentType("application/x-download");
		response.setContentLength(data.length);
		ServletOutputStream outputStream = response.getOutputStream();
		outputStream.write(data);
		outputStream.flush();
		outputStream.close();
	}

	public void writeJSON(Object obj, HttpServletResponse response) throws Exception {
		writer(objectMapper.writeValueAsString(obj), response, JSON_TYPE);
	}
}
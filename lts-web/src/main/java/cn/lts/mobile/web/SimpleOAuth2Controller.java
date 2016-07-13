package cn.lts.mobile.web;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import cn.lts.mobile.util.WechatOAuth2;
import cn.lts.mobile.web.core.BaseController;
import cn.lts.memcachedclient.MemcachedClient;
import cn.lts.common.constant.WeiXinConstants;
import cn.lts.common.vo.AccessToken;
import cn.lts.common.wx.WxConfig;

/**
 * 单纯实现OAuth2验证，不使用注解及拦截器
 *
 */
@Controller
public class SimpleOAuth2Controller extends BaseController{
	@Autowired
    private MemcachedClient memcachedClient;
	
	@Autowired
	private WxConfig wxConfig;
	/**
	 * 拼接网页授权链接
	 * 此处步骤也可以用页面链接代替
	 * @return
	 */
	@RequestMapping(value = { "/oauth2wx.do" })
	public String Oauth2API(HttpServletRequest request){
		//拼接微信回调地址
		//TODO 此处测试上面的方法，把参数：“reqUrl” 拼接你自己的 URL
		String backUrl = wxConfig.getCurrentDomain() + "/oauth2me.do";
		String redirect_uri = "";
		try {
			redirect_uri = java.net.URLEncoder.encode(backUrl, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String oauth2Url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + wxConfig.getAppId() + "&redirect_uri=" + redirect_uri
				+ "&response_type=code&scope=snsapi_userinfo&state=uparty#wechat_redirect";
		return "redirect:" + oauth2Url;
	}
	
	/**
	 * 授权回调请求处理
	 * @return
	 */
	@RequestMapping(value = { "/oauth2me.do" })
	@ResponseBody
	public String oAuth2Url(HttpServletRequest request, @RequestParam String code){
		AccessToken accessToken = getToken(memcachedClient, wxConfig, WeiXinConstants.TOKEN_AUTH, code);
		if (accessToken != null && accessToken.getToken() != null) {
			JSONObject jo = WechatOAuth2.getUserInfo(accessToken.getToken(), code, accessToken.getOpenId());
			if (jo != null) {
				return jo.getString("openid");
			}
		}
		// 这里简单处理,存储到session中
		return JSONObject.parseObject("{}").toString();
	}
}
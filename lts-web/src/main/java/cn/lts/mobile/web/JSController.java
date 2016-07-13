package cn.lts.mobile.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;

import cn.lts.mobile.util.WechatTicket;
import cn.lts.mobile.web.core.BaseController;
import cn.lts.common.constant.WeiXinConstants;
import cn.lts.common.vo.AccessToken;
import cn.lts.common.wx.WxConfig;
import cn.lts.memcachedclient.MemcachedClient;
import cn.lts.common.util.SignUtil;

/**
 * jsapi功能类
 * @author zhoujianyong
 * 2015年5月3日-下午4:52:44
 */
@Controller
public class JSController extends BaseController{
    @Autowired
    private MemcachedClient memcachedClient;
    
    @Autowired
    private WxConfig wxConfig;
    
	@RequestMapping(value="/jsapi")
	public String helloJs(Model model){
		AccessToken accessToken = getToken(memcachedClient, wxConfig, WeiXinConstants.TOKEN_PPUBLIC, null);
		String url = wxConfig.getCurrentDomain()+"/jsapi";
		JSONObject jo = WechatTicket.getTicket(accessToken.getToken());
		Map<String, String> ret = SignUtil.sign(jo.getString("ticket"), url);
        model.addAttribute("appid", wxConfig.getAppId());
        model.addAttribute("jsapi_ticket", ret.get("jsapi_ticket"));
        model.addAttribute("timestamp", ret.get("timestamp"));
        model.addAttribute("nonceStr", ret.get("nonceStr"));
        model.addAttribute("signature", ret.get("signature"));
		return "jsapi";
	}
}
package cn.lts.mobile.web;

import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import cn.lts.mobile.service.CoreServiceFacade;
import cn.lts.mobile.web.core.BaseController;
import cn.lts.common.constant.WeiXinConstants;
import cn.lts.common.enums.EnumMethod;
import cn.lts.common.vo.AccessToken;
import cn.lts.common.wx.WxConfig;
import cn.lts.common.util.HttpRequestUtil;
import cn.lts.common.util.SignUtil;
import cn.lts.common.util.WechatAccessToken;

/**
 * 测试账号使用
 * @author zhoujianyong
 * 2015年4月24日-下午5:07:01
 */
@Controller
@RequestMapping("/uparty")
public class TestController extends BaseController{
	private transient final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private CoreServiceFacade coreServiceFacade;
	
	@Autowired
	private WxConfig wxConfig;
	/**
	 * 测试消息的可靠性
	 * @param request
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @param token
	 * @param echostr
	 * @return
	 */
	@RequestMapping(value = "/acceptMsg", method = RequestMethod.GET)
	@ResponseBody
	public String acceptGet(HttpServletRequest request,
			@RequestParam(value = "signature", required=false) String signature,
			@RequestParam(value = "timestamp", required=false) String timestamp,
			@RequestParam(value = "nonce", required=false) String nonce,
			@RequestParam(value = "token", required=false) String token,
			@RequestParam(value = "echostr", required=false) String echostr) {
		if (SignUtil.checkSignature(signature, wxConfig.getToken(), timestamp, nonce)) {  
            return echostr;  
        }
		return token;
	}
	
	/**
	 * 接收消息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/acceptMsg", method = RequestMethod.POST)
	public void acceptMsg(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		//从请求中读取整个post数据
		InputStream inputStream = request.getInputStream();
		String postData = IOUtils.toString(inputStream, "UTF-8");
		logger.info(postData);
		// 响应消息
		PrintWriter out = response.getWriter();
		String respMessage = coreServiceFacade.processRequest(postData);
		logger.info("res="+respMessage);
		out.print(respMessage);
		out.close();
	}
	
	@RequestMapping(value = "/stencilMassage")
	@ResponseBody
    public String stencilMassage(HttpServletRequest request, Model model){
		AccessToken accessToken = WechatAccessToken.getAccessToken(wxConfig.getAppId(), wxConfig.getSecret(), WeiXinConstants.TOKEN_PPUBLIC, null);
		
		String requestUrl = WeiXinConstants.MEMBER_BACK_MSG.replace("ACCESS_TOKEN", accessToken.getToken());
		String openId = request.getParameter("openId");
		
		JSONObject inJson = new JSONObject();
		JSONObject dataJson = new JSONObject();
		JSONObject obj = null;
		inJson.put("touser", openId);
		inJson.put("template_id", "pbEp6-HgWhD_wLXKVbMdatCaiyRkT3VaQr7hsNEl1Cs");
		inJson.put("url", "");
		inJson.put("topcolor", "#fff");
		
		obj = new JSONObject();
		obj.put("value", "欢迎来到魔坊KTV-东门店");
		obj.put("color", "#585858");
		dataJson.put("first", obj);
		
		obj = new JSONObject();
		obj.put("value", "魔坊优惠卷");
		obj.put("color", "#169115");
		dataJson.put("keyword1", obj);
		
		obj = new JSONObject();
		obj.put("value", "MF150707001");
		obj.put("color", "#169115");
		dataJson.put("keyword2", obj);
		
		/*obj = new JSONObject();
		obj.put("value", "1000");
		obj.put("color", "#169115");
		dataJson.put("keyword3", obj);*/
		
		obj = new JSONObject();
		obj.put("value", "罗湖区东门二横街新白马服装城对面东门Ucity5楼");
		obj.put("color", "#169115");
		dataJson.put("keyword4", obj);
		
		obj = new JSONObject();
		obj.put("value", "0755-66853688");
		obj.put("color", "#169115");
		dataJson.put("keyword5", obj);
		
		obj = new JSONObject();
		obj.put("value", "您只需要到店出示此优惠券就能享受独家折扣啦。消费完毕记得给商户中肯的评价哦！");
		obj.put("color", "#585858");
		dataJson.put("remark", obj);
		inJson.put("data", dataJson);
		
		JSONObject jsonObj = HttpRequestUtil.httpRequest(requestUrl, EnumMethod.POST.name(), inJson.toString());
		
		logger.info(jsonObj.toString());
		return jsonObj.toString();
    }
}
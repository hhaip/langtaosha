package cn.lts.mobile.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.lts.server.third.wx.WxConfig;
import cn.lts.server.util.UpartyUtil;

@Controller
public class RedirectController {

	@Autowired
	private WxConfig wxConfig;

	/**
	 * 地址转发
	 * @date 2015年11月11日 下午6:24:40
	 * @author chengyadong
	 * @param request
	 * @param module
	 * @param function
	 * @return
	 * @exmple http://test.uparty.cn/redirect/menu/neighborStore?destineType=yuding
	 */
	@RequestMapping("/redirect/{module}/{action}")
	public String redirect(HttpServletRequest request, @PathVariable("module") String module, @PathVariable("action") String action) {
		String url = wxConfig.getCurrentDomain() + "/" + module + "/" + action;
		String queryString = request.getQueryString(); //buildQuery(request);
		if (StringUtils.isNotBlank(queryString)) {
			url += "?" + queryString;
		}
		return "redirect:https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + wxConfig.getAppId() + "&redirect_uri=" + UpartyUtil.encodeDefaultIfFail(url, url)
				+ "&response_type=code&scope=snsapi_base&state=uparty#wechat_redirect";
	}

}

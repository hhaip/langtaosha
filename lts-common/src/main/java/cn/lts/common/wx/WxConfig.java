package cn.lts.common.wx;

import org.apache.commons.lang3.StringUtils;

public class WxConfig {

    private String token;
    
    private String appId;
    
    private String secret;
    
	// 当前映射路径
	private String currentDomain;

	// 十号之前图片路径
	private String mediaId_before;

	// 发送预览消息的接收人openId
	private String previewToUser;
	
	public String getToken() {
        return token;
    }
	
	public void setToken(String token) {
        this.token = token;
    }
	
	public String getAppId() {
        return appId;
    }
	
	public void setAppId(String appId) {
        this.appId = appId;
    }
	
	public String getSecret() {
        return secret;
    }
	
	public void setSecret(String secret) {
        this.secret = secret;
    }

	public String getCurrentDomain() {
		return currentDomain;
	}

	public void setCurrentDomain(String currentDomain) {
		this.currentDomain = currentDomain;
	}

	public String getMediaId_before() {
		return mediaId_before;
	}

	public void setMediaId_before(String mediaId_before) {
		this.mediaId_before = mediaId_before;
	}

	public String getPreviewToUser() {
		return previewToUser;
	}

	public void setPreviewToUser(String previewToUser) {
		this.previewToUser = previewToUser;
	}

	public boolean isTestEnv(){
		if(currentDomain == null || "".equals(currentDomain.trim())){
			return false;
		}
		return StringUtils.containsAny(currentDomain, "test.uparty.cn","jianyong2015.xicp.net","xiongyao.uparty.cn","cyd1991cheng.gicp.net","http://hhaip-cn.oicp.net");
		/*return currentDomain.contains("test.uparty.cn")
			|| currentDomain.contains("jianyong2015.xicp.net")||currentDomain.contains("xiongyao.uparty.cn");*/
	}
}
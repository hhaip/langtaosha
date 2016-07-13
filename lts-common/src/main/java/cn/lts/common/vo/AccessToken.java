package cn.lts.common.vo;

import java.io.Serializable;

public class AccessToken implements Serializable{
	private static final long serialVersionUID = 7357295710237692779L;
	// 获取到的凭证  
    private String token;
    // 用户刷新access_token
    private String freshToken;
    // 用户openId
    private String openId;
    // 凭证有效时间，单位：秒  
    private int expiresIn;  
  
    public String getToken() {  
        return token;  
    }  
  
    public void setToken(String token) {  
        this.token = token;  
    }
    
    public String getFreshToken() {
		return freshToken;
	}
    
    public void setFreshToken(String freshToken) {
		this.freshToken = freshToken;
	}
    
    public String getOpenId() {
		return openId;
	}
    
    public void setOpenId(String openId) {
		this.openId = openId;
	}
  
    public int getExpiresIn() {  
        return expiresIn;  
    }  
  
    public void setExpiresIn(int expiresIn) {  
        this.expiresIn = expiresIn;  
    }  
}
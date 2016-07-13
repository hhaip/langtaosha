package cn.lts.common.domain;

import java.time.LocalDateTime;

import cn.lts.common.domain.enumeration.Gender;

/**
 * 微信用户实体
 * @author huanghaiping
 * created on 16-7-13.
 */
public class WxMember extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5008651182855316660L;
	
	public WxMember(){}
	
	public WxMember(String nickName, String headImgUrl){
		this.nickName = nickName;
		this.headImgUrl = headImgUrl;
	}
	
	/**
	 * 在关注者与公众号产生消息交互后，公众号可获得关注者的OpenID
	 * （加密后的微信号，每个用户对每个公众号的OpenID是唯一的。
	 * 对于不同公众号，同一用户的OpenID不同）
	 */
	private String openId;
	private String nickName;
	private Gender gender = Gender.UNKNOW;
	/**
	 * 用户的语言
	 */
	private String language;
	/**
	 * 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，
	 * 0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。 
	 */
	private String headImgUrl;
    private String phone;
    /**
     * 国家
     */
    private String country;
    /**
     * 身份
     */
    private String province;
    /**
     * 城市
     */
    private String city;
    /**
     * 用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）
     */
    private String privilege;
    
    /**
     * 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
     * 可为空
     */
    private LocalDateTime subscribeTime;
    
    /**
     * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
     */
    private String unionId;
    
    //商户ID
    private long merchantId;
    
    //商户店面ID
    private long merchantStoreId;
    
    /**
     * 存储用户领取的友宝取货码
     */
    private String extStr;

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}

	public LocalDateTime getSubscribeTime() {
		return subscribeTime;
	}

	public void setSubscribeTime(LocalDateTime subscribeTime) {
		this.subscribeTime = subscribeTime;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}
	
	public long getMerchantId() {
		return merchantId;
	}
	
	public void setMerchantId(long merchantId) {
		this.merchantId = merchantId;
	}
	
	public long getMerchantStoreId() {
		return merchantStoreId;
	}
	
	public void setMerchantStoreId(long merchantStoreId) {
		this.merchantStoreId = merchantStoreId;
	}
	
	public String getExtStr() {
		return extStr;
	}
	
	public void setExtStr(String extStr) {
		this.extStr = extStr;
	}
	
	public boolean isSubscribe(){
		return dataStatus == DataStatus.DEFAULT;
	}
}
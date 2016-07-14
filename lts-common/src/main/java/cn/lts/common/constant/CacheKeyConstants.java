package cn.lts.common.constant;

public class CacheKeyConstants {
	public static final int UBOX_PRODUCT_EXPIRE_TIME = 120;//单位秒
	public static final int ACCESS_TOKEN_EXPIRE_TIME = 7000;//单位秒
	public static final int UBOX_USER_EXPIRE_TIME = 60;
	public static final int UBOX_COUPON_EXPIRE_TIME = 30;
	public static final int UPARTY_CREATE_ORDER_TIME = 30;
	//accessToken key
	public static final String UPARTY_ACCESS_TOKEN_KEY = "uparty_access_token_key";
	//网页授权accessToken key
	public static final String AUTH_ACCESS_TOKEN_KEY = "auth_access_token_key";
	//jsapi ticket
	public static final String UPARTY_JSAPI_TICKET_KEY = "uparty_jsapi_ticket_key";
	//卡券ticket
    public static final String UPARTY_CARD_TICKET_KEY = "uparty_card_ticket_key";
}
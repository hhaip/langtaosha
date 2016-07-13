package cn.lts.common.constant;

public class WeiXinConstants {
	public static final int TOKEN_PPUBLIC = 0;
	public static final int TOKEN_AUTH = 1;
	public static final String MERCHANT_KEY = "1238937202";
	public static final String API_SECRET = "lbkaEGNEz8iYig2bMhhDCqi7Y6CMsIG7";
	public static final String ORI_WINXIN_ID = "gh_f55c28d3c391";
	public static final String EncodingAESKey = "kMdl0qYSuQkVJ2iYS44HTzdFg4HClnlfsBTOeUhpYkB";
	public static final String QRCODE_SCENE = "QR_SCENE";//临时二维码
	public static final String QRCODE_LIMIT_SCENE = "QR_LIMIT_SCENE";//永久二维码
	public static final String QRCODE_LIMIT_STR_SCENE = "QR_LIMIT_STR_SCENE";//永久的字符串参数值
	public static final String LONG2SHORT = "long2short";
	public static final String WEIXIN_PAY_JSAPI = "JSAPI";
	public static final String WEIXIN_PAY_NATIVE = "NATIVE";
	public static final String AUTH_MCHID = "1000052601";    
	public static final String AUTH_APPID = "wxbf42bd79c4391863";
	public static final String CERT_PARTH = "/ubox/mfs/uparty/cert/wx/apiclient_cert.p12";
	
	// 获取微信公众号：access_token的接口地址（GET） 限2000（次/天） 
	public final static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	// 换取网页授权access_token
	public static final String AUTH_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	// 获取用户授权信息
	public static final String GET_USERINFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
	// 获取jsapi ticket
	public static final String GET_JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
	// 文件上传url
	public static final String UPLOAD_WEBCHAT_URL = "https://qyapi.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
	// 媒体素材上传url
	public static final String UPLOAD_MEDIA_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
	// 高级群发上传图文消息素材
	public static final String MEDIA_UPLOADNEWS_URL = "https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=ACCESS_TOKEN";
	// 删除永久素材消息
	public static final String DEL_MEDIA_URL = "https://api.weixin.qq.com/cgi-bin/material/del_material?access_token=ACCESS_TOKEN";
	// 根据分组进行群发消息
	public static final String SENDALL_MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=ACCESS_TOKEN";
	// 根据OpenId进行群发消息
	public static final String SEND_MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=ACCESS_TOKEN";
	// 群发消息预览
	public static final String MASS_PREVIEW_URL = "https://api.weixin.qq.com/cgi-bin/message/mass/preview?access_token=ACCESS_TOKEN";
	// 通过token获取ticket
	public static final String URL_TICKET_BY_TOKEN = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=ACCESS_TOKEN";
	// 通过ticket获取code
	public static final String URL_QRCODE_BY_TICKET = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";
	// 获取用户基本信息-提前知道用户openID的情况下
	public static final String FETCH_USERINFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	// 批量获取用户基本信息
	public static final String BATCH_FETCH_USERINFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=ACCESS_TOKEN";
	// 创建自定义菜单
	public static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	// 添加客服
	public static final String ADD_KEFU_URL = "https://api.weixin.qq.com/customservice/kfaccount/add?access_token=ACCESS_TOKEN";
	// 获取客服信息
	public static final String GET_KEFU_URL = "https://api.weixin.qq.com/cgi-bin/customservice/getkflist?access_token=ACCESS_TOKEN";
	// 获取在线客服接待信息
	public static final String GET_ONLINE_KEFU_URL = "https://api.weixin.qq.com/cgi-bin/customservice/getonlinekflist?access_token=ACCESS_TOKEN";
	// 客服发消息
	public static final String KEFU_SEND_MSG = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
	// 获取短地址
	public static final String GET_SHORT_URL = "https://api.weixin.qq.com/cgi-bin/shorturl?access_token=ACCESS_TOKEN";
	// 发送模版消息给用户
	public static final String MEMBER_BACK_MSG = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
	// 获取预支付ID
	public static final String GET_PREPAY_ID_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	// 查询订单
	public static final String ORDER_QUERY_URL = "https://api.mch.weixin.qq.com/pay/orderquery";
	// 关闭订单
	public static final String CLOSE_ORDER_URL = "https://api.mch.weixin.qq.com/pay/closeorder";
	// 申请退款
	public static final String APPLY_REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
	// 查询退款
	public static final String REFUND_QUERY_URL = "https://api.mch.weixin.qq.com/pay/refundquery";
	// 发送红包
	public static final String SEND_RED_PACK_URL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack";
	//创建红包
	public static final String CREATE_ENVELOPE_URL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack";
	//摇一摇红包
	public static final String SHAKE_RED_URL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/hbpreorder";
	//创建红包活动
	public static final String CREATE_ENVELOPEACTIVITY_URL = "https://api.weixin.qq.com/shakearound/lottery/addlotteryinfo?access_token=ACCESS_TOKEN&use_template=USE_TEMPLATE&logo_url=LOGO_URL";
	//录入红包信息
	public static final String INPUT_ENVELOPE_URL = "https://api.weixin.qq.com/shakearound/lottery/setprizebucket?access_token=ACCESS_TOKEN";
	//控制活动开关
	public static final String CTRO_ACTIVITYTONOFF_URL = "https://api.weixin.qq.com/shakearound/lottery/setlotteryswitch?access_token=ACCESS_TOKEN&lottery_id=LOTTERY_ID&onoff=ONOFF";
}
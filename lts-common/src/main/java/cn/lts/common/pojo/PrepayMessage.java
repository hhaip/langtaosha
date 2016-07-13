package cn.lts.common.pojo;

import cn.lts.common.constant.WeiXinConstants;
/**
 * 预支付参数
 * @see http://pay.weixin.qq.com/wiki/doc/api/index.php?chapter=9_1
 * @author zhoujianyong
 */
public class PrepayMessage {
	//公众账号ID
	private String appid;
	//用户标识
	private String openid;
	//商户号
	private String mch_id;
	//商品描述
	private String body;
	//附加数据
	private String attach;
	//随机字符串 
	private String nonce_str;
	//通知地址 
	private String notify_url;
	//商户订单号
	private String out_trade_no;
	//终端IP
	private String spbill_create_ip;
	//总金额 
	private int total_fee;
	//交易类型,取值如下：JSAPI，NATIVE，APP，WAP
	private String trade_type = WeiXinConstants.WEIXIN_PAY_JSAPI;
	//商品ID，trade_type=NATIVE，此参数必传。
	private String product_id;
	//是否订阅了公众号
	private String is_subscribe;
	//签名
	private String sign;
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getMch_id() {
		return mch_id;
	}
	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getNonce_str() {
		return nonce_str;
	}
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}
	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}
	public int getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(int total_fee) {
		this.total_fee = total_fee;
	}
	public String getTrade_type() {
		return trade_type;
	}
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getIs_subscribe() {
		return is_subscribe;
	}
	public void setIs_subscribe(String is_subscribe) {
		this.is_subscribe = is_subscribe;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
}
package cn.lts.common.pojo;

/**
 * 红包发放参数
 * @author zhoujianyong
 * @see http://pay.weixin.qq.com/wiki/doc/api/cash_coupon.php?chapter=13_5
 * 2015年5月20日-下午3:37:49
 */
public class RedPackMessage {
    // 商户appid
    private String wxappid;
    // 微信支付分配的商户号 
    private String mch_id;
    // 商户订单号（每个订单号必须唯一）组成： mch_id+yyyymmdd+10位（一天内不能重复的数字)
    // 接口根据商户订单号支持重入， 如出现超时可再调用。 
    private String mch_billno;
    // 随机字符串，不长于32位
    private String none_str;
    // 提供方名称
    private String nick_name;
    // 红包发送者名称
    private String send_name;
    // 接受收红包的用户，用户在wxappid下的openid
    private String re_openid;
    // 付款金额，单位分
    private int total_amount;
    // 最大红包金额，单位分
    private int max_value;
    // 最小红包金额，单位分（min_value=max_value=total_amount）
    private int min_value;
    // 红包发放总人数，默认1
    private int total_num;
    // 红包祝福语
    private String wishing;
    // 调用接口的机器Ip地址
    private String client_ip;
    // 活动名称
    private String act_name;
    // 备注信息
    private String remark;
    // 商户logo的url
    private String logo_imgurl;
    // 分享文案
    private String share_content;
    // 分享链接
    private String share_url;
    // 分享的图片url
    private String share_imgurl;
    // 签名
    private String sign;
    public String getWxappid() {
        return wxappid;
    }
    public void setWxappid(String wxappid) {
        this.wxappid = wxappid;
    }
    public String getMch_id() {
        return mch_id;
    }
    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }
    public String getMch_billno() {
        return mch_billno;
    }
    public void setMch_billno(String mch_billno) {
        this.mch_billno = mch_billno;
    }
    public String getNone_str() {
        return none_str;
    }
    public void setNone_str(String none_str) {
        this.none_str = none_str;
    }
    public String getNick_name() {
        return nick_name;
    }
    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }
    public String getSend_name() {
        return send_name;
    }
    public void setSend_name(String send_name) {
        this.send_name = send_name;
    }
    public String getRe_openid() {
        return re_openid;
    }
    public void setRe_openid(String re_openid) {
        this.re_openid = re_openid;
    }
    public int getTotal_amount() {
        return total_amount;
    }
    public void setTotal_amount(int total_amount) {
        this.total_amount = total_amount;
    }
    public int getMax_value() {
        return max_value;
    }
    public void setMax_value(int max_value) {
        this.max_value = max_value;
    }
    public int getMin_value() {
        return min_value;
    }
    public void setMin_value(int min_value) {
        this.min_value = min_value;
    }
    public int getTotal_num() {
        return total_num;
    }
    public void setTotal_num(int total_num) {
        this.total_num = total_num;
    }
    public String getWishing() {
        return wishing;
    }
    public void setWishing(String wishing) {
        this.wishing = wishing;
    }
    public String getClient_ip() {
        return client_ip;
    }
    public void setClient_ip(String client_ip) {
        this.client_ip = client_ip;
    }
    public String getAct_name() {
        return act_name;
    }
    public void setAct_name(String act_name) {
        this.act_name = act_name;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getLogo_imgurl() {
        return logo_imgurl;
    }
    public void setLogo_imgurl(String logo_imgurl) {
        this.logo_imgurl = logo_imgurl;
    }
    public String getShare_content() {
        return share_content;
    }
    public void setShare_content(String share_content) {
        this.share_content = share_content;
    }
    public String getShare_url() {
        return share_url;
    }
    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }
    public String getShare_imgurl() {
        return share_imgurl;
    }
    public void setShare_imgurl(String share_imgurl) {
        this.share_imgurl = share_imgurl;
    }
    public String getSign() {
        return sign;
    }
    public void setSign(String sign) {
        this.sign = sign;
    }
}
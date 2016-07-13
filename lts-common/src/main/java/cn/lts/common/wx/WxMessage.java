package cn.lts.common.wx;

import java.util.Arrays;

public enum WxMessage {

	//续时消息
	delaytime_message("您已成功续费\n\n包间号：%s\n续费时间：%s"),

	//换房消息，参数依次为
	changeroom_message("您已成功换房\n\n%s\n\n点击下方“K歌”可点歌欢唱，点击下方“超市”可购买零食，嗨起来吧！"),

	/**
	 * 关房消息，参数依次为房号，欢唱时间，消费金额，KTV名称，KTV地址，KTV电话，链接地址
	 */
	closeroom_message("本次欢唱结束啦\n\n您已退出%s房间\n\n欢唱时间：%s\n\n消费金额：%s\n\n您可以到前台打印小票\n\n欢迎下次光临%s哦\ue418\n地址：%s\n电话：%s\n\n<a href=\"%s\">预订更多Uparty包间</a>"),

	openbox_without_uboxorder_message("您已成功购买超市商品\n\n服务员配送：%s"),

	/**
	 * 预订取消消息
	 */
	book_cancel_message("您的预订被取消\n\n预订：%s，%s\n到店时间：%s\n退款金额：%s元\n退款预计2-3天到账\n\n若有疑问，请拨打%s"),

	new_book_cancel_message("您的预订已被取消\n\n预订：%s，%s\n\n若有疑问,请拨打：\n%s"),

	book_success_message("您已预订成功\n\n预订KTV：%s, %s\n到店时间：今天%s\n预订号：%s\n预订套餐：%s\n地址：%s\n电话：%s\n\n祝您玩的开心~ \n<a href=\"%s\">点击查看预订详情</a>"),

	book_fail_message("预订失败\n\n退款金额：%s\n退款预计2-3天到账\n\n若有疑问，请咨询%s\n"),

	/**
	 * 预订分房消息
	 */
	book_assigned_message("您预订的房间已经准备好了，恭候您的大驾光临~\n\n包间号：%s\n\n我们可最多为您保留90分钟\n\n若需修改时间或取消预订，请拨打%s"),

	/**
	 * 预约取消消息
	 */
	bespeak_cancel_message("您的预约已被取消\n\n预约：%s，%s\n\n若有疑问，请拨打 %s"),

	/**
	 * 预约成功
	 */
	bespeak_success_message("您已预约成功\n\n已预约KTV：%s, %s\n到店时间：今天%s:%s\n预约号：%s\n\n地址：%s\n电话：%s\n\n祝您玩的开心~ \n<a href=\"%s\">点击查看%s详情</a>"),

	bespeak_assigned_message("您预约的(%s,%s)时间快到了\n\n我们最多可为您保留15分钟\n\n若需修改时间或取消预约请拨打：%s"),

	/**
	 * 续费提醒消息
	 */
	renew_alert_message("房间：%s 欢唱时间仅剩15分钟\n\n不过瘾？续费房间,今天唱个够！\n\n<a href=\"%s\">立刻续费</a>！"),

	/**
	 * 打烊提醒消息
	 */
	closestore_alert_message("还有15分钟小店就要打烊了, 欢迎客官下次再来哦~"),

	fivediscount_message("打折券派送中，最高可达5折\n<a href=\"%s\">马上领取</a>"),

	/**
	 * 一分钱活动
	 */
	onefen_message("想不想让一分钱=108元\n<a href=\"%s\">猛戳揭开谜底</a>"),
	//二维码活动消息
	qr_acticity_message("您的价值%s元代金券已经发放至“我的uparty-优惠券”中，使用代金券购买商品可直接享受抵扣，赶快去使用吧！\n<a href=\"%s\">进入手机超市</a>或直接扫描商品柜门二维码"),

	/**
	 * 成功连接wifi提醒消息
	 */
	successful_connection_wifi("您已成功连接%s免费WIFI"),

	/**
	 * 没有店铺信息的关注后消息，参数依次为店铺列表，预订链接地址
	 */
	subscribe_without_merchant("\ue418你终于加入Uparty了！\n\n国内首家智能自助KTV连锁品牌，K歌购物一站式体验！\n\n快扫描Uparty店内房间二维码加入房间，体验起来吧！\n\nUparty店铺：\n\n%s<a href=\"%s\">预订Uparty包间</a>"),

	/**
	 * 有明确店铺信息的关注消息,参数依次为店铺名
	 */
	subscribe_with_merchant("\ue418你终于加入Uparty了！\n\n欢迎光临%s！\r\n感谢关注Uparty\r\n国内首家智能自助KTV连锁品牌，K歌购物一站式体验！"),

	/**
	 * 店铺列表信息，参数依次为店铺名，地址，电话
	 */
	merchantstore_info("%s\n地址：%s\n电话：%s"),
	
	/**
	 * 多客服消息转发（转发到随机某个在线客服）
	 */
	multiservicemessage_forward_public("<xml><ToUserName><![CDATA[%s]]></ToUserName><FromUserName><![CDATA[%s]]></FromUserName><CreateTime>%s</CreateTime><MsgType><![CDATA[transfer_customer_service]]></MsgType></xml>"),
	
	/**
	 * 多客服消息转发（指定转发到某个客服）
	 */
	multiservicemessage_forward_assign("<xml><ToUserName><![CDATA[%s]]></ToUserName><FromUserName><![CDATA[%s]]></FromUserName><CreateTime>%s</CreateTime><MsgType><![CDATA[transfer_customer_service]]></MsgType><TransInfo><KfAccount><![CDATA[%s]]></KfAccount></TransInfo></xml>");
	

	private WxMessage(String text) {
		this.text = text;
	}

	private String text;

	public String getText(Object... params) {
		if (params == null || params.length == 0) {
			return text;
		}

		Object[] array = Arrays.stream(params).map(p -> {
			if (p == null) {
				return "";
			}
			return p;
		}).toArray();

		return String.format(text, array);
	}

}

package cn.lts.common.wx;

public class WxMenu {
	//绑定房间链接
	public static final String BIND_ROOM_URL = "/menu/bindRoom";
	//商家信息链接
	public static final String MERCHANTINFO_URL = "/menu/merchantInfo";
	//预约链接
	public static final String BESPEAK_URL = "/menu/neighborStore?destineType=yuyue";
	//预订链接
	public static final String BOOK_URL = "/menu/neighborStore?destineType=yuding";
	//续费链接
	public static final String RENEW_URL = "/menu/renew";
	//带有房间信息的续费链接
	public static final String RENEW_WITH_ROOMID_URL = "/menu/renew?roomId=%s";
	//K歌链接
	public static final String SING_URL = "/menu/kge";
	//myUparty链接
	public static final String MY_UPARTY_URL = "/menu/myUparty";
	//超市链接
	public static final String SHOP_URL = "/menu/shop";
	//带有参数的超市链接
	public static final String SHOP_WITH_PARAM_URL = "/menu/shop?promotionId=%s&shopType=3&goodsId=%s";
	//消费记录链接
	public static final String CONSUMERECORD_URL = "/menu/consumeRecordIndex";
	//五折券活动链接
	public static final String FIVE_DISCOUNT_URL = "/act/fiveDiscount";
	//一分购活动链接
	public static final String ONEFEN_URL = "/act/onefen";
	//带有订单信息的预订详情链接
	public static final String BOOK_DETAIL_WITH_ORDERID_URL = "/menu/findReserve?merchantStoreId=%s&orderId=%s&reserveType=2&openId=%s";
	//带有预订信息的预订详情链接
	public static final String BOOK_DETAIL_WITH_BOOKID_URL = "/menu/findReserve?merchantStoreId=%s&bookingId=%s&reserveType=%s&openId=%s";
	//商品详情链接
	public static final String GOODS_DETAIL_URL = "/menu/goodInner";
	//带有扫码地址的商品详情链接
	public static final String GOODS_DETAIL_WITH_SCANURL_URL = "/menu/goodInner?scanUrl=";
	//带有参数的优惠券列表链接
	public static final String COUPON_LIST_WITH_PARAM_URL = "/menu/couponList?pageNo=1&couponStatus=1&openId=";
	//活动入口链接,参数分别为商户ID，店铺ID，活动ID
	public static final String ACTIVITY_URL = "/act/join/%s/%s/%s";
	//房间首页
	public static final String ROOM_INDEX = "/menu/roomIndex?roomId=%s";

	public static String getLinkTag(String url, String name) {
		return "<a href=\"" + url + "\">" + name + "</a>";
	}
}

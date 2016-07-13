package cn.lts.common.exception;

/**
 * author: Zhang Li
 * created on 15-4-28.
 */
public class OrderErrors {
    public static final ErrorCode GOODS_IS_LACK = new ErrorCode("goods.is.lack", "商品已售完");
    public static final ErrorCode ORDER_INFO_LACK = new ErrorCode("order.info.lack", "订单信息不完整");
    public static final ErrorCode ORDER_STATUS_ERROR = new ErrorCode("order.status.error", "订单状态错误");
    public static final ErrorCode ORDER_CANNOT_CANCEL = new ErrorCode("4", "订单不能取消， 只有新建状态的订单才能取消");
    public static final ErrorCode ORDER_OPENROOM_ERROR = new ErrorCode("5", "只有套餐订单才能自动开房");
    public static final ErrorCode COBO_NOT_EXIST = new ErrorCode("6", "订单关联的套餐不存在");
    public static final ErrorCode ROOM_NOT_EXIST = new ErrorCode("7", "订单关联的房间数据有误, 不存在该房间");
    public static final ErrorCode MACHINE_NET_UNKNOW = new ErrorCode("8", "售货机未联网");


    public static final ErrorCode OPEN_ROOM_STATUS_ERROR = new ErrorCode("9", "开房失败， 房间状态不是开房使用中");
    public static final ErrorCode OPEN_ROOM_NO_WXCODE = new ErrorCode("10", "开房失败, 未拿到微信码");

    public static final ErrorCode ROOM_IS_NOT_IDLE = new ErrorCode("room_is_not_idle", "下单失败， 房间已被占用");

    public static final ErrorCode NOT_EXIST_ROOM = new ErrorCode("not_exist_room", "房间不存在");
    
    //房间已被占用
    public static final ErrorCode ROOM_LOCKED = new ErrorCode("room_locked", "该房间已被占用");

    //订单未完成支付
    public static final ErrorCode ORDER_NO_PAYED = new ErrorCode("order_no_payed", "订单未完成支付");

    //不是开房套餐
    public static final ErrorCode NOT_OPENROOM_COMBO = new ErrorCode("not_openroom_combo", "订单不属于开房套餐订单");

    //未到关房时间
    public static final ErrorCode NOT_CLOSE_ROOM_TIME = new ErrorCode("not_close_room_time", "开房订单未到关房时间");

    //不存在该商品
    public static final ErrorCode NOT_EXIST_GOODS = new ErrorCode("not_exist_goods", "商品校验失败， 不存在该商品");

    //订单开关机时间设置有误
    public static final ErrorCode KTV_START_END_TIME_ERROR = new ErrorCode("ktv_start_end_time_error", "订单开关机时间设置错误");

    //换房时，换房前的订单不存在
    public static final ErrorCode CHANGE_ROOM_NOT_EXIST_ORDER = new ErrorCode("change_room_not_exist_order", "换房订单未找到订单");

    public static final ErrorCode NOT_EXIST_ROOM_RECORD = new ErrorCode("not_exist_room_record", "不存在开房记录");

    //友宝下单失败
    public static final ErrorCode UBOX_CREATE_ORDER_ERROR = new ErrorCode("ubox_create_order_error", "友宝系统下单失败");

    //友宝出货异常
    public static final ErrorCode UBOX_SEND_ERROR = new ErrorCode("ubox_send_error", "友宝出货异常");

    //开房失败
    public static final ErrorCode KTV_OPEN_ROOM_ERROR = new ErrorCode("ktv_open_room_error", "ktv开房失败");

    public static final ErrorCode KTV_CLOSE_ROOM_ERROR = new ErrorCode("ktv_close_room_error", "ktv关房异常");

    //线下商品订单下单失败
    public static final ErrorCode KTV_OFFLINE_ORDER_ERROR = new ErrorCode("ktv_offline_order_error", "ktv线下商品下单失败");

    //ktv关机时间超过了门店打烊时间
    public static final ErrorCode KTVENDTIME_AFTER_KTVCLOSETIME = new ErrorCode("ktvendtime_after_ktvclosetime", "关机时间超过了打烊时间");

    //ktv可用时长错误
    public static final ErrorCode KTV_AVAILABLE_TIME_ERROR = new ErrorCode("ktv_available_time_error", "ktv可用时长错误");

    //续费时，房间不存在开房订单
    public static final ErrorCode DELAY_ORDER_NO_PARENT_ORDER = new ErrorCode("delay_order_no_parent_order", "续费时, 房间不存在开房订单");
    //没找到开房订单, 续时订单, 换房订单需要有开房订单
    public static final ErrorCode OPEN_ROOM_ORDER_NULL = new ErrorCode("open_room_order_null", "开房订单不存在");

    //订单已过关机时间
    public static final ErrorCode ORDER_IS_KTVENDEND = new ErrorCode("order_is_ktvendend", "订单已过ktv关机时间");
    
    //预定房间错误
    public static final ErrorCode BOOKING_ROOM_ERROR = new ErrorCode("booking_room_error", "房间不是空闲状态");
    //退订房间错误
    public static final ErrorCode UNSUBSCRIBE_ROOM_ERROR = new ErrorCode("unsubscribe_room_error", "房间不是预定状态");

    public static final ErrorCode COUPON_USE_ERROR = new ErrorCode("coupon_use_error", "优惠券抵扣失败");
    
    public static final ErrorCode WALLET_USE_ERROR = new ErrorCode("wallet.use.error", "钱包使用失败");
    
    public static final ErrorCode ACTIVITY_JOIN_ERROR = new ErrorCode("activity.join.error", "活动异常");
    
    public static final ErrorCode INVALID_MACHINE_ERROR = new ErrorCode("invalid.machine.error", "无法获取售货机信息");
    
    public static final ErrorCode NO_UBOX_ORDER_ERROR = new ErrorCode("no.ubox.goods.error", "没有友宝订单");

    public static final ErrorCode SCAN_CREATE_ORDER_ERROR = new ErrorCode("scan.create.order.error", "扫码下单异常");

    //请求太频繁
    public static final ErrorCode REQUEST_MORE_FREQUENTLY = new ErrorCode("request.more.frequently", "请求太频繁");

    //订单未完成支付
    public static final ErrorCode ORDER_NO_PAY= new ErrorCode("order.no.pay", "订单未支付完成");
    
	public static final ErrorCode ORDER_DATA_ERROR = new ErrorCode("order.data.error", "订单数据异常");

    public static final ErrorCode INVALID_ORDER_TYPE = new ErrorCode("order.type.error", "订单类型错误");
}

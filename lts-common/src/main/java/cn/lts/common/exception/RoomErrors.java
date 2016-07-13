package cn.lts.common.exception;

/**
 * 房间有关错误类型定义
 * 2015年5月15日 下午5:27:13
 * @author chengyadong
 */
public class RoomErrors {

    //预定房间错误
    public static final ErrorCode BOOKING_ROOM_ERROR = new ErrorCode(
            "booking_room_error", "房间不是空闲状态");
    //退订房间错误
    public static final ErrorCode UNBOOKING_ROOM_ERROR = new ErrorCode(
            "unbooking_room_error", "房间不是预定状态");

    public static final ErrorCode NO_JOIN_UP_ERROR = new ErrorCode("no.join.up.error", "房间没有接入系统");
    
    public static final ErrorCode NO_CUSTOMER_ROOM_RECORD_ERROR = new ErrorCode("no.customer.room.record.error", "没有开房记录");
    
}

package cn.lts.common.exception;

/**
 * author: Zhang Li
 * created on 15-8-17.
 */
public class WalletErrors {
    public static final ErrorCode BALANCE_NOT_ENOUGH = new ErrorCode("10001", "余额不足");

    public static final ErrorCode BALANCE_CHANGED = new ErrorCode("10002", "余额已发生变化");

    public static final ErrorCode WALLET_IS_BUSY = new ErrorCode("10003", "钱包支付繁忙");

    public static final ErrorCode UBOX_WALLET_PAY_ERROR = new ErrorCode("10004", "友宝钱包支付失败");

    public static final ErrorCode ROOM_WALLET_PAY_ERROR = new ErrorCode("10005", "房间余额支付失败");
    
    public static final ErrorCode MIX_PAY_ERROR = new ErrorCode("mix.pay.error", "钱包支付金额异常");
    
    public static final ErrorCode WALLET_PARAM_ERROR = new ErrorCode("wallet.param.error", "钱包支付参数异常");
}

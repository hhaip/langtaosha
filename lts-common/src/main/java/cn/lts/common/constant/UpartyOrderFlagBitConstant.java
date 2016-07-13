package cn.lts.common.constant;

/**
 * author: Zhang Li
 * created on 15-8-26.
 */
public class UpartyOrderFlagBitConstant {

    //订单是否是混合支付
    public static long MIX_PAY = 1;

    //微信-友宝钱包混合支付
    public static long MIX_WX_UBOXWALLET = 1 << 1;

    //微信-房间余额混合支付
    public static long MIX_WX_ROOMWALLET = 1 << 2;

    //是否有线上商品，　需要友宝出货
    public static long INCLUDE_UBOX_GOODS = 1 << 3;

    //包含线下商品
    public static long INCLUDE_OFFLINE_GOODS = 1 << 4;
    
    public static long COMBO_GROUP = 1 << 5;
    
    public static long TIME_GROUP = 1 << 6;

    public static boolean isMixPay(long flagBit) {
        return (flagBit & MIX_PAY) > 0;
    }

    public static boolean isMixWxUboxWallet(long flagBit) {
        return (flagBit & MIX_WX_UBOXWALLET) > 0;
    }

    public static boolean isMixWxRoomWallet(long flagBit) {
        return (flagBit & MIX_WX_ROOMWALLET) > 0;
    }

    public static boolean isIncludeUboxGoods(long flagBit) {
        return (flagBit & INCLUDE_UBOX_GOODS) > 0;
    }

    public static boolean isIncludeOfflineGoods(long flagBit) {return (flagBit & INCLUDE_OFFLINE_GOODS) > 0;}
    
    /**
     * 商品套餐团购
     * @date 2015年10月9日 下午2:41:39
     * @author chengyadong
     * @param flagBit
     * @return
     */
    public static boolean isComboGroup(long flagBit){
    	return (flagBit & COMBO_GROUP) > 0;
    }
}

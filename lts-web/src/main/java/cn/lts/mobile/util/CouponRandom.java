package cn.lts.mobile.util;

import java.util.Random;

/**
 * @author zhoujy
 * @date 创建时间：2015年7月6日 下午3:19:12
 * @see
 */
public class CouponRandom {
	private static final Random random = new Random();
	private static final int[] couponIndexArr = new int[]{0, 1, 2, 3};
	public static void main(String[] args) {
		for(int i=0;i<50;i++){
			System.out.println(getCouponIndex());
		}
	}
	
	public static int getCouponIndex(){
		int n = random.nextInt(100);
		int m = -1; // 结果数字
		if (n < 90) {
			m = 0;
		} else if (n < 94) {
			m = 1;
		} else if (n < 98) {
			m = 2;
		} else {
			m = 3;
		}
		return couponIndexArr[m];
	}
}

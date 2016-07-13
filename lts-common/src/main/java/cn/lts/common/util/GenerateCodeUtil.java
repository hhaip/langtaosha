package cn.lts.common.util;

public class GenerateCodeUtil {
    private static final char[] codeArr = {'a','b','c','d','e','f','g','h','i','g',
    	'k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
    	'A','B','C','D','E','F','G','H','I','G','K','L','M','N','O','P',
    	'Q','R','S','T','U','V','W','X','Y','Z','1','2','3','4','5','6','7','8','9','0'};

	public static String generateCode(int numCount) {
		StringBuffer b = new StringBuffer();
		java.util.Random r;
		int k;
		for (int i = 0; i < numCount; i++) {
			r = new java.util.Random();
			k = r.nextInt();
			b.append(String.valueOf(codeArr[Math.abs(k % 61)]));
		}
		return b.toString();
	}
}
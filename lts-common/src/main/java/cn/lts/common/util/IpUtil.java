package cn.lts.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author: Zhang Li
 * created on 15-5-1.
 */
public class IpUtil {

    /**
     * ip装换成数字表示
     */
    private static Pattern p = Pattern.compile("(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})");
    public static long parseIpToLong(String ipAddress) {
        Matcher m = p.matcher(ipAddress);
        if (!m.find() && m.groupCount() != 4) {
            return 0;
        }
        long ip1 = Integer.valueOf(m.group(1));
        long ip2 = Integer.valueOf(m.group(2));
        long ip3 = Integer.valueOf(m.group(3));
        long ip4 = Integer.valueOf(m.group(4));
        //return (ip1 << 24) + (ip2 << 16) + (ip3 << 8) + ip4;
        return ip1 << 24 | ip2 << 16 | ip3 << 8 | ip4;
    }

    /**
     * 解析数字表示的ip成字符串形式
     * @param ipNumber
     */
    public static String parseIpFromLong(long ipNumber) {
        StringBuffer sb = new StringBuffer();
        sb.append(String.valueOf(ipNumber >>> 24));
        sb.append(".");
        sb.append(String.valueOf((ipNumber & 0x00FFFFFF) >>> 16));
        sb.append(".");
        sb.append(String.valueOf((ipNumber & 0x0000FFFF) >>> 8));
        sb.append(".");
        sb.append(String.valueOf(ipNumber & 0x000000FF));
        return sb.toString();
    }
    
    public static void main(String[] args) {
//        long l = parseIpToLong("218.17.33.98");//3658555746
//        long l = parseIpToLong("58.250.203.145");//989514641
    	long l = parseIpToLong("210.21.223.178");//3524648882
//        long l = parseIpToLong("182.150.59.78");//3063298894(成都)
//        long l = parseIpToLong("116.30.97.84");//1948148052
//    	long l = parseIpToLong("58.250.203.145");//989514641
//    	long l = parseIpToLong("1.85.36.234");//22357226
    	System.out.println(l);
        System.out.println(parseIpFromLong(3664495258L));//218.107.194.154;
//        String s = parseIpFromLong(3074339810L);//183.62.179.226
//        System.out.println(s);
    	//3085936299
    }
}

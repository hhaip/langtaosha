package cn.lts.common.util;

import org.apache.commons.lang3.CharUtils;

/**
 * 处理四个字节特殊字符
 * @author zhoujianyong
 * 2015年5月3日-下午6:10:46
 */
public class Byte4Utils {
	public static void main(String args[]) {
		String nickName = "gΟοЦШ㈱℡¢";
		System.out.println(Byte4Utils.filter4Bytes(nickName));
	}
	
	public static String filter4Bytes(String str){
		StringBuilder sb = new StringBuilder();
		try{
			byte[] t1 = str.getBytes("UTF-8");
			for (int i = 0; i < t1.length;) {
				byte tt = t1[i];
				if (CharUtils.isAscii((char) tt)) {
					byte[] ba = new byte[1];
					ba[0] = tt;
					i++;
					sb.append(new String(ba, "UTF-8"));
				}
				if ((tt & 0xE0) == 0xC0) {
					byte[] ba = new byte[2];
					ba[0] = tt;
					ba[1] = t1[i+1];
					i++;
					i++;
					sb.append(new String(ba, "UTF-8"));
				}
				if ((tt & 0xF0) == 0xE0) {
					byte[] ba = new byte[3];
					ba[0] = tt;
					ba[1] = t1[i+1];
					ba[2] = t1[i+2];
					i++;
					i++;
					i++;
					sb.append(new String(ba, "UTF-8"));
				}
				if ((tt & 0xF8) == 0xF0) {
					byte[] ba = new byte[4];
					ba[0] = tt;
					ba[1] = t1[i+1];
					ba[2] = t1[i+2];
					ba[3] = t1[i+3];
					i++;
					i++;
					i++;
					i++;
				}
			}
		}catch(Exception e){}
		return sb.toString();
	}
}
package cn.lts.common.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;


/** 
 * author：yaozhiqiang 
 * department：uparty
 * time：2015年9月21日 
 * @version 
 */
public class WriterOrReadFile {

	public static String requestUrl = "/act/transfer?menu=fetchCouponCard";
	
	public static void writerTxt(String fileUrl,List<String> contentList,long cardCouponId,String url) {
		BufferedWriter fw = null;
		try {
			File file = new File(fileUrl);
			if(file.exists()){
				file.delete();
			}
			fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8")); // 指定编码格式，以免读取时中文字符异常
			if (contentList == null || contentList.size() == 0) {
				return;
			}
			for (String code : contentList) {
				StringBuffer sb = new StringBuffer();
				sb.append(url).append(requestUrl+"&couponCode=").append(code).append("&cardCouponId=").append(cardCouponId);
				fw.append(sb.toString());
				fw.newLine();
			}
			fw.flush(); // 全部写入缓存中的内容
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public static List<String> createCode(int number){
		List<String> codeList = new ArrayList<String>();
		for (int i = 0; i < number; i++) {
			codeList.add(GenerateCodeUtil.generateCode(7));
		}
		return codeList;
	}
	
	public static void main(String[] args) {
		String fileUrl="D:\\Ubox\\uparty_project_new\\uparty-web\\trunk\\uparty-admin\\target\\uparty-admin\\tempImg";
		List<String> contentList = new ArrayList<String>();
		contentList.add(GenerateCodeUtil.generateCode(7));
		contentList.add(GenerateCodeUtil.generateCode(7));
		contentList.add(GenerateCodeUtil.generateCode(7));
		contentList.add(GenerateCodeUtil.generateCode(7));
		String url = "http://local.uparty.cn:9090/act/transfer?menu=fetchCouponCard";
		writerTxt(fileUrl, contentList, 1, url);
	}
}

package com.caucho.hessian.client;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;


/**
 * 封装hessian的客户端，将应用名称传递到服务端便于进行日志输出以及调用次数计算
 * @author kerong.zhou
 */
public class AicaiHessianProxyFactory extends HessianProxyFactory{
	
	private String appName = "unkown";

	@Override
	protected URLConnection openConnection(URL url) throws IOException {
		URLConnection conn = super.openConnection(url);
		conn.setRequestProperty("X-App-Name", this.appName);
		return conn;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}
}

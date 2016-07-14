package com.caucho.hessian.server;

import java.lang.reflect.Method;
import java.util.Date;

public class InvokeData {
	
	private String appName;
	private String simpleClassName;
	private Method method;
	private String remoteIp;
	private Date invokeTime = new Date();
	private Date returnTime;
	private boolean invokeSuccess;
	
	public InvokeData(String remoteIp, String appName) {
		this.remoteIp = remoteIp;
		this.appName = appName;
	}
	
	public InvokeData() {
	}

	public Method getMethod() {
		return method;
	}
	public void setMethod(Method method) {
		this.method = method;
	}
	public String getRemoteIp() {
		return remoteIp;
	}
	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}

	public Date getInvokeTime() {
		return invokeTime;
	}

	public void setInvokeTime(Date invokeTime) {
		this.invokeTime = invokeTime;
	}

	public Date getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}

	public String getSimpleClassName() {
		return simpleClassName;
	}

	public void setSimpleClassName(String simpleClassName) {
		this.simpleClassName = simpleClassName;
	}

	public boolean isInvokeSuccess() {
		return invokeSuccess;
	}

	public void setInvokeSuccess(boolean invokeSuccess) {
		this.invokeSuccess = invokeSuccess;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}
}

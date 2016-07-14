package com.caucho.hessian.server;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

public class InvokeRecorder {

	private static final Logger log = Logger.getLogger(InvokeRecorder.class.getName());

	private final static ThreadLocal<InvokeData> dataHolder = new ThreadLocal<InvokeData>();

	public static void beforeInvoke(String remoteIp, String appName) {
		InvokeData invokeData = new InvokeData(remoteIp, appName);
		dataHolder.set(invokeData);
	}

	public static void afterInvoke(String simpleClassName, Method method, boolean success) {
		try {
			InvokeData invokeData = dataHolder.get();
			if (invokeData == null) {
				return;
			}
			invokeData.setInvokeSuccess(success);
			invokeData.setMethod(method);
			invokeData.setReturnTime(new Date());
			invokeData.setSimpleClassName(simpleClassName);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long costTime = invokeData.getReturnTime().getTime() - invokeData.getInvokeTime().getTime();
			log.info("ip:" + invokeData.getRemoteIp() + " app:" + invokeData.getAppName() + " m:" + simpleClassName + "." + method.getName() + " pl:" + method.getParameterTypes().length + " it:" + sdf.format(invokeData.getInvokeTime()) + " rt:" + sdf.format(invokeData.getReturnTime()) + " s:"
					+ invokeData.isInvokeSuccess() + " ct:" + costTime);
		} catch (Throwable e) {
			log.info("recorder error:msg" + e.getMessage());
		} finally {
			dataHolder.remove();
		}
	}

	public static void removeInvokeDate() {
		dataHolder.remove();
	}
}

package cn.lts.memcachedclient.cachemanager;

import java.util.concurrent.Executor;

/**
 * 参考RefreshConfig类的注释
 * @author ZhangLi
 *
 */
public class RefreshConfigBuilder {
	
	RefreshConfig refreshConfig = new RefreshConfig();
	
	public static RefreshConfigBuilder newBuilder() {
		return new RefreshConfigBuilder();
	}
	
	public RefreshConfig build() {
		return refreshConfig;
	}
	
	public RefreshConfigBuilder executor(Executor executor) {
		refreshConfig.setExecutor(executor);
		return this;
	}
	
	public RefreshConfigBuilder prefix(String prefix) {
		refreshConfig.setPrefix(prefix);
		return this;
	}
	
	public RefreshConfigBuilder loadKeyExprieSeconds(int loadKeyExprieSeconds) {
		refreshConfig.setLoadKeyExprieSeconds(loadKeyExprieSeconds);
		return this;
	}
	
	public RefreshConfigBuilder expireOffsetSeconds(int expireOffsetSeconds) {
		refreshConfig.setExpireOffsetSeconds(expireOffsetSeconds);
		return this;
	}
	
	public RefreshConfigBuilder retryTimes(int retryTimes) {
		refreshConfig.setRetryTimes(retryTimes);
		return this;
	}
	
	public RefreshConfigBuilder retryIntervalsMiliSeconds(int retryIntervalsMiliSeconds) {
		refreshConfig.setRetryIntervalsMiliSeconds(retryIntervalsMiliSeconds);
		return this;
	}
}

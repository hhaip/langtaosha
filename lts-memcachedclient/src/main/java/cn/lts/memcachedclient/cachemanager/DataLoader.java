package cn.lts.memcachedclient.cachemanager;

public interface DataLoader {
	public <T> T load();
}

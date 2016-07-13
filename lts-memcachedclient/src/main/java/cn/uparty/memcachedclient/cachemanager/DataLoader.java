package cn.uparty.memcachedclient.cachemanager;

public interface DataLoader {
	public <T> T load();
}

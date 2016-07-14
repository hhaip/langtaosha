package cn.lts.memcachedclient;

public class GetCasResult<T> {
	private T value;
	private long version;

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}
}

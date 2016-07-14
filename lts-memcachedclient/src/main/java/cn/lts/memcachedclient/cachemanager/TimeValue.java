package cn.lts.memcachedclient.cachemanager;

import java.io.Serializable;
import java.util.Date;

public class TimeValue<T> implements Serializable{
	private static final long serialVersionUID = 8149019235411612667L;
	
	/**
	 * 数据在memcached上将要过期的时间，并非当前时间
	 */
	Date date;
	T value;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	static TimeValue build(Object value, int expireSeconds) {
		return new TimeValue(new Date(System.currentTimeMillis() + expireSeconds * 1000), value);
	}
	public TimeValue(Date date, T value) {
		this.date = date;
		this.value = value;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

}

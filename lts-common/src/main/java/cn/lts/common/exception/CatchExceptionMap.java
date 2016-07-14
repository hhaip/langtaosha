package cn.lts.common.exception;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CatchExceptionMap<K, V> implements Map<K, V> {
	private Logger log = LoggerFactory.getLogger(CatchExceptionMap.class);

	private Map<K, V> target;
	private boolean catchPutException = true;

	public Map<K, V> getTarget() {
		return target;
	}

	public void setTarget(Map<K, V> target) {
		this.target = target;
	}

	public boolean isCatchPutException() {
		return catchPutException;
	}

	public void setCatchPutException(boolean catchPutException) {
		this.catchPutException = catchPutException;
	}

	@Override
	public int size() {
		return target.size();
	}

	@Override
	public boolean isEmpty() {
		return target.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return target.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return target.containsValue(value);
	}

	@Override
	public V get(Object key) {
		V result = null;
		try {
			result = target.get(key);
		} catch (Throwable e) {
			log.error("{key:'" + key + "'}", e);
		}
		return result;
	}

	@Override
	public V put(K key, V value) {
		V old = null;
		try {
			old = target.put(key, value);
		} catch (RuntimeException e) {
			log.error("{key:'" + key + "'}", e);
			if (!catchPutException) {
				throw e;
			}
		}
		return old;
	}

	@Override
	public V remove(Object key) {
		V old = null;
		try {
			old = target.remove(key);
		} catch (RuntimeException e) {
			log.error("{key:'" + key + "'}", e);
			if (!catchPutException) {
				throw e;
			}
		}
		return old;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		target.putAll(m);
	}

	@Override
	public void clear() {
		target.clear();
	}

	@Override
	public Set<K> keySet() {
		return target.keySet();
	}

	@Override
	public Collection<V> values() {
		return target.values();
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		return target.entrySet();
	}

}

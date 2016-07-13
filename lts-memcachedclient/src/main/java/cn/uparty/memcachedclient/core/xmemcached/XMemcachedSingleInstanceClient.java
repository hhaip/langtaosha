package cn.uparty.memcachedclient.core.xmemcached;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import cn.uparty.appmodel.exception.InternalRuntimeException;
import cn.uparty.memcachedclient.GetCasResult;
import net.rubyeye.xmemcached.GetsResponse;
import net.rubyeye.xmemcached.exception.MemcachedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.uparty.memcachedclient.MemcachedClient;

public class XMemcachedSingleInstanceClient implements MemcachedClient {
	private Logger log = LoggerFactory.getLogger(this.getClass());

	private net.rubyeye.xmemcached.MemcachedClient xclient;

	private MemcachedClientDel xclientDel;
	
	public void setXclient(net.rubyeye.xmemcached.MemcachedClient xclient) {
		this.xclient = xclient;
	}

	public void setXclientDel(MemcachedClientDel xclientDel) {
		this.xclientDel = xclientDel;
	}

	public XMemcachedSingleInstanceClient(net.rubyeye.xmemcached.MemcachedClient xmemcachedClient) {
		xclient = xmemcachedClient;
	}

	public XMemcachedSingleInstanceClient(net.rubyeye.xmemcached.MemcachedClient xmemcachedClient, MemcachedClientDel memcachedClientDel) {
		try {
			xclient = xmemcachedClient;
			//xclient.flushAll();
		} catch (Exception e) {
			log.error("{msg:'error occur during initing'}", e);
		}
		xclientDel = memcachedClientDel;
	}

	@Override
	public <T> T get(String key, long timeoutInMs) {
		try {
			return xclient.get(key, timeoutInMs);
		} catch (TimeoutException e) {
			log.error("{key:'" + key + "',timeout:'" + timeoutInMs + "'}", e);
		} catch (InterruptedException e) {
			log.error("{key:'" + key + "',timeout:'" + timeoutInMs + "'}", e);
		} catch (MemcachedException e) {
			log.error("{key:'" + key + "',timeout:'" + timeoutInMs + "'}", e);
		} catch (Throwable e) {
			log.error("{key:'" + key + "',timeout:'" + timeoutInMs + "'}", e);
		}
		return null;
	}

	@Override
	public <T> T get(String key) {
		try {
			return xclient.get(key);
		} catch (TimeoutException e) {
			log.error("{key: '" + key + "'}", e);
		} catch (InterruptedException e) {
			log.error("{key: '" + key + "'}", e);
		} catch (MemcachedException e) {
			log.error("{key: '" + key + "'}", e);
		} catch (Throwable e) {
			log.error("{key: '" + key + "'}", e);
		}
		return null;
	}

	@Override
	public <T> T getAndTouch(String key, int expireTimeInSecond) {
		try {
			return xclient.getAndTouch(key, expireTimeInSecond);
		} catch (TimeoutException e) {
			log.error("{key:'" + key + "',expireTime:'" + expireTimeInSecond + "'}", e);
		} catch (InterruptedException e) {
			log.error("{key:'" + key + "',expireTime:'" + expireTimeInSecond + "'}", e);
		} catch (MemcachedException e) {
			log.error("{key:'" + key + "',expireTime:'" + expireTimeInSecond + "'}", e);
		} catch (Throwable e) {
			log.error("{key:'" + key + "',expireTime:'" + expireTimeInSecond + "'}", e);
		}
		return null;
	}

	@Override
	public <T> T getAndTouch(String key, int expireTimeInSecond, long timeoutInMs) {
		try {
			return xclient.getAndTouch(key, expireTimeInSecond, timeoutInMs);
		} catch (TimeoutException e) {
			log.error("{key:'" + key + "',expireTime:'" + expireTimeInSecond + "',timeout:'" + timeoutInMs + "'}", e);
		} catch (InterruptedException e) {
			log.error("{key:'" + key + "',expireTime:'" + expireTimeInSecond + "',timeout:'" + timeoutInMs + "'}", e);
		} catch (MemcachedException e) {
			log.error("{key:'" + key + "',expireTime:'" + expireTimeInSecond + "',timeout:'" + timeoutInMs + "'}", e);
		} catch (Throwable e) {
			log.error("{key:'" + key + "',expireTime:'" + expireTimeInSecond + "',timeout:'" + timeoutInMs + "'}", e);
		}
		return null;
	}

	@Override
	public <T> boolean set(String key, int expireTimeInSecond, T value) {
		try {
			return xclient.set(key, expireTimeInSecond, value);
		} catch (TimeoutException e) {
			log.error("{key:'" + key + "',expireTime:'" + expireTimeInSecond + "',value:'" + value + "'}", e);
		} catch (InterruptedException e) {
			log.error("{key:'" + key + "',expireTime:'" + expireTimeInSecond + "',value:'" + value + "'}", e);
		} catch (MemcachedException e) {
			log.error("{key:'" + key + "',expireTime:'" + expireTimeInSecond + "',value:'" + value + "'}", e);
		}
		return false;
	}

	@Override
	public <T> GetCasResult<T> getCas(String key) {
		try {
			GetCasResult<T> getsRest = new GetCasResult<T>();
			GetsResponse<T> getsResponse = xclient.gets(key);
			if (null != getsResponse) {
				getsRest.setValue(getsResponse.getValue());
				getsRest.setVersion(getsResponse.getCas());
			}
			return getsRest;

		} catch (TimeoutException e) {
			log.error("{key: '" + key + "'}", e);
		} catch (InterruptedException e) {
			log.error("{key: '" + key + "'}", e);
		} catch (MemcachedException e) {
			log.error("{key: '" + key + "'}", e);
		}
		return null;
	}

	@Override
	public <T> GetCasResult<T> getCas(String key, long timeoutInMs) {
		try {
			GetCasResult<T> getsRest = new GetCasResult<T>();
			GetsResponse<T> getsResponse = xclient.gets(key, timeoutInMs);
			if (null != getsResponse) {
				getsRest.setValue(getsResponse.getValue());
				getsRest.setVersion(getsResponse.getCas());
			}
			return getsRest;

		} catch (TimeoutException e) {
			log.error("{key:'" + key + "',timeout:'" + timeoutInMs + "'}", e);
		} catch (InterruptedException e) {
			log.error("{key:'" + key + "',timeout:'" + timeoutInMs + "'}", e);
		} catch (MemcachedException e) {
			log.error("{key:'" + key + "',timeout:'" + timeoutInMs + "'}", e);
		}
		return null;
	}

	@Override
	public <T> boolean cas(String key, T value, long version) {
		try {
			/** 0表示永久保存 */
			return xclient.cas(key, 0, value, version);
		} catch (TimeoutException e) {
			log.error("{key:'" + key + "',value:'" + value + "',version:" + version + "'}", e);
		} catch (InterruptedException e) {
			log.error("{key:'" + key + "',value:'" + value + "',version:" + version + "'}", e);
		} catch (MemcachedException e) {
			log.error("{key:'" + key + "',value:'" + value + "',version:" + version + "'}", e);
		}
		return false;
	}

	@Override
	public <T> boolean cas(String key, int expireTimeInSecond, T value, long version) {
		try {
			return xclient.cas(key, expireTimeInSecond, value, version);
		} catch (TimeoutException e) {
			log.error("{key:'" + key + "',value:'" + value + "',version:" + version + "'}", e);
		} catch (InterruptedException e) {
			log.error("{key:'" + key + "',value:'" + value + "',version:" + version + "'}", e);
		} catch (MemcachedException e) {
			log.error("{key:'" + key + "',value:'" + value + "',version:" + version + "'}", e);
		}
		return false;
	}

	@Override
	public <T> boolean add(String key, int expireTimeInSecond, Object value) {
		try {
			return xclient.add(key, expireTimeInSecond, value);
		} catch (TimeoutException e) {
			log.error("{key:'" + key + "',value:'" + value + "'}", e);
		} catch (InterruptedException e) {
			log.error("{key:'" + key + "',value:'" + value + "'}", e);
		} catch (MemcachedException e) {
			log.error("{key:'" + key + "',value:'" + value + "'}", e);
		}
		return false;
	}

	@Override
	public void delete(String key) {
		try {
			xclient.delete(key);
		} catch (TimeoutException e) {
			log.error("{key:'" + key + "'}", e);
			throw new InternalRuntimeException("{key:'" + key + "'}", e);
		} catch (InterruptedException e) {
			log.error("{key:'" + key + "'}", e);
			throw new InternalRuntimeException("{key:'" + key + "'}", e);
		} catch (MemcachedException e) {
			log.error("{key:'" + key + "'}", e);
			throw new InternalRuntimeException("{key:'" + key + "'}", e);
		}

		try {
			if (xclientDel != null) {
				xclientDel.delete(key);
			}
		} catch (Throwable e) {
			log.error("{method:'xclientDel.delete()', key:'" + key + "'}", e);
		}

	}

    @Override
    public long increment(String key, long value, long initValue) {
        try {
            return xclient.incr(key, value, initValue);
        } catch (TimeoutException e) {
            log.error("{key:'" + key +  "', value:'" + value + "',initValue:'" + initValue + "'}", e);
        } catch (InterruptedException e) {
            log.error("{key:'" + key +  "', value:'" + value + "',initValue:'" + initValue + "'}", e);
        } catch (MemcachedException e) {
            log.error("{key:'" + key +  "', value:'" + value + "',initValue:'" + initValue + "'}", e);
        }
        return 0;
    }

	@Override
	public long increment(String key, long value, long initValue, int exp) {
		try {
			return xclient.incr(key, value, initValue, 5000, exp);
		} catch (TimeoutException e) {
			log.error("{key:'" + key + "', value:'" + value + "',initValue:'" + initValue + "'}", e);
		} catch (InterruptedException e) {
			log.error("{key:'" + key + "', value:'" + value + "',initValue:'" + initValue + "'}", e);
		} catch (MemcachedException e) {
			log.error("{key:'" + key + "', value:'" + value + "',initValue:'" + initValue + "'}", e);
		}
		return 0;
	}

    @Override
    public long decrement(String key, long value, long initValue) {
        try {
            return xclient.decr(key, value, initValue);
        } catch (TimeoutException e) {
            log.error("{key:'" + key +  "', value:'" + value + "',initValue:'" + initValue + "'}", e);
        } catch (InterruptedException e) {
            log.error("{key:'" + key +  "', value:'" + value + "',initValue:'" + initValue + "'}", e);
        } catch (MemcachedException e) {
            log.error("{key:'" + key +  "', value:'" + value + "',initValue:'" + initValue + "'}", e);
        }
        return 0;
    }

    public void shutdown() {
		try {
			xclient.shutdown();
			xclientDel.shutdown();
		} catch (IOException e) {
			log.error("{msg:'shutdown error'}", e);
		}
	}

}

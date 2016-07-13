package cn.lts.common.wx;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 管理线程
 * @author zhoujianyong
 * 2015年4月16日-下午6:56:28
 */
public class ThreadManage {

    public static ExecutorService threadExec = Executors.newFixedThreadPool(15);

    //key为唯一的标识
    public static Map<String, BaseThread> map = new HashMap<String, BaseThread>();

    /**
     * 执行线程，并把线程放入管理MAP中
     * 
     * @param thread
     * @create_time 2010-12-29 下午08:33:15
     */
    public static void excute(BaseThread thread) {
        map.put(thread.getThreadName(), thread);
        threadExec.execute(thread);
    }

    /**
     * 检查线程是否运行中 
     * @param threadName
     * @return T 是 F不是
     * @create_time 2010-12-29 下午08:55:13
     */
    public static boolean isExcute(String threadName) {
        BaseThread thread = map.get(threadName);
        if (thread == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 删除线程数据
     * @param threadName
     * @create_time 2010-12-29 下午09:46:48
     */
    public static void removeThreadData(String threadName) {
        map.remove(threadName);
    }

    /**
     * 遍历移除超时线程数据
     * 
     * @create_time 2010-12-29 下午09:58:15
     */
    public static void removeThreadDataTimeOut() {
        for (String name : map.keySet()) {
            BaseThread thread = map.get(name);
            if (thread.getEndTime() != null && thread.getRemoveDelay() > 0) {
                if (System.currentTimeMillis() > (thread.getEndTime() + thread.getRemoveDelay())) {
                    removeThreadData(name);
                }
            }
        }
    }
}
package cn.lts.common.wx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 线程基类
 * 记录线程开始时间/结束时间/ 线程名称/线程属性参数
 * @author zhoujianyong
 * 2015年4月16日-下午2:44:21
 */
public abstract class BaseThread implements Runnable {
    private transient final Logger logger = LoggerFactory.getLogger(BaseThread.class);
    
    private Long startTime;//开始时间

    private String threadName;//线程名字

    private Long endTime;//结束时间

    private Long removeDelay = 0l;//线程结束后多久移除管理,单位秒

    private volatile boolean isRun;//线程是否运行中

    /**
     * 只定义线程名，
     * 线程状态数据执行完成removeDelay秒后删除
     * @param threadName
     * @param removeDelay 小于等于0执行完后删除
     */
    public BaseThread(String threadName, Long removeDelay) {
        this.threadName = threadName;
        this.removeDelay = removeDelay;
    }

    @Override
    public void run() {
        try {
            startTime = System.currentTimeMillis();
            excuteTask();
            endTime = System.currentTimeMillis();
        } catch (Exception e) {
            logger.error(threadName+"线程异常。", e);
        } finally {
            logger.info(threadName+"线程状态数据移除。");
            ThreadManage.removeThreadData(threadName);
        }
    }

    public abstract void excuteTask();

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Long getRemoveDelay() {
        return removeDelay;
    }

    public void setRemoveDelay(Long removeDelay) {
        this.removeDelay = removeDelay;
    }

    public boolean isRun() {
        return isRun;
    }

    public void setRun(boolean isRun) {
        this.isRun = isRun;
    }
}
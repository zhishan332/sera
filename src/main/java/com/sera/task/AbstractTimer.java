package com.sera.task;

import org.apache.log4j.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 定时任务的基类，子类可以继承该类，以实现简单的定时任务
 *
 * @author wangqing
 * @since 14-4-23 下午3:00
 */
public abstract class AbstractTimer implements Runnable {
    protected final Logger log = Logger.getLogger(this.getClass());
    private ScheduledExecutorService scheduler;//JDK1.5+
    private int valid = 1;//控制该Timer是否启用
    private String executorMethod = "scheduleWithFixedDelay";
    private int initialDelay = 1; //首次执行的延迟时间 默认1  单位秒
    private int delay = 300;//一次执行终止和下一次执行开始之间的延迟 默认5分钟  单位秒
    private int period = 300;//连续执行之间的周期

    /**
     * 定时器启动
     */
    public void start() {
        if (valid != 1) {
            log.warn("定时器：[" + getTimerName() + "]未启用，可以通过配置valid=1来启动该定时器。");
            return;
        }
        log.info("定时器：[" + getTimerName() + "]开始执行,initialDelay:" + initialDelay +
                "delay:" + delay + "executorMethod:" + executorMethod + "period:" + period);
        scheduler = Executors.newScheduledThreadPool(1);
        if (initialDelay <= 0) initialDelay = 1; //如果配置值小于1则设置为默认值为1
        if (delay <= 0) delay = 300; //如果间隔小于1则设置为默认值5分钟
        if (period <= 0) period = 300; //如果间隔小于1则设置为默认值5分钟
        if ("scheduleAtFixedRate".equals(executorMethod)) {
            scheduler.scheduleAtFixedRate(this, initialDelay, period, TimeUnit.SECONDS);
        } else {
            scheduler.scheduleWithFixedDelay(this, initialDelay, delay, TimeUnit.SECONDS);
        }
    }

    /**
     * 定时器销毁
     */
    public void stop() {
        if (scheduler != null) {
            scheduler.shutdown();
        }
        log.info("定时器：[" + getTimerName() + "]已正常关闭>>>>>>>>>>>>>>>>");
    }

    /**
     * 设置启动延迟时间
     *
     * @param initialDelay 启动延迟时间（秒）
     */
    public void setInitialDelay(int initialDelay) {
        this.initialDelay = initialDelay;
    }

    /**
     * 设置间隔执行时间
     *
     * @param delay 一次执行终止和下一次执行开始之间的延迟
     */
    public void setDelay(int delay) {
        this.delay = delay;
    }

    protected abstract String getTimerName();

    /**
     * 设置执行的方法
     *
     * @param executorMethod “scheduleAtFixedRate”或者“scheduleWithFixedDelay”
     */
    public void setExecutorMethod(String executorMethod) {
        this.executorMethod = executorMethod;
    }

    /**
     * 当方法为  scheduleAtFixedRate 时设置执行周期
     *
     * @param period 连续执行之间的周期
     */
    public void setPeriod(int period) {
        this.period = period;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }
}

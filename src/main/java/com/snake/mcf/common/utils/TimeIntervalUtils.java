package com.snake.mcf.common.utils;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeIntervalUtils {

    public static final String START_FLAG = "_";

    private Map<String, Long> datas = null;

    private boolean isNs = false;

    public TimeIntervalUtils() {
        this(false);
    }

    /**
     * @param isNs 是否采用纳秒格式
     */
    public TimeIntervalUtils(boolean isNs) {
        this.datas = new LinkedHashMap<>();
        this.isNs = isNs;
        this.datas.put(START_FLAG, currTime());
    }

    /**
     * 启动一个新的时间计时器
     *
     * @param transName
     * @return
     */
    public long start(String transName) {
        if (this.datas.containsKey(transName)) {
            throw new TranslationNotSetException(transName + "已经启动");
        }
        long time = currTime();
        this.datas.put(transName, time);
        return time;
    }

    /**
     * 返回距离初始计算器的时间差
     *
     * @param trans
     * @return
     */
    public long interval(String trans) {
        return between(trans, START_FLAG);
    }

    /**
     * 返回距离初始计数器的时间差(带单位)
     *
     * @param trans
     * @return
     */
    public String intelvalString(String trans) {
        return betweenString(trans, START_FLAG);
    }

    public long interval() {
        try {
            return currTime() - getLong(START_FLAG);
        } catch (TranslationNotSetException e) {
            return -1;
        }
    }

    /**
     * 返回距离初始计数器的时间差(带单位)
     *
     * @return
     */
    public String intelvalString() {
        return interval() + getTimeUnit();
    }

    private String getTimeUnit() {
        return isNs ? "ns" : "ms";
    }

    /**
     * 停止所有的时间计数器，并返回与初始计数器的时间差
     *
     * @return
     */
    public long stop() {
        try {
            return currTime() - datas.get(START_FLAG);
        } finally {
            datas.clear();
        }
    }

    /**
     * 计算两个计数器的时间差，返回的值保证为正数
     *
     * @param trans1
     * @param trans2
     * @return
     */
    public long between(String trans1, String trans2) {
        try {
            return Math.abs(getLong(trans1) - getLong(trans2));
        } catch (TranslationNotSetException e) {
            return -1;
        }
    }

    /**
     * 计算两个计数器的时间差(带单位)，返回的值保证为正数
     *
     * @param trans1
     * @param trans2
     * @return
     */
    public String betweenString(String trans1, String trans2) {
        return between(trans1, trans2) + getTimeUnit();
    }

    private long getLong(String trans) {

        if (!this.datas.containsKey(trans)) {
            throw new TranslationNotSetException("未设置" + trans);
        }
        return this.datas.get(trans);
    }

    private long currTime() {
        if (isNs) {
            return System.nanoTime();
        } else {
            return System.currentTimeMillis();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        final StringBuilder builder = new StringBuilder(200);
        final TimeIntervalUtils intervalUtils = new TimeIntervalUtils(false);

        builder.append(intervalUtils.intelvalString());
        builder.append('\n');

        intervalUtils.start("解析报文");
        Thread.sleep(1000);
        intervalUtils.start("组装报文");

        builder.append(intervalUtils.intelvalString());
        builder.append('\n');
        builder.append(intervalUtils.betweenString("解析报文", "组装报文"));
        builder.append('\n');
        builder.append(intervalUtils.intelvalString());
        builder.append('\n');

        log.info("{}",builder.toString());
        log.info("{}",intervalUtils.stop());
    }

    public static class TranslationNotSetException extends RuntimeException {

        private static final long serialVersionUID = -4351580266936323406L;

        public TranslationNotSetException(String message) {
            super(message);
        }

    }
}

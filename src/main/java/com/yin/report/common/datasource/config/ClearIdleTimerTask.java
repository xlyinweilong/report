package com.yin.report.common.datasource.config;

import java.util.TimerTask;

/**
 * 清除空闲连接任务。
 *
 * @author yin.weilong
 * @date 2018.11.01
 */
public class ClearIdleTimerTask extends TimerTask {


    @Override
    public void run() {
        DDSHolder.instance().clearIdleDDS();
    }

}

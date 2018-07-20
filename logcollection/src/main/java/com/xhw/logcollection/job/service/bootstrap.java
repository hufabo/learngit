package com.xhw.logcollection.job.service;

import com.xhw.logcollection.job.quartz.SchedulerStart;
import com.xhw.logcollection.job.thread.tasks.TaskStatus;
import com.xhw.logcollection.util.SpringUtil;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 后端任务入口
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-02-09
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */

@Component
public class bootstrap implements InitializingBean {
    @Autowired
    private StartServ startServ;

    @Autowired
    public SchedulerStart schedulerStart;

    //@PostConstruct
    public void initMethod(){
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        new Thread(() -> {
            boolean flag = true;
            do {
                //ApplicationContext appCtx = ContextLoader.getCurrentWebApplicationContext();
                ApplicationContext appCtx = SpringUtil.getApplicationContext();
                if (appCtx!=null){
                    flag = false;
                } else {
                    try {
                        TimeUnit.MILLISECONDS.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } while (flag);

            //startServ.start();
            // 初始化任务状态
            TaskStatus.initTaskStatus();

            // 启动Quartz任务
            try {
                schedulerStart.scheduleJobs();
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }).start();


    }
}

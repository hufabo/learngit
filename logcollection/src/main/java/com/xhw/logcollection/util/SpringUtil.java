package com.xhw.logcollection.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 *  spring工具类
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-02-23
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
@Component
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;
    // 非@import显式注入，@Component是必须的，且该类必须与main同包或子包
    // 若非同包或子包，则需手动import 注入，有没有@Component都一样
    // 可复制到Test同包测试

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(SpringUtil.applicationContext == null){
            SpringUtil.applicationContext  = applicationContext;
        }
    }

    /**
     * 获取 spring applicationContext 对象
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 通过spring name名称获取Bean实例
     * @param name spring bean的名称
     * @return
     */
    public static Object getBean(String name){
        return getApplicationContext().getBean(name);

    }

    //通过class获取Bean.

    /**
     * 通过指定的接口获取spring容器管理的bean对象
     * @param clazz 接口类
     * @return 注入接口的实例对象
     */
    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

    //通过name,以及Clazz返回指定的Bean

    /**
     * 根据指定的接口类和spring 注入名称 获取注入的bean对象
     * @param name  spring bean的名称
     * @param clazz 接口类
     * @return spring管理的bean对象
     */
    public static <T> T getBean(String name,Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }

}

package com.xhw.logcollection.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * 加载 global.properties文件
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-03-05
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
public class LoadGlobalPropertiesUtil {
    private static Log log = LogFactory.getLog(LoadGlobalPropertiesUtil.class);

    private static Properties prop = new Properties();
    static {
        //加载配置文件
        try {
            prop.load(LoadGlobalPropertiesUtil.class.getResourceAsStream("/global.properties"));
        } catch (IOException e) {
            e.printStackTrace();
            log.error("加载global.properties配置文件出错！详细错误："+e.getMessage());
        }
    }

    /**
     * 手动加载配置文件入库，避免修改配置文件后必须重新启动
     * @author konggang
     * @date 2018/3/26 13:27
     */
    public static void refresh(){
        //加载配置文件
        try {
            prop.load(LoadGlobalPropertiesUtil.class.getResourceAsStream("/global.properties"));
        } catch (IOException e) {
            e.printStackTrace();
            log.error("加载global.properties配置文件出错！详细错误："+e.getMessage());
        }
    }

    /**
     * 获取配置参数
     * @param key 参数键名称
     * @param defaulValue 默认值
     * @return 返回字符串类型的参数值
     */
    public static String getProperty(String key, String defaulValue){
        return prop.getProperty(key, defaulValue);
    }

    /**
     * 获取配置参数
     * @param key 参数键名称
     * @return 返回字符串类型的参数值
     */
    public static String getProperty(String key){
        return prop.getProperty(key);
    }

    /**
     * 获取配置参数
     * @param key 参数键名称
     * @param defaulValue  默认值
     * @return 返回int类型的参数值
     */
    public static int getProperty(String key, int defaulValue){
        String strVal = getProperty(key);
        if(strVal != null){
            try {
                return Integer.parseInt(strVal);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return defaulValue;
    }



    public static void main(String[] args) {

        System.out.println("-------------");
        System.out.println(getProperty(Constant.FILE_PUT_MAX_THREADS));
    }

}

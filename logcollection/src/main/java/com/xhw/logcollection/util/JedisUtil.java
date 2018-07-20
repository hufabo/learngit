package com.xhw.logcollection.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * jedis 工具类
 * @Author 孔纲
 * @Date 2018/3/14
 */
public class JedisUtil {

    private static Log log = LogFactory.getLog(JedisUtil.class);

    private static String host = "127.0.0.1";
    private static int port = 6379;
    private static String password = null;
    private static int timeout = 300000;

    // 备份文件列表长度
    private static int BACKUP_LIST_STOCK_LENGTH = 99;
    private static int BACKUP_LIST_INCR_LENGTH = 99;

    static {
        // 从配置文件中读取相关属性
        try {
            Properties prop = new Properties();
            prop.load(JedisUtil.class.getResourceAsStream("/application.properties"));
            host = prop.getProperty("spring.redis.host");
            port = Integer.parseInt(prop.getProperty("spring.redis.port"));
            String auth = prop.getProperty("spring.redis.password");
            if(auth != null && !"".equals(auth)){
                password = auth;
            }
            timeout = Integer.parseInt(prop.getProperty("spring.redis.timeout"));

            String stock_file_max = LoadGlobalPropertiesUtil.getProperty("file_put.stock.max.backup");
            if(stock_file_max != null){
                BACKUP_LIST_STOCK_LENGTH = Integer.valueOf(stock_file_max);
            }

            String incr_file_max = LoadGlobalPropertiesUtil.getProperty("file_put.incr.max.backup");
            if(incr_file_max != null){
                BACKUP_LIST_INCR_LENGTH = Integer.valueOf(incr_file_max);
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("加载application.properties配置文件出错！详细错误："+e.getMessage());
        }
    }

    /**
     * 从redis缓存中读取文件序号
     * @author konggang
     * @date 2018/3/20 17:28
     */
    public synchronized static String getSN(){
        String sn = "1";
        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        //连接Redis服务获取最新的序列号
        JedisPool pool = new JedisPool(new JedisPoolConfig(), host, port, timeout, password);
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            String max = jedis.get("sn");
            String sn_date = jedis.get("sn_date");
            if(max == null || sn_date == null || !sn_date.equals(today)){
                jedis.set("sn",sn);
                jedis.set("sn_date",today);
            }else{
                int i = Integer.parseInt(max);
                i++;
                sn = String.valueOf(i);
                jedis.set("sn", sn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(jedis != null){
                jedis.close();
            }
        }
        pool.destroy();
        // 格式化序号为6位
        String sn6 = String.format("%06d", Integer.parseInt(sn));
        return sn6;
    }

    /**
     * 保存文件名到队列，如果超过容量则返回最开始的文件名
     * @author konggang
     * @date 2018/3/23 16:39
     */
    public synchronized static String pushStockFileName(String fileName){
        //连接Redis服务获取最新的序列号
        JedisPool pool = new JedisPool(new JedisPoolConfig(), host, port, timeout, password);
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            Long length = jedis.lpush("backup_stock_list", fileName);
            if(length > BACKUP_LIST_STOCK_LENGTH){
                return jedis.rpop("backup_stock_list");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(jedis != null){
                jedis.close();
            }
        }
        return null;
    }

    /**
     * 保存文件名到队列，如果超过容量则返回最开始的文件名
     * @author konggang
     * @date 2018/3/23 16:39
     */
    public synchronized static String pushIncrFileName(String fileName){
        //连接Redis服务获取最新的序列号
        JedisPool pool = new JedisPool(new JedisPoolConfig(), host, port, timeout, password);
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            Long length = jedis.lpush("backup_incr_list", fileName);
            if(length > BACKUP_LIST_INCR_LENGTH){
                return jedis.rpop("backup_incr_list");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(jedis != null){
                jedis.close();
            }
        }
        return null;
    }

}

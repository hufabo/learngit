package com.xhw.logcollection.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期、时间操作类
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-02-28
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
public class DateUtil {
    public static final String YYYYMMDD="yyyy-MM-dd";
    public static final String YYYYMMDD2="yyyyMMdd";
    public static final String YYYYMMDDHHMMDD="yyyy-MM-dd HH:mm:ss";
    public static final String YYYYMMDDHHMMDD2="yyyyMMddHHmmss";
    public static final String YYYYMMDDHHMM2="yyyyMMddHHmm";

    /**
     * 返回指定格式的当前系统时间
     *
     * @param format 日期时间格式
     * @return
     */
    public static String dateNow2str(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }

    /**
     * 转换指定日期为Date类型
     * @param date 字符串类型的日期
     * @param format 日期的格式
     * @return
     * @throws ParseException
     */
    public static Date str2Date(String date, String format) throws ParseException {
        return new SimpleDateFormat(format).parse(date);
    }

    /**
     * 转换指定日期的格式
     * @param date 日期
     * @param format 日期的格式
     * @return
     * @throws ParseException
     */
    public static String date2Str(Date date, String format){
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * 获取系统当前时间
     *
     * @return
     */
    public static Date dateNow(){
        return new Date();
    }
}

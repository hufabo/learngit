package com.xhw.logcollection.util;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Objects;
import java.util.Random;

/**
 * 字符串工具类
 * @author wanghaiyang
 * @Time 2017.09.06 12:30
 */
public class StringUtil extends StringUtils {

    /**
     * 处理接口得到的参数
     * @author wanghaiyang
     * @Time 2017.09.06 12:36
     * @param str
     * @return
     */
    public static String strHandler(String str){
        String afterCheckStr = hasInvalidCharacter(str);
        JSONObject paraJson = new JSONObject();
        try {
            paraJson = JSONObject.fromObject(afterCheckStr);
        }catch (Exception e){
            try {
                String decodeStr = URLDecoder.decode(afterCheckStr,"UTF-8");
                paraJson = JSONObject.fromObject(decodeStr);
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
        }
        return paraJson.toString();
    }

    /**
     * check if has other invalid character
     * @author wanghaiyang
     * @Time 2017.09.09 13:24
     * @param str
     * @return
     */
    public static String hasInvalidCharacter(String str){
        String after = null;
        if(str.endsWith("=")){
            after = str.substring(0,str.lastIndexOf('='));
        }else{
            return str;
        }
        return after;
    }

    //生成随机数字和字母,

    /**
     * 随机生成用户名:字母+数字
     * @author wanghaiyang
     * @time 2017.09.09 14:14
     * @param length
     * @return
     */
    public static String getStringRandom(int length) {

        String val = "";
        Random random = new Random();

        //参数length，表示生成几位随机数
        for(int i = 0; i < length; i++) {

            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if( "char".equalsIgnoreCase(charOrNum) ) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char)(random.nextInt(26) + temp);
            } else if( "num".equalsIgnoreCase(charOrNum) ) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

    public static boolean isEmpty(String str){
        return Objects.equals(null,str) || Objects.equals("",str) ;
    }

    public static String NVLLIF(String str, String replace){
        return str == null ? replace : str;
    }

}

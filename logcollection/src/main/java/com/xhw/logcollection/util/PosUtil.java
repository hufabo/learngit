package com.xhw.logcollection.util;

import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 位置获取工具类
 * @author wanghaiyang
 * @time 2017.09.10 15:08
 */
public class PosUtil {

    /**
     * 根据经纬度获取具体地址
     * @author wanghaiyang
     * @Time 2017.09.10 15:36
     * @param longitude 经度
     * @param latitude 纬度
     * @return
     */
    public static JSONObject getCity(String longitude,String latitude) {
        Logger log = Logger.getLogger(PosUtil.class);
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            URL getUrl = new URL("http://api.map.baidu.com/geocoder/v2/?output=json&location="+latitude+",%20"+longitude+"&ak=sO6GQc18t1BZrY1gWbMhgOOa");
            connection= (HttpURLConnection) getUrl.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();
            reader= new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line =reader.readLine()) !=null) {
                builder.append(line);
            }
            if (log.isDebugEnabled())
                log.debug(builder.toString());
            net.sf.json.JSONArray newArray =net.sf.json.JSONArray.fromObject("["+builder.toString()+"]");
            JSONObject obj = (JSONObject)newArray.get(0);
            JSONObject result = (JSONObject)obj.get("result");
            String address = result.get("formatted_address").toString();
            String cityCode = result.get("cityCode").toString();
            JSONObject city = new JSONObject();
            city.put("address",address);
            city.put("cityCode",cityCode);
            return city;

        }catch(Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }finally{
            try {
                reader.close();
            }catch(IOException e) {
                e.printStackTrace();
            }finally{
                connection.disconnect();
            }
        }
        return null;
    }

    public static String[] lngAndLatTrans(String lng,String lat){
        int posLng = lng.indexOf(".");
        int posLat = lat.indexOf(".");
        String[] cordinates = new String[2];
        if(posLat > 2){
            float afterLng = Float.valueOf(lng.substring(0,posLng-2)) + Float.valueOf(lng.substring(posLng-2,lng.length()))/60;
            float afterLat = Float.valueOf(lat.substring(0,posLat-2)) + Float.valueOf(lat.substring(posLat-2,lat.length()))/60;
            cordinates[0] = String.valueOf(afterLng);
            cordinates[1] = String.valueOf(afterLat);
        }
        return cordinates;
    }
}

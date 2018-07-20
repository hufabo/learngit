package com.xhw.logcollection.job.service.impl;

import com.sun.management.OperatingSystemMXBean;
import com.xhw.logcollection.job.service.ReportHeartbeatServ;
import com.xhw.logcollection.job.ws.ReportHeartbeatWsServ;
import com.xhw.logcollection.monitor.entity.BusMonitorHeartbeat;
import com.xhw.logcollection.monitor.service.BusMonitorHeartbeatServ;
import com.xhw.logcollection.util.DateUtil;
import com.xhw.logcollection.util.LoadGlobalPropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * 心跳状态上报服务接口实现类
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-02-28
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
@Service
public class ReportHeartbeatServImpl implements ReportHeartbeatServ {

    private static Logger logger = LoggerFactory.getLogger(ReportHeartbeatServImpl.class);

    @Autowired
    private BusMonitorHeartbeatServ heartbeatServ;
    @Autowired
    private ReportHeartbeatWsServ heartbeatWsServ;

    @Override
    public void reportHeartbeat() throws Exception {
        BusMonitorHeartbeat bean = this.getHeartbeat();

        //保存心跳数据
        try {
            heartbeatServ.save(bean);
        }catch (Exception e){
            logger.error(bean.toString());
            logger.error("心跳数据保存异常：" + e.getMessage());
            throw e;
        }

        //上报心跳数据
        heartbeatWsServ.reportHeartbeat(bean);
    }

    //获得心跳数据
    private BusMonitorHeartbeat getHeartbeat() throws Exception{
        BusMonitorHeartbeat bean = new BusMonitorHeartbeat();
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

        String babh = LoadGlobalPropertiesUtil.getProperty("system.babh");
        bean.setBabh (babh); //备案编号
        bean.setSbzq(DateUtil.dateNow2str(DateUtil.YYYYMMDDHHMM2)); //上报周期，YYYYMMDDHH24Mi
        bean.setCpusyl(Double.valueOf(osBean.getSystemCpuLoad()).shortValue()); //cpu使用率
        //  已用内存大小/总内存大小
        long rateMemeory = osBean.getFreePhysicalMemorySize()*100/osBean.getTotalPhysicalMemorySize();
        bean.setNcsyl(Long.valueOf(rateMemeory).shortValue());      //内存使用率
        bean.setXtfz(osBean.getSystemLoadAverage()<0?0
                :Double.valueOf(osBean.getSystemLoadAverage()).shortValue()); //系统负荷（window中为-1）
        bean.setGxsj(DateUtil.dateNow()); //更新时间，精确到秒
        bean.setCpsyl(Double.valueOf(this.getDiskUsage()).shortValue()); //磁盘使用率


        return bean;
    }

    /**
     * Window 和Linux 得到磁盘的使用率
     */
    private double getDiskUsage() throws Exception {
        String osName = System.getProperty("os.name");
        double totalHD = 0;
        double usedHD = 0;
        if (osName.toLowerCase().contains("windows")
                || osName.toLowerCase().contains("win")) {
            long allTotal = 0;
            long allFree = 0;
            for (char c = 'A'; c <= 'Z'; c++) {
                String dirName = c + ":/";
                File win = new File(dirName);
                if (win.exists()) {
                    long total = (long) win.getTotalSpace();
                    long free = (long) win.getFreeSpace();
                    allTotal = allTotal + total;
                    allFree = allFree + free;
                }
            }
            Double precent = (Double) (1 - allFree * 1.0 / allTotal) * 100;
            BigDecimal b1 = new BigDecimal(precent);
            precent = b1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            return precent;
        } else {
            Runtime rt = Runtime.getRuntime();
            Process p = rt.exec("df -hlP");// df -hl 查看硬盘空间 参数P设置结果不换行，否则解析结果会出错
            BufferedReader in = null;
            try {
                in = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String str = null;
                String[] strArray = null;
                while ((str = in.readLine()) != null) {
                    logger.debug("df -hl:" + str);
                    int m = 0;
                    strArray = str.split(" ");
                    for (String tmp : strArray) {
                        if (tmp.trim().length() == 0)
                            continue;
                        ++m;
                        if (tmp.indexOf("G") != -1) {
                            if (m == 2) {
                                if (!tmp.equals("") && !tmp.equals("0"))
                                    totalHD += Double.parseDouble(tmp.substring(0, tmp.length() - 1)) * 1024;
                            }
                            if (m == 3) {
                                if (!tmp.equals("none") && !tmp.equals("0"))
                                    usedHD += Double.parseDouble(tmp.substring(0, tmp.length() - 1)) * 1024;
                            }
                        }
                        if (tmp.indexOf("M") != -1) {
                            if (m == 2) {
                                if (!tmp.equals("") && !tmp.equals("0"))
                                    totalHD += Double.parseDouble(tmp.substring(0, tmp.length() - 1));
                            }
                            if (m == 3) {
                                if (!tmp.equals("none") && !tmp.equals("0"))
                                    usedHD += Double.parseDouble(tmp.substring(0, tmp.length() - 1));
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                in.close();
            }
            // 保留2位小数
            double precent = (usedHD / totalHD) * 100;
            BigDecimal b1 = new BigDecimal(precent);
            precent = b1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            logger.debug("cpsyl:" + precent);
            return precent;
        }

    }

    public static void main(String[] args) {
        List<String> df = new ArrayList<>();
        df.add("Filesystem            Size  Used Avail Use% Mounted on");
        df.add("/dev/mapper/vg_server49-lv_root");
        df.add("                       50G  2.3G   45G   5% /");
        df.add("tmpfs                 1.9G     0  1.9G   0% /dev/shm");
        df.add("/dev/sda1             477M   78M  374M  18% /boot");
        df.add("/dev/mapper/vg_server49-lv_home");
        df.add("                       61G  1.5G   57G   3% /home");

        double totalHD = 0;
        double usedHD = 0;

        for(String syl:df){
            int m = 0;
            String[] strArray = syl.split(" ");
            for (String tmp : strArray) {
                if (tmp.trim().length() == 0)
                    continue;
                ++m;
                if (tmp.indexOf("G") != -1) {
                    if (m == 2) {
                        if (!tmp.equals("") && !tmp.equals("0"))
                            totalHD += Double.parseDouble(tmp.substring(0, tmp.length() - 1)) * 1024;
                    }
                    if (m == 3) {
                        if (!tmp.equals("none") && !tmp.equals("0"))
                            usedHD += Double.parseDouble(tmp.substring(0, tmp.length() - 1)) * 1024;
                    }
                }
                if (tmp.indexOf("M") != -1) {
                    if (m == 2) {
                        if (!tmp.equals("") && !tmp.equals("0"))
                            totalHD += Double.parseDouble(tmp.substring(0, tmp.length() - 1));
                    }
                    if (m == 3) {
                        if (!tmp.equals("none") && !tmp.equals("0"))
                            usedHD += Double.parseDouble(tmp.substring(0, tmp.length() - 1));
                    }
                }
            }
        }

        // 保留2位小数
        double precent = (usedHD / totalHD) * 100;
        BigDecimal b1 = new BigDecimal(precent);
        precent = b1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        System.out.println(precent);
    }

}

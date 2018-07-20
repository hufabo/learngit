package com.xhw.logcollection.job.ws.impl;

import com.xhw.logcollection.job.entity.ContextBean;
import com.xhw.logcollection.job.ws.FileResultStatusWsServ;
import com.xhw.logcollection.util.IPUtil;
import com.xhw.logcollection.util.WSUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 日志解析文件反馈处理webservice接口实现类
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-03-05
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
@Service
public class FileResultStatusWsServImpl implements FileResultStatusWsServ {

    private WSUtil wsUtil = new WSUtil();

    /**
     * 通过webservice接口获取策略数据
     * @param files 文件名集合
     *
     * @return
     */
    @Override
    public String resultXML(List<String> files) throws Exception {

        //接口参考 “20171211公安交通管理信息安全监管系统外挂接口手册.doc”
        //      ->“2.3.9. 数据文件入库反馈写入接口”

        String babh = "";//备案编号
        String sqm = "";//授权码
        String azdm = "";
        String fileSize = String.valueOf(files.size());
        String softIP = IPUtil.getIpAddress();
        String mac = IPUtil.getMacAddress();
        StringBuilder xmlDoc = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
        xmlDoc.append("<root><fankui>");
        xmlDoc.append("<babh>").append(babh).append("</babh>");
        xmlDoc.append("<sqm>").append(sqm).append("</sqm>");
        xmlDoc.append("<softip>").append(softIP).append("</softip>");
        xmlDoc.append("<softmac>").append(mac).append("</softmac>");
        xmlDoc.append("<azdm>").append(azdm).append("</azdm>");
        xmlDoc.append("<filelist wjs="+fileSize+">");
        for (String wjm:files) {
            xmlDoc.append("<wjm>").append(wjm).append("</wjm>");
        }
        xmlDoc.append("</filelist>");
        xmlDoc.append("</fankui></root>");

        String rst = wsUtil.commonXml(ContextBean.InterfaceID.FILERSTSTATUS.toString(),xmlDoc.toString(),"queryObjectRds");

        return rst;
    }
}

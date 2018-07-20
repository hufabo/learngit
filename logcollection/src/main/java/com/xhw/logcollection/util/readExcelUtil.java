package com.xhw.logcollection.util;

/**
 * 读取Excel工具类
 * @author wanghaiyang
 * @time 2017.09.18 14:40
 */
public class readExcelUtil {

    /**
     * 读取Excel文件内容
     * @author wanghaiyang
     * @time 2017.09.18 14:41
     * @param is 输入流
     * @return
     */
    /*public List<Map<Integer, String>> readExcelContentByList(InputStream is) {

        List<Map<Integer, String>> list = new ArrayList<Map<Integer,String>>();

        Sheet sheet;

        Workbook wb = null;

        Row row = null;

        try {
            //fs = new POIFSFileSystem(is);
            wb = new HSSFWorkbook(is);
            //wb = new XSSFWorkbook(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        sheet = wb.getSheetAt(0);

        // 得到总行数
        int rowNum = sheet.getLastRowNum();
        row = sheet.getRow(0);
        int colNum = row.getPhysicalNumberOfCells();

        // 正文内容应该从第二行开始,第一行为表头的标题
        for (int i = 1; i <= rowNum; i++) {
            row = sheet.getRow(i);
            int j = 0;
            Map<Integer,String> map = new HashMap<Integer, String>();

            while (j < colNum) {
                // 每个单元格的数据内容用"-"分割开，以后需要时用String类的replace()方法还原数据
                // 也可以将每个单元格的数据设置到一个javabean的属性中，此时需要新建一个javabean
                // str += getStringCellValue(row.getCell((short) j)).trim() +
                // "-";

                map.put(j, getCellFormatValue(row.getCell((short) j)).trim().replaceAll("\t\r", ""));
                //str += getCellFormatValue(row.getCell((short) j)).trim() + "    ";
                j++;
            }
            list.add(map);
        }
        return list;
    }*/
}

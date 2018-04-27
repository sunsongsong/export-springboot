package com.song.export.util.export.execl;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExportExeclUtil {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ExportExeclUtil.class);
    /**
     * 列名转换Map
     */
    private static HashMap<String,String> hashMapHead = new HashMap<String,String>(){
        {
            put("pkgID","收藏夹");
            put("elementType","媒体类型");
            put("title","标题");
            put("time","时间");
            put("source","来源");
            put("author","作者");
            put("exportNum","转载量");
            put("summary","摘要");
            put("content","正文");
            put("url","链接");
        }
    };

    /**
     * 生成execl
     * @param table
     * @return
     */
    public static HSSFWorkbook generateExcel(ExcelTable table){
        //创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wb = new HSSFWorkbook();
        //建立新的sheet对象（excel的表单）
        HSSFSheet sheet=wb.createSheet(table.getSheetName());

        //设置表头
        if(table.getHasHead()){
            //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
            HSSFRow row1=sheet.createRow(0);
            //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
            HSSFCell cell=row1.createCell(0);
            //设置表头
            cell.setCellValue(table.getHead());
            //合并表头CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
            sheet.addMergedRegion(new CellRangeAddress(0,0,0,table.getColumnCount()));
        }
        //第二行,列名
        HSSFRow row2=sheet.createRow(table.getHasHead()?1:0);
        //创建单元格并设置单元格内容
        for(int i=0;i<table.getColumnName().size();i++){
            row2.createCell(i).setCellValue(table.getColumnName().get(i));
        }
        //从第三行开始在sheet里写入内容
        int j = table.getHasHead()?2:1;
        for(int i=0;i<table.getColumnContent().size();i++){
            HSSFRow row = sheet.createRow(j+i);
            List<String> contentList = table.getColumnContent().get(i);
            for(int k=0;k<contentList.size();k++){
                String str = contentList.get(k);
                if(null != str && str.length() > 32000){//maximum length of cell contents (text) is 32,767 characters
                    str = str.substring(0,32000);
                }
                row.createCell(k).setCellValue(str);
            }
        }
        return wb;
    }

    /**
     * word中生成表格
     * @param table
     * @return
     */
    public static XWPFDocument generateWordTable(ExcelTable table){
        //创建文档
        XWPFDocument document= new XWPFDocument();

        //创建表格
        XWPFTable ComTable = document.createTable();

        //列宽自动分割
        CTTblWidth comTableWidth = ComTable.getCTTbl().addNewTblPr().addNewTblW();
        comTableWidth.setType(STTblWidth.DXA);
        comTableWidth.setW(BigInteger.valueOf(9072));

        //表格头
        XWPFTableRow comTableRowOne = ComTable.getRow(0);

        for(int i=0;i<table.getColumnName().size();i++){//注意:必须先定位创建一个才能追加
            if(i ==0){
                comTableRowOne.getCell(0).setText(table.getColumnName().get(0));
            }else{
                comTableRowOne.addNewTableCell().setText(table.getColumnName().get(i));
            }
        }
        //表格内容
        for(List<String> list:table.getColumnContent()){
            XWPFTableRow comTableRowTwo = ComTable.createRow();
            for(int i=0;i<list.size();i++){
                comTableRowTwo.getCell(i).setText(list.get(i));
            }
        }
        return document;
    }

    public static void exportFavourite(HttpServletResponse response, List<Map> resultList,
                                       String fileName, String exportContent,
                                       HashMap<String,String> hashMapHead){
        //根据所选导出内容生成标题列表和内容列表
        List<String> columnName = new ArrayList<String>();
        //自动导出 媒体类型 和 收藏夹字段
        exportContent = "pkgID,elementType," + exportContent;

        String array[] = exportContent.split(",");
        //生成列名
        for (String str:array) {
            columnName.add(hashMapHead.get(str));
        }
        //生成内容
        List<List<String>> rows = new ArrayList<>();
        for(Map<String,String> map : resultList){
            List<String> row = new ArrayList<String>();
            for(String str:array){
                row.add(map.get(str));
            }
            rows.add(row);
        }
        ExcelTable table = new ExcelTable();
        table.setColumnName(columnName);
        table.setColumnContent(rows);
        table.setColumnCount(columnName.size());
        table.setHasHead(false);
        table.setSheetName(fileName);
        //输出文件
        ExportExeclUtil.output(response,table,fileName);
    }

    public static void exportNewsList(HttpServletResponse response, List<Map<String,String>> resultList,
                                      String fileName, String exportContent,
                                      HashMap<String,String> hashMapHead){
        //根据所选导出内容生成标题列表和内容列表
        List<String> columnName = new ArrayList<String>();
        //自动导出 媒体类型 和 收藏夹字段
        exportContent = exportContent;

        String array[] = exportContent.split(",");
        //生成列名
        for (String str:array) {
            columnName.add(hashMapHead.get(str));
        }
        //生成内容
        List<List<String>> rows = new ArrayList<>();
        for(Map<String,String> map : resultList){
            List<String> row = new ArrayList<String>();
            for(String str:array){
                row.add(map.get(str));
            }
            rows.add(row);
        }
        ExcelTable table = new ExcelTable();
        table.setColumnName(columnName);
        table.setColumnContent(rows);
        table.setColumnCount(columnName.size());
        table.setHasHead(false);
        table.setSheetName(fileName);
        //输出文件
        ExportExeclUtil.output(response,table,fileName);
    }

    /**
     *
     * @param response
     * @param table 导出数据模型
     * @param fileName 导出文件名
     */
    public static void output(HttpServletResponse response, ExcelTable table, String fileName){
        try{
            int textMode = 1;
            OutputStream output = response.getOutputStream();
            HSSFWorkbook wb = null;
            XWPFDocument doc = null;
            String fileSuffix = "";
            String contentType = "";
            if(textMode == 1){//excel
                wb = ExportExeclUtil.generateExcel(table);
                fileSuffix = ".xls";
                contentType = "application/msexcel";
            }else if(textMode == 0){//word
                doc = ExportExeclUtil.generateWordTable(table);
                fileSuffix = ".doc";
                contentType = "application/msword";
            }
            //fileName = fileName + DateUtil.format(new Date(),"yyyy:MM:dd:hh:mm:ss");
            fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + fileSuffix);
            response.setContentType(contentType);
            if(textMode == 1){//excel
                wb.write(output);
            }else if(textMode == 0){//word
                doc.write(output);
            }
            output.close();
        }catch (IOException e){
            logger.error("ExportUtil.output error"+e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        XWPFDocument document= new XWPFDocument();

        //Write the Document in file system
        FileOutputStream out = new FileOutputStream(new File("C:/Users/XAYQ-SunSongsong/Desktop/create_table.docx"));
        //工作经历表格
        XWPFTable ComTable = document.createTable();


        //列宽自动分割
        CTTblWidth comTableWidth = ComTable.getCTTbl().addNewTblPr().addNewTblW();
        comTableWidth.setType(STTblWidth.DXA);
        comTableWidth.setW(BigInteger.valueOf(9072));

        //表格第一行
        XWPFTableRow comTableRowOne = ComTable.getRow(0);
        comTableRowOne.getCell(0).setText("开始时间");
        comTableRowOne.addNewTableCell().setText("结束时间");
        comTableRowOne.addNewTableCell().setText("公司名称");
        comTableRowOne.addNewTableCell().setText("title");

        //表格第二行
        XWPFTableRow comTableRowTwo = ComTable.createRow();
        comTableRowTwo.getCell(0).setText("2016-09-06");
        comTableRowTwo.getCell(1).setText("至今");
        comTableRowTwo.getCell(2).setText("seawater");
        comTableRowTwo.getCell(3).setText("Java开发工程师");

        document.write(out);
        out.close();
        System.out.println("create_table document written success.");
    }


}

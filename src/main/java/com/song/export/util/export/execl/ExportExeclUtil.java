package com.song.export.util.export.execl;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
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
import java.util.*;

public class ExportExeclUtil {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ExportExeclUtil.class);

    public static ExeclTable initTable(){
        ExeclTable execlTable = new ExeclTable();
        execlTable.setSheetName("表1");
        execlTable.setHead("简报");
        execlTable.setHasHead(Boolean.TRUE);
        List<String> columnName = new ArrayList<>(Arrays.asList("标题","发表时间","内容","原文链接"));
        execlTable.setColumnName(columnName);
        List<List<String>> columnContent = new ArrayList<List<String>>(){{
            add(Arrays.asList("title1","pushTime1","content1","url1"));
            add(Arrays.asList("title2","pushTime2","content2","url2"));
            add(Arrays.asList("title3","pushTime3","content3","url3"));
            add(Arrays.asList("title4","pushTime4","content4","url4"));
            add(Arrays.asList("title5","pushTime5","content5","url5"));
        }};//内容
        execlTable.setColumnContent(columnContent);
        execlTable.setColumnCount(columnName.size()-1);
        return execlTable;
    }

    public static void createExecl(HttpServletResponse response){
        //初始化数据
        ExeclTable table = initTable();
        //生成文档
        HSSFWorkbook wb = generateExcel(table);
        //输出
        output(response,wb,"简报");
    }

    public static HSSFCellStyle getStyle(HSSFWorkbook wb){
        //设置单元格风格，居中对齐.
        HSSFCellStyle cs = wb.createCellStyle();
        cs.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cs.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        cs.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        cs.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        cs.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

        //设置字体:
        HSSFFont font = wb.createFont();
        font.setFontName("黑体");
        font.setFontHeightInPoints((short) 12);//设置字体大小
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
        cs.setFont(font);//要用到的字体格式

        //设置背景颜色
        cs.setFillBackgroundColor(HSSFColor.BLUE.index);
        cs.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
        return cs;
    }

    /**
     * 生成execl
     * @param table
     * @return
     */
    public static HSSFWorkbook generateExcel(ExeclTable table){
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
            cell.setCellStyle(getStyle(wb));
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
    public static XWPFDocument generateWordTable(ExeclTable table){
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

    /**
     * 输出文件
     * @param response
     * @param wb
     * @param fileName
     */
    public static void output(HttpServletResponse response, HSSFWorkbook wb, String fileName){
        try{
            OutputStream output = response.getOutputStream();
            String fileSuffix = ".xls";
            String contentType = "application/msexcel";
            fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + fileSuffix);
            response.setContentType(contentType);
            wb.write(output);
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

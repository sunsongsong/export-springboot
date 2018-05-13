package com.song.export.util.export.word;

import com.song.export.util.export.ExportBean;
import com.song.export.util.export.FileUtil;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.relationships.Relationship;
import org.docx4j.wml.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ExportWordUtil extends ExportWordUtilBase {

    public static List<ExportBean> initData(){
        List<ExportBean> resultList = new ArrayList<>();
        ExportBean bean = new ExportBean("开会了",
                "20180510",
                "中共中央国务院举行春节团拜会 习近平发表讲话",
                "http://invest.hket.com/article/2062998/樂視去年蝕138億差絕A股 中港虧本王比拼");
        for(int i=0;i<10;i++){
            resultList.add(bean);
        }
        return resultList;
    }

    public static List<ExportBean> initDataUrl(){
        List<ExportBean> resultList = new ArrayList<>();

        /** 要读取的文件路径，可以自己修改成自己的路径 */
        /**
         * 读取文件数据
         */
        //String url = "F:\\kaiyuan\\export-springboot\\src\\main\\resources\\问题链接.txt";
        String url = "C:\\Users\\Administrator\\Desktop\\问题链接.txt";
        File file = new File(url);
        if (!file.exists() || file.isDirectory())
        {
            System.out.println("文件不存在！");
            return null;
        }
        StringBuffer sb = null;
        BufferedReader br;
        try
        {
            br = new BufferedReader(new FileReader(file));
            String temp = null;
            sb = new StringBuffer();
            temp = br.readLine();
            while (temp != null)
            {
                sb.append(temp+"===");
                temp = br.readLine();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        /** 读取的文件内容 */
        String info = sb.toString();
        String infos[] = info.split("===");
        for(int i=0;i<infos.length;i++){
            ExportBean bean = new ExportBean("开会了",
                    "20180510",
                    "中共中央国务院举行春节团拜会 习近平发表讲话",
                    infos[i]);
            resultList.add(bean);
        }
        return resultList;
    }

    /**
     * 创建文档,并向浏览器输出
     * @param response
     * @throws Exception
     */
    public static void createWord(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //初始化需要导出的数据
        List<ExportBean> dataList = initDataUrl();

        WordprocessingMLPackage wordMLPackage = createPackage(request,dataList);

        //浏览器输出文件
        String fileName = "舆情资讯简报.docx";
        response.reset();
        fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
        response.setHeader("content-disposition", "attachment;filename=" + fileName);
        response.setContentType("application/msword");
        response.setCharacterEncoding("UTF-8");
        wordMLPackage.save(response.getOutputStream());
    }

    /**
     * 导出压缩word文档
     * @param request
     * @param response
     */
    public static void createWordZip(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //1.文件生成到指定目录(也可以指定绝对路径)
        String filePath =ExportWordUtil.class.getClassLoader().getResource("").getPath() + "exportWord/";
        filePath = filePath + System.currentTimeMillis() +"/";
        File file = new File(filePath);
        if(!file.exists()){
            file.mkdirs();
        }
        //2.文件打包
        List<ExportBean> dataList = initData();
        List<File> srcfile = new ArrayList<>();
        for(int i=1;i<=5;i++){
            WordprocessingMLPackage wordMLPackage = createPackage(request,dataList);
            File outFile = new File(filePath+i+".doc");
            wordMLPackage.save(outFile);
            srcfile.add(outFile);
        }
        String name = "简报.zip";
        File zipfile = new File(filePath,name);
        FileUtil.zipFiles(srcfile, zipfile);
        //3.浏览器输出
        FileUtil.downFile(response,filePath,name);
        //4.删除打包目录下的文件
        FileUtil.DeleteFolder(filePath);
    }

    public static WordprocessingMLPackage createPackage(HttpServletRequest request,List<ExportBean> dataList) throws Exception{
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();
        MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();

        //开始生成word文档
        String title = "Word简报";
        org.docx4j.wml.P p1 = createStyledText(title, "bold", "CENTER", "40");//设置段落样式
        documentPart.getJaxbElement().getBody().getContent().add(p1);
        documentPart.addParagraphOfText("");
        documentPart.addStyledParagraphOfText("MessageHeader", "新闻目录：");

        //增加目录设置
        directorySetting(documentPart);

        //循环数据
        for (ExportBean bean : dataList) {
            //添加一级目录
            documentPart.addStyledParagraphOfText("Heading1", bean.getTitle());

            String strPubTime = createBoldUnboldText("发布时间：",bean.getPushTime());
            documentPart.addObject(org.docx4j.XmlUtils.unmarshalString(strPubTime));

            if(containsSpecialChar(bean.getUrl())){//url中包含特殊字符的word导出使用文本输出，不使用超链接的形式
                String strSource = createBoldUnboldText("原文链接：", bean.getUrl());
                documentPart.addObject(org.docx4j.XmlUtils.unmarshalString(strSource));
            }else {
                //以下为添加超链接的方式
                P.Hyperlink link = createHyperlink(documentPart, bean.getUrl());
                org.docx4j.wml.P paragraph = Context.getWmlObjectFactory().createP();
                org.docx4j.wml.ObjectFactory factory = Context.getWmlObjectFactory();
                org.docx4j.wml.Text t = factory.createText();
                t.setValue("原文链接：");
                org.docx4j.wml.R run = factory.createR();
                run.getContent().add(t);
                org.docx4j.wml.RPr rpr = factory.createRPr();
                org.docx4j.wml.BooleanDefaultTrue b = new org.docx4j.wml.BooleanDefaultTrue();
                b.setVal(true);
                rpr.setB(b);
                HpsMeasure size = new HpsMeasure();
                size.setVal(new BigInteger("25"));
                rpr.setSz(size);
                rpr.setSzCs(size);
                run.setRPr(rpr);
                paragraph.getContent().add(run);
                paragraph.getContent().add(link);
                documentPart.addObject(paragraph);
            }

            String content = createBoldUnboldText("正文：","");
            documentPart.addObject(org.docx4j.XmlUtils.unmarshalString(content));

            String newsContent = bean.getContent();
            if (newsContent != null) {
                newsContent = newsContent.replaceAll("\\<(o|/o)(.*?)\\>", "");
                newsContent = new String(newsContent.getBytes("GB2312"), "GBK");
                newsContent = newsContent.replaceAll("\\?", " ");
                //String[] strArray = newsContent.split("  ");
                String[] strArray = newsContent.split("\\s\\s\\s*");
                for (int j = 0; j < strArray.length; j++) {
                    if ("".equals(strArray[j])) {
                        continue;
                    }
                    documentPart.addParagraphOfText(strArray[j]);
                }
            }
        }

        //增加页眉
        String logoName = "exportWordLogo.png";
        Relationship header_relationship = createHeaderPart(wordMLPackage, request, logoName);
        createHeaderReference(wordMLPackage, header_relationship);

        //增加页脚
        Relationship footer_relationship = createFooterPart(wordMLPackage);
        createFooterReference(wordMLPackage, footer_relationship);
        return wordMLPackage;
    }
}

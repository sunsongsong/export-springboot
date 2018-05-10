package com.song.export.util.export.word;

import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.io.SaveToZipFile;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.relationships.Relationship;
import org.docx4j.wml.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class CreateWordUtil extends CreateWordUtilBase {

    public static List<WordContentBean> initData(){
        List<WordContentBean> resultList = new ArrayList<>();
        WordContentBean bean = new WordContentBean("开会了",
                "20180510",
                "中共中央国务院举行春节团拜会 习近平发表讲话",
                "http://news.ifeng.com/a/20180214/56059106_0.shtml");
        for(int i=0;i<10;i++){
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
        List<WordContentBean> dataList = initData();

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
        for (WordContentBean bean : dataList) {
            //添加一级目录
            documentPart.addStyledParagraphOfText("Heading1", bean.getTitle());

            String strPubTime = createBoldUnboldText("发布时间：",bean.getPushTime());
            documentPart.addObject(org.docx4j.XmlUtils.unmarshalString(strPubTime));

            //documentPart.addParagraphOfText("原文链接：" + bean.getUrl());

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

        String fileName = "舆情资讯简报.docx";
        response.reset();
        fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
        response.setHeader("content-disposition", "attachment;filename=" + fileName);
        response.setContentType("application/msword");
        response.setCharacterEncoding("UTF-8");
        SaveToZipFile saver = new SaveToZipFile(wordMLPackage);
        saver.save(response.getOutputStream());

    }
}

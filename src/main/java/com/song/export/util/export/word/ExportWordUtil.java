package com.song.export.util.export.word;

import com.song.export.util.export.DeleteFileUtil;
import com.song.export.util.export.DownFileUtil;
import com.song.export.util.export.ZipUtil;
import org.docx4j.XmlUtils;
import org.docx4j.jaxb.Context;
import org.docx4j.model.structure.SectionWrapper;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.io.SaveToZipFile;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.FooterPart;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.openpackaging.parts.relationships.Namespaces;
import org.docx4j.relationships.Relationship;
import org.docx4j.wml.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 导出word的工具类
 */
public class ExportWordUtil {
    private static Logger logger = LoggerFactory.getLogger(ExportWordUtil.class);

    private static ObjectFactory factory = new ObjectFactory();

    /**
     * 导出所选文件的压缩包
     * @param request
     * @param response
     * @param packPath 文件路径
     * @param wordDataMap 数据模型
     * @param hashMapHead 导出字段转换map
     * @param exportContent 需要导出的字段
     * @throws Exception
     */
    public static void exportFavourite(HttpServletRequest request, HttpServletResponse response,
                                       String packPath,
                                       HashMap<String,Map<String,List<Map>>> wordDataMap,
                                       HashMap<String,String> hashMapHead,
                                       String exportContent) throws Exception {
        //增加判断：如果只有一个文件,不用打包
        Boolean needZip = true;
        if(wordDataMap.keySet().size() < 2){
            needZip = false;
        }
        //第一步：按媒体类型 循环生成word文档
        List<File> srcfile = new ArrayList<File>();
        for(String str : wordDataMap.keySet()){
            String suffix = getElementTypeStr(Integer.valueOf(str).intValue());
            Map<String,List<Map>> dataMap = new HashMap<String,List<Map>>();
            dataMap = wordDataMap.get(str);
            exportFavouriteWord(request,response,srcfile,suffix,packPath,dataMap,hashMapHead,exportContent,needZip);
        }
        if(needZip){
            //第二步：文件打包
            String name = "舆情资讯简报.zip";
            File zipfile = new File(packPath,name);
            ZipUtil.zipFiles(srcfile, zipfile);
            //第三步：文件输出
            DownFileUtil.downFile(response,packPath,name);
            //第四步：清除原文件
            DeleteFileUtil.DeleteFolder(packPath);
        }
    }

    /**
     * 将数据导出word文件到指定目录下
     * @param request
     * @param response
     * @param srcfile 全部导出的文件
     * @param suffix 文件名后缀
     * @param packPath 文件路径
     * @param dataMap 数据模型
     * @param hashMapHead 导出字段转换map
     * @param exportContent 需要导出的字段
     * @param needZip 是否需要压缩
     * @throws Exception
     */
    public static void exportFavouriteWord(HttpServletRequest request, HttpServletResponse response,
                                           List<File> srcfile,
                                           String suffix,
                                           String packPath,
                                           Map<String,List<Map>> dataMap,
                                           HashMap<String,String> hashMapHead,
                                           String exportContent,
                                           Boolean needZip
    ) throws Exception {

        //第1步：加载模板文件
        WmlTool wmlTool = new WmlTool();
        //第2步：生成标题头
        MainDocumentPart documentPart = wmlTool.getWmlPackage().getMainDocumentPart();
        String title =  "舆情资讯简报"+"（"+suffix+"）";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String subtitle = "(" + df.format(new Date()) + ")";
        P p1 = createStyledText(title, "bold", "CENTER", "36");
        documentPart.getJaxbElement().getBody().getContent().add(p1);
        P p2 = createStyledText(subtitle, "bold", "CENTER", "32");
        documentPart.getJaxbElement().getBody().getContent().add(p2);
        P p3 = createStyledText("目录", "bold", "LEFT", "26");
        documentPart.getJaxbElement().getBody().getContent().add(p3);

        //第3步：生成目录信息
        wmlTool.createCatalog(3);
        //第4步：生成内容
        for(String pak_id_name : dataMap.keySet()){
            String pkgName = pak_id_name.split("====")[1];
            //wmlTool.addHeading("", new HeadingStyle(1, "Heading1"));
            documentPart.addStyledParagraphOfText("Heading1", "收藏夹：" + pkgName);
            List dataList = dataMap.get(pak_id_name);
            //填充数据
            String muluStyle = "Heading2";
            fillDataToWord(dataList,suffix,exportContent,documentPart,hashMapHead,wmlTool,muluStyle);
        }
        //第5步：增加页脚
        Relationship footer_relationship = createFooterPart(wmlTool.getWmlPackage());
        createFooterReference(wmlTool.getWmlPackage(), footer_relationship);
        if(needZip){
            //第6步：输出文件
            String filePath = packPath;
            File file = new File(filePath);
            if(!file.exists()){
                file.mkdirs();
            }
            File outFile = new File(filePath + title + ".docx");
            wmlTool.getWmlPackage().save(outFile);
            srcfile.add(outFile);
        }else {//不需要打包的直接输出
            output(response,wmlTool,title);
        }
    }

    /**
     * 向word中填充数据
     * @param dataList
     * @param suffix
     * @param exportContent
     * @param documentPart
     * @param hashMapHead
     * @param wmlTool
     * @throws Exception
     */
    public static void fillDataToWord(List dataList,String suffix,String exportContent,MainDocumentPart documentPart,
                        HashMap<String,String> hashMapHead,WmlTool wmlTool,
                        String muluStyle
                        ) throws Exception{
        for(int i=0;i<dataList.size();i++){
            Map map = (Map)dataList.get(i);
            String mulu = "";
            if("微博".equals(suffix)){//微博类型的‘目录’取内容，其余类型取标题
                mulu = "正文：" + getMapValueByKey(map,"content");
            }else{
                mulu = (String) getMapValueByKey(map,"title");
            }
            if (exportContent.contains("title") || ("微博".equals(suffix) && exportContent.contains("content"))) {//标题
                documentPart.addStyledParagraphOfText(muluStyle, mulu);
            }
            if (exportContent.contains("time")) {//时间
                String strPubTime = createBoldUnboldText(hashMapHead.get("time") + ":", getMapValueByKey(map,"time"));
                documentPart.addObject(org.docx4j.XmlUtils.unmarshalString(strPubTime));
            }
            if (exportContent.contains("source")) {//来源
                String strSource = createBoldUnboldText(hashMapHead.get("source") + ":", getMapValueByKey(map,"source"));
                documentPart.addObject(org.docx4j.XmlUtils.unmarshalString(strSource));
            }
            if (exportContent.contains("author")) {//作者
                String strSource = createBoldUnboldText(hashMapHead.get("author") + ":", getMapValueByKey(map,"author"));
                documentPart.addObject(org.docx4j.XmlUtils.unmarshalString(strSource));
            }
            if (exportContent.contains("exportNum")) {//转载量
                String strSource = createBoldUnboldText(hashMapHead.get("exportNum") + ":", getMapValueByKey(map,"exportNum"));
                documentPart.addObject(org.docx4j.XmlUtils.unmarshalString(strSource));
            }
            if (exportContent.contains("url") && !"".equals(getMapValueByKey(map,"url"))) {//链接
                P.Hyperlink link = createHyperlink(documentPart, getMapValueByKey(map,"url"));
                org.docx4j.wml.P paragraph = Context.getWmlObjectFactory().createP();
                org.docx4j.wml.ObjectFactory factory = Context.getWmlObjectFactory();
                org.docx4j.wml.Text t = factory.createText();
                t.setValue(hashMapHead.get("url")+":");
                org.docx4j.wml.R run = factory.createR();
                run.getContent().add(t);
                org.docx4j.wml.RPr rpr = factory.createRPr();
                org.docx4j.wml.BooleanDefaultTrue b = new org.docx4j.wml.BooleanDefaultTrue();
                b.setVal(true);
                rpr.setB(b);
                HpsMeasure size = new HpsMeasure();
                size.setVal(new BigInteger("20"));
                rpr.setSz(size);
                rpr.setSzCs(size);
                run.setRPr(rpr);
                paragraph.getContent().add(run);
                paragraph.getContent().add(link);
                documentPart.addObject(paragraph);
            }
            if (exportContent.contains("summary")) {//摘要
                String strSummary = createBoldUnboldText(hashMapHead.get("summary") + ":", getMapValueByKey(map,"summary"));
                documentPart.addObject(org.docx4j.XmlUtils.unmarshalString(strSummary));
            }
            if (exportContent.contains("content") && !"微博".equals(suffix)) {//正文
                String strSummary = createBoldUnboldText(hashMapHead.get("content") + ":", "");
                documentPart.addObject(org.docx4j.XmlUtils.unmarshalString(strSummary));
                String content = getMapValueByKey(map,"content");
                String array[] = content.split("\n");
                for (String arr : array) {
                    if (!"".equals(arr)) {
                        wmlTool.addTextParagraph(arr);
                    }
                }
            }
        }
    }
    /**
     * 导出全景舆情新闻列表
     * @param request
     * @param response
     * @param hashMapHead 导出字段转换map
     * @param exportContent 需要导出的字段
     * @throws Exception
     */
    public static void exportNewsList(HttpServletRequest request, HttpServletResponse response,
                                      String suffix,
                                      List<Map<String,String>> resultList,
                                      HashMap<String,String> hashMapHead,
                                      String exportContent) throws Exception {
        //生成word文档,并输出
        exportNewsListWord(request,response,suffix,resultList,hashMapHead,exportContent);
    }
    /**
     * 将数据导出word文件到指定目录下
     * @param request
     * @param response
     * @param hashMapHead 导出字段转换map
     * @param exportContent 需要导出的字段
     * @throws Exception
     */
    public static void exportNewsListWord(HttpServletRequest request, HttpServletResponse response,
                                          String suffix,
                                          List<Map<String,String>> resultList,
                                          HashMap<String,String> hashMapHead,
                                          String exportContent) throws Exception {

        //第1步：加载模板文件
        WmlTool wmlTool = new WmlTool();
        //第2步：生成标题头
        MainDocumentPart documentPart = wmlTool.getWmlPackage().getMainDocumentPart();
        String title =  "舆情资讯简报"+"（"+suffix+"）";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String subtitle = "(" + df.format(new Date()) + ")";
        P p1 = createStyledText(title, "bold", "CENTER", "36");
        documentPart.getJaxbElement().getBody().getContent().add(p1);
        P p2 = createStyledText(subtitle, "bold", "CENTER", "32");
        documentPart.getJaxbElement().getBody().getContent().add(p2);
        P p3 = createStyledText("目录", "bold", "LEFT", "26");
        documentPart.getJaxbElement().getBody().getContent().add(p3);

        //第3步：生成目录信息
        wmlTool.createCatalog(1);
        //第4步：生成内容
        String muluStyle = "Heading1";
        fillDataToWord(resultList,suffix,exportContent,documentPart,hashMapHead,wmlTool,muluStyle);
        //第5步：增加页脚
        Relationship footer_relationship = createFooterPart(wmlTool.getWmlPackage());
        createFooterReference(wmlTool.getWmlPackage(), footer_relationship);
        //第6步：输出文件
        output(response,wmlTool,title);
    }
    /**
     *
     * @param response
     * @param wmlTool 导出数据模型
     * @param fileName 导出文件名
     */
    public static void output(HttpServletResponse response, WmlTool wmlTool, String fileName){
        try{
            fileName = fileName + ".doc";
            response.reset();
            fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
            response.setHeader("content-disposition", "attachment;filename=" + fileName);
            response.setContentType("application/msword");
            response.setCharacterEncoding("UTF-8");
            SaveToZipFile saver = new SaveToZipFile(wmlTool.getWmlPackage());
            saver.save(response.getOutputStream());
        }catch (Exception e){
            logger.error("ExportWordUtil.output error"+e.getMessage());
            e.printStackTrace();
        }
    }
    /**
     *  创建页脚部分并绑定到主文档
     *
     *  @return Relationship
     *  @throws Exception
     */
    private static Relationship createFooterPart(
            WordprocessingMLPackage wordMLPackage)
            throws Exception {
        FooterPart footerPart = new FooterPart();
        footerPart.setPackage(wordMLPackage);
        footerPart.setJaxbElement(createFooterWithPageNr());

        return wordMLPackage.getMainDocumentPart().addTargetPart(footerPart);
    }
    /**
     *  创建页脚页码
     *
     * @return Ftr
     */
    public static Ftr createFooterWithPageNr() {
        Ftr ftr = factory.createFtr();
        P paragraph = factory.createP();

        PPr pPr = paragraph.getPPr();
        if(pPr == null){
            pPr = factory.createPPr();
        }
        Jc jc = pPr.getJc();
        if(jc == null){
            jc = new org.docx4j.wml.Jc();
        }
        //页脚条形码所处位置
        jc.setVal(JcEnumeration.CENTER);
        pPr.setJc(jc);
        paragraph.setPPr(pPr);

        addFieldBegin(paragraph);
        addPageNumberField(paragraph);
        addFieldEnd(paragraph);

        ftr.getContent().add(paragraph);
        return ftr;
    }
    /**
     * 创建段落域开头
     * @param paragraph
     */
    private static void addFieldBegin(P paragraph) {
        R run = factory.createR();
        FldChar fldchar = factory.createFldChar();
        fldchar.setFldCharType(STFldCharType.BEGIN);
        run.getContent().add(fldchar);
        paragraph.getContent().add(run);
    }
    /**
     * 创建页码域
     * @param paragraph
     */
    private static void addPageNumberField(P paragraph) {
        R run = factory.createR();
        Text txt = new Text();
        txt.setSpace("preserve");
        txt.setValue(" PAGE   \\* MERGEFORMAT ");
        run.getContent().add(factory.createRInstrText(txt));
        paragraph.getContent().add(run);
    }

    /**
     * 创建段落域结尾
     * @param paragraph
     */
    private static void addFieldEnd(P paragraph) {
        FldChar fldcharend = factory.createFldChar();
        fldcharend.setFldCharType(STFldCharType.END);
        R run3 = factory.createR();
        run3.getContent().add(fldcharend);
        paragraph.getContent().add(run3);
    }
    /**
     * 创建页脚引用
     */
    public static void createFooterReference(
            WordprocessingMLPackage wordMLPackage,
            Relationship relationship)
            throws InvalidFormatException {

        List<SectionWrapper> sections =
                wordMLPackage.getDocumentModel().getSections();

        SectPr sectPr = sections.get(sections.size() - 1).getSectPr();
        if (sectPr == null ){
            sectPr = factory.createSectPr();
            wordMLPackage.getMainDocumentPart().addObject(sectPr);
            sections.get(sections.size() - 1).setSectPr(sectPr);
        }

        FooterReference footerReference = factory.createFooterReference();
        footerReference.setId(relationship.getId());
        footerReference.setType(HdrFtrRef.DEFAULT);
        sectPr.getEGHdrFtrReferences().add(footerReference);
    }
    /**
     *  创建具有样式的文字部分
     *  @return P
     */
    public static P createStyledText(String content, String boldStyle, String alignStyle, String fontSize){
        org.docx4j.wml.ObjectFactory factory = Context.getWmlObjectFactory();
        P p = factory.createP();
        org.docx4j.wml.Text t = factory.createText();
        t.setValue(content);
        org.docx4j.wml.R run = factory.createR();
        run.getContent().add(t);
        p.getContent().add(run);

        org.docx4j.wml.RPr rpr = factory.createRPr();
        org.docx4j.wml.BooleanDefaultTrue b = new org.docx4j.wml.BooleanDefaultTrue();
        b.setVal(true);
        //rpr.setB(b);

        HpsMeasure size = new HpsMeasure();
        size.setVal(new BigInteger(fontSize));
        rpr.setSz(size);
        rpr.setSzCs(size);
        run.setRPr(rpr);

        org.docx4j.wml.PPr ppr = factory.createPPr();
        Jc jc = ppr.getJc();
        if(jc == null){
            jc = new org.docx4j.wml.Jc();
        }

        if("LEFT".equals(alignStyle)){
            jc.setVal(JcEnumeration.LEFT);
        }else if("CENTER".equals(alignStyle)){
            jc.setVal(JcEnumeration.CENTER);
        }else if("RIGHT".equals(alignStyle)){
            jc.setVal(JcEnumeration.RIGHT);
        }
        //jc.setVal(JcEnumeration.CENTER);
        ppr.setJc(jc);

        p.setPPr( ppr );
        org.docx4j.wml.ParaRPr paraRpr = factory.createParaRPr();
        ppr.setRPr(paraRpr);
        if("bold".equals(boldStyle)){
            rpr.setB(b);
        }
        return p;
    }
    public static String createBoldUnboldText(String boldText, String unboldText){
        return "<w:p xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\">" +
                "<w:pPr><w:rPr><w:sz w:val=\"20\"/><w:szCs w:val=\"20\"/></w:rPr></w:pPr>" +
                "<w:proofErr w:type=\"spellStart\"/><w:r><w:rPr><w:b/><w:sz w:val=\"20\"/><w:szCs w:val=\"20\"/></w:rPr>" +
                "<w:t>" + boldText + "</w:t></w:r><w:r><w:rPr><w:sz w:val=\"20\"/><w:szCs w:val=\"20\"/></w:rPr>" +
                "<w:t><![CDATA[" + unboldText + "]]></w:t></w:r><w:proofErr w:type=\"spellEnd\"/></w:p>";
    }
    /**
     *  创建超链接部分
     *  @return P
     */
    public static P.Hyperlink createHyperlink(MainDocumentPart mdp, String url) {
        try {
            //注意：在生成超链接时,  需要做特殊处理，否则会导致word低版本打不开
            url = url.replaceAll("&","&amp;");
            if(url.contains("{") || url.contains("}")){
                url = java.net.URLEncoder.encode(url, "UTF-8");
            }

//            System.out.println(url);
//            url = java.net.URLEncoder.encode(url, "UTF-8");
//            url = url.replaceAll("_","");
//            url = url.replaceAll("shtml","");
//            url = url.replaceAll("html","");
//
//            url = url.replaceAll(",","");
//            url = url.replaceAll("jspa","");
//
//
//            url = url.replaceAll("-","");
//            url = url.replaceAll("htm","");
//            url = url.replaceAll("%","");
//
//            url = "";


            org.docx4j.relationships.ObjectFactory factory =
                    new org.docx4j.relationships.ObjectFactory();

            org.docx4j.relationships.Relationship rel = factory.createRelationship();
            rel.setType( Namespaces.HYPERLINK  );
            rel.setTarget(url);
            rel.setTargetMode("External");

            mdp.getRelationshipsPart().addRelationship(rel);

            String hpl = "<w:hyperlink r:id=\"" + rel.getId() + "\" xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\" " +
                    "xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\" >" +
                    "<w:r>" +
                    "<w:rPr>" +
                    "<w:rStyle w:val=\"Hyperlink\" />" +
                    "</w:rPr>" +
                    "<w:t>" + url + "</w:t>" +
                    "</w:r>" +
                    "</w:hyperlink>";
            return (P.Hyperlink) XmlUtils.unmarshalString(hpl);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 1新闻，3微博，5微信，7博客，9论坛，11公告，13研报，15二级市场
     * @param type
     * @return
     */
    public static String getElementTypeStr(int type){
        String str = "";
        switch (type){
            case 1:
                str = "新闻";
                break;
            case 3:
                str = "微博";
                break;
            case 5:
                str = "微信";
                break;
            case 7:
                str = "博客";
                break;
            case 9:
                str = "论坛";
                break;
            case 11:
                str = "公告";
                break;
            case 13:
                str = "研报";
                break;
            case 15:
                str = "二级市场";
                break;
            default:
                str = "";
                break;
        }
        return str;
    }

    /**
     * 获取map中的值 并替换特殊字符 否则会出现下载后的文件,部分word版本打不开
     * @param map
     * @param key
     * @return
     */
    public static String getMapValueByKey(Map map,String key){
        String str = "";
        if(map.get(key) != null){
            str = map.get(key).toString();
            //str = str.replaceAll("&","&amp;");
            //str = str.replaceAll("<","&lt;");
        }
        return str;
    }
}


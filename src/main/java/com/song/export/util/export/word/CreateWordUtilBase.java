package com.song.export.util.export.word;

import org.docx4j.XmlUtils;
import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.jaxb.Context;
import org.docx4j.model.structure.SectionWrapper;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.Part;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.openpackaging.parts.WordprocessingML.FooterPart;
import org.docx4j.openpackaging.parts.WordprocessingML.HeaderPart;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.openpackaging.parts.relationships.Namespaces;
import org.docx4j.relationships.Relationship;
import org.docx4j.utils.BufferUtil;
import org.docx4j.wml.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import java.io.File;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 创建word文档的基类
 */
public class CreateWordUtilBase {

    public static ObjectFactory factory = new ObjectFactory();

    /**
     * 目录设置
     * @param documentPart
     */
    public static void directorySetting(MainDocumentPart documentPart){
        org.docx4j.wml.Document wmlDocumentEl = (org.docx4j.wml.Document)documentPart.getJaxbElement();
        Body body =  wmlDocumentEl.getBody();

        P paragraphForTOC = factory.createP();
        R r = factory.createR();

        FldChar fldchar = factory.createFldChar();
        fldchar.setFldCharType(STFldCharType.BEGIN);
        fldchar.setDirty(true);
        r.getContent().add(getWrappedFldChar(fldchar));
        paragraphForTOC.getContent().add(r);

        R r1 = factory.createR();
        Text txt = new Text();
        txt.setSpace("preserve");
        txt.setValue("TOC \\o \"1-3\" \\h \\z \\u \\h");
        r.getContent().add(factory.createRInstrText(txt) );
        paragraphForTOC.getContent().add(r1);

        FldChar fldcharend = factory.createFldChar();
        fldcharend.setFldCharType(STFldCharType.END);
        R r2 = factory.createR();
        r2.getContent().add(getWrappedFldChar(fldcharend));
        paragraphForTOC.getContent().add(r2);

        body.getContent().add(paragraphForTOC);
        addPageBreak(documentPart);
    }

    public static JAXBElement getWrappedFldChar(FldChar fldchar) {
        return new JAXBElement( new QName(Namespaces.NS_WORD12, "fldChar"),
                FldChar.class, fldchar);
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
        //rpr.setB(b);

        return p;
    }

    /**
     *  创建超链接部分
     *  @return P
     */
    public static P.Hyperlink createHyperlink(MainDocumentPart mdp, String url) {
        try {
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
                    "<w:t><![CDATA[" + url +"]]></w:t>" +
                    "</w:r>" +
                    "</w:hyperlink>";

            return (P.Hyperlink) XmlUtils.unmarshalString(hpl);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String createBoldUnboldText(String boldText, String unboldText){
        return "<w:p xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\">" +
                "<w:pPr><w:rPr><w:sz w:val=\"25\"/><w:szCs w:val=\"25\"/></w:rPr></w:pPr>" +
                "<w:proofErr w:type=\"spellStart\"/><w:r><w:rPr><w:b/><w:sz w:val=\"25\"/><w:szCs w:val=\"25\"/></w:rPr>" +
                "<w:t>" + boldText + "</w:t></w:r><w:r><w:rPr><w:sz w:val=\"25\"/><w:szCs w:val=\"25\"/></w:rPr>" +
                "<w:t><![CDATA[" + unboldText + "]]></w:t></w:r><w:proofErr w:type=\"spellEnd\"/></w:p>";
    }

    /**
     *  创建页眉部分并绑定到主文档
     *
     *  @return Relationship
     *  @throws Exception
     */

    public static Relationship createHeaderPart(
            WordprocessingMLPackage wordprocessingMLPackage, HttpServletRequest request, String logoName)
            throws Exception {

        HeaderPart headerPart = new HeaderPart();
        Relationship rel =  wordprocessingMLPackage.getMainDocumentPart()
                .addTargetPart(headerPart);

        headerPart.setJaxbElement(getHdr(wordprocessingMLPackage, headerPart, request, logoName));

        return rel;
    }

    /**
     *  创建页脚部分并绑定到主文档
     *
     *  @return Relationship
     *  @throws Exception
     */
    public static Relationship createFooterPart(
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
    public static void addFieldBegin(P paragraph) {
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
    public static void addPageNumberField(P paragraph) {
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
    public static void addFieldEnd(P paragraph) {
        FldChar fldcharend = factory.createFldChar();
        fldcharend.setFldCharType(STFldCharType.END);
        R run3 = factory.createR();
        run3.getContent().add(fldcharend);
        paragraph.getContent().add(run3);
    }

    /**
     * 创建页眉引用
     */
    public static void createHeaderReference(
            WordprocessingMLPackage wordMLPackage,
            Relationship relationship)
            throws InvalidFormatException {

        List<SectionWrapper> sections = wordMLPackage.getDocumentModel().getSections();

        SectPr sectPr = sections.get(sections.size() - 1).getSectPr();
        if (sectPr==null ) {
            sectPr = factory.createSectPr();
            wordMLPackage.getMainDocumentPart().addObject(sectPr);
            sections.get(sections.size() - 1).setSectPr(sectPr);
        }

        HeaderReference headerReference = factory.createHeaderReference();
        headerReference.setId(relationship.getId());
        headerReference.setType(HdrFtrRef.DEFAULT);
        sectPr.getEGHdrFtrReferences().add(headerReference);
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
     * 获取页眉部分
     */
    public static Hdr getHdr(WordprocessingMLPackage wordMLPackage,
                             Part sourcePart, HttpServletRequest request, String logoName) throws Exception {

        Hdr hdr = factory.createHdr();
        String packPath = CreateWordUtilBase.class.getClassLoader().getResource("").getPath();
        String logoPath = packPath + "/static/picture/" +logoName;
        File file = new File(logoPath);
        java.io.InputStream is = new java.io.FileInputStream(file);

        hdr.getContent().add(
                newImage(wordMLPackage,
                        sourcePart,
                        BufferUtil.getBytesFromInputStream(is),
                        "filename", "alttext", 1, 2, 3000
                )
        );

        return hdr;

    }

    /**
     * 创建图像
     */
    public static org.docx4j.wml.P newImage( WordprocessingMLPackage wordMLPackage,
                                             Part sourcePart,
                                             byte[] bytes,
                                             String filenameHint, String altText,
                                             int id1, int id2, long cx) throws Exception {

        BinaryPartAbstractImage imagePart = BinaryPartAbstractImage.createImagePart(wordMLPackage, sourcePart, bytes);

        Inline inline = imagePart.createImageInline( filenameHint, altText,
                id1, id2, cx, false);

        org.docx4j.wml.ObjectFactory factory = Context.getWmlObjectFactory();
        org.docx4j.wml.P p = factory.createP();
        org.docx4j.wml.R run = factory.createR();
        org.docx4j.wml.R run_tab = factory.createR();
        p.getContent().add(run_tab);
        p.getContent().add(run);
        org.docx4j.wml.Drawing drawing = factory.createDrawing();
        //run.getContent().add(drawing);
        run_tab.getContent().add(drawing);
        drawing.getAnchorOrInline().add(inline);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String footer_time = df.format(new Date());

        Text text = new Text();
        Text text_tab = new Text();
        text_tab.setSpace("preserve");
        text_tab.setValue("\t\t\t\t\t\t\t\t\t\t\t\t");
        run_tab.getContent().add(text_tab);
        text.setValue(footer_time);
        run.getContent().add(text);

		/*PPr pPr = p.getPPr();
        if(pPr == null){
            pPr = factory.createPPr();
        }
        Jc jc = pPr.getJc();
        if(jc == null){
            jc = new org.docx4j.wml.Jc();
        }
        //页脚所处位置
        jc.setVal(JcEnumeration.RIGHT);
        pPr.setJc(jc);
        p.setPPr(pPr);*/

        return p;

    }

    /**
     * 创建分页符
     * @param documentPart
     */
    public static void addPageBreak(MainDocumentPart documentPart) {
        Br breakObj = new Br();
        breakObj.setType(STBrType.PAGE);

        P paragraph = factory.createP();
        paragraph.getContent().add(breakObj);
        documentPart.getJaxbElement().getBody().getContent().add(paragraph);
    }
}

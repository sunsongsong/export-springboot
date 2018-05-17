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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExportWordUtil extends ExportWordUtilBase {

    public static String content = "春税秋费何时了，成本知多少？小楼昨夜又涨疯，民工义无反顾工地中。万亿流毒今犹在，只是称谓改，问君融资几多愁，恰似众多企业向东流！\\n  全面撤离！停工停厂！\\n  前不久，又一世界级巨头公司宣布撤离中国！  这就是深圳的奥林巴斯，曾经的15000人大厂，如今只剩下1500人！\\n  奥林巴斯作为日本乃至世界精密、光学技术的代表企业之一，自19世纪80年代末进入中国。公司位于深圳市高新技术产业园区北区，占地面积10万平方米，投资总额达 2亿美元。深圳奥林巴斯最高峰拥有员工15000多人，出口额过4亿美元，是一家不折不扣的高端制造业企业。由于近年来奥林巴斯逐渐将中国订单转移到越南工厂，目前深圳工厂仅剩下1500人。  无锡尼康、苏州日东电工、深圳三星通信&hellip;&hellip;.外企上演大撤退，近年以来频频上演！  我们不禁要问了，外企当年争着抢着要来中国的外企，为什么现在却竞相出逃、停工停厂呢？\\n  1\\n  当然，外企的全面大撤退，与自身业务结构调整、市场的竞争激烈有关，但更重要的是，我们的自身优势在丧失，过去那个赚便宜钱的年代，一去不复返了！  过去，为何外企蜂拥而入？  首先，为了“GDP”高增长，为了吸引外商，我们给外企开出了“超国民待遇”，优先享受土地税收优惠政策，地方政府更是敞开怀抱、热烈欢迎，即使高污染、即使高能耗、即使高危害，只要能为政绩做出贡献，一样可以无底线迎进来。\\n  除了政策红利之外，外企来华，还可享受超级便宜的劳动力。比如一个iPod的生产， 163美元被美国拿走；26美元属于日本，大部分归功于东芝的硬盘；而中国呢？\\n  3美元而已！  大量的制造业工人在血汗工厂挥汗如雨，拿着最低的工资、干着最辛苦的工作，却又缺乏基本的社会保障。  外企能赚大钱、GDP高增长，一举两得、合作共赢！但是这背后，是我们所付出的牺牲环境、牺牲人力牺牲资源、牺牲民企发展空间的巨大代价。\\n  然而，美好的时代一去不复返了。\\n  我们的生存环境以及产业格局的需求，已经不允许我们以挤压自身的代价来让外企赚大钱，于是，外企的种种政策红利已然取消，享受不到特别优待了。\\n  2008年新《企业所得税法》及其实施条例施行后，外商投资企业和外国企业原来执行的再投资退税、特许权使用费免税和定期减免税等税收优惠政策面临取消。  除此之外，我们所依赖的人口成本优势已经丧失。\\n  今年年初，新发布的《经济蓝皮书：中国经济前景分析》指出，目前，中国制造业的劳动力成本已趋近美国。不仅人均工资水平年涨幅位列全球第一，达到了8%，东南沿海地区的人均工资水平更是远超东南亚国家，人均成本是柬埔寨的4.3倍，越南的2.7倍，印度的2倍，印尼的1.8倍。\\n  而且在房价狂飙、物价上涨的情况下，企业的生产成本更是直线上升。\\n  目前，高房价、高地价所带来的土地成本，已经成为企业的不能承受之重。美国工业用地地价仅为2万美元/英亩，相当于2万元人民币/亩，如果按照中国现在许多县城工业用地100万元/亩算，是美国的50倍。  高房价已经抬高了营商成本、炒房热更是形成了挤出效应。当四成上市公司利润，比不上深圳一套房时，既然靠炒炒房子、金融套利就可以赚大钱，要是还在费心思本着工匠精神搞制造业、冒着风险攻坚核心技术，已经显得很不合时宜。\\n  于是，我们看到，国内有头有脸的制造业企业，其实都开始搞起了地产或者金融项目，所以美的地产、海尔地产、联想地产等纷纷出现。  钱是最聪明的，它会自动流向利润丰厚的地方，资本用看似最合理却又最无情的方式在攫取着越来越小的蛋糕。\\n  比如苏州，作为媲美深圳的另一个中国制造业之都，曾经是世界五百强企业的聚集地。耐克、阿迪达斯、联建、宏晖、飞利浦、普光、华尔润、诺基亚、紫兴、希捷、及成.....个个都是声名赫赫的世界名企，在苏州曾经员工动辄上万。  然而，这些当初蜂拥而来的外企，却一个个纷纷向着东南亚打马而去。\\n  2\\n  然而，那个赚便宜钱的美好时代结束了，靠新动能增长的时代已经开启了吗？当外企上演大撤退了，中国的民营企业就能够及时补位、更好发展了吗？\\n  破产跑路！自杀身亡！  最近，企业违约潮的消息刷爆朋友圈，无数曾经身家煊赫的大佬却因为现金流断裂、企业难以续命失踪跑路，甚至走上绝路。  延伸阅读：《又一大佬自杀身亡！2018，什么才是压垮他们的最后一根稻草？》  不难发现，导致这些大佬被迫跑路的重要原因，就在于负债高企、乃至无力偿还，而民间借贷的高利，就是他们不能承受之重。\\n  问题在于，如果能从银行借到更便宜、更安全的资金，谁会去借高成本、高风险的民间高利贷呢？\\n  历来，中小企业都存在着“融资难”“融资贵”的困局。除了外企的竞争外，民企还要与有着天量贷款的国企基建竞争。由于“预算软约束”的隐性担保，国企轻易可以拿到大把的便宜钱，于是产生了“死而不僵”“产能过剩”的僵尸企业，没对实体经济干出多少实质性贡献，却反而挤占了大把的便宜资源。  而被寄予“经济增长新动力(310328,基金吧)”的民企，却在融资市场上遭到了无情的歧视。\\n  一名企业家心酸地描述了自己的贷款经历——  当他去银行咨询贷款的时候，银行的第一个问题就是：你有房产吗？相对于房产而言，公司的经营状况和项目本身根本算不了什么，即使你是乔布斯，拥有最好的手机产品，如果他在中国，依然是一毛钱也贷不到。\\n  但企业的困境远不止于如此，好不容易融到了钱，接下来，你还要面对高税费。\\n  目前，我国社保缴纳的压力已经非常之高。我国社保缴费率世界排名第一，缴费基数是邻国的4.6倍。\\n  据清华大学教授白重恩的测算，中国社会保险法定缴费之和相当于工资水平的40%，有的地区甚至达到50%;我国的社保缴费率在全球181个国家中排名第一，约为“金砖四国”其他三国平均水平的2倍，是北欧五国的3倍，是G7国家的2.8倍，是东亚邻国的4.6倍。  当我国民企的平均利润率仅仅约为10%时，如何能够在承担如此重的税负，养活这么多人的前提下，还能赚到钱呢？\\n  于是这些年来，越来越多的民营企业家要么做出与外企一样的选择，将工厂迁移至国外；要么把实业交给国家，让别人来经营实体企业，自己也加入轻资产、炒泡沫的行列之中。\\n  3\\n  企业告急！实体告急！\\n  在中美贸易战如火如荼之时，我们的实体却发出了这样的危险信号，一边是外企的突然袭击、全面撤离，一边是民企的破产倒闭、违约清算。在大国博弈的激烈交锋之外，这些，才是最能直接影响每一个人的头等大事！\\n  毕竟，一家企业背后就是千千万万人的就业岗位，就是一个地方的税收来源、GDP增长来源。  当越来越多的企业出走、越来越多的资本撤退，必然会有越来越多的人失业、越来越多的地方陷入萧条；当实体经济日渐萎缩、当企业难以承受赋税之重，地方想要提振经济、想要取得收入，势必会重新转向依赖房地产、重吹金融泡沫的老路。  海南的变节，或许只是一个开始。  延伸阅读：《一夜变天！海南楼市大逆转，谁来叫醒装睡的中国楼市？》  也许，中国经历了十多年的房地产繁荣之后，真正的灰犀牛已经到来。\\n主业血流不止，“空壳化”的乐视网(300104,股吧)将走向何方  猜你喜欢  \\n\\n\\n一个金融掮客的自述：大型企业是如何倒闭的？资本\\n  老周大名周明，小名“周扒皮”，资本圈的老人，不过性格很独，从来独来独往，他不喜欢那种所谓的小弟以及所...  6小时前\\n\\n原创\\n\\n\\n资本为何不欢迎污点艺人？资本污点艺人\\n  投资最终要论回报率，污点艺人的负面新闻给投资方带来的经济损失显而易见，这才是让资方远离污点艺人的真正...  6天前\\n\\n\\n\\n\\n摩拜兴衰史：成也资本，败也资本资本摩拜\\n  在企业家眼里，企业就像自己的孩子一样，希望他健康成长，走得更长远。而对于资本来说，投资企业就像养猪，...  2018-05-08\\n\\n\\n\\n\\n史上最短复出，资本不欢迎污点艺人？资本艺人\\n  实际上，艺人的“八卦”虽然是人们津津乐道的饭后谈资，但他们背后的资本们却没那么轻松。  2018-05-07\\n\\n  精彩阅读原创资讯投资人物专栏  \\n\\n\\n对话孙宏斌：详解乐视困局和辞任乐视网董事长之谜投资乐视孙宏斌\\n  孙宏斌强调，自己提前卸任董事长，现在也成了一个普通的乐视网投资者。今天就以一个比较了解乐视情况的投资...  2018-03-26\\n\\n\\n\\n\\n如何量产新零售独角兽？五星控股汪建国的三大要诀新零售汪建国\\n  在新零售领域，五星控股董事长汪建国“量产”了五家新锐企业。  2018-03-26\\n\\n\\n\\n\\n郭树清兼任央行党委书记，金融混业监管格局进一步清晰央行郭树清\\n  在新的“一行两会”金融监管架构中，央行须发挥强势作用，业界当时就有预期，或有进一步的人事后续组合安排...  2018-03-26\\n\\n\\n\\n\\n利润大爆发之后，2018年房企面临市场下滑和资金链风险利润资金链\\n  进入2018年，政府对房地产市场调控不放松，楼市面临市场下滑和资金链的双重风险。  2018-03-26\\n\\n\\n\\n\\n区块链泡沫中掘金：巨头加码、 连续创业者入场背后区块链应用场景\\n  在技术层面上真正有强说服力的区块链公司很少，更多的是，将创新的区块链技术与已有应用场景相结合。  2018-03-26\\n\\n\\n\\n\\n特朗普挑起贸易战，苹果谷歌IBM高管在京大谈中美合作谷歌苹果特朗普\\n  “市场有自己的声音，我们应该去听听这样的声音，全世界都需要一个更强大的中国和美国，都不想看到中美之间...  2018-03-26\\n\\n\\n\\n\\n闷声发大财的全民K歌腾讯全民K歌\\n  靠腾讯左手流量，右手版权扶起来的全民K歌，已经开始反哺腾讯。  2018-03-26\\n\\n\\n\\n\\n烫手的“三体”电影三体科幻\\n  回过头来看，《三体》原作者刘慈欣对作品的认识是最正确的，要做“三体”的影视改编，确实太难。目前中国也...  2018-03-26\\n\\n\\n\\n\\n沃尔玛弃用支付宝涉嫌垄断不公平？都别入戏太深沃尔玛支付宝\\n  在阿里巴巴的新零售里，能不能用微信支付？在腾讯、京东的新零售里，能不能用支付宝？  2018-03-26\\n\\n\\n\\n\\n从野蛮生长到精耕细作：共享办公的“聚”与“变”优客工场共享空间\\n  共享空间这个新兴领域尽管只有三年发展史，但已经从野蛮生长迅速走入整合升级阶段。2  2018-03-26\\n\\n      \\n       作者：蜜姐\\n    （责任编辑: 和讯网站）\\n\\n";

    public static Map<String,String> map = new HashMap<String,String>(){{
        put("content",content);
    }};


    public static List<ExportBean> initData(ExportBean bean){
        List<ExportBean> resultList = new ArrayList<>();
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
                    content,
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
    public static void createWord(HttpServletRequest request, HttpServletResponse response,List<ExportBean> dataList) throws Exception {
        //创建文档
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
        List<ExportBean> dataList = initData(new ExportBean());
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
                /*newsContent = newsContent.replaceAll("\\<(o|/o)(.*?)\\>", "");
                newsContent = new String(newsContent.getBytes("GB2312"), "GBK");
                newsContent = newsContent.replaceAll("\\?", " ");
                //String[] strArray = newsContent.split("  ");
                String[] strArray = newsContent.split("\\s\\s\\s*");
                for (int j = 0; j < strArray.length; j++) {
                    if ("".equals(strArray[j])) {
                        continue;
                    }
                    documentPart.addParagraphOfText(strArray[j]);
                }*/
                //System.out.println(newsContent);
                System.out.println("newsContent.contains(\"\\n\")="+newsContent.contains("\\n"));
                String array[] = newsContent.split("\\n");
                for (String arr : array) {
                    if (!"".equals(arr) && !" ".equals(arr) ) {
                        //wmlTool.addTextParagraph(arr);
                        arr = arr.replaceAll(" ","");
                        arr = arr.replaceAll("\\s*", "");
                        arr = arr.trim();
                        arr = arr.replaceAll(" +","");
                        String strSource = createContentText("", arr);
                        documentPart.addObject(org.docx4j.XmlUtils.unmarshalString(strSource));
                    }
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

    public static String createContentText(String boldText, String unboldText){
        return  "<w:p xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\">" +
                "<w:pPr><w:rPr><w:sz w:val=\"22\"/><w:szCs w:val=\"22\"/></w:rPr></w:pPr>" +
                "<w:proofErr w:type=\"spellStart\"/><w:r><w:rPr><w:b/><w:sz w:val=\"22\"/><w:szCs w:val=\"22\"/></w:rPr>" +
                "<w:t>" + boldText + "</w:t></w:r><w:r><w:tab/><w:rPr><w:sz w:val=\"22\"/><w:szCs w:val=\"22\"/></w:rPr>" +
                "<w:t><![CDATA[" + unboldText + "]]></w:t></w:r><w:proofErr w:type=\"spellEnd\"/></w:p>";
    }
}

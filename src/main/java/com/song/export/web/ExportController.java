package com.song.export.web;

import com.song.export.config.HttpClient;
import com.song.export.model.common.JsonResult;
import com.song.export.util.export.ExportBean;
import com.song.export.util.export.execl.ExportExeclUtil;
import com.song.export.util.export.word.ExportWordUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/export", produces = "application/json;charset=utf-8")
public class ExportController {

    Logger logger = LoggerFactory.getLogger(ExportController.class);

    @RequestMapping(value = "/word")
    public String export(HttpServletRequest request, HttpServletResponse response){
        logger.info("ExportController.export invoked");
        try {

            //api url地址
            String url = "http://172.22.11.38:8002/base/idol/getContent?url=http%253A%252F%252Fm.hexun.com%252Fbschool%252F2018-05-15%252F193022709.html";
            //发送http请求并返回结果
            HttpClient httpClient = new HttpClient();
//            Map map = httpClient.get(url);
            Map map = (Map)httpClient.get(url,Map.class);
            List list = (List)map.get("data");
            Map map1 = (Map)list.get(0);
            List list1 = (List)map1.get("__KEY_DATAS");
            Map map2 = (Map)list1.get(0);

            String content = map2.get("drecontent").toString();
            String title = map2.get("title").toString();
            String time = map2.get("opertime").toString();
            String newsUrl = map2.get("reference").toString();
            ExportBean bean = new ExportBean(title,time,content,newsUrl);
            List<ExportBean> resultList = new ArrayList<>();
            resultList.add(bean);
            ExportWordUtil.createWord(request,response,resultList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return JsonResult.errorMsgResult("导出word失败");
        }
        logger.info("ExportController.export OK");
        return JsonResult.okResult("导出word成功");
    }

    @RequestMapping(value = "/words")
    public String exports(HttpServletRequest request, HttpServletResponse response){
        logger.info("ExportController.export invoked");
        try {

            //api url地址
            //String url = "http://localhost:8080/export/test";
            String url = "http://172.22.11.38:8002/base/idol/getExportList";

            Map<String, String> params= new HashMap<>();
            params.put("cate", "neg");
            params.put("orgvalue", "10100002206");
            params.put("src", "");
            params.put("num", "1000");
            params.put("e_date", "2018-05-14");
            params.put("sort", "publishdate:decreasing");
            params.put("page", "1");
            params.put("s_date", "2018-04-15");
            params.put("db", "vnews");


            //发送http请求并返回结果
            HttpClient httpClient = new HttpClient();
            //Map map = httpClient.post(url,params);
            Map map = (Map)httpClient.post(url,params,Map.class);

            //封装返回结果
            List list = (List)map.get("data");
            Map map1 = (Map)list.get(0);
            List<Map> dataList = (List)map1.get("__KEY_DATAS");
            List<ExportBean> resultList = new ArrayList<>();
            for(Map m : dataList){
                String content = m.get("drecontent").toString();
                String title = m.get("title").toString();
                String time = m.get("publishdate").toString();
                String newsUrl = m.get("reference").toString();
                ExportBean bean = new ExportBean(title,time,content,newsUrl);
                resultList.add(bean);
            }
            ExportWordUtil.createWord(request,response,resultList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return JsonResult.errorMsgResult("导出word失败");
        }
        logger.info("ExportController.export OK");
        return JsonResult.okResult("导出word成功");
    }

    @RequestMapping(value = "/wordZip")
    public String wordZip(HttpServletRequest request, HttpServletResponse response){
        logger.info("ExportController.wordZip invoked");
        try {
            ExportWordUtil.createWordZip(request,response);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return JsonResult.errorMsgResult("导出wordZip失败");
        }
        return JsonResult.okResult("导出wordZip成功");
    }

    @RequestMapping(value = "/execl")
    public String execl(HttpServletRequest request, HttpServletResponse response){
        logger.info("ExportController.execl invoked");
        try {
            ExportExeclUtil.createExecl(response);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return JsonResult.errorMsgResult("导出execl失败");
        }
        return JsonResult.okResult("导出execl成功");
    }

    @RequestMapping(value = "/test")
    public String test(@RequestBody Map map){
        System.out.println("-------------");
        System.out.println(JSONObject.fromObject(map));
        return JsonResult.okResult("test");
    }
}

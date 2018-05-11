package com.song.export.web;

import com.song.export.model.common.JsonResult;
import com.song.export.util.export.execl.ExportExeclUtil;
import com.song.export.util.export.word.ExportWordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/export", produces = "application/json;charset=utf-8")
public class ExportController {

    Logger logger = LoggerFactory.getLogger(ExportController.class);

    @RequestMapping(value = "/word")
    public String export(HttpServletRequest request, HttpServletResponse response){
        logger.info("ExportController.export invoked");
        try {
            ExportWordUtil.createWord(request,response);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return JsonResult.errorMsgResult("导出word失败");
        }
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

}

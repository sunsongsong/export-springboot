package com.song.export.web;

import com.song.export.model.common.JsonResult;
import com.song.export.util.export.word.CreateWordUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/export", produces = "application/json;charset=utf-8")
public class ExportController {

    @RequestMapping(value = "/word")
    public String export(HttpServletRequest request, HttpServletResponse response){
        try {
            CreateWordUtil.createWord(request,response);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.errorResult(-1,"导出word失败");
        }
        return JsonResult.errorMsgResult("导出成功");
    }
}

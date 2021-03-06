package com.song.export.web;

import com.song.export.annotation.SongCacheAnnotation;
import com.song.export.model.common.JsonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/cache", produces = "application/json;charset=utf-8")
public class CacheController {

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    @SongCacheAnnotation(key = "testKey",value = "id_1",time = 60L,cache = true)
    public String test() {
        return JsonResult.okResult("test");
    }



}

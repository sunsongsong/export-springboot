package com.song.export.web;

import com.song.export.annotation.AspectCacheAnnotation;
import com.song.export.model.common.JsonResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/aspect", produces = "application/json;charset=utf-8")
public class AspectController {
    /**
     * 获取json返回值
     * @return
     */
    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String test(String arg) {
        //Integer i = null;
        //i.intValue();
        return JsonResult.okResult("AspectTest:arg"+arg);
    }


    /**
     * 获取json返回值
     * @return
     */
    @RequestMapping(value = "/cache",method = RequestMethod.GET)
    @AspectCacheAnnotation()//不写key 就是方法名+参数为redis的key
    public String cache(String arg) {
        String str = "cache:arg:" + arg;
        return JsonResult.okResult(str);
    }

    /**
     * 获取json返回值
     * @return
     */
    @RequestMapping(value = "/cachePost",method = RequestMethod.POST)
    @AspectCacheAnnotation()//不写key 就是方法名+参数为redis的key
    public String cachePost(@RequestBody Map params) {
        String str = "cache:arg:" + params.toString();
        return JsonResult.okResult(str);
    }
}

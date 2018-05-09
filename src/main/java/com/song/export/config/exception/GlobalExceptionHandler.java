package com.song.export.config.exception;


import com.song.export.model.common.JsonResult;
import net.sf.json.JSONObject;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


//@ControllerAdvice(annotations=RestController.class)
//@ControllerAdvice(basePackages={"com.xxx","com.ooo"})
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    //    @ExceptionHandler(value={RuntimeException.class,MyRuntimeException.class})
    //    @ExceptionHandler//处理所有异常
    public String exceptionHandler(Exception e,Model model) {
        model.addAttribute("exception", e.getMessage());
        e.printStackTrace();
        return "error";
    }
}

/*
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    //    @ExceptionHandler(value={RuntimeException.class,MyRuntimeException.class})
    //    @ExceptionHandler//处理所有异常
    @ResponseBody //在返回自定义相应类的情况下必须有，这是@ControllerAdvice注解的规定
    public String exceptionHandler(Exception e) {
        e.printStackTrace();
        return JsonResult.errorResult(-1,e.getMessage());
    }
}*/

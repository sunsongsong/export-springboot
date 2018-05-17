package com.song.export.util.string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 自己得String工具类
 */
public class SongStringUtil {

    /**
     * 验证字符串中是否含着中文
     * @param str
     * @return
     */
    public static Boolean HasChineseChar(String str){
        String pattern = "[\u4e00-\u9fa5]+";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
        return m.find();
    }

    public static void main(String[] args) {
        String str = "視傳";
        System.out.println(HasChineseChar(str));
    }
}

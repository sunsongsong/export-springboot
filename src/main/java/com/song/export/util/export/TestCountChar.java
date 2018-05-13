package com.song.export.util.export;

import io.swagger.models.auth.In;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 统计文本含有字符串的数量
 */
public class TestCountChar {

    public static void main(String[] args)
    {
        /** 要读取的文件路径，可以自己修改成自己的路径 */
        String url = "C:\\Users\\Administrator\\Desktop\\问题链接.txt";
        /**
         * 读取文件数据
         */
        File file = new File(url);
        if (!file.exists() || file.isDirectory())
        {
            System.out.println("文件不存在！");
            return;
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
                sb.append(temp);
                temp = br.readLine();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        /** 读取的文件内容 */
        String info = sb.toString();
        int num = 0;
        Map<String,Integer> countMap = new TreeMap(new MyComparator());

        for (int i = 0; i < info.length(); i++)
        {
            String str = info.charAt(i)+"";
            if(countMap.containsKey(str)){
                countMap.put(str,countMap.get(str)+1);
            }else{
                countMap.put(str,1);
            }
        }
        System.out.println(JSONObject.fromObject(countMap));
    }

    static class MyComparator implements Comparator {

        @Override
        public int compare(Object o1, Object o2) {
            // TODO Auto-generated method stub
            String param1 = (String)o1;
            String param2 = (String)o2;
            return param1.compareTo(param2);
        }

    }

}

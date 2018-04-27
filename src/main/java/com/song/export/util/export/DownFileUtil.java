package com.song.export.util.export;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

public class DownFileUtil {
    /**
     * 提供excel压缩后，下载
     * @param response 请求
     * @param serverPath 文件路径
     * @param str 文件名
     */
    public static void downFile(HttpServletResponse response, String serverPath, String fileName) {
        try {
            String path = serverPath + fileName;
            File file = new File(path);
            if (file.exists()) {
                fileName = URLEncoder.encode(fileName, "UTF-8");
                InputStream ins = new FileInputStream(path);
                BufferedInputStream bins = new BufferedInputStream(ins);// 放到缓冲流里面
                OutputStream outs = response.getOutputStream();// 获取文件输出IO流
                BufferedOutputStream bouts = new BufferedOutputStream(outs);
                response.setContentType("application/x-download");// 设置response内容的类型
                response.setHeader(
                        "Content-disposition",
                        "attachment;filename="
                                + fileName);// 设置头部信息
                int bytesRead = 0;
                byte[] buffer = new byte[8192];
                //开始向网络传输文件流
                while ((bytesRead = bins.read(buffer, 0, 8192)) != -1) {
                    bouts.write(buffer, 0, bytesRead);
                }
                bouts.flush();// 这里一定要调用flush()方法
                ins.close();
                bins.close();
                outs.close();
                bouts.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

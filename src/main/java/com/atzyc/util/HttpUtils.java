package com.atzyc.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {
    //获取连接对象
    public static HttpURLConnection getHttpURLConnection(String url) throws IOException {
        URL HttUrl = new URL(url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) HttUrl.openConnection();
        //向网站服务器发送标识信息
        httpURLConnection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/14.0.835.163 Safari/535.1");
        return httpURLConnection;
    }
    //获取下载文件文件名
    public static String getHttpFileName(String url){
        int index = url.lastIndexOf("/");
        return url.substring(index+1);
    }
}

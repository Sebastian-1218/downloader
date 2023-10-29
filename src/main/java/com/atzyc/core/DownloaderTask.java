package com.atzyc.core;

import com.atzyc.constant.Constant;
import com.atzyc.util.HttpUtils;
import com.atzyc.util.LogUtils;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.util.concurrent.Callable;

public class DownloaderTask implements Callable {

    private String url;

    private long startPos;

    private long endPos;

    private int part;

    public DownloaderTask(String url, long startPos, long endPos, int part) {
        this.url = url;
        this.startPos = startPos;
        this.endPos = endPos;
        this.part = part;
    }

    @Override
    public Object call() throws Exception {
        //获取文件名
        String httpFileName = HttpUtils.getHttpFileName(url);
        //分块的文件名
        httpFileName = httpFileName+".temp"+part;
        //下载路径
        httpFileName = Constant.PATH + httpFileName;
//获取分块下载的链接
        HttpURLConnection httpURLConnection = HttpUtils.getHttpURLConnection(url, startPos, endPos);
        try(
                InputStream input = httpURLConnection.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(input);
                RandomAccessFile accessFile = new RandomAccessFile(httpFileName,"rw")
        ){
            byte[] buffer = new byte[Constant.BYTE_SIZE];
            int len = -1;
            while ((len= bis.read(buffer))!=-1){
                accessFile.write(buffer,0,len);
            }
        }catch (FileNotFoundException e){
            LogUtils.error("下载文件不存在{}",url);
            return false;
        }catch (Exception e){
            LogUtils.error("下载出现异常");
            return false;
        }finally {
            httpURLConnection.disconnect();
        }
        return true;
    }
}

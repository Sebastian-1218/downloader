package com.atzyc.core;

import com.atzyc.constant.Constant;
import com.atzyc.util.HttpUtils;
import com.atzyc.util.LogUtils;
import com.atzyc.util.fileUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Downloader {
    public ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    public void download(String url) {
        String httpFileName = HttpUtils.getHttpFileName(url);
        //文件下载路径
        httpFileName = Constant.PATH+httpFileName;
        //获取本地文件大小
        long localFIleLength = fileUtils.getFileContentLength(httpFileName);
        //获取连接对象
        HttpURLConnection httpURLConnection = null;
        DownloadInfoThread downloadInfoThread = null;
        try {
            httpURLConnection = HttpUtils.getHttpURLConnection(url);
            int contentLength = httpURLConnection.getContentLength();
            if(localFIleLength>=contentLength){
                LogUtils.info("{},已下载完毕，无需重新下载",httpFileName);
                return;
            }
            //创建获取下载信息的任务对象
            downloadInfoThread = new DownloadInfoThread(contentLength);
            //将任务交给线程执行，每隔疫苗执行一次
            scheduledExecutorService.scheduleAtFixedRate(downloadInfoThread,1,1, TimeUnit.SECONDS);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try(InputStream input = httpURLConnection.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(input);
            FileOutputStream fos = new FileOutputStream(httpFileName);
            //内部是8192的数组
            BufferedOutputStream bos = new BufferedOutputStream(fos)
        ){
           int len = -1;
           byte[] buffer = new byte[Constant.BYTE_SIZE];
           while((len = bis.read(buffer))!=-1){
               downloadInfoThread.downSize +=len;
               bos.write(buffer,0,len);
           }
        } catch (FileNotFoundException e) {
//            System.out.println("下载文件不存咋");
            LogUtils.error("下载文件不存在:{}",url);
        }catch (Exception e){
//            System.out.println("下载失败");
            LogUtils.error("下载失败");
        }finally {
            System.out.print("\r");
            System.out.print("下载完成");
            //关闭连接对象
            if(httpURLConnection!=null){
                httpURLConnection.disconnect();
            }
            scheduledExecutorService.shutdownNow();
        }
    }
}

package com.atzyc.core;

import com.atzyc.constant.Constant;

public class DownloadInfoThread implements Runnable {
    //下载文件总大小
    private long httpFileContentLength;
    //本地已下载文件的大小，
    public double finishedSize;
    //前一次下载的大小
    public double prevSize;
    //本次累计下载的大小
    public volatile double downSize;
    public DownloadInfoThread(int httpFileContentLength){
        this.httpFileContentLength = httpFileContentLength;
    }
    @Override
    public void run() {
        String httpFileSize = String.format("%.2f", httpFileContentLength / Constant.MB);
        //计算每一秒下载速度 kb
        int speed = (int)((downSize-prevSize) / 1024d);
        prevSize = downSize;

        //剩余文件大小
        double remainSize = httpFileContentLength - finishedSize - downSize;
        //估算剩余时间
        String remainTime = String.format("%.1f", remainSize / 1024d / speed);
        if("Infinity".equalsIgnoreCase(remainTime)){
            remainTime="-";
        }
        //一下再大小
        String currentFileSize = String.format("%.2f", (downSize - finishedSize) / Constant.MB);

        String downInfo = String.format("已下载 %smb/%smb,速度%skb/s,剩余时间%ss",
                currentFileSize, httpFileSize, speed, remainTime);
        System.out.print("\r");
        System.out.println(downInfo);
    }
}

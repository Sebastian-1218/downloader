package com.atzyc;


import com.atzyc.core.Downloader;
import com.atzyc.util.LogUtils;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
       //下载地址
        String url = null;
        if(args == null || args.length==0){
            for (;;){
//                System.out.println("请输入下载链接");
                LogUtils.info("请输入下载地址");
                Scanner sc = new Scanner(System.in);
                url = sc.next();
                if(url!=null){
                    break;
                }
            }
        }else {
            //不做校验地址
            url = args[0];
        }
        Downloader downloader = new Downloader();
        downloader.download(url);
    }
}
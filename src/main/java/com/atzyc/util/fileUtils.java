package com.atzyc.util;

import java.io.File;

public class fileUtils {
    //获取本地文件大小
    public static long getFileContentLength(String path){
        File file = new File(path);
        return file.exists() && file.isFile()?file.length():0;
    }
}

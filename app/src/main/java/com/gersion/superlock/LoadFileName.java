package com.gersion.superlock;

import java.io.File;

public class LoadFileName {
    public static void main(String[] args){
        String path = "C:\\Users\\gersy\\Desktop\\新建文件夹 (2)";
        File file = new File(path);
        StringBuilder sb = new StringBuilder();
        if (file.exists()){
            File[] files = file.listFiles();
            for (File file1 : files) {
                String name = file1.getName();
                sb.append(name.substring(0,name.indexOf("."))).append(",");
            }
        }
        System.out.print(sb.toString());
    }

}

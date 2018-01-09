package com.gersion.superlock.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Utils {
    
    public static String encode(String pwd){
        String str = "";
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] digest = md.digest(pwd.getBytes());
            for (byte b : digest) {
                String a = Integer.toHexString(b&0xff);
                if(a.length()==1){
                    a = "0"+a;
                }
                str = str + a;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return str;
    }
    
    public static  String encodeTimes(String pwd) {
        return encodeWithTimes(pwd,100);
    }

    public static  String encodeWithTimes(String pwd, int times) {
        String str = encode(pwd);
        for (int i = 0; i < times; i++) {
            str = encode(str);
            if(i%2==0){
                str = str+str;
            }
        }
        return str;
    }
}
  
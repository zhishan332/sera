package com.sera.helper;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;


/**
 * md5加密，不可逆
 *
 * @author wangqing
 * @since 1.0.0
 */
@Component
public class MD5Helper {


    public static void main(String[] args) {
        MD5Helper md5 = new MD5Helper();
        String str = md5.string2MD5("superman" + "_" + "!passion&%$~11235<>?" + "_" + "zhishan99");
        System.out.println(str);
    }

    public  String string2MD5(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
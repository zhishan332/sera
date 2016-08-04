package com.sera.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具
 * Created by wangqing on 16/8/3.
 */
public class CommonUtils {


    /**
     * 判断是否是手机号
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNo(String mobiles) {
        Pattern p = Pattern.compile("^(1)\\d{10}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 判断是否是邮箱
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (null == email || email.equals("")) return false;
        boolean flag;
        try {
            String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }


    public static void main(String[] args) {
//        System.out.println(CommonUtils.isMobileNO("18501378993"));
//        System.out.println(CommonUtils.isMobileNO("28501378993"));
//        System.out.println(CommonUtils.isMobileNO("185013789933"));
//        System.out.println(CommonUtils.isMobileNO("1850137899332"));
//        System.out.println(CommonUtils.isMobileNO("13401854993"));

        System.out.println(CommonUtils.isEmail("13401854993@qq.com"));
        System.out.println(CommonUtils.isEmail("zhishan99@163com"));
    }
}

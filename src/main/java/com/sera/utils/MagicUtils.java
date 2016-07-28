package com.sera.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 一些特殊...
 * Created by wangqing on 16/7/28.
 */
public class MagicUtils {

    public static long getRunDay() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d1 = df.parse("2013-10-24");
            Date d2 = new Date();
            long diff = d2.getTime() - d1.getTime();

            return diff / (1000 * 60 * 60 * 24);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 1024;
    }


    public static void main(String[] args) {
        System.out.println(MagicUtils.getRunDay());
    }
}

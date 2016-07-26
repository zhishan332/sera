package com.sera.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 操作时间的工具类
 *
 * @author wangqing
 * @since 14-11-20 下午1:56
 */
public class TimeUtils {
    private static final Logger log = LoggerFactory.getLogger(TimeUtils.class);
    /**
     * 获取unix时间戳
     *
     * @return unix时间戳类似“1416463120”
     */
    public static long getUnixTime() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 计算耗时
     *
     * @param begTime 开始时间
     * @param endTime 结束时间
     * @return 耗时单位秒
     */
    public static long getCostTime(long begTime, long endTime) {
        return endTime - begTime;
    }

    /**
     * 根据时间字符串，获取unix时间戳
     *
     * @param date 要求格式2014-10-10
     * @return unix时间戳类似“1416463120”
     */
    public static long getUnixTime(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dt;
        try {
            dt = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            return 0;
        }
        if (dt != null) {
            return dt.getTime() / 1000;
        }
        return 0;
    }

    /**
     * 根据时间字符串，获取unix时间戳
     *
     * @param formater yyyy-MM-dd HH:mm:ss组合
     * @return unix时间戳类似“1416463120”
     */
    public static long getUnixTime(String date, String formater) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formater);
        Date dt;
        try {
            dt = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            return 0;
        }
        if (dt != null) {
            return dt.getTime() / 1000;
        }
        return 0;
    }

    /**
     * 根据指定的时间进行判定并得到时间
     *
     * @param time 给定LONG类型时间
     * @return 时间
     */
    public static long getUnixTime(Long time) {
        if (time == null || time <= 0) return getUnixTime();
        return time;
    }

    /**
     * 根据给定时间戳获取格式化的字符串
     *
     * @param unixTime 时间戳
     * @return yyyy-MM-dd HH:mm:ss组合
     */
    public static String getFormatTime(long unixTime) {
        if(unixTime<=0) unixTime=getUnixTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(new Date(unixTime * 1000));
    }

    /**
     * 根据给定时间戳获取格式化的字符串
     *
     * @param unixTime 时间戳
     * @return formater组合
     */
    public static String getFormatTime(long unixTime,String formater) {
        if(unixTime<=0) unixTime=getUnixTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formater);
        return simpleDateFormat.format(new Date(unixTime * 1000));
    }

    static  String[] CnFormatList={
            "yyyy-MM-dd HH:mm:ss",
            "yyyy/MM/dd HH:mm:ss",
            "yyyy_MM_dd HH:mm:ss",
            "yyyy年MM月dd日 HH:mm:ss",
            "yyyy.MM.dd HH:mm:ss",
            "yyyy-M-d HH:mm:ss",
            "yy-MM-dd HH:mm:ss",
            "yyyy-MM-dd HH:mm",
            "yyyy-M-d HH:mm",
            "yyyy.MM.dd HH:mm",
            "yyyy年MM月dd日 HH:mm",
            "yyyy/MM/dd HH:mm",
            "yyyy_MM_dd HH:mm",
            "yy-MM-dd HH:mm",
            "yyyy-M-d",
            "yyyy-MM-dd",
            "yyyy.MM.dd",
            "yyyy年MM月dd",
            "yyyy/MM/dd",
            "yyyy_MM_dd",
            "yy-MM-dd"
            };

    static String[] EnFormatList={
            "EEE MMM dd HH:mm:ss Z yyyy",
            "EEE MMM dd HH:mm:ss yyyy",
            "MMM dd HH:mm:ss Z yyyy",
            "EEE MMM dd HH:mm Z yyyy",
            "MMM dd HH:mm Z yyyy",
            "EEE MMM dd Z yyyy",
            "MMM dd Z yyyy",
            "MMM dd yyyy",
            "MMM dd,yyyy",
            "MMM dd.yyyy"
    };
    public static long getTimeByStr(String str) {
        if(str==null) return TimeUtils.getUnixTime();
        Date date=null;
        for(String ftr:CnFormatList){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(ftr);
            try {
                date=simpleDateFormat.parse(str);
                log.info("时间格式化成功，使用格式："+ftr+"，当前日期字符串："+str);
                break;
            } catch (ParseException ignored) {

            }
        }
        //分析英文日期
        if(date==null){
            for(String ftr:EnFormatList){
                SimpleDateFormat sdf = new SimpleDateFormat(ftr, Locale.US);
                try {
                    date=sdf.parse(str);
                    log.info("英文时间格式化成功，使用格式："+ftr+"，当前日期字符串："+str);
                    break;
                } catch (ParseException ignored) {
                }
            }
        }
        if(date==null){
            return TimeUtils.getUnixTime();
        }else{
            return date.getTime() / 1000;
        }
    }

    public static void main(String[] args) throws ParseException {
//        System.out.println(TimeUtils.getTimeByStr("2015-1-19 8:45:48"));
//        System.out.println(TimeUtils.getFormatTime(1421628348));

        String ttstr=TimeUtils.getFormatTime(1422797045);
        System.out.println(ttstr);
//        long dd=System.currentTimeMillis()/1000;
//        System.out.println(TimeUtils.getUnixTime());
////        System.out.println(TimeUtils.getTimeByStr("2015-1-23 10:52:48"));
//        long dsf=TimeUtils.getTimeByStr("2015-1-23 10:52:48");
//        System.out.println(TimeUtils.getFormatTime(dsf));
//        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-M-d HH:mm:ss");
//        Date date=sdf.parse("2015-1-23 10:52:48");
//        System.out.println(date);
//        System.out.println(TimeUtils.getFormatTime(date.getTime()/1000));
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.M.d");
//        System.out.println(simpleDateFormat.parse("2014.1.1").getTime());
//
//        simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
//        System.out.println(simpleDateFormat.parse("14.1.1").getTime());
//
//        simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
//        System.out.println(simpleDateFormat.parse("2014.11.21").getTime());
    }
}

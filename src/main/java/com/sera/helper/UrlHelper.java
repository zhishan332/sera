package com.sera.helper;

import com.sera.utils.WebContent;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * url帮助
 * Created by wangqing on 16/7/12.
 */
@Component
public class UrlHelper {

    private final static Logger logger = LoggerFactory.getLogger(UrlHelper.class);

    /**
     * 判断一个URL是否有效
     * <p>
     * 只有明确的访问到url并获取了响应码才会返回false
     *
     * @param urlString url
     * @return 有效返回true
     */
    public boolean isValid(String urlString) {
        URL url = null;
        try {
            url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            // 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在
            // http正文内，因此需要设为true, 默认情况下是false;
            urlConnection.setDoOutput(true);
            // 设置是否从httpUrlConnection读入，默认情况下是true;
            urlConnection.setDoInput(true);
            // Post 请求不能使用缓存
            urlConnection.setUseCaches(false);
            //：设置连接主机超时（单位：毫秒）
            urlConnection.setConnectTimeout(4000);
            //设置从主机读取数据超时（单位：毫秒）
            urlConnection.setReadTimeout(4000);

            int respCode = urlConnection.getResponseCode();

            return respCode >= 200 && respCode < 400;
        } catch (UnknownHostException uex) {
            return false;
        } catch (Exception e) {
            //可能应为是墙外的域名暂时无法访问,不能设置为false
            logger.warn("分析url是否有效出现异常", e);
        }
        return true;
    }


    public String getFormatUrlTitle(String urlString) {
        Document doc = null;
        String resTitle = null;
        try {
            doc = Jsoup.connect(urlString).userAgent("Mozilla").timeout(6000).get();

            if (doc != null) {
                resTitle = StringUtils.stripToEmpty(doc.title());
//                if (StringUtils.isBlank(resTitle)) {
//                    WebContent webContent = new WebContent();
//                    resTitle = webContent.getTitle(doc.html());
//                }
            }

        } catch (Exception e) {
            logger.error("解析url标题失败", e);
        }
        if (StringUtils.isBlank(resTitle)) {
            return getHost(urlString);
        } else return resTitle;
    }

    private static String getHost(String url) {
        if (url == null || url.trim().equals("")) {
            return "";
        }
        String host = "";
        Pattern p = Pattern.compile("(?<=//|)((\\w)+\\.)+\\w+");
        Matcher matcher = p.matcher(url);
        if (matcher.find()) {
            host = matcher.group();
        }
        return host;
    }
}

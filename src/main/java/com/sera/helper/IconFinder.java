package com.sera.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 获取Icon
 * Created by wangqing on 16/7/12.
 */
@Component
public class IconFinder {
    private final static Logger logger = LoggerFactory.getLogger(IconFinder.class);

    private static final Pattern[] ICON_PATTERNS = new Pattern[]{
            Pattern.compile("rel=[\"']shortcut icon[\"'][^\r\n>]+?((?<=href=[\"']).+?(?=[\"']))"),
            Pattern.compile("((?<=href=[\"']).+?(?=[\"']))[^\r\n<]+?rel=[\"']shortcut icon[\"']")};
    private static final Pattern HEAD_END_PATTERN = Pattern.compile("</head>");

    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        IconFinder dd = new IconFinder();
        System.out.println(dd.getIconUrlString("http://www.hao123.com/"));
        System.out.println("耗时：" + (System.currentTimeMillis() - startTime) + " ms");
    }

    // 获取稳定url
    private static String getFinalUrl(String urlString) {
        HttpURLConnection connection = null;
        try {
            connection = getConnection(urlString);
            //：设置连接主机超时（单位：毫秒）
            connection.setConnectTimeout(200);
            //设置从主机读取数据超时（单位：毫秒）
            connection.setReadTimeout(500);
            connection.connect();

            // 是否跳转，若跳转则跟踪到跳转页面
            if (connection.getResponseCode() == HttpURLConnection.HTTP_MOVED_PERM
                    || connection.getResponseCode() == HttpURLConnection.HTTP_MOVED_TEMP) {
                String location = connection.getHeaderField("Location");
                if (!location.contains("http")) {
                    location = urlString + "/" + location;
                }
                return location;
            }
        } catch (Exception e) {
            logger.error("获取跳转链接超时，返回原链接" + urlString,e.getMessage());
        } finally {
            if (connection != null)
                connection.disconnect();
        }
        return urlString;
    }

    // 获取Icon地址
    public String getIconUrlString(String urlString) {

        try {
            urlString = getFinalUrl(urlString);
            URL url = null;
            try {
                url = new URL(urlString);
            } catch (MalformedURLException e) {
                logger.error("获取图标异常",e);
            }
            String iconUrl = url.getProtocol() + "://" + url.getHost() + "/favicon.ico";// 保证从域名根路径搜索
            if (hasRootIcon(iconUrl))
                return iconUrl;

            return getIconUrlByRegex(urlString);
        } catch (Exception e) {
            logger.error("分析icon异常",e.getMessage());
        }
        return null;
    }

    // 判断在根目录下是否有Icon
    private static boolean hasRootIcon(String urlString) {
        HttpURLConnection connection = null;

        try {
            connection = getConnection(urlString);
            //：设置连接主机超时（单位：毫秒）
            connection.setConnectTimeout(200);
            //设置从主机读取数据超时（单位：毫秒）
            connection.setReadTimeout(500);
            connection.connect();
            return HttpURLConnection.HTTP_OK == connection.getResponseCode() && connection.getContentLength() > 0;
        } catch (Exception e) {
            logger.error("判断在根目录下是否有Icon异常",e.getMessage());
            return false;
        } finally {
            if (connection != null)
                connection.disconnect();
        }
    }

    // 从html中获取Icon地址
    private static String getIconUrlByRegex(String urlString) {

        try {
            String headString = getHead(urlString);

            for (Pattern iconPattern : ICON_PATTERNS) {
                Matcher matcher = iconPattern.matcher(headString);

                if (matcher.find()) {
                    String iconUrl = matcher.group(1);
                    if (iconUrl.contains("http"))
                        return iconUrl;

                    if (iconUrl.charAt(0) == '/') {//判断是否为相对路径或根路径
                        URL url = new URL(urlString);
                        iconUrl = url.getProtocol() + "://" + url.getHost() + iconUrl;
                    } else {
                        iconUrl = urlString + "/" + iconUrl;
                    }
                    return iconUrl;
                }
            }
        } catch (Exception e) {
            logger.error("从html中获取Icon地址异常",e.getMessage());
        }
        return null;
    }


    // 获取截止到head尾标签的文本
    private static final String getHead(String urlString) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            connection = getConnection(urlString);
            //：设置连接主机超时（单位：毫秒）
            connection.setConnectTimeout(200);
            //设置从主机读取数据超时（单位：毫秒）
            connection.setReadTimeout(500);
            connection.connect();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line = null;
            StringBuilder headBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                Matcher matcher = HEAD_END_PATTERN.matcher(line);
                if (matcher.find())
                    break;
                headBuilder.append(line);
            }

            return headBuilder.toString();
        } catch (Exception e) {
            logger.error("获取截止到head尾标签的文本异常",e.getMessage());
            return null;
        } finally {
            try {
                if (reader != null)
                    reader.close();
                if (connection != null)
                    connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 获取一个连接
    private static HttpURLConnection getConnection(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setInstanceFollowRedirects(false);
        connection.setConnectTimeout(300);
        connection.setReadTimeout(500);
        connection
                .setRequestProperty("User-Agent",
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.131 Safari/537.36");
        return connection;
    }
}

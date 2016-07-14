package com.sera;

import com.sera.utils.HtmlUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.File;

/**
 * jsoup
 * Created by wangqing on 16/7/14.
 */
public class TestJoup {

    @Test
    public void test() throws Exception {
        Document doc = Jsoup.parse(new File("/Users/wangqing/Downloads/bookmarks.html"), "UTF-8");

        Elements el = doc.select("dt");

        for (Element node : el) {
            Elements ee = node.select("h3");
            String h3 = "";
            if (ee != null) {
                Element ff = ee.first();
                if (ff != null) {
                    h3 = ff.text();
                }
            }

            Elements aa = node.select("a");
            if (aa != null) {
                for (Element kk : aa) {
                    String linkHref = kk.attr("href");
                    String linkText = kk.text();
                    System.out.println(h3 + ":" + linkText + linkHref);
                }
            }
        }

    }

    private void dealDl(Element node) {

        Elements el = node.select("dl");

        for (Element node2 : el) {
            System.out.println(node2.toString());
        }
    }

    @Test
    public void testDelTag() {
        String tt=Jsoup.parse("http://ip.zdaye.com/css/ice.css\" media=\"all\" rel=\"stylesheet\" /><link href=\"/css/zzsc.css\" rel=\"stylesheet\" type=\"text/css\"><link href=\">\n" +
                "                                                                                                \n" +
                "                                                                                                                                <div class=\"content\">").text();

        System.out.println(tt);

        String ee= HtmlUtils.filterHtml("http://ip.zdaye.com/css/ice.css\" media=\"all\" rel=\"stylesheet\" /><link href=\"/css/zzsc.css\" rel=\"stylesheet\" type=\"text/css\"><link href=\">\n" +
                "                                                                                                \n" +
                "                                                                                                                                <div class=\"content\">");

        System.out.println(ee);

        System.out.println(HtmlUtils.filterHtml(tt));
    }
}

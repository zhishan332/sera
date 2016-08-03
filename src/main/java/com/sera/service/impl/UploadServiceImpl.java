package com.sera.service.impl;

import com.sera.config.SequenceConfig;
import com.sera.entity.FavGroupEntity;
import com.sera.entity.FavListEntity;
import com.sera.helper.SequenceHelper;
import com.sera.helper.UserHelper;
import com.sera.service.FavService;
import com.sera.service.UploadService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 导入
 * Created by wangqing on 16/8/3.
 */

@Service
public class UploadServiceImpl implements UploadService {
    private static final Logger log = LoggerFactory.getLogger(UploadServiceImpl.class);
    @Value("#{config['upload.temp.path']}")
    private String uploadPath;
    @Resource
    private FavService favService;

    @Resource
    private SequenceHelper sequenceHelper;

    @Override
    public boolean parse(long userID,String userName, String fileName) {
        File file = new File(fileName);
        try {

            String str = FileUtils.readFileToString(file, "UTF-8");
            //解析间接菜单
            parseByRegx(userID,userName, str);
            parseByJsoup(userID,userName, str);
            return true;
        } catch (Exception e) {
            log.error("解析上传标签异常");
            return false;
        }finally {
            file.delete();
        }
    }

    private void parseByJsoup(long userID, String userName, String str) {
        //解析直接菜单
        Document doc = Jsoup.parse(str, "UTF-8");

        Elements el = doc.select("dt");

        List<Element> nodoList = new ArrayList<>();
        for (Element node : el) {
            try {
                Elements ee = node.select("h3");
                String title = "";
                if (ee != null) {
                    Element ff = ee.first();
                    if (ff != null) {
                        title = ff.text();
                    }
                } else {
                    continue;
                }
                log.info("当前导入的分组名称:" + title);
                Elements aa = node.select("a");
                if (aa != null) {
                    for (Element kk : aa) {
                        try {
                            if (StringUtils.isBlank(title)) {
                                nodoList.add(kk);
                            }
                        } catch (Exception e) {
                            log.error("新增fav失败", e);
                        }
                    }
                }
            } catch (Exception e) {
                log.error("新增组失败", e);
            }
        }
        if (!nodoList.isEmpty()) {
            FavGroupEntity group = new FavGroupEntity();
            long groupId = sequenceHelper.getSeq(SequenceConfig.FAV_GROUP_SEQ);
            group.setGroupId(groupId);
            group.setUserId(userID);
            group.setUserName(userName);
            group.setGroupName("未分组");
            favService.addFavGroup(group);
            for (Element elt : nodoList) {
                Elements cc = elt.select("a");
                for (Element kk : cc) {
                    try {
                        String linkHref = kk.attr("href");
                        String linkText = kk.text();
                        FavListEntity checkObj = favService.isDuplicate(userID, linkHref);
                        if (null != checkObj) {
                            log.warn("当前导入的url重复userID:" + userID + ";url:" + linkHref);
                            continue;
                        }
                        FavListEntity fav = new FavListEntity();
                        fav.setFavUrl(linkHref);
                        fav.setFavTitle(linkText);
                        fav.setGroupId(group.getGroupId());
                        fav.setUserId(userID);
                        fav.setUserName(userName);
                        favService.addFav(fav);
                    } catch (Exception e) {
                        log.error("新增fav失败", e);
                    }
                }
            }
        }
    }

    private void parseByRegx(long userID, String userName, String arg) {
        arg = arg.replaceAll("\\s*|\t|\r|\n", "");
        String rg = "ADD_DATE=\"(\\d+)\"";
        arg = arg.replaceAll(rg, "");
        String rg2 = "LAST_MODIFIED=\"(\\d+)\"";
        arg = arg.replaceAll(rg2, "");
        String rg3 = "PERSONAL_TOOLBAR_FOLDER=\"(\\d+)\"";
        arg = arg.replaceAll(rg3, "");
        Pattern pattern = Pattern.compile("<DT><H3>(.*?)</H3><DL><p><DT>(.*?)</A></DL><p>?", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher matcher = pattern.matcher(arg);
        while (matcher.find()) {
            try {
                String groupName = matcher.group(1);
                if (StringUtils.isBlank(groupName)) continue;
                long groupId = sequenceHelper.getSeq(SequenceConfig.FAV_GROUP_SEQ);
                FavGroupEntity group = new FavGroupEntity();
                group.setGroupId(groupId);
                group.setUserId(userID);
                group.setUserName(userName);
                group.setGroupName(groupName);
                favService.addFavGroup(group);
                String urlstr = matcher.group(2) + "</A>";
                Pattern pattern2 = Pattern.compile("<AHREF=\"(.*?)\">(.*?)</A>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
                Matcher matcher2 = pattern2.matcher(urlstr);
                while (matcher2.find()) {
                    try {
                        String urlName = matcher2.group(2);
                        String linkHref = matcher2.group(1);
                        FavListEntity checkObj = favService.isDuplicate(userID, linkHref);
                        if (null != checkObj) {
                            log.warn("当前导入的url重复userID:" + userID + ";url:" + linkHref);
                            continue;
                        }
                        FavListEntity fav = new FavListEntity();
                        fav.setFavUrl(linkHref);
                        fav.setFavTitle(urlName);
                        fav.setGroupId(group.getGroupId());
                        fav.setUserId(userID);
                        fav.setUserName(userName);
                        favService.addFav(fav);
                    } catch (Exception e) {
                        log.error("导入时解析URL遇到错误", e);
                    }
                }
            } catch (Exception e) {
                log.error("导入时遇到错误", e);
            }

        }
    }
}

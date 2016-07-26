package com.sera.controller;

import com.sera.config.SequenceConfig;
import com.sera.dto.Response;
import com.sera.entity.FavGroupEntity;
import com.sera.entity.FavListEntity;
import com.sera.helper.SequenceHelper;
import com.sera.helper.UserHelper;
import com.sera.service.FavService;
import com.sera.utils.JacksonUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件上传,所有上传必须要经过token验证，防止非法上传
 *
 * @author wangqing
 * @since 14-11-27 上午10:14
 */
@Controller
@RequestMapping("/import")
public class BookMarkUploadController {
    private static final Logger log = LoggerFactory.getLogger(BookMarkUploadController.class);

    @Resource
    private FavService favService;

    @Resource
    private UserHelper userHelper;

    @Resource
    private SequenceHelper sequenceHelper;

    @RequestMapping(value = "/bookmark", method = RequestMethod.POST)
    public void infoImgUpload(HttpServletResponse response, String timestamp, String token,
                              @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        response.setContentType("text/html");
        Response resp = new Response();
        resp.setStatus(Response.FAILURE);

        if (file == null || file.isEmpty()) {
            log.warn("上传文件为空，已终止！");
            resp.setMsg("上传文件为空，已终止！");
            response.getWriter().write(JacksonUtils.defaultMapper().toJson(resp));
            return;
        }
        if (file.getSize() > 10 * 1024 * 1024) {
            log.warn("上传文件尺寸过大，已终止！最大尺寸是：" + 10 + "MB");
            resp.setMsg("上传文件尺寸过大，已终止！最大尺寸是：" + 10 + "MB");
            response.getWriter().write(JacksonUtils.defaultMapper().toJson(resp));
            return;
        }
        try {
            byte[] bytes = file.getBytes();
            String str = new String(bytes, "UTF-8");

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
                    }
                    FavGroupEntity group = new FavGroupEntity();
                    if (!StringUtils.isBlank(title)) {
                        long groupId = sequenceHelper.getSeq(SequenceConfig.FAV_GROUP_SEQ);
                        group.setGroupId(groupId);
                        group.setUserId(userHelper.getUserID());
                        group.setUserName(userHelper.getUser().getUserName());
                        group.setGroupName(title);
                        favService.addFavGroup(group);
                    }
                    Elements aa = node.select("a");
                    if (aa != null) {
                        for (Element kk : aa) {
                            try {
                                String linkHref = kk.attr("href");
                                String linkText = kk.text();
                                if (StringUtils.isBlank(title)) {
                                    nodoList.add(kk);
                                } else {
                                    FavListEntity checkObj = favService.isDuplicate(userHelper.getUserID(), linkHref);
                                    if (null != checkObj) {
                                        log.warn("当前导入的url重复userID:" + userHelper.getUserID() + ";url:" + linkHref);
                                        continue;
                                    }
                                    FavListEntity fav = new FavListEntity();
                                    fav.setFavUrl(linkHref);
                                    fav.setFavTitle(linkText);
                                    fav.setGroupId(group.getGroupId());
                                    fav.setUserId(userHelper.getUserID());
                                    fav.setUserName(userHelper.getUser().getUserName());
                                    favService.addFav(fav);
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
                group.setUserId(userHelper.getUserID());
                group.setUserName(userHelper.getUser().getUserName());
                group.setGroupName("未分组");
                favService.addFavGroup(group);
                for (Element elt : nodoList) {
                    Elements cc = elt.select("a");
                    for (Element kk : cc) {
                        try {
                            String linkHref = kk.attr("href");
                            String linkText = kk.text();
                            FavListEntity checkObj = favService.isDuplicate(userHelper.getUserID(), linkHref);
                            if (null != checkObj) {
                                log.warn("当前导入的url重复userID:" + userHelper.getUserID() + ";url:" + linkHref);
                                continue;
                            }
                            FavListEntity fav = new FavListEntity();
                            fav.setFavUrl(linkHref);
                            fav.setFavTitle(linkText);
                            fav.setGroupId(group.getGroupId());
                            fav.setUserId(userHelper.getUserID());
                            fav.setUserName(userHelper.getUser().getUserName());
                            favService.addFav(fav);
                        } catch (Exception e) {
                            log.error("新增fav失败", e);
                        }
                    }
                }
            }
            resp.setStatus(Response.SUCCESS);
            resp.setData("");
            response.getWriter().write(JacksonUtils.defaultMapper().toJson(resp));
        } catch (Exception e) {
            log.error("上传处理失败", e);
            resp.setMsg("上传内部异常");
            response.getWriter().write(JacksonUtils.defaultMapper().toJson(resp));
        }
    }

}

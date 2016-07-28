package com.sera.controller;

import com.sera.config.ViewConfig;
import com.sera.entity.FavGroupEntity;
import com.sera.entity.FavListEntity;
import com.sera.helper.UserHelper;
import com.sera.service.FavService;
import com.sera.utils.MagicUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 推荐
 * Created by wangqing on 16/7/28.
 */
@Controller
@RequestMapping("/recom")
public class RecomController {

    private static final Logger log = LoggerFactory.getLogger(RecomController.class);

    @Resource
    private UserHelper userHelper;
    @Resource
    private FavService favService;
    @Value("#{config['user.show.id']}")
    private long showUserId;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView showIndex() {
        ModelAndView mav = new ModelAndView("recom");
        mav.getModel().put("pageName", "推荐");
        mav.getModel().put("runTime", MagicUtils.getRunDay());
        try {
            long userId = 0;

            if (!userHelper.isLogin()) {
                userId = showUserId;
            } else {
                userId = showUserId;
                mav.getModel().put("createTime", userHelper.getUser().getCreateTime());
                mav.getModel().put("username", userHelper.getUser().getUserName());
                mav.getModel().put("userID", userHelper.getUser().getUserId());
                mav.getModel().put("pageName", "发现");
            }
            List<FavGroupEntity> groupList = favService.findFavGroup(userId, 1);
            mav.getModel().put("groupData", groupList);
            if (groupList != null && !groupList.isEmpty()) {
                Map<String, List<FavListEntity>> data = new HashMap<>();
                for (FavGroupEntity group : groupList) {
                    List<FavListEntity> list = favService.findByGroup(userId, group.getGroupId(), null, 0, ViewConfig.DEFAULT_PAGE_LIMIT);
                    data.put(group.getGroupName() + "/" + group.getGroupId() + "/" + (list == null ? 0 : list.size()) + "/" + getGroupColor(group.getGroupId()) + "/" + group.getCovert(), list);
                }
                mav.getModel().put("favData", data);
                mav.getModel().put("groupWith", "four");
                mav.getModel().put("titleLen", 18);
                mav.getModel().put("urlLen", 30);
            }
        } catch (Exception e) {
            log.error("showIndex error!", e);
            mav = new ModelAndView("error");
            mav.getModel().put("info", "系统错误,请重试");
        }
        return mav;
    }

    private String getGroupColor(long id) {

//        String[] colorArry=new String[]{"teal","red","purple","pink","orange","green","blue","yellow"};
        String[] colorArry = new String[]{"green"};

        long aa = id % colorArry.length;

        return colorArry[(int) aa];
    }
}

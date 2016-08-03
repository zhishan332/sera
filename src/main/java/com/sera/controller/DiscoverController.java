package com.sera.controller;

import com.sera.config.ViewConfig;
import com.sera.entity.FavGroupEntity;
import com.sera.entity.FavListEntity;
import com.sera.helper.UserHelper;
import com.sera.service.FavService;
import com.sera.utils.MagicUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 发现
 * Created by wangqing on 16/7/28.
 */
@Controller
@RequestMapping("/dis")
public class DiscoverController {
    private static final Logger log = LoggerFactory.getLogger(DiscoverController.class);

    @Resource
    private UserHelper userHelper;
    @Resource
    private FavService favService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView showIndex() {
        ModelAndView mav = new ModelAndView("discover");
        mav.getModel().put("pageName", "发现");
        mav.getModel().put("runTime", MagicUtils.getRunDay());
        try {
            List<FavGroupEntity> groupList =null;
            if (userHelper.isLogin()) {
                mav.getModel().put("createTime", userHelper.getUser().getCreateTime());
                mav.getModel().put("username", userHelper.getUser().getUserName());
                mav.getModel().put("userID", userHelper.getUser().getUserId());
                mav.getModel().put("pageName", "发现");
                List<FavGroupEntity> myGroupList = favService.findFavGroup(userHelper.getUser().getUserId(), 0);
                mav.getModel().put("myGroupData", myGroupList);
                groupList = favService.findForDiscovery(userHelper.getUser().getUserId());
                mav.getModel().put("groupData", groupList);
            }else {
                groupList = favService.findForDiscovery(0);
                mav.getModel().put("groupData", groupList);
            }
            if (groupList != null && !groupList.isEmpty()) {
                Map<String, List<FavListEntity>> data = new HashMap<>();
                for (FavGroupEntity group : groupList) {
                    List<FavListEntity> list = favService.findByGroup(group.getUserId(), group.getGroupId(), null, 0, ViewConfig.DEFAULT_PAGE_LIMIT);
                    if (list != null && !list.isEmpty()) {
                        data.put(group.getGroupName() + "/" + group.getGroupId() + "/" + (list.size()) + "/" + getGroupColor(group.getGroupId()) + "/" + group.getCovert(), list);
                    }
                }
                mav.getModel().put("favData", data);
                mav.getModel().put("groupWith", "four");
                mav.getModel().put("titleLen", 24);
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
        String[] colorArry = new String[]{"pink"};

        long aa = id % colorArry.length;

        return colorArry[(int) aa];
    }
}

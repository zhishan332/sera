package com.sera.controller;

import com.sera.config.ViewConfig;
import com.sera.dto.Response;
import com.sera.dto.UserInfoDto;
import com.sera.entity.FavGroupEntity;
import com.sera.entity.FavListEntity;
import com.sera.helper.UserHelper;
import com.sera.service.FavService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 * 首页
 * Created by wangqing on 16/5/13.
 */
@Controller
@RequestMapping("")
public class FavController {
    private static final Logger log = LoggerFactory.getLogger(FavController.class);

    @Resource
    private UserHelper userHelper;
    @Resource
    private FavService favService;

    @RequestMapping(value = {"", "index", "/index", "/fav"}, method = RequestMethod.GET)
    public ModelAndView showIndex(Long gid, String gname) {
        ModelAndView mav = new ModelAndView("fav");
        mav.getModel().put("pageName", "首页");
        UserInfoDto dto = new UserInfoDto();
        dto.setUserId(99999999L);
        dto.setUserName("admin");
        userHelper.setUserSession(dto);

        List<FavGroupEntity> groupList = favService.findFavGroup(userHelper.getUserID());
        mav.getModel().put("groupData", groupList);
        if (gid != null && gid > 0) {
            Map<String, List<FavListEntity>> data = new HashMap<>();
            List<FavListEntity> list = favService.findByGroup(userHelper.getUserID(), gid, null, 0, ViewConfig.DEFAULT_PAGE_LIMIT);
            data.put(gname + "/" + gid + "/" + (list == null ? 0 : list.size()), list);
            mav.getModel().put("favData", data);
        } else {
            if (groupList != null && !groupList.isEmpty()) {
                Map<String, List<FavListEntity>> data = new TreeMap<>(new Comparator<String>() {

                    @Override
                    public int compare(String o1, String o2) {
                        String[] arr1 = o1.split("/");
                        Integer num1 = Integer.parseInt(arr1[2]);

                        String[] arr2 = o2.split("/");
                        Integer num2 = Integer.parseInt(arr2[2]);

                        return num2.compareTo(num1);
                    }
                });
                for (FavGroupEntity group : groupList) {
                    List<FavListEntity> list = favService.findByGroup(userHelper.getUserID(), group.getGroupId(), null, 0, ViewConfig.DEFAULT_PAGE_LIMIT);
                    data.put(group.getGroupName() + "/" + group.getGroupId() + "/" + (list == null ? 0 : list.size()), list);
                }
                mav.getModel().put("favData", data);
            }
        }
        return mav;
    }

    @RequestMapping(value = "/fav/list", method = RequestMethod.GET)
    @ResponseBody
    public List<FavListEntity> findFavList(String keyword) {
        return favService.findByGroup(userHelper.getUserID(), 0L, keyword, 0, Integer.MAX_VALUE);
    }


    @RequestMapping(value = "/fav/add", method = RequestMethod.POST)
    @ResponseBody
    public Response addFav(FavListEntity entity) {
        Response resp = new Response();
        entity.setUserId(userHelper.getUserID());
        entity.setUserName(userHelper.getUser().getUserName());
        favService.addFav(entity);
        resp.setStatus(Response.SUCCESS);
        return resp;
    }


    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ModelAndView showIndex2() {
        ModelAndView mav = new ModelAndView("test");
        mav.getModel().put("pageName", "test");
        return mav;
    }


}

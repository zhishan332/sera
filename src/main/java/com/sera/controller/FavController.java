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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            data.put(gname + "/" + gid + "/" + (list == null ? 0 : list.size()) + "/" + getGroupColor(gid)+"/1", list);
            mav.getModel().put("favData", data);
            mav.getModel().put("gid", gid);
            mav.getModel().put("groupWith", "sixteen");
            mav.getModel().put("titleLen", 80);
            mav.getModel().put("urlLen", 100);
        } else {
            if (groupList != null && !groupList.isEmpty()) {
//                Map<String, List<FavListEntity>> data = new TreeMap<>(new Comparator<String>() {
//
//                    @Override
//                    public int compare(String o1, String o2) {
//                        String[] arr1 = o1.split("/");
//                        Integer num1 = Integer.parseInt(arr1[2]);
//
//                        String[] arr2 = o2.split("/");
//                        Integer num2 = Integer.parseInt(arr2[2]);
//
//                        return num2.compareTo(num1);
//                    }
//                });
                Map<String, List<FavListEntity>> data = new HashMap<>();
                for (FavGroupEntity group : groupList) {
                    List<FavListEntity> list = favService.findByGroup(userHelper.getUserID(), group.getGroupId(), null, 0, ViewConfig.DEFAULT_PAGE_LIMIT);
                    data.put(group.getGroupName() + "/" + group.getGroupId() + "/" + (list == null ? 0 : list.size()) + "/" + getGroupColor(group.getGroupId())+"/"+group.getCovert(), list);
                }
                mav.getModel().put("favData", data);
                mav.getModel().put("groupWith", "four");
                mav.getModel().put("titleLen", 18);
                mav.getModel().put("urlLen", 30);
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

    @RequestMapping(value = "/fav/del", method = RequestMethod.POST)
    @ResponseBody
    public Response delFav(FavListEntity entity) {
        Response resp = new Response();
        favService.delFav(userHelper.getUserID(), entity.getFavId());
        resp.setStatus(Response.SUCCESS);
        return resp;
    }


    @RequestMapping(value = "/fav/group/add", method = RequestMethod.POST)
    @ResponseBody
    public Response addFavGroup(FavGroupEntity entity) {
        Response resp = new Response();
        entity.setUserId(userHelper.getUserID());
        entity.setUserName(userHelper.getUser().getUserName());
        favService.addFavGroup(entity);
        resp.setStatus(Response.SUCCESS);
        return resp;
    }

    @RequestMapping(value = "/fav/group/del", method = RequestMethod.POST)
    @ResponseBody
    public Response delFavGroup(FavGroupEntity entity) {
        Response resp = new Response();
        favService.delFavGroup(userHelper.getUserID(), entity.getGroupId());
        resp.setStatus(Response.SUCCESS);
        return resp;
    }


    @RequestMapping(value = "/fav/group/lock", method = RequestMethod.POST)
    @ResponseBody
    public Response lockFavGroup(FavGroupEntity entity) {
        Response resp = new Response();
        favService.protectFavGroup(userHelper.getUserID(), entity.getGroupId(), entity.getCovert());
        resp.setStatus(Response.SUCCESS);
        return resp;
    }

    @RequestMapping(value = "/fav/follow", method = RequestMethod.POST)
    @ResponseBody
    public Response followFavGroup(FavListEntity entity) {
        Response resp = new Response();
        favService.focusFav(userHelper.getUserID(), entity.getGroupId(), entity.getFavFocus());
        resp.setStatus(Response.SUCCESS);
        return resp;
    }

    @RequestMapping(value = "/fav/check", method = RequestMethod.POST)
    @ResponseBody
    public Response doCheck() {
        Response resp = new Response();
        favService.checkFav(userHelper.getUserID());
        resp.setStatus(Response.SUCCESS);
        return resp;
    }

    @RequestMapping(value = "/fav/arrange", method = RequestMethod.POST)
    @ResponseBody
    public Response doArrange() {
        Response resp = new Response();
        favService.checkFav(userHelper.getUserID());
        resp.setStatus(Response.SUCCESS);
        return resp;
    }

    @RequestMapping(value = "/fav/expire/del", method = RequestMethod.POST)
    @ResponseBody
    public Response delExpireFav() {
        Response resp = new Response();
        favService.delExpireFav(userHelper.getUserID());
        resp.setStatus(Response.SUCCESS);
        return resp;
    }


    private String getGroupColor(long id) {

//        String[] colorArry=new String[]{"teal","red","purple","pink","orange","green","blue","yellow"};
        String[] colorArry = new String[]{"orange"};

        long aa = id % colorArry.length;

        return colorArry[(int) aa];
    }

}

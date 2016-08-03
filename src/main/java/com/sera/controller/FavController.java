package com.sera.controller;

import com.sera.config.ViewConfig;
import com.sera.dto.Response;
import com.sera.entity.FavGroupEntity;
import com.sera.entity.FavListEntity;
import com.sera.helper.UserHelper;
import com.sera.service.FavService;
import com.sera.utils.MagicUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("#{config['user.show.id']}")
    private long showUserId;

    @RequestMapping(value = {"", "index", "/index",}, method = RequestMethod.GET)
    public ModelAndView showIndex(Long gid, String gname, String wd) {
        ModelAndView mav = new ModelAndView("fav");
        mav.getModel().put("pageName", "首页");
        mav.getModel().put("runTime", MagicUtils.getRunDay());
        try {
            long userId = 0;

            if (!userHelper.isLogin()) {
                userId = showUserId;
            } else {
                userId = userHelper.getUserID();
                mav.getModel().put("createTime", userHelper.getUser().getCreateTime());
                mav.getModel().put("username", userHelper.getUser().getUserName());
                mav.getModel().put("userID", userHelper.getUser().getUserId());
            }
            List<FavGroupEntity> groupList = favService.findFavGroup(userId, 0);
            mav.getModel().put("groupData", groupList);
            if (gid != null && gid > 0) {
                Map<String, List<FavListEntity>> data = new HashMap<>();
                List<FavListEntity> list = favService.findByGroup(userId, gid, null, 0, ViewConfig.DEFAULT_PAGE_LIMIT);
                data.put(gname + "/" + gid + "/" + (list == null ? 0 : list.size()) + "/" + getGroupColor(gid) + "/1", list);
                mav.getModel().put("favData", data);
                mav.getModel().put("gid", gid);
                mav.getModel().put("groupWith", "sixteen");
                mav.getModel().put("titleLen", 80);
                mav.getModel().put("urlLen", 100);
            } else if (!StringUtils.isBlank(wd)) {
                wd = StringUtils.trim(wd);
                if (wd.length() > 10) wd = wd.substring(0, 10);
                Map<String, List<FavListEntity>> data = new HashMap<>();
                List<FavListEntity> list = favService.findByGroup(userId, 0L, wd, 0, ViewConfig.DEFAULT_PAGE_LIMIT);
                data.put("搜索结果" + "/" + 0 + "/" + (list == null ? 0 : list.size()) + "/" + getGroupColor(0L) + "/1", list);
                mav.getModel().put("favData", data);
                mav.getModel().put("gid", 0);
                mav.getModel().put("groupWith", "sixteen");
                mav.getModel().put("titleLen", 80);
                mav.getModel().put("urlLen", 100);
                mav.getModel().put("wd", 1);
            } else {
                if (groupList != null && !groupList.isEmpty()) {
                    Map<String, List<FavListEntity>> data = new HashMap<>();
                    for (FavGroupEntity group : groupList) {
                        List<FavListEntity> list = favService.findByGroup(userId, group.getGroupId(), null, 0, ViewConfig.DEFAULT_PAGE_LIMIT);
                        data.put(group.getGroupName() + "/" + group.getGroupId() + "/" + (list == null ? 0 : list.size()) + "/" + getGroupColor(group.getGroupId()) + "/" + group.getCovert(), list);
                    }
                    mav.getModel().put("favData", data);
                    mav.getModel().put("groupWith", "four");
                    mav.getModel().put("titleLen", 17);
                    mav.getModel().put("urlLen", 30);
                }
            }
        } catch (Exception e) {
            log.error("showIndex error!", e);
            mav = new ModelAndView("error");
            mav.getModel().put("info", "系统错误,请重试");
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
        if (StringUtils.isBlank(entity.getFavUrl())) {
            resp.setStatus(Response.FAILURE);
            resp.setMsg("收藏的网址不能为空");
            return resp;
        }
        entity.setUserId(userHelper.getUserID());
        entity.setUserName(userHelper.getUser().getUserName());

        try {
            FavListEntity checkObj = favService.isDuplicate(entity.getUserId(), entity.getFavUrl());
            if (null != checkObj) {
                resp.setStatus(Response.FAILURE);
                resp.setMsg("已经收藏过该网址了,收藏在:" + checkObj.getGroupName());
                return resp;
            }
            favService.addFav(entity);
            resp.setStatus(Response.SUCCESS);
        } catch (Exception e) {
            log.error("addFav error!", e);
            resp.setStatus(Response.FAILURE);
            resp.setMsg("系统错误,请稍后重试");
            return resp;
        }
        return resp;
    }

    @RequestMapping(value = "/fav/del", method = RequestMethod.POST)
    @ResponseBody
    public Response delFav(FavListEntity entity) {
        Response resp = new Response();
        try {
            favService.delFav(userHelper.getUserID(), entity.getFavId());
            resp.setStatus(Response.SUCCESS);
            return resp;
        } catch (Exception e) {
            log.error("delFav error!", e);
            resp.setStatus(Response.FAILURE);
            resp.setMsg("系统错误,请稍后重试");
            return resp;
        }
    }

    @RequestMapping(value = "/fav/edit", method = RequestMethod.POST)
    @ResponseBody
    public Response delEdit(FavListEntity entity) {
        Response resp = new Response();
        try {
            if (StringUtils.isBlank(entity.getFavTitle())) {
                resp.setStatus(Response.FAILURE);
                resp.setMsg("标题不能为空");
                return resp;
            }
            favService.updateTitle(userHelper.getUserID(), entity.getFavId(), entity.getFavTitle());
            resp.setStatus(Response.SUCCESS);
            return resp;
        } catch (Exception e) {
            log.error("delFav error!", e);
            resp.setStatus(Response.FAILURE);
            resp.setMsg("系统错误,请稍后重试");
            return resp;
        }
    }

    @RequestMapping(value = "/fav/move", method = RequestMethod.POST)
    @ResponseBody
    public Response moveFav(FavListEntity entity) {
        Response resp = new Response();
        try {
            favService.updateFavGroup(userHelper.getUserID(), entity.getFavId(), entity.getGroupId());
            resp.setStatus(Response.SUCCESS);
            return resp;
        } catch (Exception e) {
            log.error("moveFav error!", e);
            resp.setStatus(Response.FAILURE);
            resp.setMsg("系统错误,请稍后重试");
            return resp;
        }
    }

    @RequestMapping(value = "/fav/group", method = RequestMethod.GET)
    @ResponseBody
    public Response findFavGroup() {
        Response resp = new Response();
        try {
            List<FavGroupEntity> data = favService.findFavGroup(userHelper.getUserID(), 0);
            resp.setStatus(Response.SUCCESS);
            resp.setData(data);
            return resp;
        } catch (Exception e) {
            log.error("addFavGroup error!", e);
            resp.setStatus(Response.FAILURE);
            resp.setMsg("系统错误,请稍后重试");
            return resp;
        }
    }


    @RequestMapping(value = "/fav/group/add", method = RequestMethod.POST)
    @ResponseBody
    public Response addFavGroup(FavGroupEntity entity) {
        Response resp = new Response();
        try {
            entity.setUserId(userHelper.getUserID());
            entity.setUserName(userHelper.getUser().getUserName());
            favService.addFavGroup(entity);
            resp.setStatus(Response.SUCCESS);
            return resp;
        } catch (Exception e) {
            log.error("addFavGroup error!", e);
            resp.setStatus(Response.FAILURE);
            resp.setMsg("系统错误,请稍后重试");
            return resp;
        }
    }

    @RequestMapping(value = "/fav/group/del", method = RequestMethod.POST)
    @ResponseBody
    public Response delFavGroup(FavGroupEntity entity) {
        Response resp = new Response();
        try {
            favService.delFavGroup(userHelper.getUserID(), entity.getGroupId());
            resp.setStatus(Response.SUCCESS);
            return resp;
        } catch (Exception e) {
            log.error("delFavGroup error!", e);
            resp.setStatus(Response.FAILURE);
            resp.setMsg("系统错误,请稍后重试");
            return resp;
        }
    }


    @RequestMapping(value = "/fav/group/lock", method = RequestMethod.POST)
    @ResponseBody
    public Response lockFavGroup(FavGroupEntity entity) {
        Response resp = new Response();
        try {
            favService.protectFavGroup(userHelper.getUserID(), entity.getGroupId(), entity.getCovert());
            resp.setStatus(Response.SUCCESS);
            return resp;
        } catch (Exception e) {
            log.error("lockFavGroup error!", e);
            resp.setStatus(Response.FAILURE);
            resp.setMsg("系统错误,请稍后重试");
            return resp;
        }
    }

    @RequestMapping(value = "/fav/follow", method = RequestMethod.POST)
    @ResponseBody
    public Response followFavGroup(FavListEntity entity) {
        Response resp = new Response();
        try {
            int status = favService.focusFav(userHelper.getUserID(), entity.getFavId());
            resp.setStatus(Response.SUCCESS);
            resp.setData(status);
            return resp;
        } catch (Exception e) {
            log.error("followFavGroup error!", e);
            resp.setStatus(Response.FAILURE);
            resp.setMsg("系统错误,请稍后重试");
            return resp;
        }
    }

    @RequestMapping(value = "/fav/check", method = RequestMethod.POST)
    @ResponseBody
    public Response doCheck() {
        Response resp = new Response();
        try {
            boolean isOK = favService.addCheckFavTask(userHelper.getUserID());
            if (!isOK) {
                log.warn("URL检查任务已经存在:" + userHelper.getUserID());
            }
            resp.setStatus(Response.SUCCESS);
            return resp;
        } catch (Exception e) {
            log.error("doCheck error!", e);
            resp.setStatus(Response.FAILURE);
            resp.setMsg("系统错误,请稍后重试");
            return resp;
        }
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
        try {
            favService.delExpireFav(userHelper.getUserID());
            resp.setStatus(Response.SUCCESS);
            return resp;
        } catch (Exception e) {
            log.error("delExpireFav error!", e);
            resp.setStatus(Response.FAILURE);
            resp.setMsg("系统错误,请稍后重试");
            return resp;
        }
    }


    private String getGroupColor(long id) {

//        String[] colorArry=new String[]{"teal","red","purple","pink","orange","green","blue","yellow"};
        String[] colorArry = new String[]{"blue"};

        long aa = id % colorArry.length;

        return colorArry[(int) aa];
    }

}

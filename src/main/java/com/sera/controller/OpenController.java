package com.sera.controller;

import com.sera.dto.Response;
import com.sera.entity.FavListEntity;
import com.sera.helper.UserHelper;
import com.sera.service.FavService;
import com.sera.utils.JacksonUtils;
import com.sera.utils.MagicUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 开放的url
 * Created by wangqing on 16/7/27.
 */
@Controller
@RequestMapping("")
public class OpenController {
    private static final Logger log = LoggerFactory.getLogger(OpenController.class);
    @Resource
    private UserHelper userHelper;
    @Resource
    private FavService favService;

    @RequestMapping(value = "/open/tools", method = RequestMethod.GET)
    public ModelAndView showUser() {
        ModelAndView mav = new ModelAndView("favtools");
        mav.getModel().put("runTime", MagicUtils.getRunDay());
        mav.getModel().put("pageName", "收藏工具");
        mav.getModel().put("createTime", userHelper.getUser().getCreateTime());
        mav.getModel().put("username", userHelper.getUser().getUserName());
        mav.getModel().put("userID", userHelper.getUser().getUserId());
        return mav;
    }

    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public ModelAndView showAbout() {
        ModelAndView mav = new ModelAndView("about");
        mav.getModel().put("runTime", MagicUtils.getRunDay());
        mav.getModel().put("pageName", "关于我们");
        mav.getModel().put("createTime", userHelper.getUser().getCreateTime());
        mav.getModel().put("username", userHelper.getUser().getUserName());
        mav.getModel().put("userID", userHelper.getUser().getUserId());
        return mav;
    }


    @RequestMapping(value = "/open/fav/add", method = RequestMethod.GET)
    public String addFav(@RequestParam("u") String url, HttpServletRequest request, HttpServletResponse response) {
        String callback = request.getParameter("callback");
        Response resp = new Response();
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = null;
        try {

            out = response.getWriter();

            if (StringUtils.isBlank(url)) {
                resp.setStatus(Response.FAILURE);
                resp.setMsg("收藏的网址不能为空");
                String json = JacksonUtils.defaultMapper().toJson(resp);
                out.print(callback + "(" + json + ")");
                return null;
            }
            FavListEntity entity = new FavListEntity();
            entity.setUserId(userHelper.getUserID());
            entity.setUserName(userHelper.getUser().getUserName());
            entity.setFavUrl(url);
            FavListEntity checkObj = favService.isDuplicate(entity.getUserId(), entity.getFavUrl());
            if (null != checkObj) {
                resp.setStatus(Response.FAILURE);
                resp.setMsg("已经收藏过了");
                String json = JacksonUtils.defaultMapper().toJson(resp);
                out.print(callback + "(" + json + ")");
                return null;
            }
            favService.addFav(entity);
            resp.setStatus(Response.SUCCESS);
            String json = JacksonUtils.defaultMapper().toJson(resp);

            out.print(callback + "(" + json + ")");
        } catch (Exception e) {
            log.error("open addFav error!", e);
            if (out != null) {
                resp.setStatus(Response.FAILURE);
                resp.setMsg("收藏失败");
                String json = JacksonUtils.defaultMapper().toJson(resp);
                out.print(callback + "(" + json + ")");
            }
            return null;
        }
        return null;
    }
}

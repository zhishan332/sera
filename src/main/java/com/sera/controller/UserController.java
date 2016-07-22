package com.sera.controller;

import com.sera.dto.Response;
import com.sera.entity.UserInfoEntity;
import com.sera.helper.UserHelper;
import com.sera.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * 用户控制器
 * Created by wangqing on 16/7/19.
 */
@Controller
@RequestMapping("/user")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;

    @Resource
    private UserHelper userHelper;

    /**
     * 使用用户名登录
     *
     * @param username 手机号
     * @param password 密码
     * @return 用户信息
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Response login(HttpSession httpSession, String username, String password) {
        Response resp = new Response();

        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            resp.setStatus(Response.FAILURE);
            resp.setMsg("用户名密码不能为空");
            return resp;
        }

        try {
            UserInfoEntity user = userService.login(username, password);
            if (user == null) {
                resp.setStatus(Response.FAILURE);
                resp.setMsg("用户名密码错误");
            } else {
                userHelper.setUserSession(user);
                resp.setStatus(Response.SUCCESS);
            }
        } catch (Exception e) {
            resp.setStatus(Response.FAILURE);
            resp.setMsg("内部错误,请稍后重试");
            log.error("登录异常", e);
        }
        return resp;
    }

    /**
     * 登出
     *
     * @return 用户信息
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public Response logout(HttpSession httpSession) {
        Response resp = new Response();

        try {
            httpSession.removeAttribute("user");
        } catch (Exception e) {
            resp.setStatus(Response.FAILURE);
            resp.setMsg("内部错误,请稍后重试");
            log.error("登录异常", e);
        }
        return resp;
    }


    /**
     * 注册
     *
     * @param username 手机号
     * @param password 密码
     * @return 用户信息
     */
    @RequestMapping(value = "/regist", method = RequestMethod.POST)
    @ResponseBody
    public Response regist(HttpSession httpSession, String username, String password) {
        Response resp = new Response();

        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            resp.setStatus(Response.FAILURE);
            resp.setMsg("用户名密码不能为空");
            return resp;
        }

        if (password.length() > 16 || password.length() < 6) {
            resp.setStatus(Response.FAILURE);
            resp.setMsg("密码长度为8到16位");
            return resp;
        }

        try {

            UserInfoEntity userReg = new UserInfoEntity();
            userReg.setUserName(username);
            userReg.setUserPhone(username);
            userReg.setPassword(password);

            UserInfoEntity user = userService.regist(userReg);
            userHelper.setUserSession(user);

            resp.setStatus(Response.SUCCESS);
        } catch (Exception e) {
            resp.setStatus(Response.FAILURE);
            resp.setMsg("内部错误,请稍后重试");
            log.error("登录异常", e);
        }
        return resp;
    }

}

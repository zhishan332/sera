package com.sera.controller;

import com.sera.config.ViewConfig;
import com.sera.dto.Response;
import com.sera.entity.UserInfoEntity;
import com.sera.helper.UserHelper;
import com.sera.service.UserService;
import com.sera.utils.CommonUtils;
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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登录和注册
 * Created by wangqing on 16/7/28.
 */
@Controller
@RequestMapping("")
public class LoginRegistController {
    private static final Logger log = LoggerFactory.getLogger(LoginRegistController.class);

    @Resource
    private UserService userService;

    @Resource
    private UserHelper userHelper;

    @Value("#{config['user.show.id']}")
    private long showUserId;

    /**
     * 使用用户名登录
     *
     * @param username 手机号
     * @param password 密码
     * @return 用户信息
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Response login(HttpSession httpSession, String username, String password, HttpServletResponse response) {
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
                //以后考虑加密
                Cookie cookie = new Cookie(ViewConfig.KEY_USER_COOKIE, username + "," + password);
                cookie.setMaxAge(60 * 60 * 24 * 365);//保存365天
                cookie.setPath("/");
                response.addCookie(cookie);

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
    public Response logout(HttpSession httpSession, HttpServletRequest request, HttpServletResponse response) {
        Response resp = new Response();

        try {
            httpSession.removeAttribute("user");
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (ViewConfig.KEY_USER_COOKIE.equals(cookie.getName())) {
                        cookie.setValue(null);
                        cookie.setMaxAge(0);
                        cookie.setPath("/");
                        response.addCookie(cookie);
                    }
                }
            }
            resp.setStatus(Response.SUCCESS);
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
    public Response regist(HttpSession httpSession, String username, String password, String code) {
        Response resp = new Response();

        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            resp.setStatus(Response.FAILURE);
            resp.setMsg("用户名密码不能为空");
            return resp;
        }

        String token = String.valueOf(httpSession.getAttribute("captchaToken"));

        if (StringUtils.isBlank(code)) {
            resp.setStatus(Response.FAILURE);
            resp.setMsg("验证码不能为空");
            return resp;
        }


        if (!code.equalsIgnoreCase(token)) {
            resp.setStatus(Response.FAILURE);
            resp.setMsg("验证码不正确");
            return resp;
        }

        boolean isMobileNo = false;
        boolean isEmail = false;
        if (CommonUtils.isMobileNo(username)) {
            isMobileNo = true;
        }

        if (CommonUtils.isEmail(username)) {
            isEmail = true;
        }

        if (!isEmail && !isMobileNo) {
            resp.setStatus(Response.FAILURE);
            resp.setMsg("用户名必须为手机号或者邮箱");
            return resp;
        }
        if (password.length() > 16 || password.length() < 6) {
            resp.setStatus(Response.FAILURE);
            resp.setMsg("密码长度为8到16位");
            return resp;
        }

        if (password.contains(",")) {
            resp.setStatus(Response.FAILURE);
            resp.setMsg("密码中不能包含:,");
            return resp;
        }


        try {

            UserInfoEntity userReg = new UserInfoEntity();

            if (isMobileNo) {
                userReg.setUserName(username);
                userReg.setUserPhone(username);
            } else {
                userReg.setUserName(username);
            }
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


    @RequestMapping(value = "/dipan", method = RequestMethod.GET)
    public ModelAndView infoImgUpload() {
        ModelAndView mav = new ModelAndView("dipan");
        return mav;
    }
}

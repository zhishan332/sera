package com.sera.inteceptor;

import com.sera.config.ViewConfig;
import com.sera.entity.UserInfoEntity;
import com.sera.helper.UserHelper;
import com.sera.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * cookie的验证
 * Created by wangqing on 16/7/22.
 */
public class CookieInterceptor extends HandlerInterceptorAdapter {
    private static final Logger log = LoggerFactory.getLogger(CookieInterceptor.class);

    @Resource
    private UserService userService;

    @Resource
    private UserHelper userHelper;

    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        UserInfoEntity user = (UserInfoEntity) request.getSession().getAttribute("user");
        if (user == null) {
            Cookie[] cookies = request.getCookies();
            if (cookies == null) return true;
            for (Cookie cookie : cookies) {
                if (ViewConfig.KEY_USER_COOKIE.equals(cookie.getName())) {
                    String text = cookie.getValue();
                    if (!StringUtils.isBlank(text)) {
                        String[] arry = text.split(",");
                        String username = arry[0];
                        String password = arry[1];
                        UserInfoEntity userInfo = userService.login(username, password);
                        if (userInfo != null) {
                            userHelper.setUserSession(userInfo);
                        }
                    }
                }
            }
        }
        return true;
    }
}

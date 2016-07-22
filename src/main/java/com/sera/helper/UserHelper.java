package com.sera.helper;

import com.sera.entity.UserInfoEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 用户信息
 * Created by wangqing on 16/7/12.
 */
@Component
public class UserHelper {

    public UserInfoEntity getUser() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        if (session != null) {
            return (UserInfoEntity) session.getAttribute("user");
        }
        return null;
    }

    public long getUserID() {
        UserInfoEntity user = this.getUser();
        if (null != user) return user.getUserId();
        return 0;
    }

    public boolean isLogin() {
        return getUserID() > 0;
    }

    public void setUserSession(UserInfoEntity dto) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute("user", dto);
    }
}

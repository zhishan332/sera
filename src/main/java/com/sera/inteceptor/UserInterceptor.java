package com.sera.inteceptor;

import com.sera.dto.Response;
import com.sera.entity.UserInfoEntity;
import com.sera.utils.JacksonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * session拦截，后台页面必须经过登录才可以访问
 *
 * @author wangqing
 * @since 14-11-26 下午1:04
 */
public class UserInterceptor extends HandlerInterceptorAdapter {
    private static final Logger log = LoggerFactory.getLogger(UserInterceptor.class);


    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        UserInfoEntity user = (UserInfoEntity) request.getSession().getAttribute("user");
        if (user == null) {
            log.warn("未进行登录，跳转到登录页面");
            Response resp = new Response();
            resp.setStatus(Response.NOLOGIN);
            resp.setMsg("请登录后使用该功能");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(JacksonUtils.defaultMapper().toJson(resp));
//            response.sendRedirect("/index");
            return false;
        }
        return true;
    }
}

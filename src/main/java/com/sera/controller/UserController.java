package com.sera.controller;

import com.sera.helper.UserHelper;
import com.sera.service.UserService;
import com.sera.utils.MagicUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    @Value("#{config['user.show.id']}")
    private long showUserId;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView showUser() {
        ModelAndView mav = new ModelAndView("user");
        mav.getModel().put("runTime", MagicUtils.getRunDay());
        mav.getModel().put("pageName", "用户中心");

        mav.getModel().put("createTime", new SimpleDateFormat("yyyy-MM-dd").format(new Date(userHelper.getUser().getCreateTime().getTime())));
        mav.getModel().put("username", userHelper.getUser().getUserName());
        mav.getModel().put("userID", userHelper.getUser().getUserId());
        return mav;
    }


}

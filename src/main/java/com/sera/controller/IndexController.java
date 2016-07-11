package com.sera.controller;

import com.sera.service.IndexService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * 首页
 * Created by wangqing on 16/5/13.
 */
@Controller
@RequestMapping("")
public class IndexController {

    @RequestMapping(value = {"", "index", "/index"}, method = RequestMethod.GET)
    public ModelAndView showIndex() {
        ModelAndView mav = new ModelAndView("index");
        mav.getModel().put("pageName", "首页");
        return mav;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ModelAndView showIndex2() {
        ModelAndView mav = new ModelAndView("test");
        mav.getModel().put("pageName", "test");
        return mav;
    }


}

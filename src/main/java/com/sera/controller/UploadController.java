package com.sera.controller;

import com.alibaba.fastjson.JSON;
import com.sera.dto.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 文件上传,所有上传必须要经过token验证，防止非法上传
 *
 * @author wangqing
 * @since 14-11-27 上午10:14
 */
@Controller
@RequestMapping("/import")
public class UploadController {
    private static final Logger log = LoggerFactory.getLogger(UploadController.class);

    @RequestMapping(value = "/bookmark", method = RequestMethod.POST)
    public void infoImgUpload(HttpServletResponse response, String timestamp, String token,
                              @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        response.setContentType("text/html");
        Response resp = new Response();
        resp.setStatus(Response.FAILURE);

        if (file == null || file.isEmpty()) {
            log.warn("上传文件为空，已终止！");
            resp.setMsg("上传文件为空，已终止！");
            response.getWriter().write(JSON.toJSONString(resp));
            return;
        }
        if (file.getSize() > 10 * 1024 * 1024) {
            log.warn("上传文件尺寸过大，已终止！最大尺寸是：" + 10 + "MB");
            resp.setMsg("上传文件尺寸过大，已终止！最大尺寸是：" + 10 + "MB");
            response.getWriter().write(JSON.toJSONString(resp));
            return;
        }
        try {
            byte[] bytes = file.getBytes();
            String str = new String(bytes, "UTF-8");
            log.info("导入的字符串:" + str);

            resp.setStatus(Response.SUCCESS);
            resp.setData("");
            response.getWriter().write(JSON.toJSONString(resp));
        } catch (Exception e) {
            log.error("上传图片处理失败", e);
            resp.setMsg("上传图片内部异常");
            response.getWriter().write(JSON.toJSONString(resp));
        }
    }
}

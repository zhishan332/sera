package com.sera.controller;

import com.sera.config.TaskConfig;
import com.sera.dto.Response;
import com.sera.helper.UserHelper;
import com.sera.task.TaskHelper;
import com.sera.utils.JacksonUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * 文件上传,所有上传必须要经过token验证，防止非法上传
 *
 * @author wangqing
 * @since 14-11-27 上午10:14
 */
@Controller
@RequestMapping("/import")
public class BookMarkUploadController {
    private static final Logger log = LoggerFactory.getLogger(BookMarkUploadController.class);

    @Resource
    private TaskHelper taskHelper;

    @Resource
    private UserHelper userHelper;

    @Value("#{config['upload.temp.path']}")
    private String uploadPath;

    @RequestMapping(value = "/bookmark", method = RequestMethod.POST)
    public void infoImgUpload(HttpServletResponse response, String timestamp, String token,
                              @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        response.setContentType("text/html");
        Response resp = new Response();
        resp.setStatus(Response.FAILURE);

        if (file == null || file.isEmpty()) {
            log.warn("上传文件为空，已终止！");
            resp.setMsg("上传文件为空，已终止！");
            response.getWriter().write(JacksonUtils.defaultMapper().toJson(resp));
            return;
        }
        if (file.getSize() > 10 * 1024 * 1024) {
            log.warn("上传文件尺寸过大，已终止！最大尺寸是：" + 10 + "MB");
            resp.setMsg("上传文件尺寸过大，已终止！最大尺寸是：" + 10 + "MB");
            response.getWriter().write(JacksonUtils.defaultMapper().toJson(resp));
            return;
        }
        try {
            String tempFile = uploadPath + File.separator + System.nanoTime() + ".htm";
            FileUtils.copyInputStreamToFile(file.getInputStream(), new File(tempFile));
            long userId = userHelper.getUserID();
            taskHelper.createTask(String.valueOf(userId), TaskConfig.UPLOAD_BOOKMARK_TASK_TYPE,
                    userHelper.getUser().getUserName(), tempFile);
            resp.setStatus(Response.SUCCESS);
            resp.setData("");
            response.getWriter().write(JacksonUtils.defaultMapper().toJson(resp));
        } catch (Exception e) {
            log.error("上传处理失败", e);
            resp.setMsg("上传内部异常");
            response.getWriter().write(JacksonUtils.defaultMapper().toJson(resp));
        }
    }


}

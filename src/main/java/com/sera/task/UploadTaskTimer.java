package com.sera.task;

import com.sera.entity.WorkTaskEntity;
import com.sera.service.UploadService;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 导入功能执行
 * Created by wangqing on 16/8/1.
 */
public class UploadTaskTimer extends AbstractTimer {

    @Resource
    private TaskHelper taskHelper;

    @Resource
    private UploadService uploadService;


    private String sqlCondition;

    @Override
    protected String getTimerName() {
        return null;
    }

    @Override
    public void run() {
        long taskID = 0;
        try {
            List<WorkTaskEntity> taskList = taskHelper.selectTask(sqlCondition);

            if (taskList != null && !taskList.isEmpty()) {
                WorkTaskEntity task = taskList.get(0);
                taskID = task.getId();
                String data = task.getTaskData();
                if (!StringUtils.isBlank(data)) {
                    boolean isOK = uploadService.parse(Long.parseLong(task.getRefId()), task.getKeyA(), task.getTaskData());
                    if (isOK) {
                        taskHelper.success(taskID);
                    } else {
                        taskHelper.fail(taskID);
                    }
                } else {
                    taskHelper.success(taskID);
                }
            }
        } catch (Exception e) {
            taskHelper.fail(taskID);
            log.error("任务执行失败", e);
        }
    }


    public void setSqlCondition(String sqlCondition) {
        this.sqlCondition = sqlCondition;
    }
}

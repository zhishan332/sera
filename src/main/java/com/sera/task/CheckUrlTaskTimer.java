package com.sera.task;

import com.sera.entity.WorkTaskEntity;
import com.sera.service.FavService;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 任务定时执行器
 * Created by wangqing on 16/7/25.
 */
public class CheckUrlTaskTimer extends AbstractTimer {

    @Resource
    private FavService favService;
    @Resource
    private TaskHelper taskHelper;

    private String sqlCondition;


    @Override
    protected String getTimerName() {
        return "检查url可用性";
    }


    @Override
    public void run() {
        long taskID = 0;
        try {
            List<WorkTaskEntity> taskList = taskHelper.selectTask(sqlCondition);

            if (taskList != null) {
                WorkTaskEntity task = taskList.get(0);
                taskID = task.getId();
                String data = task.getTaskData();
                if (!StringUtils.isBlank(data)) {
                    boolean isOK = favService.checkFav(Long.valueOf(data));
                    if (isOK) taskHelper.success(taskID);
                    else taskHelper.fail(taskID);
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

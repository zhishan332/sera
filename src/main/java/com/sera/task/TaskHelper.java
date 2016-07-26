package com.sera.task;

import com.sera.dao.WorkTaskMapper;
import com.sera.entity.WorkTaskEntity;
import com.sera.utils.TimeUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 任务帮助类
 * Created by wangqing on 16/7/25.
 */
@Component
public class TaskHelper {


    @Resource
    private WorkTaskMapper workTaskMapper;

    public boolean createTask(int taskType, String data) {
        WorkTaskEntity workTask = new WorkTaskEntity();
        long now = TimeUtils.getUnixTime();
        workTask.setStatus(1);
        workTask.setCreateTime(now);
        workTask.setUpdateTime(now);
        workTask.setTaskExeCount(0);
        workTask.setTaskType(taskType);
        return workTaskMapper.insertTask(workTask) > 0;
    }

    public boolean createTask(Object refId, int taskType) {
        WorkTaskEntity workTask = new WorkTaskEntity();
        long now = TimeUtils.getUnixTime();
        workTask.setStatus(1);
        workTask.setCreateTime(now);
        workTask.setUpdateTime(now);
        workTask.setTaskExeCount(0);
        workTask.setRefId(String.valueOf(refId));
        workTask.setTaskType(taskType);
        return workTaskMapper.insertTask(workTask) > 0;
    }

    public boolean createTask(Object refId, int taskType, String data) {
        WorkTaskEntity workTask = new WorkTaskEntity();
        long now = TimeUtils.getUnixTime();
        workTask.setStatus(1);
        workTask.setCreateTime(now);
        workTask.setUpdateTime(now);
        workTask.setTaskExeCount(0);
        workTask.setRefId(String.valueOf(refId));
        workTask.setTaskType(taskType);
        workTask.setTaskData(data);
        return workTaskMapper.insertTask(workTask) > 0;
    }

    public boolean createTask(Object refId, int taskType, String keyA, String data) {
        WorkTaskEntity workTask = new WorkTaskEntity();
        long now = TimeUtils.getUnixTime();
        workTask.setStatus(1);
        workTask.setCreateTime(now);
        workTask.setUpdateTime(now);
        workTask.setTaskExeCount(0);
        workTask.setRefId(String.valueOf(refId));
        workTask.setTaskType(taskType);
        workTask.setKeyA(keyA);
        workTask.setTaskData(data);
        return workTaskMapper.insertTask(workTask) > 0;
    }

    public boolean createNoDataTask(Object refId, int taskType, String keyA) {
        WorkTaskEntity workTask = new WorkTaskEntity();
        long now = TimeUtils.getUnixTime();
        workTask.setStatus(1);
        workTask.setCreateTime(now);
        workTask.setUpdateTime(now);
        workTask.setTaskExeCount(0);
        workTask.setRefId(String.valueOf(refId));
        workTask.setTaskType(taskType);
        workTask.setKeyA(keyA);
        return workTaskMapper.insertTask(workTask) > 0;
    }

    public boolean createNoDataTask(Object refId, int taskType, String keyA, String keyB) {
        WorkTaskEntity workTask = new WorkTaskEntity();
        long now = TimeUtils.getUnixTime();
        workTask.setStatus(1);
        workTask.setCreateTime(now);
        workTask.setUpdateTime(now);
        workTask.setTaskExeCount(0);
        workTask.setRefId(String.valueOf(refId));
        workTask.setTaskType(taskType);
        workTask.setKeyA(keyA);
        workTask.setKeyB(keyB);
        return workTaskMapper.insertTask(workTask) > 0;
    }

    public boolean createNoDataTask(Object refId, int taskType, String keyA, String keyB, String keyC) {
        WorkTaskEntity workTask = new WorkTaskEntity();
        long now = TimeUtils.getUnixTime();
        workTask.setStatus(1);
        workTask.setCreateTime(now);
        workTask.setUpdateTime(now);
        workTask.setTaskExeCount(0);
        workTask.setRefId(String.valueOf(refId));
        workTask.setTaskType(taskType);
        workTask.setKeyA(keyA);
        workTask.setKeyB(keyB);
        workTask.setKeyC(keyC);
        return workTaskMapper.insertTask(workTask) > 0;
    }

    public boolean createTask(Object refId, int taskType, String keyA, String keyB, String data) {
        WorkTaskEntity workTask = new WorkTaskEntity();
        long now = TimeUtils.getUnixTime();
        workTask.setStatus(1);
        workTask.setCreateTime(now);
        workTask.setUpdateTime(now);
        workTask.setTaskExeCount(0);
        workTask.setRefId(String.valueOf(refId));
        workTask.setTaskType(taskType);
        workTask.setKeyA(keyA);
        workTask.setKeyB(keyB);
        workTask.setTaskData(data);
        return workTaskMapper.insertTask(workTask) > 0;
    }

    public boolean createTask(Object refId, int taskType,
                              String keyA, String keyB, String keyC, String data) {
        WorkTaskEntity workTask = new WorkTaskEntity();
        long now = TimeUtils.getUnixTime();
        workTask.setStatus(1);
        workTask.setCreateTime(now);
        workTask.setUpdateTime(now);
        workTask.setTaskExeCount(0);
        workTask.setRefId(String.valueOf(refId));
        workTask.setTaskType(taskType);
        workTask.setKeyA(keyA);
        workTask.setKeyB(keyB);
        workTask.setKeyC(keyC);
        workTask.setTaskData(data);
        return workTaskMapper.insertTask(workTask) > 0;
    }

    public boolean createTaskWithoutId(int taskType,
                                       String keyA, String keyB, String keyC, String data) {
        WorkTaskEntity workTask = new WorkTaskEntity();
        long now = TimeUtils.getUnixTime();
        workTask.setStatus(1);
        workTask.setCreateTime(now);
        workTask.setUpdateTime(now);
        workTask.setTaskExeCount(0);
        workTask.setTaskType(taskType);
        workTask.setKeyA(keyA);
        workTask.setKeyB(keyB);
        workTask.setKeyC(keyC);
        workTask.setTaskData(data);
        return workTaskMapper.insertTask(workTask) > 0;
    }

    public List<WorkTaskEntity> selectTask(String condition) {
        return workTaskMapper.findTask(condition);
    }

    public WorkTaskEntity  getTaskByKeyA(String keyA) {
        return workTaskMapper.getTaskByKeyA(keyA);
    }

    public WorkTaskEntity  getTaskByRefId(String refId) {
        return workTaskMapper.getTaskByRefId(refId);
    }

    public boolean retry(long taskId) {
        return workTaskMapper.updateAddExeNum(taskId) > 0;
    }

    public boolean success(long taskId) {
        return workTaskMapper.deleteById(taskId) > 0;
    }

    public boolean fail(long taskId) {
        WorkTaskEntity workTask = new WorkTaskEntity();
        workTask.setId(taskId);
        workTask.setStatus(3);
        return workTaskMapper.updateTask(workTask) > 0;
    }

    public boolean updateToRun(long taskId){
        return workTaskMapper.updateToRun(taskId) > 0;
    }



}

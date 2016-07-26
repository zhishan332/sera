package com.sera.entity;

import java.io.Serializable;

/**
 * 对应数据库表名：work_task
 *
 * @since 2014-11-23 14:04:47
 */
public class WorkTaskEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;//id
	private String refId;//ref_id
	private Integer taskType;//task_type
	private String keyA;//key_a
	private String keyB;//key_b
	private String keyC;//key_c
	private String taskData;//task_data
	private Integer taskExeCount;//task_exe_count
	private Integer status;//status
	private Long createTime;//create_time
	private Long updateTime;//update_time

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public Integer getTaskType() {
		return taskType;
	}

	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}

	public String getKeyA() {
		return keyA;
	}

	public void setKeyA(String keyA) {
		this.keyA = keyA;
	}

	public String getKeyB() {
		return keyB;
	}

	public void setKeyB(String keyB) {
		this.keyB = keyB;
	}

	public String getKeyC() {
		return keyC;
	}

	public void setKeyC(String keyC) {
		this.keyC = keyC;
	}

	public String getTaskData() {
		return taskData;
	}

	public void setTaskData(String taskData) {
		this.taskData = taskData;
	}

	public Integer getTaskExeCount() {
		return taskExeCount;
	}

	public void setTaskExeCount(Integer taskExeCount) {
		this.taskExeCount = taskExeCount;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

}
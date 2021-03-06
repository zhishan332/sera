package com.sera.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 对应数据库表名：fav_group
 *
 * @since 2016-07-12 10:43:15
 */
public class FavGroupEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;//(字段：id)
	private Long groupId;//(字段：group_id)
	private String groupName;//(字段：group_name)
	private Integer covert;
	private Long userId;//(字段：user_id)
	private String userName;//(字段：user_name)
	private Timestamp createTime;//(字段：create_time)

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Integer getCovert() {
		return covert;
	}

	public void setCovert(Integer covert) {
		this.covert = covert;
	}
}
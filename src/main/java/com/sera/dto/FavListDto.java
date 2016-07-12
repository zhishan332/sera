package com.sera.dto;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 对应数据库表名：fav_list
 *
 * @since 2016-07-12 10:43:15
 */
public class FavListDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long favId;//(字段：fav_id)
	private Long groupId;//(字段：group_id)
	private String groupName;//(字段：group_name)
	private String favTitle;//(字段：fav_title)
	private String favUrl;//(字段：fav_url)
	private Long userId;//(字段：user_id)
	private String userName;//(字段：user_name)
	private Integer favStatus;//状态1表示正常，2表示已失效，其他状态值继续扩展(字段：fav_status)
	private Integer favFocus;//关注2其他为1(字段：fav_focus)
	private Integer favSort;//用于排序的字段(字段：fav_sort)
	private Timestamp createTime;//(字段：create_time)
	private Timestamp checkTime;//(字段：check_time)
	private Timestamp updateTime;//(字段：update_time)

	public Long getFavId() {
		return favId;
	}

	public void setFavId(Long favId) {
		this.favId = favId;
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

	public String getFavTitle() {
		return favTitle;
	}

	public void setFavTitle(String favTitle) {
		this.favTitle = favTitle;
	}

	public String getFavUrl() {
		return favUrl;
	}

	public void setFavUrl(String favUrl) {
		this.favUrl = favUrl;
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

	public Integer getFavStatus() {
		return favStatus;
	}

	public void setFavStatus(Integer favStatus) {
		this.favStatus = favStatus;
	}

	public Integer getFavFocus() {
		return favFocus;
	}

	public void setFavFocus(Integer favFocus) {
		this.favFocus = favFocus;
	}

	public Integer getFavSort() {
		return favSort;
	}

	public void setFavSort(Integer favSort) {
		this.favSort = favSort;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Timestamp checkTime) {
		this.checkTime = checkTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

}
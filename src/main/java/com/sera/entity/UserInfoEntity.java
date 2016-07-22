package com.sera.entity;

import java.io.Serializable;
import java.util.Date;
import java.sql.Timestamp;

/**
 * 对应数据库表名：user_info
 *
 * @since 2016-07-12 10:43:15
 */
public class UserInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;//(字段：id)
	private Long userId;//(字段：user_id)
	private String userName;//(字段：user_name)
	private String userPhone;//(字段：user_phone)
	private String password;//(字段：password)
	private Integer sex;//1表示男性2表示女性3表示其他(字段：sex)
	private Date birthday;//(字段：birthday)
	private String tinyurl;//(字段：tinyurl)
	private String headurl;//(字段：headurl)
	private String mainurl;//(字段：mainurl)
	private String hometown;//(字段：hometown)
	private String workHistory;//(字段：work_history)
	private String universityHistory;//(字段：university_history)
	private String hsHistory;//中学(字段：hs_history)
	private String province;//(字段：province)
	private String city;//(字段：city)
	private Long socialUid;//社会化平台的统一uid参见http://developer.baidu.com/wiki/index.php?title=docs/social/api/list(字段：social_uid)
	private String mediaUid;//第三方平台用户uid(字段：media_uid)
	private String meidaType;//第三方平台的标识(字段：meida_type)
	private String weibo;//(字段：weibo)
	private String qq;//(字段：qq)
	private Timestamp createTime;//(字段：create_time)

	private Timestamp updateTime;//(字段：create_time)

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getTinyurl() {
		return tinyurl;
	}

	public void setTinyurl(String tinyurl) {
		this.tinyurl = tinyurl;
	}

	public String getHeadurl() {
		return headurl;
	}

	public void setHeadurl(String headurl) {
		this.headurl = headurl;
	}

	public String getMainurl() {
		return mainurl;
	}

	public void setMainurl(String mainurl) {
		this.mainurl = mainurl;
	}

	public String getHometown() {
		return hometown;
	}

	public void setHometown(String hometown) {
		this.hometown = hometown;
	}

	public String getWorkHistory() {
		return workHistory;
	}

	public void setWorkHistory(String workHistory) {
		this.workHistory = workHistory;
	}

	public String getUniversityHistory() {
		return universityHistory;
	}

	public void setUniversityHistory(String universityHistory) {
		this.universityHistory = universityHistory;
	}

	public String getHsHistory() {
		return hsHistory;
	}

	public void setHsHistory(String hsHistory) {
		this.hsHistory = hsHistory;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Long getSocialUid() {
		return socialUid;
	}

	public void setSocialUid(Long socialUid) {
		this.socialUid = socialUid;
	}

	public String getMediaUid() {
		return mediaUid;
	}

	public void setMediaUid(String mediaUid) {
		this.mediaUid = mediaUid;
	}

	public String getMeidaType() {
		return meidaType;
	}

	public void setMeidaType(String meidaType) {
		this.meidaType = meidaType;
	}

	public String getWeibo() {
		return weibo;
	}

	public void setWeibo(String weibo) {
		this.weibo = weibo;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
}
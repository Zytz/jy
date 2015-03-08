package com.dxt.model;

import java.util.Date;

public class User{
	//
	private String id;// 主键
	private String email;// 用户邮箱
	private String nickName;// 用户昵称
	private String password;// 用户密码
	private String newPassword;//修改密码用，不用存库
	
	private String usertype;// 用户类型
	private String icon;//头像
	private String realname;//正式名字
	private String gender;//性别
	private Date birthday;//生日
	private String province;//省会
	private String city;//市
	private String school;//学校
	private String grade;//年级
	private String mobilePhone;//手机
	private String phone;//电话
	private String qq;
	private String sinaBlog;//新浪
	private String tencentBlog;//腾讯博客
	private Integer accountStatus;//用户状态  0 待激活，1激活。

	
	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
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

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getSinaBlog() {
		return sinaBlog;
	}

	public void setSinaBlog(String sinaBlog) {
		this.sinaBlog = sinaBlog;
	}

	public String getTencentBlog() {
		return tencentBlog;
	}

	public void setTencentBlog(String tencentBlog) {
		this.tencentBlog = tencentBlog;
	}

	public Integer getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(Integer accountStatus) {
		this.accountStatus = accountStatus;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}

}

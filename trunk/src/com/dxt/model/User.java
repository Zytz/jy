package com.dxt.model;

import java.util.Date;

public class User{
	//
private String id;// 主键
	
	private String email;// 用户邮箱
	
	private int balance;//余额
	
	private String icon;//头像
	
	private String realname;//真实姓名
	
	private String nickName;// 用户昵称
	
	//学生专属字段
	private String school;//学校（学生）
	
	private int grade;//年级（学生）
	
	private int tutoredSubject;//辅导科目（学生）
	
	private int beforeTutoring;//辅导前（学生）
	
	private int afterTutoring;//辅导后（学生）
	
	private String tutoringCenter;//辅导后（学生）
	
	private String studentCaseContent;//案例内容（学生）
	
	//教师专属字段
	private String graduateSchool;//毕业学校（教师）
	
	private String major;//专业（教师）
	
	private int teachPhase;//教授阶段（教师）
	
	private int teachSubject;//教授科目（教师）
	
	private String teacherIntroduction;//教师简介（教师）
	
	private String teachImages;//教学图片（教师）
	
	
	private int gender;//性别
	
	private String password;// 用户密码
	
	private String newPassword;//修改密码用，不用存库
	
	private String phone;//电话 
	
	private String mobilePhone;//手机
	
	private String qq;
	
	private String sinaBlog;
	
	private String tencentBlog;
	
	private int userType;// 用户类型
	
	private int accountStatus;// 用户类型
	
	private int recommend;// 是否推荐该用户
	
	private Date birthday;//生日
	
	private String province;//省会
	
	private String city;//市
	
	private Date created;
	
	private Date updated;
	
	private Date deleted;//删除时间
	
	private int hasPublishRight;//发布权限（教师）

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

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
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

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public int getTutoredSubject() {
		return tutoredSubject;
	}

	public void setTutoredSubject(int tutoredSubject) {
		this.tutoredSubject = tutoredSubject;
	}

	public int getBeforeTutoring() {
		return beforeTutoring;
	}

	public void setBeforeTutoring(int beforeTutoring) {
		this.beforeTutoring = beforeTutoring;
	}

	public int getAfterTutoring() {
		return afterTutoring;
	}

	public void setAfterTutoring(int afterTutoring) {
		this.afterTutoring = afterTutoring;
	}

	public String getTutoringCenter() {
		return tutoringCenter;
	}

	public void setTutoringCenter(String tutoringCenter) {
		this.tutoringCenter = tutoringCenter;
	}

	public String getStudentCaseContent() {
		return studentCaseContent;
	}

	public void setStudentCaseContent(String studentCaseContent) {
		this.studentCaseContent = studentCaseContent;
	}

	public String getGraduateSchool() {
		return graduateSchool;
	}

	public void setGraduateSchool(String graduateSchool) {
		this.graduateSchool = graduateSchool;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public int getTeachPhase() {
		return teachPhase;
	}

	public void setTeachPhase(int teachPhase) {
		this.teachPhase = teachPhase;
	}

	public int getTeachSubject() {
		return teachSubject;
	}

	public void setTeachSubject(int teachSubject) {
		this.teachSubject = teachSubject;
	}

	public String getTeacherIntroduction() {
		return teacherIntroduction;
	}

	public void setTeacherIntroduction(String teacherIntroduction) {
		this.teacherIntroduction = teacherIntroduction;
	}

	public String getTeachImages() {
		return teachImages;
	}

	public void setTeachImages(String teachImages) {
		this.teachImages = teachImages;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public int getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(int accountStatus) {
		this.accountStatus = accountStatus;
	}

	public int getRecommend() {
		return recommend;
	}

	public void setRecommend(int recommend) {
		this.recommend = recommend;
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

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public Date getDeleted() {
		return deleted;
	}

	public void setDeleted(Date deleted) {
		this.deleted = deleted;
	}

	public int getHasPublishRight() {
		return hasPublishRight;
	}

	public void setHasPublishRight(int hasPublishRight) {
		this.hasPublishRight = hasPublishRight;
	}

	

}

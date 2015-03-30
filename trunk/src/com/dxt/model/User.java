package com.dxt.model;

import java.util.Date;

public class User{
	//
private String id;// ����
	
	private String email;// �û�����
	
	private double balance;//���

	
	private String icon;//ͷ��
	
	private String realname;//��ʵ����
	
	private String nickName;// �û��ǳ�
	
	//ѧ��ר���ֶ�
	private String school;//ѧУ��ѧ����
	
	private int grade;//�꼶��ѧ����
	
	private int tutoredSubject;//������Ŀ��ѧ����
	
	private int beforeTutoring;//����ǰ��ѧ����
	
	private int afterTutoring;//������ѧ����
	
	private String tutoringCenter;//������ѧ����
	
	private String studentCaseContent;//�������ݣ�ѧ����
	
	//��ʦר���ֶ�
	private String graduateSchool;//��ҵѧУ����ʦ��
	
	private String major;//רҵ����ʦ��
	
	private int teachPhase;//���ڽ׶Σ���ʦ��
	
	private int teachSubject;//���ڿ�Ŀ����ʦ��
	
	private String teacherIntroduction;//��ʦ��飨��ʦ��
	
	private String teachImages;//��ѧͼƬ����ʦ��
	
	
	private int gender;//�Ա�
	
	private String password;// �û�����
	
	private String newPassword;//�޸������ã����ô��
	
	private String phone;//�绰 
	
	private String mobilePhone;//�ֻ�
	
	private String qq;
	
	private String sinaBlog;
	
	private String tencentBlog;
	
	private int userType;// �û�����
	
	private int accountStatus;// �û�����
	
	private int recommend;// �Ƿ��Ƽ����û�
	
	private Date birthday;//����
	
	private String province;//ʡ��
	
	private String city;//��
	
	private Date created;
	
	private Date updated;
	
	private Date deleted;//ɾ��ʱ��
	
	private int hasPublishRight;//����Ȩ�ޣ���ʦ��

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

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
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

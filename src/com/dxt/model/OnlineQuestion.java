package com.dxt.model;

import java.util.Date;

public class OnlineQuestion {
	private String id;
	
	private String grade;//年级
	
	private String subject;//学科
	
	private Integer rewardPoint;//悬赏分
	
	private int viewCount;//问题被浏览的次数
	
	private int answerCount;//问题解答数
	
	private String textDescription;//问题描述
	
	private String questionImage;//问题图片 
	
	private String studentId;//存储用户Id
	private String studentName;//若存在昵称，直接存储昵称；若不存在昵称，存储为：学员****** 形式 如：学员210489
	
	private String studentIcon;//学员头像 
	
	private Date created;
	
	private Date updated;
	
	private Date deleted;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public int getRewardPoint() {
		return rewardPoint;
	}

	public void setRewardPoint(int rewardPoint) {
		this.rewardPoint = rewardPoint;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}
	
	public int getAnswerCount() {
		return answerCount;
	}

	public void setAnswerCount(int answerCount) {
		this.answerCount = answerCount;
	}

	public String getTextDescription() {
		return textDescription;
	}

	public void setTextDescription(String textDescription) {
		this.textDescription = textDescription;
	}

	public String getQuestionImage() {
		return questionImage;
	}

	public void setQuestionImage(String questionImage) {
		this.questionImage = questionImage;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getStudentIcon() {
		return studentIcon;
	}

	public void setStudentIcon(String studentIcon) {
		this.studentIcon = studentIcon;
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

	@Override
	public String toString() {
		return "OnlineQuestion [id=" + id + ", grade=" + grade + ", subject="
				+ subject + ", rewardPoint=" + rewardPoint + ", viewCount="
				+ viewCount + ", answerCount=" + answerCount
				+ ", textDescription=" + textDescription + ", questionImage="
				+ questionImage + ", studentId=" + studentId + ", studentName="
				+ studentName + ", studentIcon=" + studentIcon + ", created="
				+ created + ", updated=" + updated + ", deleted=" + deleted
				+ "]";
	}
	
}

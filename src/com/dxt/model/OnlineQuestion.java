package com.dxt.model;

import java.util.Date;

public class OnlineQuestion {
private String id;
	
	private int grade;//�꼶
	
	private int subject;//ѧ��
	
	private int rewardPoint;//���ͷ�
	
	private int viewCount;//���ⱻ����Ĵ���
	
	private int answerCount;//��������
	
	private int adopted;
	
	private String textDescription;//��������
	
	private String questionImage;//����ͼƬ 
	
	private String studentId;//�洢�û�Id
	
	private String studentName;//�������ǳƣ�ֱ�Ӵ洢�ǳƣ����������ǳƣ��洢Ϊ��ѧԱ****** ��ʽ �磺ѧԱ210489
	
	private String studentIcon;//ѧԱͷ�� 
	
	private Date created;
	
	private Date updated;
	
	private Date deleted;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public int getSubject() {
		return subject;
	}

	public void setSubject(int subject) {
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
	
	public int getAdopted() {
		return adopted;
	}

	public void setAdopted(int adopted) {
		this.adopted = adopted;
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

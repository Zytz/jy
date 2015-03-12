package com.dxt.model;

public class SearchOnlineQuestionBean {
	private String grade;
	private String subject;
	private int orderWay=3;
	private int pageNum=0;
	private int pageSize=2;
	
	private String studentId;
	
	
	
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
	public int getOrderWay() {
		return orderWay;
	}
	public void setOrderWay(int orderWay) {
		this.orderWay = orderWay;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	@Override
	public String toString() {
		return "SearchOnlineQuestionBean [grade=" + grade + ", subject="
				+ subject + ", orderWay=" + orderWay + ", pageNum=" + pageNum
				+ ", pageSize=" + pageSize + ", studentId=" + studentId + "]";
	}
	
}

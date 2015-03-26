package com.dxt.model;

public class SearchOnlineQuestionBean {
	private int grade=-1;
	private int subject=-1;
	private int orderWay=-1;
	private int number =0;
	private int pageNum=0;
	private int pageSize=2;
	
	private String studentId;
	private String studentName;
	
	
	
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
	public int getOrderWay() {
		return orderWay;
	}
	public void setOrderWay(int orderWay) {
		this.orderWay = orderWay;
	}
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
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
	
	
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	@Override
	public String toString() {
		return "SearchOnlineQuestionBean [grade=" + grade + ", subject="
				+ subject + ", orderWay=" + orderWay + ", pageNum=" + pageNum
				+ ", pageSize=" + pageSize + ", studentId=" + studentId + "]";
	}
	
}

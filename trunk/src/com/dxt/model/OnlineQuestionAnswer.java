package com.dxt.model;

import java.util.Date;

public class OnlineQuestionAnswer {
	
	private String id;
	
	private String answerAuthor;
	
	private String answerAuthorId;
	
	private String textAnswer;
	
	private String imageAnswer;
	
	private int adopted;
	
	private String onlineQuestionId;
	
	private Date created;
	
	private Date updated;
	
	private Date deleted;

	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAnswerAuthor() {
		return answerAuthor;
	}

	public void setAnswerAuthor(String answerAuthor) {
		this.answerAuthor = answerAuthor;
	}

	public String getAnswerAuthorId() {
		return answerAuthorId;
	}

	public void setAnswerAuthorId(String answerAuthorId) {
		this.answerAuthorId = answerAuthorId;
	}

	public String getTextAnswer() {
		return textAnswer;
	}

	public void setTextAnswer(String textAnswer) {
		this.textAnswer = textAnswer;
	}

	public String getImageAnswer() {
		return imageAnswer;
	}

	public void setImageAnswer(String imageAnswer) {
		this.imageAnswer = imageAnswer;
	}
	
	public int getAdopted() {
		return adopted;
	}

	public void setAdopted(int adopted) {
		this.adopted = adopted;
	}

	public String getOnlineQuestionId() {
		return onlineQuestionId;
	}

	public void setOnlineQuestionId(String onlineQuestionId) {
		this.onlineQuestionId = onlineQuestionId;
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
		return "OnlineQuestionAnswer [id=" + id + ", answerAuthor="
				+ answerAuthor + ", answerAuthorId=" + answerAuthorId
				+ ", textAnswer=" + textAnswer + ", imageAnswer=" + imageAnswer
				+ ", onlineQuestionId=" + onlineQuestionId + ", created="
				+ created + ", updated=" + updated + ", deleted=" + deleted
				+ "]";
	}
}

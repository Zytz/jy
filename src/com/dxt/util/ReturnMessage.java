package com.dxt.util;

public class ReturnMessage {
	private int status;
	private String message;
	private String data;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "ReturnMessage [status=" + status + ", message=" + message + "]";
	}
	
}

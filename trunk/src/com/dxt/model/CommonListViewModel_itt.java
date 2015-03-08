package com.dxt.model;

public class CommonListViewModel_itt {


	private int icon;
	private String name;
	private String message;

	public CommonListViewModel_itt() {
		super();
	}

	public CommonListViewModel_itt(int icon, String name, String message) {
		super();
		this.icon = icon;
		this.name = name;
		this.message = message;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}




	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "UserInfo [icon=" + icon + ", name=" + name + ", message="
				+ message + "]";
	}
}

package com.dxt.model;

public class CommonListViewModel {


	private int icon;
	private String name;
	private int message;

	public CommonListViewModel() {
		super();
	}

	public CommonListViewModel(int icon, String name, int message) {
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



	public int getMessage() {
		return message;
	}

	public void setMessage(int message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "UserInfo [icon=" + icon + ", name=" + name + ", message="
				+ message + "]";
	}
}

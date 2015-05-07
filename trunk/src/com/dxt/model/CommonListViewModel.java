package com.dxt.model;

public class CommonListViewModel {


	private int icon;
	private String name;

	public CommonListViewModel() {
		super();
	}

	public CommonListViewModel(int icon, String name) {
		super();
		this.icon = icon;
		this.name = name;
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

/*
	@Override
	public String toString() {
		return "UserInfo [icon=" + icon + ", name=" + name + ", message="
				+ message + "]";
	}*/
	
	
}

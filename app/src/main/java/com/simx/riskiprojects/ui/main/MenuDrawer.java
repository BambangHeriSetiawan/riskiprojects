package com.simx.riskiprojects.ui.main;

/**
 * User: simx Date: 15/08/18 0:23
 */
public class MenuDrawer {
	private String title;
	private String code;
	private int icon;

	public MenuDrawer(String title, String code, int icon) {
		this.title = title;
		this.code = code;
		this.icon = icon;
	}

	public MenuDrawer() {
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}

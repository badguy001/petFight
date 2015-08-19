package com.me;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
	private String url = null; //¿÷∂∑url
	private String isfighted = null;  //“—¿÷∂∑
	private String QQ = null;  //qq
	private String level = null; //’Ω¡¶
	private static String reg = "B_UID=([0-9]+)";
	private static int group = 1;
	private static Pattern pattern = Pattern.compile(reg);
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIsfighted() {
		return isfighted;
	}
	public void setIsfighted(String isfighted) {
		this.isfighted = isfighted;
	}
	public String getQQ() {
		return QQ;
	}
	public void setQQ(String qQ) {
		QQ = qQ;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	
	public User(String url) {
		setUrl(url);
		Matcher m = pattern.matcher(url);
		if (m.find()) 
			QQ = m.group(group);
	}
	
}

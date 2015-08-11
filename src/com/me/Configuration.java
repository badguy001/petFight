package com.me;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Configuration {
	private String userAgent;
	private String loginURL;
	private String username;
	private String Configure;
	
	private String confPath = "Configure.xml";
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private String password;
	public String getLoginURL() {
		return loginURL;
	}

	public void setLoginURL(String loginURL) {
		this.loginURL = loginURL;
	}

	public List<String> getAttrName() {
		return attrName;
	}

	public void setAttrName(List<String> attrName) {
		this.attrName = attrName;
	}

	public String getLoginNameAttrName() {
		return loginNameAttrName;
	}

	public void setLoginNameAttrName(String loginNameAttrName) {
		this.loginNameAttrName = loginNameAttrName;
	}

	public String getLoginPwdAttrName() {
		return loginPwdAttrName;
	}

	public void setLoginPwdAttrName(String loginPwdAttrName) {
		this.loginPwdAttrName = loginPwdAttrName;
	}

	private List<String> attrName;
	private String loginNameAttrName;
	private String loginPwdAttrName;
	
	Configuration(){
		
		File f = new File(confPath);
		BufferedReader reader = null;
		String s = null;
		try {
			reader = new BufferedReader( new FileReader(f) );
			String tmp = null;
			while ((tmp=reader.readLine()) != null)
				s += tmp;
		} catch (FileNotFoundException e) {
			System.out.println("Configuration File " + confPath + " not found!");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("read from " + confPath + " fail!");
			e.printStackTrace();
		}
		attrName = new ArrayList<>();
		attrName.add("sid");
		attrName.add("sidtype");
		attrName.add("hiddenPwd");
		attrName.add("aid");
		attrName.add("go_url");
		attrName.add("login_url");
		loginNameAttrName = "qq";
		loginPwdAttrName = "pwd";
		loginURL = "http://dld.qzapp.z.qq.com";
		username = "1129426277";
		password = "Djh19931004";
	}
	
	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
}

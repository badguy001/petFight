package com.me;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;


public class Configuration {
	private String userAgent;
	private String loginURL;
	private String username;
	private Clickable root;
	private String maxzhanli;
	private String maxlevel;
	private String minlevel;
	public Clickable getRoot() {
		return root;
	}

	public void setRoot(Clickable root) {
		this.root = root;
	}

	private String confPath = "Configuration.xml";
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

	public List<String> getLoginAttrName() {
		return loginAttrName;
	}

	public void setLoginAttrName(List<String> attrName) {
		this.loginAttrName = attrName;
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

	private List<String> loginAttrName;
	private String loginNameAttrName;
	private String loginPwdAttrName;
	private String[] verifyAttrName;
	private String verifyname;
	private String [] verifyfresh;
	public String[] getVerifyAttrName() {
		return verifyAttrName;
	}

	public void setVerifyAttrName(String[] verifyAttrName) {
		this.verifyAttrName = verifyAttrName;
	}

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
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		Source source = new Source(s);
		Element e = source.getFirstElement("login");
		loginAttrName = new ArrayList<>();
		for( String s1:e.getAttributeValue("params").split(",")){
			loginAttrName.add(s1);
		}
		loginNameAttrName = e.getAttributeValue("loginNameAttrName");
		loginPwdAttrName = e.getAttributeValue("loginPwdAttrName");
		loginURL = e.getAttributeValue("host");
		username = e.getAttributeValue("username");
		password = e.getAttributeValue("password");
		e = source.getFirstElement("loginverify");
		verifyAttrName = e.getAttributeValue("params").split(",");
		setVerifyname(e.getAttributeValue("verifyname"));
		e = source.getFirstElement("verifyfresh");
		setVerifyfresh(e.getAttributeValue("params") == null ? null : e.getAttributeValue("params").split(","));
		root = new Clickable(source.getFirstElement("root"));
		e = source.getFirstElement("fightconfig");
		maxzhanli = e.getAttributeValue("maxzhanli");
		maxlevel = e.getAttributeValue("maxlevel");
		minlevel = e.getAttributeValue("minlevel");
	}
	
	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getVerifyname() {
		return verifyname;
	}

	public void setVerifyname(String verifyname) {
		this.verifyname = verifyname;
	}

	public String [] getVerifyfresh() {
		return verifyfresh;
	}

	public void setVerifyfresh(String [] verifyfresh) {
		this.verifyfresh = verifyfresh;
	}

	public String getMaxzhanli() {
		return maxzhanli;
	}

	public void setMaxzhanli(String maxzhanli) {
		this.maxzhanli = maxzhanli;
	}

	public String getMaxlevel() {
		return maxlevel;
	}

	public void setMaxlevel(String maxlevel) {
		this.maxlevel = maxlevel;
	}

	public String getMinlevel() {
		return minlevel;
	}

	public void setMinlevel(String minlevel) {
		this.minlevel = minlevel;
	}

	public String getConfPath() {
		return confPath;
	}

	public void setConfPath(String confPath) {
		this.confPath = confPath;
	}
}

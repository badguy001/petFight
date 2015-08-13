package com.me;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.htmlparser.jericho.Element;

public class Clickable implements Comparable<Clickable>{
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String[] getParams() {
		return params;
	}
	public void setParams(String[] params) {
		this.params = params;
	}
	public String getInparams() {
		return inparams;
	}
	public void setInparams(String inparams) {
		this.inparams = inparams;
	}
	List<Clickable> childs;
	public List<Clickable> getChilds() {
		return childs;
	}
	public void setChilds(List<Clickable> childs) {
		this.childs = childs;
	}
	private String contain;
	public String getContain() {
		return contain;
	}
	public void setContain(String contian) {
		this.contain = contian;
	}
	private String name;
	private String params[];
	private String inparams;
	private int order;
	private String context;
	private int freshtime;
	public int getFreshtime() {
		return freshtime;
	}
	public void setFreshtime(int freshtime) {
		this.freshtime = freshtime;
	}
	public int getFreshwait() {
		return freshwait;
	}
	public void setFreshwait(int freshwait) {
		this.freshwait = freshwait;
	}
	private int freshwait;
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public Clickable(Element e) {
		params = e.getAttributeValue("params") == null ? null : e.getAttributeValue("params").split(",");
		setInparams(e.getAttributeValue("inparams"));
		setName(e.getAttributeValue("name"));
		setOrder(e.getAttributeValue("order") == null || e.getAttributeValue("order").equals("") ? 100 : Integer.valueOf(e.getAttributeValue("order")));
		setContain(e.getAttributeValue("contain"));
		setContext(e.getAttributeValue("context"));
		setFreshtime(e.getAttributeValue("freshtime") == null || e.getAttributeValue("freshtime").equals("") ? 0 : Integer.valueOf(e.getAttributeValue("freshtime")));
		setFreshwait(e.getAttributeValue("freshwait") == null || e.getAttributeValue("freshwait").equals("") ? 0 : Integer.valueOf(e.getAttributeValue("freshwait")));
		childs = getChilds(e);
		if ( childs != null ){
			Collections.sort(childs);
		}
	}
	
	private List<Clickable> getChilds(Element e) {
		List<Clickable> l = null;
		List<Element> childs = e.getChildElements();
		if (childs.size()==0)
			return l;
		l = new ArrayList<>();
		for (Element e1:childs) {
			Clickable c = new Clickable(e1);
			l.add(c);
		}
		return l;
	}
	
	public int compareTo(Clickable o) {
		if (order > o.order)
			return 1;
		else if (order == o.order)
			return 0;
		else 
			return -1;
	}
}

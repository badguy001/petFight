package com.me;

import java.util.ArrayList;
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
	private String name;
	private String params[];
	private String inparams;
	private int order;
	
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
		setOrder(e.getAttributeValue("order") == null || e.getAttributeValue("order").equals("") ? 0 : Integer.valueOf(e.getAttributeValue("order")));
		childs = getChilds(e);
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

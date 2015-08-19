package com.me;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.htmlparser.jericho.Element;

public class Clickable implements Comparable<Clickable>{
	private String containor[];
	private String contain;
	private String name;
	private String params[];
	private String inparams;
	private int order;
	private String context;
	private int freshtime;
	private List<TimeRange> timeranges;
	private String containand[];
	private int freshwait;
	List<Clickable> childs;
	private String paragrapheand[];
	private String paragrapheor[];
	private String notparagrapheand[];
	private String notparagrapheor[];
	private String notcontainand[];
	private String notcontainor[];
	private String notcontextand[];
	private String notcontextor[];
	private boolean disablebelow;
	private String resultreg[];
	private String contextand[];
	private String contextor[];
	private boolean isshow;
	private boolean passthis;
	private boolean disable;
	private boolean sethost;
	private boolean isfight;
	private String aboveparagrapheor[];
	public String[] getNotcontextand() {
		return notcontextand;
	}
	public void setNotcontextand(String[] notcontextand) {
		this.notcontextand = notcontextand;
	}
	public String[] getNotcontainand() {
		return notcontainand;
	}
	public void setNotcontainand(String[] notcontainand) {
		this.notcontainand = notcontainand;
	}
	public String[] getNotcontainor() {
		return notcontainor;
	}
	public void setNotcontainor(String[] notcontainor) {
		this.notcontainor = notcontainor;
	}
	public String[] getNotparagrapheand() {
		return notparagrapheand;
	}
	public void setNotparagrapheand(String[] notparagrapheand) {
		this.notparagrapheand = notparagrapheand;
	}
	public String[] getNotparagrapheor() {
		return notparagrapheor;
	}
	public void setNotparagrapheor(String[] notparagrapheor) {
		this.notparagrapheor = notparagrapheor;
	}
	public String[] getParagrapheand() {
		return paragrapheand;
	}
	public void setParagrapheand(String[] paragrapheand) {
		this.paragrapheand = paragrapheand;
	}
	public String[] getParagrapheor() {
		return paragrapheor;
	}
	public void setParagrapheor(String[] paragrapheor) {
		this.paragrapheor = paragrapheor;
	}
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
	public List<Clickable> getChilds() {
		return childs;
	}
	public void setChilds(List<Clickable> childs) {
		this.childs = childs;
	}
	public String getContain() {
		return contain;
	}
	public void setContain(String contian) {
		this.contain = contian;
	}
	public String[] getContainand() {
		return containand;
	}
	public void setContainand(String[] containand) {
		this.containand = containand;
	}
	public String[] getContainor() {
		return containor;
	}
	public void setContainor(String[] containor) {
		this.containor = containor;
	}
	public List<TimeRange> getTimeranges() {
		return timeranges;
	}
	public void setTimeranges(List<TimeRange> timeranges) {
		this.timeranges = timeranges;
	}
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
		setDisable(e.getAttributeValue("disable") == null || !e.getAttributeValue("disable").equals("1") ? false : true);
		if (isDisable())
			return;
		params = e.getAttributeValue("params") == null ? null : e.getAttributeValue("params").split(",");
		setInparams(e.getAttributeValue("inparams"));
		setName(e.getAttributeValue("name"));
		setOrder(e.getAttributeValue("order") == null || e.getAttributeValue("order").equals("") ? 100 : Integer.valueOf(e.getAttributeValue("order")));
		setContain(e.getAttributeValue("contain"));
		setContext(e.getAttributeValue("context"));
		setFreshtime(e.getAttributeValue("freshtime") == null || e.getAttributeValue("freshtime").equals("") ? 0 : Integer.valueOf(e.getAttributeValue("freshtime")));
		setFreshwait(e.getAttributeValue("freshwait") == null || e.getAttributeValue("freshwait").equals("") ? 0 : Integer.valueOf(e.getAttributeValue("freshwait")));
		setTimeranges(getTimeranges(e.getAttributeValue("timeranges")));
		setContainand(e.getAttributeValue("containand") == null ? null : e.getAttributeValue("containand").split("&&"));
		setContainor(e.getAttributeValue("containor") == null ? null : e.getAttributeValue("containor").split("&&"));
		setParagrapheand(e.getAttributeValue("paragrapheand") == null ? null : e.getAttributeValue("paragrapheand").split("&&"));
		setParagrapheor(e.getAttributeValue("paragrapheor") == null ? null : e.getAttributeValue("paragrapheor").split("&&"));
		setNotparagrapheand(e.getAttributeValue("notparagrapheand") == null ? null : e.getAttributeValue("notparagrapheand").split("&&"));
		setNotparagrapheor(e.getAttributeValue("notparagrapheor") == null ? null : e.getAttributeValue("notparagrapheor").split("&&"));
		setNotcontainand(e.getAttributeValue("notcontainand") == null ? null : e.getAttributeValue("notcontainand").split("&&"));
		setNotcontainor(e.getAttributeValue("notcontainor") == null ? null : e.getAttributeValue("notcontainor").split("&&"));
		setNotcontextand(e.getAttributeValue("notcontextand") == null ? null : e.getAttributeValue("notcontextand").split("&&"));
		setNotcontextor(e.getAttributeValue("notcontextor") == null ? null : e.getAttributeValue("notcontextor").split("&&"));
		setDisablebelow(e.getAttributeValue("disablebelow") == null || Integer.valueOf(e.getAttributeValue("disablebelow")) != 1 ? false : true ); 
		setResultreg(e.getAttributeValue("resultreg") == null || e.getAttributeValue("resultreg").equals("") ? null : e.getAttributeValue("resultreg").split("&&"));
		setContextand(e.getAttributeValue("contextand") == null ? null : e.getAttributeValue("contextand").split("&&"));
		setContextor(e.getAttributeValue("contextor") == null ? null : e.getAttributeValue("contextor").split("&&"));
		setIsshow(e.getAttributeValue("isshow") == null || !e.getAttributeValue("isshow").equals("1") ? false : true);
		setPassthis(e.getAttributeValue("passthis") == null || !e.getAttributeValue("passthis").equals("1") ? false : true);
		setSethost(e.getAttributeValue("sethost") == null || !e.getAttributeValue("sethost").equals("1") ? false : true);
		setIsfight(e.getAttributeValue("isfight") == null || !e.getAttributeValue("isfight").equals("1") ? false : true);
		setAboveparagraphe(e.getAttributeValue("aboveparagrapheor") == null ? null : e.getAttributeValue("aboveparagrapheor").split("&&"));
		if (timeranges != null)
			Collections.sort(timeranges);
		childs = getChilds(e);
		if ( childs != null ){
			Collections.sort(childs);
		}
	}
	
	private List<TimeRange> getTimeranges(String ranges){
		if (ranges == null)
			return null;
		List<TimeRange> l = new ArrayList<>();
		String s[] = ranges.split(",");
		for (String s1:s)
			l.add(new TimeRange(s1));
		return l;
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
	public String[] getNotcontextor() {
		return notcontextor;
	}
	public void setNotcontextor(String notcontextor[]) {
		this.notcontextor = notcontextor;
	}
	public boolean isDisablebelow() {
		return disablebelow;
	}
	public void setDisablebelow(boolean disablebelow) {
		this.disablebelow = disablebelow;
	}
	public String[] getResultreg() {
		return resultreg;
	}
	public void setResultreg(String resultreg[]) {
		this.resultreg = resultreg;
	}
	public String[] getContextor() {
		return contextor;
	}
	public void setContextor(String contextor[]) {
		this.contextor = contextor;
	}
	public String[] getContextand() {
		return contextand;
	}
	public void setContextand(String contextand[]) {
		this.contextand = contextand;
	}
	public boolean getIsshow() {
		return isshow;
	}
	public void setIsshow(boolean isshow) {
		this.isshow = isshow;
	}
	public boolean isPassthis() {
		return passthis;
	}
	public void setPassthis(boolean passthis) {
		this.passthis = passthis;
	}
	public boolean isDisable() {
		return disable;
	}
	public void setDisable(boolean disable) {
		this.disable = disable;
	}
	public boolean isSethost() {
		return sethost;
	}
	public void setSethost(boolean sethost) {
		this.sethost = sethost;
	}
	public boolean isIsfight() {
		return isfight;
	}
	public void setIsfight(boolean isfight) {
		this.isfight = isfight;
	}
	public String[] getAboveparagraphe() {
		return aboveparagrapheor;
	}
	public void setAboveparagraphe(String aboveparagraphe[]) {
		this.aboveparagrapheor = aboveparagraphe;
	}

}

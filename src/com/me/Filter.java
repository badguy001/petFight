package com.me;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.dom4j.Element;



public class Filter implements Comparable<Filter>{
	private String containor[];
	private String name;
	private String inparams;
	private int order;
	private String context;
	private List<TimeRange> timeranges;
	private String containand[];
	List<Filter> childs;
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
	private boolean isfight;
	private String aboveparagrapheor[];
	private String belowparagrapheor[];
	private String belowparagrapheand[];
	private String belowparagraphe;
	private int waittime;
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
	public String getInparams() {
		return inparams;
	}
	public void setInparams(String inparams) {
		this.inparams = inparams;
	}
	public List<Filter> getChilds() {
		return childs;
	}
	public void setChilds(List<Filter> childs) {
		this.childs = childs;
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
	public Filter(Element e) {
		setDisable(e.attributeValue(StaticVar.disable) == null || !e.attributeValue(StaticVar.disable).equals("1") ? false : true);
		if (isDisable())
			return;
		setInparams(e.attributeValue(StaticVar.inparams) == null || e.attributeValue(StaticVar.inparams).equals("") ? null : e.attributeValue(StaticVar.inparams));
		setName(e.attributeValue(StaticVar.name) == null || e.attributeValue(StaticVar.name).equals("") ? null : e.attributeValue(StaticVar.name));
		setOrder(e.attributeValue(StaticVar.order) == null || e.attributeValue(StaticVar.order).equals("") ? 100 : Integer.valueOf(e.attributeValue(StaticVar.order)));
		setContext(e.attributeValue(StaticVar.context) == null || e.attributeValue(StaticVar.context).equals("") ? null : e.attributeValue(StaticVar.context));
		setTimeranges(getTimeranges(e.attributeValue(StaticVar.timeranges)));
		setContainand(e.attributeValue(StaticVar.containand) == null || e.attributeValue(StaticVar.containand).equals("") ? null : e.attributeValue(StaticVar.containand).split(StaticVar.SpiltString));
		setContainor(e.attributeValue(StaticVar.containor) == null || e.attributeValue(StaticVar.containor).equals("") ? null : e.attributeValue(StaticVar.containor).split(StaticVar.SpiltString));
		setParagrapheand(e.attributeValue(StaticVar.paragrapheand) == null || e.attributeValue(StaticVar.paragrapheand).equals("") ? null : e.attributeValue(StaticVar.paragrapheand).split(StaticVar.SpiltString));
		setParagrapheor(e.attributeValue(StaticVar.paragrapheor) == null || e.attributeValue(StaticVar.paragrapheor).equals("") ? null : e.attributeValue(StaticVar.paragrapheor).split(StaticVar.SpiltString));
		setNotparagrapheand(e.attributeValue(StaticVar.notparagrapheand) == null || e.attributeValue(StaticVar.notparagrapheand).equals("") ? null : e.attributeValue(StaticVar.notparagrapheand).split(StaticVar.SpiltString));
		setNotparagrapheor(e.attributeValue(StaticVar.notparagrapheor) == null || e.attributeValue(StaticVar.notparagrapheor).equals("") ? null : e.attributeValue(StaticVar.notparagrapheor).split(StaticVar.SpiltString));
		setNotcontainand(e.attributeValue(StaticVar.notcontainand) == null || e.attributeValue(StaticVar.notcontainand).equals("") ? null : e.attributeValue(StaticVar.notcontainand).split(StaticVar.SpiltString));
		setNotcontainor(e.attributeValue(StaticVar.notcontainor) == null || e.attributeValue(StaticVar.notcontainor).equals("") ? null : e.attributeValue(StaticVar.notcontainor).split(StaticVar.SpiltString));
		setNotcontextand(e.attributeValue(StaticVar.notcontextand) == null || e.attributeValue(StaticVar.notcontextand).equals("") ? null : e.attributeValue(StaticVar.notcontextand).split(StaticVar.SpiltString));
		setNotcontextor(e.attributeValue(StaticVar.notcontextor) == null ? null : e.attributeValue(StaticVar.notcontextor).split(StaticVar.SpiltString));
		setDisablebelow(e.attributeValue(StaticVar.disablebelow) == null || !e.attributeValue(StaticVar.disablebelow).equals("1") ? false : true ); 
		setResultreg(e.attributeValue(StaticVar.resultreg) == null || e.attributeValue(StaticVar.resultreg).equals("") ? null : e.attributeValue(StaticVar.resultreg).split(StaticVar.SpiltString));
		setContextand(e.attributeValue(StaticVar.contextand) == null || e.attributeValue(StaticVar.contextand).equals("") ? null : e.attributeValue(StaticVar.contextand).split(StaticVar.SpiltString));
		setContextor(e.attributeValue(StaticVar.contextor) == null || e.attributeValue(StaticVar.contextor).equals("") ? null : e.attributeValue(StaticVar.contextor).split(StaticVar.SpiltString));
		setIsshow(e.attributeValue(StaticVar.isshow) == null || !e.attributeValue(StaticVar.isshow).equals("1") ? false : true);
		setPassthis(e.attributeValue(StaticVar.passthis) == null || !e.attributeValue(StaticVar.passthis).equals("1") ? false : true);
		setIsfight(e.attributeValue(StaticVar.isfight) == null || !e.attributeValue(StaticVar.isfight).equals("1") ? false : true);
		setAboveparagraphe(e.attributeValue(StaticVar.aboveparagrapheor) == null || e.attributeValue(StaticVar.aboveparagrapheor).equals("") ? null : e.attributeValue(StaticVar.aboveparagrapheor).split(StaticVar.SpiltString));
		setWaittime(e.attributeValue(StaticVar.waittime) == null || e.attributeValue(StaticVar.waittime).equals("") ? 0 : Integer.valueOf(e.attributeValue(StaticVar.waittime)));
		setBelowparagrapheor(e.attributeValue(StaticVar.belowparagrapheor) == null ? null : e.attributeValue(StaticVar.belowparagrapheor).split(StaticVar.SpiltString));
		setBelowparagrapheand(e.attributeValue(StaticVar.belowparagrapheand) == null ? null : e.attributeValue(StaticVar.belowparagrapheand).split(StaticVar.SpiltString));
		setBelowparagraphe(e.attributeValue(StaticVar.belowparagraphe) == null ? null : e.attributeValue(StaticVar.belowparagraphe));
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
	
	private List<Filter> getChilds(Element e) {
		List<Filter> l = null;
		List<Element> childs = e.elements();
		if (childs.size()==0)
			return l;
		l = new ArrayList<>();
		for (Element e1:childs) {
			Filter c = new Filter(e1);
			l.add(c);
		}
		return l;
	}
	
	public int compareTo(Filter o) {
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
	public int getWaittime() {
		return waittime;
	}
	public void setWaittime(int waittime) {
		this.waittime = waittime;
	}
	public String[] getBelowparagrapheor() {
		return belowparagrapheor;
	}
	public void setBelowparagrapheor(String belowparagrapheor[]) {
		this.belowparagrapheor = belowparagrapheor;
	}
	public String[] getBelowparagrapheand() {
		return belowparagrapheand;
	}
	public void setBelowparagrapheand(String belowparagrapheand[]) {
		this.belowparagrapheand = belowparagrapheand;
	}
	public String getBelowparagraphe() {
		return belowparagraphe;
	}
	public void setBelowparagraphe(String belowparagraphe) {
		this.belowparagraphe = belowparagraphe;
	}

}

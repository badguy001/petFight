package com.me;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;



public class User {
	private String userName;
	private String host;
	private String zhanli;
	private PersonnalFilter personnalFilter;
	private static final String judgeLoginReg = "¶·ÓÑ";
	private String getinfocmd = "cmd=totalinfo&type=1";
	private String searchqqreg = "(B_UID|passerby_uin)=([0-9]+)";
	private int qqgroupid = 2;
	private String hostcmd = "cmd=index";
	private String searchzhanlireg = "Õ½¶·Á¦</a>:([0-9]+)";
	private int zhanligroupid = 1;
	public boolean logined;
	public String getMainpage() {
		return mainpage;
	}

	public void setMainpage(String mainpage) {
		this.mainpage = mainpage;
	}

	private String mainpage;
	public User(String userName, String host) {
		mainpage = Browse.getResult(host, "get", null);
		Pattern p = Pattern.compile(judgeLoginReg);
		Matcher m = p.matcher(mainpage);
		if (!m.find()){
			logined = false;
			return;
		}
		personnalFilter = new PersonnalFilter(userName);
		this.userName = userName;
		this.host = host;
		p = Pattern.compile(searchzhanlireg);
		m = p.matcher(mainpage);
		if (m.find())
			zhanli = m.group(zhanligroupid);
		personnalFilter.setZhanli(String.valueOf(Integer.valueOf(zhanli) - 50));
		logined = true;
	}
	



	private boolean match(Element body, Element e1, Filter c1){
		if (c1.getInparams() != null && !e1.attributeValue("href").contains(c1.getInparams().replaceAll("&amp;", "&")) )
			return false;
		if (c1.getContext() != null && !e1.getText().equals(c1.getContext()))
			return false;
		if (c1.getContextand() != null && !containsAll(e1.getText(), c1.getContextand()))
			return false;
		if (c1.getContextor() != null && !containsAny(e1.getText(), c1.getContextor()))
			return false;
		if (c1.getNotcontextand() != null && containsAll(e1.getText(), c1.getNotcontextand()))
			return false;
		if (c1.getNotcontextor() != null && containsAny(e1.getText(), c1.getNotcontextor()))
			return false;
		if (c1.getContainand() != null && !containsAll(body.asXML(), c1.getContainand()))
			return false;
		if (c1.getContainor() != null && !containsAny(body.asXML(), c1.getContainor()))
			return false;
		if (c1.getNotcontainand() != null && containsAll(body.asXML(), c1.getNotcontainand()))
			return false;
		if (c1.getNotcontainor() != null && containsAny(body.asXML(), c1.getNotcontainor()))
			return false;

		String br[] = body.asXML().split("<br");
		for (int i=0;i<br.length;i++){
			String br1 = br[i];
			if (br1.contains(e1.asXML()))
				if (c1.getParagrapheand() != null && !containsAll(br1, c1.getParagrapheand()))
					return false;
				else if (c1.getParagrapheor() != null && !containsAny(br1, c1.getParagrapheor()))
					return false;
				else if (c1.getNotparagrapheand() != null && containsAll(br1, c1.getNotparagrapheand()))
					return false;
				else if (c1.getNotparagrapheor() != null && containsAny(br1, c1.getNotparagrapheor()))
					return false;
				else if (c1.getAboveparagraphe() != null && i != 0 && !containsAny(br[i-1], c1.getAboveparagraphe()))
					return false;
		}
		return true;
		
	}
	private boolean containsAll(String src, String e[]){
		Pattern p;
		Matcher m;
		for (String s:e){
			p = Pattern.compile(s);
			m = p.matcher(src);
			if (!m.find())
				return false;
			m.reset();
		}
		return true;
	}
	private boolean containsAny(String src, String e[]){
		Pattern p;
		Matcher m;
		for (String s:e){
			p = Pattern.compile(s);
			m = p.matcher(src);
			if (m.find())
				return true;
			m.reset();
		}
		return false;
	}
	
	public boolean canFight (String url){
		boolean result = false;
		Pattern p = Pattern.compile(searchqqreg);
		Matcher m = p.matcher(url);
		String qq = null;
		if (m.find())
			qq = m.group(qqgroupid); 
		else 
			return false;
		String infourl = host;
		infourl = infourl.replaceAll(searchqqreg, "B_UID=" + qq);
		infourl = infourl.replaceAll(hostcmd, getinfocmd);
		String src = Browse.getResult(infourl, "get", null);
		p = Pattern.compile(searchzhanlireg);
		m = p.matcher(src);
		if (m.find()){
			if (m.group(zhanligroupid).compareTo(personnalFilter.getMaxzhanli()) < 0)
				result = true;
			else result = false;
		} else return false;
			
		return result;
	}
	
	private List<Element> getURLs(List<Element> l, String tagName){
		List<Element> urls = new ArrayList<>();
		if (l == null || l.size() == 0)
			return urls;
		for (Element e:l){
			if (e.getName().equals(tagName)){
				urls.add(e);
			}
			urls.addAll(getURLs(e.elements(), tagName));
		}
		return urls;
	}
	
	public void Do(String src, Filter c) {
		if (src == null)
			return;
		src = src.replaceAll("<!DOCTYPE[^>]+>", "");
		src = src.replaceAll("&nbsp", "");
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(src);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		Element body =doc.getRootElement();
		List<Element> urls = getURLs(body.elements(), "a");
		for (Filter c1 : c.getChilds()) {
			if (c1.isDisable())
				continue;
			for (Element e1 : urls) {

				if (match(body, e1, c1)) {
					if (c1.getTimeranges() != null && c1.getTimeranges().size() > 0) {
						new Schedule(e1, c1).begin();
						continue;
					}
					String URL = e1.attributeValue("href");
					System.out.println(c1.getName() + ":" + URL);

					if (c1.isIsfight()){
						if (!canFight(URL))
							continue;
					}
					String result = Browse.getResult(URL, "get", null);
					if (c1.getIsshow())
						System.out.println(result);
					if (c1.getResultreg() != null )
						System.out.println(getResult(result, c1.getResultreg()));
					
					if (c1.getChilds() != null && c1.getChilds().size() != 0) {
						Do(result, c1);
					}
					if (c1.getWaittime() > 0){
						try {
							Thread.sleep(c1.getWaittime() * 1000);
						} catch (InterruptedException e2) {
							e2.printStackTrace();
						}
					}
					if (c1.isPassthis())
						Do(result, c);
					if (c1.isDisablebelow())
						break;
					continue;
				}
			}
		}
	}
	
	private String getResult(String src, String resultreg[]){
		String result = "";
		Pattern pattern = null;
		Matcher matcher = null;
		for (String reg:resultreg){
			pattern = Pattern.compile(reg);
			matcher = pattern.matcher(src);
			while (matcher.find())
				result = result + matcher.group() + "\n\t" ;
		}
		return result;
	}


	private class Schedule extends TimerTask {
		Element e1;
		Filter c1;

		public Schedule(Element e1, Filter c1) {
			this.e1 = e1;
			this.c1 = c1;
		}

		public void begin() {
			for (TimeRange timerange : c1.getTimeranges()) {
				if (timerange.getBegin().compareTo(timerange.getBegin().getNowTime()) <= 0
						&& timerange.getEnd().compareTo(timerange.getBegin().getNowTime()) >= 0) {
					run();
				} else if (timerange.getBegin().compareTo(timerange.getBegin().getNowTime()) > 0
						&& timerange.getEnd().compareTo(timerange.getBegin().getNowTime()) > 0) {
					System.out.println(timerange.getBegin().getDate().toString());
					ScheduleJob.getTimer().schedule(this, timerange.getBegin().getDate());
				}
			}
		}

		@Override
		public void run() {
			System.out.println(c1.getName() + ":" + e1.attributeValue("href"));
			String result = Browse.getResult(e1.attributeValue("href"), "get", null);
			if (c1.getResultreg() != null )
				System.out.println(getResult(result, c1.getResultreg()));
//			System.out.println(Browse.getResult(e1.getAttributeValue("href"), "get", null));

			if (c1.getChilds() != null && c1.getChilds().size() > 0) {

				Do(Browse.getResult(e1.attributeValue("href"), "get", null), c1);

			}
		}

	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void Do() {
		this.Do(mainpage, BasicFitler.rootFilter);
	}
	
}

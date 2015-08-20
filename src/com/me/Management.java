package com.me;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import net.htmlparser.jericho.Attribute;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

public class Management {
	private Configuration c;
	private String host;
	private String getinfocmd = "cmd=totalinfo&type=1";
	private String searchqqreg = "(B_UID|passerby_uin)=([0-9]+)";
	private int qqgroupid = 2;
	private String hostcmd = "cmd=index";
	private String searchzhanlireg = "战斗力</a>:([0-9]+)";
	private int zhanligroupid = 1;
	public Configuration getC() {
		return c;
	}

	public void setC(Configuration c) {
		this.c = c;
	}

	public String getMainpage() {
		return mainpage;
	}

	public void setMainpage(String mainpage) {
		this.mainpage = mainpage;
	}

	private String mainpage;
	
	public Management() {
		c = new Configuration();
	}
	
	public boolean login(String host[]){
		if (host == null)
			return false;
		for(String s:host){
			if ( s.contains(c.getUsername()) ) {
				String src = Browse.getResult(s, "get", null);
				if (src.contains("斗友")){
					mainpage = src;
					this.host = s;
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean login(){
		String src = null;
		src = Browse.getResult(c.getLoginURL(), "get", null);

		Source source = new Source(src);
		Element e = source.getFirstElement("anchor");
		List<Element> elements = e.getAllElements("postfield");
		List<NameValuePair> formParams = new ArrayList<>();
		String uri = null;
		for (String s : c.getLoginAttrName()) {
			for (Element e1 : elements) {
				if ((e1.getAttributeValue("name")).equals(s))
					formParams.add(new BasicNameValuePair(s, e1.getAttributeValue("value")));
			}
		}
		formParams.add(new BasicNameValuePair(c.getLoginNameAttrName(), c.getUsername()));
		formParams.add(new BasicNameValuePair(c.getLoginPwdAttrName(), c.getPassword()));
		List<Attribute> attritubes = e.getFirstElement("go").getAttributes();
		for (Attribute attr : attritubes) {
			if (attr.getName().equals("href")) {
				uri = attr.getValue();
			}
		}
		mainpage = Browse.getResult(uri, "post", formParams);
		if (mainpage == null){
			System.out.println("login fail!server return null");
			return false;
		} else if ( mainpage.contains("验证码")) {
			source.clearCache();
			source = new Source(mainpage);
			elements = source.getAllElements("anchor").get(1).getAllElements("postfield");
			formParams.clear();
			for (String s : c.getVerifyAttrName()) {
				for (Element e1 : elements) {
					if (e1.getAttributeValue("name").equals(s))
						formParams.add(new BasicNameValuePair(s, e1.getAttributeValue("value")));
				}
			}
			uri = source.getAllElements("anchor").get(1).getFirstElement("go").getAttributeValue("href");
			String verify = getVerify(source, c.getVerifyfresh());
			formParams.add(new BasicNameValuePair(c.getVerifyname(), verify));
			mainpage = Browse.getResult(uri, "post", formParams);
//			System.out.println(mainpage);
			return true;
		} else
			return true;
	}
	
	private String getVerify(Source source, String[] verifyfresh) {
		String imgURL = null;
		String result = null;
		imgURL = source.getFirstElement("img").getAttributeValue("src");
		result = new getVerifyCode().getcode(imgURL);
		return result;
	}


	private boolean match(Source e, Element e1, Clickable c1){
		if (c1.getInparams() != null && !e1.getAttributeValue("href").replaceAll("&amp;", "&").contains(c1.getInparams()) )
			return false;
		if (c1.getContext() != null && !e1.getContent().toString().contains(c1.getContext()))
			return false;
		if (c1.getContextand() != null && !containsAll(e1.getContent().toString(), c1.getContextand()))
			return false;
		if (c1.getContextor() != null && !containsAny(e1.getContent().toString(), c1.getContextor()))
			return false;
		if (c1.getNotcontextand() != null && containsAll(e1.getContent().toString(), c1.getNotcontextand()))
			return false;
		if (c1.getNotcontextor() != null && containsAny(e1.getContent().toString(), c1.getNotcontextor()))
			return false;
		if (c1.getContainand() != null && !containsAll(e.toString(), c1.getContainand()))
			return false;
		if (c1.getContainor() != null && !containsAny(e.toString(), c1.getContainor()))
			return false;
		if (c1.getNotcontainand() != null && containsAll(e.toString(), c1.getNotcontainand()))
			return false;
		if (c1.getNotcontainor() != null && containsAny(e.toString(), c1.getNotcontainor()))
			return false;

		String br[] = e.toString().split("<br");
		for (int i=0;i<br.length;i++){
			String br1 = br[i];
			if (br1.contains(e1.toString()))
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
			if (m.group(zhanligroupid).compareTo(this.c.getMaxzhanli()) < 0)
				result = true;
			else result = false;
		} else return false;
			
		return result;
	}
	
	public void Do(Source e, Clickable c) {
		List<Element> urls = e.getAllElements("a");
		
		for (Clickable c1 : c.getChilds()) {
			if (c1.isDisable())
				continue;
			for (Element e1 : urls) {

				if (match(e, e1, c1)) {
					if (c1.getFreshtime() > 0) {
						new Thread(new FreshThread(e1, c1)).start();
						continue;
					} else if (c1.getTimeranges() != null && c1.getTimeranges().size() > 0) {
						new Schedule(e1, c1).begin();
						continue;
					}
					System.out.println(c1.getName() + ":" + e1.getAttributeValue("href"));
					if (c1.isSethost()){
						host = e1.getAttributeValue("href");
						System.out.println(host);
						continue;
					}
					if (c1.isIsfight()){
						if (!canFight(e1.getAttributeValue("href")))
							continue;
					}
					String result = Browse.getResult(e1.getAttributeValue("href"), "get", null);
					if (c1.getIsshow())
						System.out.println(result);
					if (c1.getResultreg() != null )
						System.out.println(getResult(result, c1.getResultreg()));
					
					if (c1.getChilds() != null && c1.getChilds().size() != 0) {
						Do(new Source(Browse.getResult(e1.getAttributeValue("href"), "get", null)), c1);
					}
					if (c1.getWaittime() > 0){
						try {
							Thread.sleep(c1.getWaittime() * 1000);
						} catch (InterruptedException e2) {
							e2.printStackTrace();
						}
					}
					if (c1.isPassthis())
						Do(new Source(result), c);
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
	private class FreshThread implements Runnable {
		Element e1;
		Clickable c1;

		public FreshThread(Element e1, Clickable c1) {
			this.e1 = e1;
			this.c1 = c1;
		}

		@Override
		public void run() {
			for (int i = 0; i <= c1.getFreshtime(); i++) {
				System.out.println(c1.getName() + ":" + e1.getAttributeValue("href"));
				String result = Browse.getResult(e1.getAttributeValue("href"), "get", null);
				if (c1.getResultreg() != null )
					System.out.println(getResult(result, c1.getResultreg()));
//				System.out.println(Browse.getResult(e1.getAttributeValue("href"), "get", null));

				if (c1.getChilds() != null && c1.getChilds().size() != 0) {
					Do(new Source(Browse.getResult(e1.getAttributeValue("href"), "get", null)), c1);
				}
				try {
					Thread.sleep(c1.getFreshwait()*1000);
				} catch (InterruptedException e2) {
					e2.printStackTrace();
				}
			}
		}
	}

	private class Schedule extends TimerTask {
		Element e1;
		Clickable c1;

		public Schedule(Element e1, Clickable c1) {
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
			System.out.println(c1.getName() + ":" + e1.getAttributeValue("href"));
			String result = Browse.getResult(e1.getAttributeValue("href"), "get", null);
			if (c1.getResultreg() != null )
				System.out.println(getResult(result, c1.getResultreg()));
//			System.out.println(Browse.getResult(e1.getAttributeValue("href"), "get", null));

			if (c1.getChilds() != null && c1.getChilds().size() > 0) {

				Do(new Source(Browse.getResult(e1.getAttributeValue("href"), "get", null)), c1);

			}
		}

	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
	
}

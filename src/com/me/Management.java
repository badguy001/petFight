package com.me;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import net.htmlparser.jericho.Attribute;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

public class Management {
	private Configuration c;
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
//			String imageURL = null;
//			source.clearCache();
//			source = new Source(mainpage);
//			imageURL = source.getFirstElement("img").getAttributeValue("src");
//			formParams.clear();
//			for (String s : c.getLoginAttrName()) {
//				for (Element e1 : elements) {
//					if ((e1.getAttributeValue("name")).equals(s))
//						formParams.add(new BasicNameValuePair(s, e1.getAttributeValue("value")));
//				}
//			}
			System.out.println("需要验证码");
			return false;
		} else
			return true;
	}
	
	private boolean match(Source e, Element e1, Clickable c1){
		if (c1.getInparams() != null && !e1.getAttributeValue("href").replaceAll("&amp;", "&").contains(c1.getInparams()) )
			return false;
		if (c1.getContext() != null && !e1.getContent().toString().contains(c1.getContext()))
			return false;
	}
	
	public void Do(Source e, Clickable c) {
		List<Element> urls = e.getAllElements("a");

		for (Clickable c1 : c.getChilds()) {
			for (Element e1 : urls) {
				if (e1.getAttributeValue("href").replaceAll("&amp;", "&").contains(c1.getInparams())
						&& (c1.getContain() == null || e.toString().contains(c1.getContain()))
						&& (c1.getContain() == null || e1.getContent().toString().contains(c1.getContain()))) {
					if (c1.getFreshtime() > 0) {
						new Thread(new FreshThread(e1, c1)).start();
						break;
					} else if (c1.getTimeranges() != null && c1.getTimeranges().size() > 0) {
						new Schedule(e1, c1).begin();
						break;
					}
					System.out.println(c1.getName() + ":" + e1.getAttributeValue("href"));

//					System.out.println(Browse.getResult(e1.getAttributeValue("href"), "get", null));

					if (c1.getChilds() != null && c1.getChilds().size() != 0) {

						Do(new Source(Browse.getResult(e1.getAttributeValue("href"), "get", null)), c1);

					}
					break;
				}
			}
		}
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

//				System.out.println(Browse.getResult(e1.getAttributeValue("href"), "get", null));

				if (c1.getChilds() != null && c1.getChilds().size() != 0) {

					Do(new Source(Browse.getResult(e1.getAttributeValue("href"), "get", null)), c1);

				}
				try {
					Thread.sleep(c1.getFreshwait());
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

//			System.out.println(Browse.getResult(e1.getAttributeValue("href"), "get", null));

			if (c1.getChilds() != null && c1.getChilds().size() > 0) {

				Do(new Source(Browse.getResult(e1.getAttributeValue("href"), "get", null)), c1);

			}
		}

	}
	
}

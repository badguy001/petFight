package com.me;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import net.htmlparser.jericho.Attribute;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

public class Test {

	public static void main(String[] args) {
		Configuration c = new Configuration();
		Browse b = new Browse(c);
		String src = new String();
		try {
			System.out.println();
			src = b.getResult(new URI(c.getLoginURL()), "get", null);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		Source source = new Source(src);
		Element e = source.getFirstElement("anchor");
		List<Element> elements = e.getAllElements("postfield");
		List<NameValuePair> formParams = new ArrayList<>();
		URI uri = null;
		for ( String s:c.getAttrName()){
			for ( Element e1:elements){
				if (( e1.getAttributeValue("name")).equals(s))
					formParams.add(new BasicNameValuePair(s, e1.getAttributeValue("value")));
			}
		}
		formParams.add(new BasicNameValuePair(c.getLoginNameAttrName(), c.getUsername()));
		formParams.add(new BasicNameValuePair(c.getLoginPwdAttrName(), c.getPassword()));
		List<Attribute> attritubes = e.getFirstElement("go").getAttributes();
		for ( Attribute attr:attritubes){
			if ( attr.getName().equals("href")){
				try {
					uri = new URI(attr.getValue());
				} catch (URISyntaxException e1) {
					e1.printStackTrace();
				}
			}
		}
		List<String> l = new ArrayList<String>();
		l.add("����");
		Map<String, URI> map = Tool.foundURL(b.getResult(uri, "post", formParams), l);
		System.out.println(b.getResult(map.get("����"), "get", null));
		
		b.destroy();
	}
}

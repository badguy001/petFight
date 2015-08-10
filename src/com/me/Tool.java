package com.me;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

public class Tool {
	public static Map<String, URI> foundURL(String src, List<String> key ){
		Map<String, URI> map = new HashMap<>();
		Source source = new Source(src);
		List<Element> elements = source.getAllElements("a");
		for ( Element e:elements){
			String s = e.getContent().toString();
			if( key.contains(s) && e.getAttributeValue("href") != null ){
				try {
					map.put(e.getContent().toString(), new URI(e.getAttributeValue("href")));
				} catch (URISyntaxException e1) {
					e1.printStackTrace();
				}
			}
		}
		
		return map;
	}
}

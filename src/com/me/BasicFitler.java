package com.me;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class BasicFitler {
	static Filter rootFilter;
	static {
		SAXReader reader = null;
		Document doc = null;
		try {
			reader = new SAXReader();
			doc = reader.read(StaticVar.BasicConfFileName);
			Element root = doc.getRootElement().element(StaticVar.BasicFilterTagName);
			rootFilter = new Filter(root);
		} catch (DocumentException e) {
			rootFilter = null;
			e.printStackTrace();
		} finally {
		}
	}
}

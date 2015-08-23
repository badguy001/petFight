package com.me;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class PersonnalFilter {
	private File f;
	private String maxzhanli = null;
	private XMLWriter writer = null;
	private SAXReader reader;
	private Document doc;
	private OutputFormat format;
	private String maxLevel;
	private String minLevel;

	public PersonnalFilter(String userName) {
		format = new OutputFormat();
		format.setIndent(true);
		format.setIndent("    ");
		format.setNewlines(true);
		try {
			writer = new XMLWriter(format);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		f = new File(userName + ".xml");
		f.delete();
		if (!f.exists()){
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(f);
				f.createNewFile();
				doc = DocumentHelper.createDocument();
				Element root = doc.addElement("root");
				Element e = root.addElement(StaticVar.PersonnalFightConfTagName);
				e.addAttribute(StaticVar.maxzhanli, "");
				writer.setOutputStream(fos);
				writer.write(doc);
				writer.flush();
			} catch (IOException e) {
				destroy();
				e.printStackTrace();
			} finally {
				try {
					
					if (writer != null)
						writer.close();
					if (fos != null)
						fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return;
		}
		reader = new SAXReader();
		try {
			doc = reader.read(f);
			setMaxzhanli(doc.getRootElement().element(StaticVar.PersonnalFightConfTagName).attributeValue(StaticVar.maxzhanli));
		} catch (DocumentException e) {
			
			destroy();
			e.printStackTrace();
		} 
	}
	
	public boolean setZhanli(String zhanli){
		doc.getRootElement().element(StaticVar.PersonnalFightConfTagName).addAttribute(StaticVar.maxzhanli, zhanli);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(f);
			writer = new XMLWriter(format);
			writer.setOutputStream(fos);
			writer.write(doc);
			writer.flush();
		} catch (IOException e) {
			destroy();
			
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (writer != null)
					writer.close();
				if (fos != null)
					fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		setMaxzhanli(zhanli);
		return true;
	}
	
	public void destroy(){
		f = null;
		setMaxzhanli(null);
		doc = null;
		reader = null;
		format = null;
		if (writer != null){
			try {
				writer.flush();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	public String getMaxzhanli() {
		return maxzhanli;
	}

	public void setMaxzhanli(String maxzhanli) {
		this.maxzhanli = maxzhanli;
	}

	public String getMinLevel() {
		return minLevel;
	}

	public void setMinLevel(String minLevel) {
		this.minLevel = minLevel;
	}

	public String getMaxLevel() {
		return maxLevel;
	}

	public void setMaxLevel(String maxLevel) {
		this.maxLevel = maxLevel;
	}
}

package com.me;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.http.client.CookieStore;

import net.htmlparser.jericho.Source;

public class Test {
	private static final String cookieStoreFile = "Cookie"; 
	private static final String hostsFile = "hosts";
	public static void main(String[] args) {
		Browse.init();
		User m = new User("409966172", "http://dld.qzapp.z.qq.com/qpet/cgi-bin/phonepk?zapp_uin=409966172&B_UID=409966172&sid=AeY0uNBtPqwJex24MtVcMY5E&channel=0&g_ut=1&cmd=index");
//		CookieStore cookiestore = new Test().getcookie();
//		if (cookiestore != null){
//			Browse.init(cookiestore);
//		} else {
//			Browse.init();
//		}
//		if (!m.login(new Test().gethosts()))
//			if (!m.login()) {
//				System.out.println("login fail!");
//				System.exit(0);
//			}
//			else {}
//		else 
//			System.out.println("√‚√‹¬Îµ«¬º");
		if (m.logined)
			System.out.println("√‚√‹¬Îµ«¬º");
		m.Do();
//		new Test().savecookie(Browse.getCookieStore());
//		new Test().savehost(m.getHost());
	}
	
	private CookieStore getcookie(){
		File cookieFile = null;
		FileInputStream fin = null;
		ObjectInputStream objin = null;
		CookieStore cookiestore = null;
		cookieFile = new File(cookieStoreFile);
		if (cookieFile.exists()){
			try {
				fin = new FileInputStream(cookieFile);
				objin = new ObjectInputStream(fin);
				cookiestore = (CookieStore)objin.readObject();
			} catch (FileNotFoundException e) {
				System.out.println("file :" + cookieStoreFile + " do not exit!");
				cookiestore = null;
			} catch (ClassNotFoundException e) {
				System.out.println("can not find class:CookieStore!");
				cookiestore = null;
			} catch (IOException e) {
				System.out.println("read from file:" + cookieStoreFile + " error!");
				cookiestore = null;
			} finally {
				try {
					if (objin != null)
						objin.close();
					if (fin != null)
						fin.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return cookiestore;
	}
	
	private boolean savecookie(CookieStore cookiestore){
		if (cookiestore == null)
			return true;
		boolean result;
		File cookieFile = null;
		FileOutputStream fin = null;
		ObjectOutputStream objin = null;
		cookieFile = new File(cookieStoreFile);
		try {
			if (!cookieFile.exists()) {
				cookieFile.createNewFile();
			}
			fin = new FileOutputStream(cookieFile);
			objin = new ObjectOutputStream(fin);
			objin.writeObject(cookiestore);
			objin.flush();
		} catch (FileNotFoundException e) {
			System.out.println("file :" + cookieStoreFile + " do not exit!");
			result = false;
		} catch (IOException e) {
			System.out.println("read from file:" + cookieStoreFile + " error!");
			result = false;
		} finally {
			try {
				if (objin != null)
					objin.close();
				if (fin != null)
					fin.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		result = true;
		return result;
	}
	
	private boolean savehost(String host){
		if (host == null)
			return true;
		File hostsfile = null;
		FileWriter writer = null;
		String s[] = gethosts();
		if (s != null)
			for (String s1 : s)
				if (s1.equals(host))
					return true;
		hostsfile = new File(hostsFile);
		try {
			if (!hostsfile.exists())
				hostsfile.createNewFile();
			writer = new FileWriter(hostsfile);
			writer.append(host);
			writer.append("\n");
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (writer != null)
					writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	private String[] gethosts(){
		File hostsfile = null;
		String s = "";
		BufferedReader reader = null;
		hostsfile = new File(hostsFile);
		try {
			if (!hostsfile.exists())
				hostsfile.createNewFile();
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(hostsfile)));
			String tmp = "";
			while ((tmp=reader.readLine()) != null )
				s += tmp + "\n";
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (s == null || s.equals(""))
			return null;
		return s.split("\n");
		
	}
}

package com.me;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Test {
//	private static final String cookieStoreFile = "Cookie"; 
//	private static final String hostsFile = "hosts";
	private static final String usersFile = "users.xml";
	private static final String userTagName = "user";
	public static void main(String[] args) {
		Browse.init();
		List<User> users = new Test().getUsers();
		Calendar d = Calendar.getInstance();
		d.setTimeInMillis(System.currentTimeMillis());
		while(true){
			Calendar d1 = Calendar.getInstance();
			d1.setTimeInMillis(System.currentTimeMillis());
			System.out.println(d1.getTime().toString());
			if ( d1.get(Calendar.HOUR_OF_DAY ) >= 7 || (d1.get(Calendar.HOUR_OF_DAY ) == 6 && d1.get(Calendar.MINUTE) >= 35) ){
				if (d1.get(Calendar.DAY_OF_YEAR) - d.get(Calendar.DAY_OF_YEAR) > 0
						|| d1.get(Calendar.YEAR) > d.get(Calendar.YEAR)) {
					// if (d1.get(Calendar.MINUTE) - d.get(Calendar.MINUTE) > 0
					// || d1.get(Calendar.HOUR_OF_DAY) >
					// d.get(Calendar.HOUR_OF_DAY)){
					users = new Test().getUsers();

					d = d1;
				}
				new Test().begin(users);
				try {
					Thread.sleep(2*60*60*1000);
//					Thread.sleep(20*60*1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				try {
					Thread.sleep(15*60*1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}

	}
	
	private List<User> getUsers(){
		List<User> users = new ArrayList<>();
		File fin = new File(usersFile);
		SAXReader reader = new SAXReader();
		try {
			Document doc = reader.read(fin);
			List<Element> e = doc.getRootElement().element("users").elements(userTagName);
			for (Element e1:e){
				users.add(new User(e1.attributeValue("name"), e1.attributeValue("host")));
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return users;
		
	}
	
	private void begin(List<User> users){
		for (User user:users){
			new Thread(new MyThread(user)).start();
			try {
				Thread.sleep(5*60*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private class MyThread implements Runnable{
		User u;
		public MyThread(User u) {
			this.u = u;
		}
		@Override
		public void run() {
			if (u.logined)
				u.Do();
		}
	}
//	private CookieStore getcookie(){
//		File cookieFile = null;
//		FileInputStream fin = null;
//		ObjectInputStream objin = null;
//		CookieStore cookiestore = null;
//		cookieFile = new File(cookieStoreFile);
//		if (cookieFile.exists()){
//			try {
//				fin = new FileInputStream(cookieFile);
//				objin = new ObjectInputStream(fin);
//				cookiestore = (CookieStore)objin.readObject();
//			} catch (FileNotFoundException e) {
//				System.out.println("file :" + cookieStoreFile + " do not exit!");
//				cookiestore = null;
//			} catch (ClassNotFoundException e) {
//				System.out.println("can not find class:CookieStore!");
//				cookiestore = null;
//			} catch (IOException e) {
//				System.out.println("read from file:" + cookieStoreFile + " error!");
//				cookiestore = null;
//			} finally {
//				try {
//					if (objin != null)
//						objin.close();
//					if (fin != null)
//						fin.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		return cookiestore;
//	}
//	
//	private boolean savecookie(CookieStore cookiestore){
//		if (cookiestore == null)
//			return true;
//		boolean result;
//		File cookieFile = null;
//		FileOutputStream fin = null;
//		ObjectOutputStream objin = null;
//		cookieFile = new File(cookieStoreFile);
//		try {
//			if (!cookieFile.exists()) {
//				cookieFile.createNewFile();
//			}
//			fin = new FileOutputStream(cookieFile);
//			objin = new ObjectOutputStream(fin);
//			objin.writeObject(cookiestore);
//			objin.flush();
//		} catch (FileNotFoundException e) {
//			System.out.println("file :" + cookieStoreFile + " do not exit!");
//			result = false;
//		} catch (IOException e) {
//			System.out.println("read from file:" + cookieStoreFile + " error!");
//			result = false;
//		} finally {
//			try {
//				if (objin != null)
//					objin.close();
//				if (fin != null)
//					fin.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		result = true;
//		return result;
//	}
//	
//	private boolean savehost(String host){
//		if (host == null)
//			return true;
//		File hostsfile = null;
//		FileWriter writer = null;
//		String s[] = gethosts();
//		if (s != null)
//			for (String s1 : s)
//				if (s1.equals(host))
//					return true;
//		hostsfile = new File(hostsFile);
//		try {
//			if (!hostsfile.exists())
//				hostsfile.createNewFile();
//			writer = new FileWriter(hostsfile);
//			writer.append(host);
//			writer.append("\n");
//			writer.flush();
//		} catch (IOException e) {
//			e.printStackTrace();
//			return false;
//		} finally {
//			try {
//				if (writer != null)
//					writer.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		return true;
//	}
//	private String[] gethosts(){
//		File hostsfile = null;
//		String s = "";
//		BufferedReader reader = null;
//		hostsfile = new File(hostsFile);
//		try {
//			if (!hostsfile.exists())
//				hostsfile.createNewFile();
//			reader = new BufferedReader(new InputStreamReader(new FileInputStream(hostsfile)));
//			String tmp = "";
//			while ((tmp=reader.readLine()) != null )
//				s += tmp + "\n";
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//			return null;
//		} finally {
//			try {
//				if (reader != null)
//					reader.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		if (s == null || s.equals(""))
//			return null;
//		return s.split("\n");
//		
//	}
}

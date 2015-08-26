package com.me;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class Browse {
	private static CookieStore cookieStore;
	private static CloseableHttpClient httpclient;

	public static boolean init(){
		cookieStore = new BasicCookieStore();
		init(cookieStore);
		return true;
	}
	
	public static boolean init(CookieStore cookieStore){
		httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore)
				.setUserAgent("Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0)")
				.build();
		return true;
	}
	
	public static String getResult(String sURI, String method, List<NameValuePair> formParams) {
		String result = new String();
		HttpGet httpget = null;
		URI uri = null;
		HttpPost httppost = null;
		CloseableHttpResponse response = null;
		BufferedReader reader = null;
		try {
			uri = new URI(sURI);
			if (method.equals("get")) {
				httpget = new HttpGet(uri);
				response = httpclient.execute(httpget);
			} else if (method.equals("post")) {
				httppost = new HttpPost(uri);
				httppost.setEntity(new UrlEncodedFormEntity(formParams, "UTF-8"));
				response = httpclient.execute(httppost);
			}
			reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String s = null;
			while ((s = reader.readLine()) != null) {
				result += s + "\n";
			}
			// 不用重定向直接返回结果
//			if (response.getStatusLine().getStatusCode() != 301 && response.getStatusLine().getStatusCode() != 302) {
//				return result;
//			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			System.out.println(sURI + " is not a URI!");
			e.printStackTrace();
		} 
		finally {
			try {
				if (reader != null)
					reader.close();
				if (response != null)
					response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
//
//		// 需要重定向（httpclient能处理标准的重定向，这里只处理不规范的重定向
//
//		Source src = new Source(result);
//		Element element = src.getFirstElement("a");
//		if (element == null) {
//			// 无可用url，重定向失败
//			System.out.println("cannot find redrictURI!");
//			return result;
//		}
//		Attributes attributes = element.getAttributes();
//		String redrictURI = null;
//		for (Attribute attr : attributes)
//			if (attr.getName().equals("href"))
//				redrictURI = attr.getValue();
//		if (redrictURI == null) {
//			// redrict fail
//			System.out.println("cannot find redrictURI!");
//			return result;
//		} else{
//			System.out.println("redrict to " + redrictURI);
//			return getResult(redrictURI, "get", null);
//		}
			
	}
	
	
	public static void destroy() {
		System.out.println("destroy httpclient");
		cookieStore.clear();
		cookieStore = null;
		try {
			httpclient.close();
		} catch (IOException e) {
			System.out.println("wrong, when httpclient close!");
			e.printStackTrace();
		}
	}

	public static CookieStore getCookieStore() {
		return cookieStore;
	}

	public static void setCookieStore(CookieStore cookieStore) {
		Browse.cookieStore = cookieStore;
	}
}

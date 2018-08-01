/**
 * 
 */
package com.thy.rss.xml;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

import com.thy.rss.bean.RSSItemBean;

/**
 * 采用PULL解析XML
 * @author tanghaoyuan
 * @2013-3-31
 */
public class PullParseService {

	/**
	 * 根据url字符串获得IO的输入流
	 * @param urlStr
	 * @return
	 * @throws IOException
	 */
	private static InputStream getInputStreamFormUrl(String urlStr) throws IOException  {
		URL url = new URL(urlStr);
		HttpURLConnection httpConn = (HttpURLConnection)url.openConnection();
		return httpConn.getInputStream();
	}
	
    /**
     * 通过解析XML文件获得一个rss源内容的集合
     * @param input
     * @return
     */
	public static List<RSSItemBean> getRssContentByXml(String rssUrl) throws Exception{
		InputStream input = getInputStreamFormUrl(rssUrl);
		List<RSSItemBean> rpbList = new ArrayList<RSSItemBean>();
		XmlPullParser xmlParser = Xml.newPullParser();//获得XML解析器
		RSSItemBean rpb = null;
		xmlParser.setInput(input,"UTF-8");
		int eventType = xmlParser.getEventType();
		//boolean flag = false;
		while(eventType != XmlPullParser.END_DOCUMENT){
			switch(eventType){
				case XmlPullParser.START_DOCUMENT:
					break;
				case XmlPullParser.START_TAG:
					if("item".equals(xmlParser.getName())){
						rpb = new RSSItemBean();
					}
					if(null != rpb){
						if("title".equals(xmlParser.getName())){
							rpb.setTitle(xmlParser.nextText());
						}
						if("link".equals(xmlParser.getName())){
							rpb.setLink(xmlParser.nextText());
						}
//						if("author".equals(xmlParser.getName())){
//							flag = true;
//							System.out.println("开始解析author了。。。");
//						}
//						if("name".equals(xmlParser.getName()) && flag){
//							rpb.setAuthor(xmlParser.nextText());
//						}
						if("pubDate".equals(xmlParser.getName())){
							rpb.setPubDate(formartDate(xmlParser.nextText()));
						}
					}
					break;
				case XmlPullParser.END_TAG:
					if("item".equals(xmlParser.getName())){
						rpbList.add(rpb);
						rpb = null;
						//flag = false;
					}
					break;
			}
			eventType = xmlParser.next();//进入下一个元素
		}
		return rpbList;
	}
	
	public static String formartDate(String date){
		String result = null;
		try {
			Date d = new Date(date);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss EEE",Locale.CHINA);
			result = dateFormat.format(d);
		} catch (Exception e) {
			e.printStackTrace();
			return date;
		}
		return result;
	}
}

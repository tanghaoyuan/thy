/**
 * 
 */
package com.thy.rss.dao;

import android.provider.BaseColumns;

/**
 * @author thy
 *
 */
public class RssColumns implements BaseColumns {

	public final static String RSS_ID = "RSS_ID";
	public final static String RSS_TTTLE = "RSS_TITLE";
	public final static String RSS_URL = "RSS_URL";
	public final static String RSS_TYPE = "RSS_TYPE";
	public final static String RSS_CONTENT = "RSS_CONTENT";
	
	public static String [] getRssListColumns(){
		return new String []{
				RSS_ID,
				RSS_TTTLE,
				RSS_URL,
				RSS_TYPE,
				RSS_CONTENT
		};
	}
}

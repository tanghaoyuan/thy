/**
 * 
 */
package com.thy.rss.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * RSSFeed和一个完整的RSS文件相对应
 * @author tanghaoyuan
 * @2013-4-6
 */
public class RSSFeed {

	private String title = null;
	private String pubDate = null;
	private int itemCount;
	private List<RSSItemBean> itemList;
	
	public RSSFeed() {  
        itemList = new ArrayList<RSSItemBean>();  
    }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	public int getItemCount() {
		return itemCount;
	}

	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}

	public List<RSSItemBean> getItemList() {
		return itemList;
	}

	public void setItemList(List<RSSItemBean> itemList) {
		this.itemList = itemList;
	}
}

/**
 * 
 */
package com.thy.rss.bean;

/**
 * 
 * @author thy
 */
public class RssInfoBean {
	
	private int rssId;
	private String rssTitle;
	private String rssUrl;
	private String rssType;
	private String rssContent;
	
	public RssInfoBean(int rssId,String rssTitle,String rssUrl,String rssType,String rssContent){
		this.rssId = rssId;
		this.rssTitle = rssTitle;
		this.rssUrl = rssUrl;
		this.rssType = rssType;
		this.rssContent = rssContent;
	}
	
	public int getRssId() {
		return rssId;
	}
	public void setRssId(int rssId) {
		this.rssId = rssId;
	}
	public String getRssTitle() {
		return rssTitle;
	}
	public void setRssTitle(String rssTitle) {
		this.rssTitle = rssTitle;
	}
	public String getRssUrl() {
		return rssUrl;
	}
	public void setRssUrl(String rssUrl) {
		this.rssUrl = rssUrl;
	}
	public String getRssType() {
		return rssType;
	}
	public void setRssType(String rssType) {
		this.rssType = rssType;
	}
	public String getRssContent() {
		return rssContent;
	}
	public void setRssContent(String rssContent) {
		this.rssContent = rssContent;
	}
}

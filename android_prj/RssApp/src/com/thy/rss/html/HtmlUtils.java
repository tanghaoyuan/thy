/**
 * 
 */
package com.thy.rss.html;

import java.util.List;

import com.thy.rss.bean.RSSItemBean;

/**
 * html tools
 * @author tanghaoyuan
 * @2013-3-31
 */
public class HtmlUtils {

	/**
	 * 根据RSS的xml内容生成HTML的内容
	 * @param rpp
	 * @return
	 */
	public static String createHtmlContent(List<RSSItemBean> rpbList){
		StringBuffer html = new StringBuffer("<html><body>");
		for(RSSItemBean rpb : rpbList){
			html.append("<b><a href='"+rpb.getLink()+"'>"+rpb.getTitle()+"</a></b>")
				.append("<br>")
				.append(rpb.getPubDate()).append(" | ").append(rpb.getAuthor())
				.append("<br>").append(rpb.getDescription())
				.append("</br></br>");
		}
		html.append("</body></html>");
		return html.toString();
	}
	
}

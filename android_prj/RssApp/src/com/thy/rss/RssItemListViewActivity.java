/**
 * 
 */
package com.thy.rss;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.thy.rss.bean.RSSItemBean;
import com.thy.rss.xml.PullParseService;

/**
 * 用于显示一个频道下面的文章list
 * @author tanghaoyuan
 * @2013-4-6
 */
public class RssItemListViewActivity extends ListActivity {

	List<RSSItemBean> rssItemList = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rss_child_listview);
		Intent intent = getIntent();
		String rssUrl = intent.getStringExtra("rssUrl");
		initListView(rssUrl);
		
	}
	
	private void initListView(String rssUrl){
		try {
			rssItemList = PullParseService.getRssContentByXml(rssUrl);
			List<HashMap<String, String>> listViewData = new ArrayList<HashMap<String,String>>();
			
			for(RSSItemBean rssItem : rssItemList){
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("rss_title", rssItem.getTitle());
				//map.put("rss_item_author", rssItem.getAuthor());
				map.put("rss_item_date", "更新时间："+rssItem.getPubDate());
				
				listViewData.add(map);
			}
			SimpleAdapter adapter = new SimpleAdapter(this, listViewData, R.layout.rss_item_listview, new String[]{"rss_title","rss_item_date"}, new int[]{R.id.rss_title,R.id.rss_item_date});
			setListAdapter(adapter);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		RSSItemBean rssItem = rssItemList.get(position);
		Intent intent = new Intent();
		intent.putExtra("rssItemUrl", rssItem.getLink());
		intent.setClass(this, RssWebViewActivity.class);
		startActivity(intent);
	}
}

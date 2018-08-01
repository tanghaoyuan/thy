/**
 * 
 */
package com.thy.rss;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.thy.rss.bean.RssInfoBean;
import com.thy.rss.common.CommonUtils;
import com.thy.rss.dao.RssOperDAO;
import com.thy.rss.db.SQLiteUtils;

/**
 * RSS信息显示页面
 * @author tanghaoyuan
 * @2013-3-29
 */
public class RssListViewActivity extends Activity {

	private EditText etRssTitle;
	private Spinner spRssType;
	private Button btnSearchDialog;
	
	ListView listViewRead = null;
	private RssReadAdapter rssReadAdapter = null;
	
	List<RssInfoBean> rssList = null;
	List<HashMap<String, String>> listViewData = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rss_listview);
		listViewRead = (ListView)findViewById(R.id.rss_listview_read);
		btnSearchDialog = (Button)findViewById(R.id.searchDialog);
		btnSearchDialog.setOnClickListener(new ViewSearchDialog());
		listViewRead.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long id) {
				if(!CommonUtils.checkNetWorkConnect(RssListViewActivity.this)){
					HashMap<String,String> strTitle = new HashMap<String, String>();
					strTitle.put("title", "提示");
					strTitle.put("msg", "网络不可用,请您检查或开启。");
					strTitle.put("positive", "确定");
					CommonUtils.showDialogView(RssListViewActivity.this, strTitle, R.drawable.info1, null, null);
					return;
				}
				RssInfoBean rssInfo = rssList.get(position);
				Intent intent = new Intent();
				intent.putExtra("rssUrl", rssInfo.getRssUrl());
				intent.setClass(RssListViewActivity.this, RssItemListViewActivity.class);
				startActivity(intent);
				overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
			}
			
		});
		searchListViewData(null, null);
	}
	
	//显示搜索条件
	class ViewSearchDialog implements OnClickListener{

		@Override
		public void onClick(View v) {
			LayoutInflater factory = LayoutInflater.from(RssListViewActivity.this);
			//加载我们的dialog的布局文件
			final View dialogView = factory.inflate(R.layout.rss_search_dialog, null);
			AlertDialog dialog = new AlertDialog.Builder(RssListViewActivity.this)
			.setTitle("RSS搜索")
			.setIcon(R.drawable.search1)
			.setView(dialogView)//把布局文件的view设置到dialog里面去
			.setPositiveButton("搜索", new SearchOnClickListen())
			.setNegativeButton("取消", new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			})
			.create();
			dialog.show();
			Window window = dialog.getWindow();
			//必须要到我们指定的这个windiw中去根据id去寻找
			etRssTitle = (EditText)window.findViewById(R.id.etSearchTitle);
			spRssType = (Spinner)window.findViewById(R.id.viewRssType);
		}
	}
	
	//执行搜索
	class SearchOnClickListen implements DialogInterface.OnClickListener{

		@Override
		public void onClick(DialogInterface dialog, int which) {
			String keyWord = etRssTitle.getText().toString();
			String rssType = spRssType.getSelectedItem().toString();
			searchListViewData(keyWord, rssType);
		}
	}
	
	/**
	 * 初始化 listview的数据
	 * @param keyWord
	 * @param rssType
	 */
	public void searchListViewData(String keyWord,String rssType){
		RssOperDAO rssDAO = new RssOperDAO(new SQLiteUtils(RssListViewActivity.this));
		rssList = rssDAO.listRss(keyWord, rssType);
		
		listViewData = new ArrayList<HashMap<String,String>>();
		
		for(RssInfoBean rss : rssList){
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("rss_title", rss.getRssTitle());
			map.put("rss_url", rss.getRssUrl());
			listViewData.add(map);
		}
		if(null == listViewData || listViewData.size() <= 0){
			Toast t = Toast.makeText(this, "没有查找到相关记录！", Toast.LENGTH_SHORT);
			t.setGravity(Gravity.CENTER, 0, 0);
			t.show();
		}
		try {
			if(null == rssReadAdapter){
				rssReadAdapter = new RssReadAdapter(this);
				listViewRead.setAdapter(rssReadAdapter);
			}
			else{
				rssReadAdapter.notifyDataSetChanged();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("出错了"+e.getMessage());
		}
	}
	//自定义我的listview
	class RssReadAdapter extends BaseAdapter{

		private LayoutInflater inflater;
		
		public RssReadAdapter(Activity activity){
			inflater = LayoutInflater.from(activity);
		}
		
		@Override
		public int getCount() {
			return rssList.size();
		}
		
		public int getItemViewType(int position) {
			return position;
		}

		@Override
		public Object getItem(int position) {
			return rssList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			//使用一个对象来封装我们listview中的内容项，避免每次都重新到布局文件去加载，提高性能
			ViewHolder holder = null;
			if(null == convertView){
				convertView = inflater.inflate(R.layout.rss_listview_item, parent, false);
				holder = new ViewHolder();
				//通过布局文件获得listview中控件的对象
				holder.rssTitle = (TextView)convertView.findViewById(R.id.rss_title);
				holder.rssUrl = (TextView)convertView.findViewById(R.id.rss_url);
				//
				convertView.setTag(holder);
			} else {
				//将存在的listview内容赋值给缓存的ViewHolder
				holder = (ViewHolder)convertView.getTag();
			}
			//往listview中添加数据
			HashMap<String, String> map = listViewData.get(position);
			holder.rssTitle.setText(map.get("rss_title"));
			holder.rssUrl.setText(map.get("rss_url"));
			
			return convertView;
		}
	}
	class ViewHolder {
		public TextView rssTitle;
		public TextView rssUrl;
	}
}

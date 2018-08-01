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
import android.os.Bundle;
import android.os.Vibrator;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.thy.rss.bean.RssInfoBean;
import com.thy.rss.common.CommonUtils;
import com.thy.rss.dao.RssOperDAO;
import com.thy.rss.db.SQLiteUtils;

/**
 * RSS内容管理
 * @author tanghaoyuan
 * @2013-4-11
 */
public class RssMgrListViewActivity extends Activity {

	private EditText etRssTitle;
	private Spinner spRssType;
	
	private EditText dialogEtRssTitle; 
	private EditText dialogEtRssUrl;
	
	private Button btnSearchDialog;
	ListView listView = null;
	private RssMgrAdapter rssMgrAdapter = null;
	private RssOperDAO rssDAO = null;
	
	private int currentPosition = -1;
	
	private List<RssInfoBean> rssList = null;
	private List<HashMap<String, String>> listViewData = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rss_m_listview);
		listView = (ListView)findViewById(R.id.rss_listview_mgr);
		btnSearchDialog = (Button)findViewById(R.id.searchDialog);
		btnSearchDialog.setOnClickListener(new ViewSearchDialog());
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
				//震动一下
				Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
				vibrator.vibrate(40);
				currentPosition = position;
				rssMgrAdapter.notifyDataSetChanged();
			}
			
		});
		rssDAO = new RssOperDAO(new SQLiteUtils(RssMgrListViewActivity.this));
		searchListViewData(null, null);
	}

	//显示搜索条件
	class ViewSearchDialog implements OnClickListener{

		@Override
		public void onClick(View v) {
			LayoutInflater factory = LayoutInflater.from(RssMgrListViewActivity.this);
			final View dialogView = factory.inflate(R.layout.rss_search_dialog, null);
			AlertDialog dialog = new AlertDialog.Builder(RssMgrListViewActivity.this)
			.setTitle("RSS搜索")
			.setIcon(R.drawable.search1)
			.setView(dialogView)
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
	//修改频道信息
	class SaveOnClickListen implements DialogInterface.OnClickListener{

		@Override
		public void onClick(DialogInterface dialog, int which) {
			RssInfoBean bean = rssList.get(currentPosition);
			bean.setRssTitle(dialogEtRssTitle.getText().toString());
			bean.setRssUrl(dialogEtRssUrl.getText().toString());
			rssDAO.updateRss(bean);
			searchListViewData(null, null);
			Toast t = Toast.makeText(RssMgrListViewActivity.this, "保存成功！", Toast.LENGTH_SHORT);
			t.setGravity(Gravity.CENTER, 0, 0);
			t.show();
		}
	}
	
	//执行删除listview的数据
	class DeleteOnClickListen implements DialogInterface.OnClickListener{

		@Override
		public void onClick(DialogInterface dialog, int which) {
			RssInfoBean bean = rssList.get(currentPosition);
			rssDAO.deleteRss(bean.getRssId());
			searchListViewData(null, null);
			Toast t = Toast.makeText(RssMgrListViewActivity.this, "删除成功！", Toast.LENGTH_SHORT);
			t.setGravity(Gravity.CENTER, 0, 0);
			t.show();
		}
	}
	
	/**
	 * 初始化 listview的数据
	 * @param keyWord
	 * @param rssType
	 */
	public void searchListViewData(String keyWord,String rssType){
		rssList = rssDAO.listRss(keyWord, rssType);
		
		listViewData = new ArrayList<HashMap<String,String>>();
		
		for(RssInfoBean rss : rssList){
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("rss_title", rss.getRssTitle());
			listViewData.add(map);
		}
		if(null == listViewData || listViewData.size() <= 0){
			Toast t = Toast.makeText(this, "没有查找到相关记录！", Toast.LENGTH_SHORT);
			t.setGravity(Gravity.CENTER, 0, 0);
			t.show();
		}
		try {
			if(null == rssMgrAdapter){
				//rssMgrAdapter = new RssMgrAdapter(this, listViewData, R.layout.rss_m_listview_item, new String[]{"rss_title","rss_url"}, new int[]{R.id.rss_title,R.id.rss_url});
				rssMgrAdapter = new RssMgrAdapter(this);
				listView.setAdapter(rssMgrAdapter);
			}
			else{
				rssMgrAdapter.notifyDataSetChanged();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("出错了"+e.getMessage());
		}
	}
	
	//自定义我的listview
	class RssMgrAdapter extends BaseAdapter{

		private LayoutInflater inflater;
		
		public RssMgrAdapter(Activity activity){
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
			ViewHolder holder = null;
			if(null == convertView){
				convertView = inflater.inflate(R.layout.rss_m_listview_item, parent, false);
				holder = new ViewHolder();
				holder.rssTitle = (TextView)convertView.findViewById(R.id.rss_title);
				holder.linearLayout = (LinearLayout)convertView.findViewById(R.id.layout_operate);
				holder.editLayout = (LinearLayout)convertView.findViewById(R.id.item_edit);
				holder.deleteLayout = (LinearLayout)convertView.findViewById(R.id.item_delete);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder)convertView.getTag();
			}
			//往listview中添加数据
			HashMap<String, String> map = listViewData.get(position);
			holder.rssTitle.setText(map.get("rss_title"));
			
			//如果点击的当前的item 显示操作按钮
			if (position == currentPosition) {
				holder.linearLayout.setVisibility(View.VISIBLE);
				holder.editLayout.setClickable(true);
				holder.deleteLayout.setClickable(true);
				
				//绑定编辑事件
				holder.editLayout.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						RssInfoBean rss = rssList.get(currentPosition);
						LayoutInflater factory = LayoutInflater.from(RssMgrListViewActivity.this);
						final View dialogView = factory.inflate(R.layout.rss_edit_dialog, null);
						dialogEtRssTitle = (EditText)dialogView.findViewById(R.id.editTitle);
						dialogEtRssUrl = (EditText)dialogView.findViewById(R.id.editUrl);
						//Spinner rssType = (Spinner)dialogView.findViewById(R.id.viewRssType);
						dialogEtRssTitle.setText(rss.getRssTitle());
						dialogEtRssUrl.setText(rss.getRssUrl());
						//关于资源类型暂时没有提供修改功能
						HashMap<String,String> textMap = new HashMap<String, String>();
						HashMap<String,DialogInterface.OnClickListener> eventMap = new HashMap<String, DialogInterface.OnClickListener>();
						textMap.put("title", "频道信息修改");
						textMap.put("positive", "保存");
						textMap.put("negative", "取消");
						eventMap.put("positive", new SaveOnClickListen());
						CommonUtils.showDialogView(RssMgrListViewActivity.this,textMap, R.drawable.rss_edit, dialogView, eventMap);
					}
				});
				//绑定删除事件
				holder.deleteLayout.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						HashMap<String,String> textMap = new HashMap<String, String>();
						HashMap<String,DialogInterface.OnClickListener> eventMap = new HashMap<String, DialogInterface.OnClickListener>();
						textMap.put("title", "注意");
						textMap.put("msg", "确认要删除该频道?");
						textMap.put("positive", "删除");
						textMap.put("negative", "取消");
						eventMap.put("positive", new DeleteOnClickListen());
						CommonUtils.showDialogView(RssMgrListViewActivity.this,textMap, R.drawable.rss_delete, null, eventMap);
					}
				});
			}else {
				holder.linearLayout.setVisibility(View.GONE);
				holder.editLayout.setClickable(false);
				holder.deleteLayout.setClickable(false);
			}
			return convertView;
		}
	}
	class ViewHolder {
		public TextView rssTitle;
		public LinearLayout linearLayout;
		public LinearLayout editLayout;
		public LinearLayout deleteLayout;
	}
	
}

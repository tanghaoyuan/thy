/**
 * 
 */
package com.thy.rss;


import com.thy.rss.bean.RssInfoBean;
import com.thy.rss.dao.RssOperDAO;
import com.thy.rss.db.SQLiteUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * RSS添加页面
 * @author thy
 */
public class RssAddActivity extends Activity {

	private Button btnRssSave;
	private EditText etRssTitle;
	private EditText etRssUrl;
	private Spinner spRssType;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rss_add);
		
		etRssTitle = (EditText)findViewById(R.id.addRssTitle);
		etRssUrl = (EditText)findViewById(R.id.addRssUrl);
		spRssType = (Spinner)findViewById(R.id.addRssType);
		
		btnRssSave = (Button)findViewById(R.id.btnAddRss);
		btnRssSave.setOnClickListener(new RssSaveOnclickListen());
	}
	
	class RssSaveOnclickListen implements OnClickListener{

		@Override
		public void onClick(View v) {
			String rssTitle = etRssTitle.getText().toString();
			String rssUrl = etRssUrl.getText().toString();
			String rssType = spRssType.getSelectedItem().toString();
			if(null == etRssTitle || "".equals(rssTitle)){
				showDialogInfo("RSS的标题不能为空！",false);
				return;
			}
			if(null == etRssUrl || "".equals(rssUrl)){
				showDialogInfo("RSS的URL地址不能为空！",false);
				return;
			}
			RssOperDAO rssDAO = new RssOperDAO(new SQLiteUtils(RssAddActivity.this));
			RssInfoBean rssBean = new RssInfoBean(0,rssTitle,rssUrl,rssType,"");
			rssDAO.addRss(rssBean);
			System.out.println("----> RSS INFO INSERT SUCESS...");
			showDialogInfo("RSS新增成功！",true);
		}
	}
	
	/**
	 * 显示提示信息
	 * @param msg
	 */
	private void showDialogInfo(String msg,final boolean flag){
		int icon = R.drawable.info1;
		if(flag){
			icon = R.drawable.rss_ok;
		}
		Dialog dialog = new AlertDialog.Builder(RssAddActivity.this)
		.setTitle("提示")
		.setIcon(icon)
		.setMessage(msg)
		.setPositiveButton("确定",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				if(flag){
					Intent intent = new Intent();
					intent.setClass(RssAddActivity.this, RssListViewActivity.class);
					startActivity(intent);
					overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
				}
			}
		}).create();
		dialog.show();
	}
}

package com.thy.rss;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class MainRssActivity extends Activity {

	private Button btnAdd;
	private Button btnView;
	private Button btnMgr;
	private Button btnFavorite;
	private Button btnAbout;
	
	private static final int MENU_ABOUT = 1; //菜单选项的第1个=关于
	private static final int MENU_EXIT = 2; //菜单选项的第2个=退出
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//不显示标题
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.index);
        
        btnAdd = (Button)findViewById(R.id.index_btn_add_rss);
        btnView = (Button)findViewById(R.id.index_btn_view_rss);
        btnMgr = (Button)findViewById(R.id.index_btn_m_rss);
        btnFavorite = (Button)findViewById(R.id.index_btn_local_rss);
        btnAbout = (Button)findViewById(R.id.btn_about);
        
        btnAdd.setOnClickListener(new RssAddOnclickListen());
        btnView.setOnClickListener(new RssViewOnclickListen());
        btnMgr.setOnClickListener(new RssMgrOnclickListen());
        btnFavorite.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Dialog dialog = new AlertDialog.Builder(MainRssActivity.this)
				.setTitle("提示")
				.setIcon(R.drawable.info1)
			    .setMessage("功能待建设!")
			    .setPositiveButton("确定", new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
			    }).create();
				dialog.show();
			}
		});
        btnAbout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainRssActivity.this, AboutActivity.class);
				startActivity(intent);
				//左右滑动
				overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right); 
			}
		});
    } 
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//向界面中添加连个菜单选项
		menu.add(0, MENU_ABOUT, 1, R.string.rss_about);
		menu.add(0, MENU_EXIT, 2, R.string.rss_exit);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		//菜单选项的点击事件，是通过选项的id来确定点击的哪个选项
		if(item.getItemId() == MENU_EXIT){
			exitOperation();
		}
		else if(item.getItemId() == MENU_ABOUT){//点击关于按钮
			Intent intent = new Intent();
			intent.setClass(this, AboutActivity.class);
			startActivity(intent);
			//左右滑动
			overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right); 
		}
		return super.onMenuItemSelected(featureId, item);
	}
	
	class RssAddOnclickListen implements OnClickListener{

		@Override
		public void onClick(View v) {
			forwardActivity(RssAddActivity.class);
		}
	}
	
	class RssViewOnclickListen implements OnClickListener{

		@Override
		public void onClick(View v) {
			forwardActivity(RssListViewActivity.class);
		}
	}
	
	class RssMgrOnclickListen implements OnClickListener{

		@Override
		public void onClick(View v) {
			forwardActivity(RssMgrListViewActivity.class);
		}
	}
	
	/**
	 * 用于activity之前的跳转
	 */
	private void forwardActivity(Class c){
		Intent intent = new Intent();
		intent.setClass(MainRssActivity.this, c);
		startActivity(intent);
		//左右滑动
		overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);  
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 如果是返回键,直接返回到桌面
		if(keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME){
			exitOperation();
			//必须返回false不然最后会调用方法最后一句代码，从而使页面照样退出
		    return false;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	/**
	 * 退出操作
	 */
	public void exitOperation(){
		Dialog dialog = new AlertDialog.Builder(MainRssActivity.this)
		.setTitle("提示")
		.setIcon(R.drawable.rss_logout)
	    .setMessage("确定是否要退出程序!")
	    .setPositiveButton("确定", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				MainRssActivity.this.finish();
			}
		})
		.setNegativeButton("取消", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		})
	    .create();
	    dialog.show();
	}
}
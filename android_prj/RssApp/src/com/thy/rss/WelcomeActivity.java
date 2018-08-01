/**
 * 
 */
package com.thy.rss;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * 欢迎页面
 * @author tanghaoyuan
 * @2013-3-28
 */
public class WelcomeActivity extends Activity {

	private final int SPLASH_DISPLAY_LENGHT = 500; // 延迟3秒
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.welcome);
		
		new Handler().postDelayed(new Runnable() {
			public void run() {
				Intent mainIntent = new Intent(WelcomeActivity.this,
						MainRssActivity.class);
				WelcomeActivity.this.startActivity(mainIntent);
				//左右滑动效果
				overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
				WelcomeActivity.this.finish();
			}

		}, SPLASH_DISPLAY_LENGHT);
	}

	
}

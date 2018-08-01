/**
 * 
 */
package com.thy.rss;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * 用户显示网页的内容
 * @author tanghaoyuan
 * @2013-3-31
 */
public class RssWebViewActivity extends Activity{

	private WebView webView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rss_content_view);
		webView = (WebView)findViewById(R.id.rssHtmlContent);
		Intent intent = getIntent();
		String rssItemUrl = intent.getStringExtra("rssItemUrl");
		WebSettings ws = webView.getSettings();
		ws.setJavaScriptEnabled(true);
		ws.setSupportZoom(true);
		ws.setBuiltInZoomControls(true);
		webView.loadUrl(rssItemUrl);
		
	}
}

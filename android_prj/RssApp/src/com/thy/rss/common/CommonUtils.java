/**
 * 
 */
package com.thy.rss.common;

import java.util.HashMap;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;

/**
 * 设备网络相关工具类
 * @author tanghaoyuan
 * @2013-4-21
 */
public class CommonUtils {

	/**
	 * 判断当前设备的网络是否处于可用的状态
	 * @param context
	 * @return
	 */
	public static boolean checkNetWorkConnect(Context context){
		try {
			ConnectivityManager connectivity = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
			if(null != connectivity){
				 // 获取网络连接管理的对象 
	            NetworkInfo info = connectivity.getActiveNetworkInfo(); 
	            if(null != info && info.isConnected()){
	            	// 判断当前网络是否已经连接 
	                if (info.getState() == NetworkInfo.State.CONNECTED) { 
	                    return true; 
	                } 
	            }
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	/**
	 * 创建对话框
	 * @param strTitle
	 * @param icon
	 * @param v
	 * @param eventMap
	 */
	public static void showDialogView(Context context,HashMap<String,String> strTitle,int icon,View v,HashMap<String, DialogInterface.OnClickListener> eventMap){
		AlertDialog dialog = new AlertDialog.Builder(context)
		.setTitle(strTitle.get("title"))
		.setMessage(strTitle.get("msg"))
		.setIcon(icon)
		.setView(v != null ? v : null)
		.setPositiveButton(strTitle.get("positive"), (eventMap != null) ? eventMap.get("positive") : new DefaultEvent())
		.setNegativeButton(strTitle.get("negative"), new DefaultEvent())
		.create();
		dialog.show();
	}
	
	static class DefaultEvent implements DialogInterface.OnClickListener{
		@Override
		public void onClick(DialogInterface dialog, int which) {
			dialog.dismiss();
		}
	}
}

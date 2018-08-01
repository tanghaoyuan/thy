/**
 * 
 */
package com.thy.rss.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.thy.rss.bean.RssInfoBean;
import com.thy.rss.db.SQLiteUtils;

/**
 * RSS信息数据库操作类
 * @author THY
 */
public class RssOperDAO {

	private final static String rssTableName = "RSS_INFO";
	private SQLiteUtils sqLiteUtils;
	
	public RssOperDAO(SQLiteUtils sqLiteUtils){
		this.sqLiteUtils = sqLiteUtils; 
	}
	
	
	/**
	 *新增RSS信息
	 * @param rssTitle
	 * @param rssUrl
	 * @param rssContent
	 */
	public void addRss(RssInfoBean rssObj){
		SQLiteDatabase db = sqLiteUtils.getWritableDatabase();
		try {
			db.beginTransaction();
			ContentValues cv = new ContentValues();
			cv.put(RssColumns.RSS_TTTLE, rssObj.getRssTitle());
			cv.put(RssColumns.RSS_URL, rssObj.getRssUrl());
			cv.put(RssColumns.RSS_TYPE, rssObj.getRssType());
			cv.put(RssColumns.RSS_CONTENT, rssObj.getRssContent());
			db.insert(rssTableName, "RSS_CONTENT", cv);
			db.setTransactionSuccessful();
			db.endTransaction();
			System.out.println("rss_info insert sucess...");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("====>"+e.getMessage());
		}finally{
			if(null != db){
				db.close();
			}
		}
	}
	
	/**
	 * 修改RSS信息
	 * @param rssId
	 * @param rssTitle
	 * @param rssUrl
	 * @param rssContent
	 * @return
	 */
	public int updateRss(RssInfoBean rssObj){
		SQLiteDatabase db = sqLiteUtils.getWritableDatabase();
		int res = -1;
		try {
			db.beginTransaction();
			ContentValues cv = new ContentValues();
			cv.put(RssColumns.RSS_ID,rssObj.getRssId());
			cv.put(RssColumns.RSS_TTTLE,rssObj.getRssTitle());
			cv.put(RssColumns.RSS_URL,rssObj.getRssUrl());
			cv.put(RssColumns.RSS_TYPE, rssObj.getRssType());
			cv.put(RssColumns.RSS_CONTENT,rssObj.getRssContent());
			String [] args = {String.valueOf(rssObj.getRssId())};
			res = db.update(rssTableName, cv, "RSS_ID=?", args);
			db.setTransactionSuccessful();
			db.endTransaction();
			System.out.println("rss_info update sucess...");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(null != db){
				db.close();
			}
		}
		return res;
	}
	
	/**
	 * 删除RSS信息
	 * @param rssId
	 * @return
	 */
	public int deleteRss(int rssId){
		SQLiteDatabase db = sqLiteUtils.getWritableDatabase();
		try {
			String [] args = {String.valueOf(rssId)};
			return db.delete(rssTableName, "RSS_ID=?", args);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(null != db){
				db.close();
			}
		}
		return -1;
	}
	
	/**
	 * 条件查询RSS信息
	 * @param keyWord
	 * @param rssType
	 * @return
	 */
	public List<RssInfoBean> listRss(String keyWord,String rssType){
		List<RssInfoBean> rssList = new ArrayList<RssInfoBean>();
		SQLiteDatabase readdb = sqLiteUtils.getReadableDatabase();
		String whereStr = "1=1 ";
		List<String> strList = new ArrayList<String>();
		String [] args = new String[]{};
		if(null != keyWord && !"".equals(keyWord)){
			whereStr+=" and "+RssColumns.RSS_TTTLE+" like ?";
			strList.add("%"+keyWord+"%");
		}
		if(null != rssType && !"".equals(rssType)){
			whereStr+=" and "+RssColumns.RSS_TYPE+" = ?";
			strList.add(rssType);
		}
		args = strList.toArray(args);
		Cursor cur = readdb.query(rssTableName, RssColumns.getRssListColumns(), whereStr, args, null, null, "RSS_ID DESC");
		while(cur.moveToNext()){
			int rssId = cur.getInt(cur.getColumnIndex(RssColumns.RSS_ID));
			String rssTitle = cur.getString(cur.getColumnIndex(RssColumns.RSS_TTTLE));
			String rssUrl = cur.getString(cur.getColumnIndex(RssColumns.RSS_URL));
			String rssInfoType = cur.getString(cur.getColumnIndex(RssColumns.RSS_TYPE));
			RssInfoBean rss = new RssInfoBean(rssId,rssTitle,rssUrl,rssInfoType,"");
			
			rssList.add(rss);
		}
		readdb.close();
		return rssList;
	}
}

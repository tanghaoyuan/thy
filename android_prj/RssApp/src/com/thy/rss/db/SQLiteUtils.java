package com.thy.rss.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.thy.rss.dao.RssColumns;

/**
 * @author thy
 *
 */
public class SQLiteUtils extends SQLiteOpenHelper {

	private SQLiteDatabase db;
	private final static String dbName = "rssInfo.db";
	private final static int dbVersion = 1; 
	
	private final static String RSS_INIT_TABLE = "CREATE TABLE RSS_INFO (" + RssColumns.RSS_ID + " " +
			"INTEGER PRIMARY KEY AUTOINCREMENT," + RssColumns.RSS_TTTLE + " TEXT," + RssColumns.RSS_URL + " " +
			"TEXT," + RssColumns.RSS_TYPE + " TEXT," + RssColumns.RSS_CONTENT + " TEXT)";
	private final static String RSS_DROP_TABLE = "DROP TABLE IF EXISTS RSS_INFO"; 
	
	public SQLiteUtils(Context context) {
		super(context, dbName, null, dbVersion);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		this.db = db;
		db.execSQL(RSS_DROP_TABLE);
		db.execSQL(RSS_INIT_TABLE);
		
		System.out.println("create table over...");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(RSS_DROP_TABLE);
		onCreate(db);
	}
	
	public SQLiteDatabase getSQLiteDatabase(){
		return this.db;
	}
}

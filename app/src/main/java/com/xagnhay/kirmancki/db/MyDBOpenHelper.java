package com.xagnhay.kirmancki.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDBOpenHelper extends SQLiteOpenHelper {
	private static final String LOGTAG = "KIRMANCKI DBHELPER";

	private static final String DATABASE_NAME = "kirmancki.db";
	private static final int DATABASE_VERSION = 2;
	
	public static final String TABLE_CATEGORY = "category";
	public static final String COLUMN_CATID   = "catId";
	public static final String COLUMN_CATNAME = "catName";
	public static final String COLUMN_CATDESC = "catDesc";
	public static final String COLUMN_CATLANGID = "catLangId";
	
//	private static final String TABLE_CREATE_CATEGORY = 
//			"CREATE TABLE " + TABLE_CATEGORY + "(" +
//			COLUMN_CATID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//			COLUMN_CATLANGID + " INTEGER, " + 		
//			COLUMN_CATNAME + " TEXT, " +
//			COLUMN_CATDESC + " TEXT " +			
//			")";
		private static final String TABLE_CREATE_CATEGORY = 
				"CREATE TABLE " + TABLE_CATEGORY + "(" +
				COLUMN_CATID + " INTEGER, " +
				COLUMN_CATLANGID + " INTEGER, " + 		
				COLUMN_CATNAME + " TEXT, " +
				COLUMN_CATDESC + " TEXT,  UNIQUE(catlangId,catId) ON CONFLICT REPLACE" +			
				")";	
	
//	public static final String CREATE_INDEX_CATEGORY_IDLANG =
//			"CREATE UNIQUE INDEX IF NOT EXIST category_catid_lang_idx ON category (catlangId,catId)";
	
	public static final String TABLE_WORDS = "words";
	public static final String COLUMN_WORDSID   = "wordId";
	public static final String COLUMN_WORDSGROUPID   = "groupId";	
	public static final String COLUMN_WORDSLANGID = "langId";
	public static final String COLUMN_WORDSTYPE = "wordType";	
	public static final String COLUMN_WORDSCATID = "catId";
	public static final String COLUMN_WORDSACTIVE = "wordActive";
	public static final String COLUMN_WORDSCUSTOM = "wordCustom";
	public static final String COLUMN_WORDSTEXT = "wordText";
	public static final String COLUMN_WORDSREMARKS = "wordRemarks";
	public static final String COLUMN_WORDSUSAGE = "wordUsage";
	public static final String COLUMN_WORDSSOUND = "wordSound";
	public static final String COLUMN_WORDSIMAGE = "wordImage";
	

	private static final String TABLE_CREATE_WORDS = 
			"CREATE TABLE " + TABLE_WORDS + "(" +
			COLUMN_WORDSID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			COLUMN_WORDSGROUPID + " INTEGER, " +
			COLUMN_WORDSLANGID + " INTEGER, " +			
			COLUMN_WORDSCATID + " INTEGER, " +
			COLUMN_WORDSTYPE + " TEXT, " +
			COLUMN_WORDSACTIVE + " INTEGER, " +
			COLUMN_WORDSCUSTOM + " INTEGER, " +
			COLUMN_WORDSTEXT + " TEXT, " +
			COLUMN_WORDSREMARKS + " TEXT, " +
			COLUMN_WORDSUSAGE + " TEXT, " +
			COLUMN_WORDSSOUND + " TEXT, " +
			COLUMN_WORDSIMAGE + " TEXT " +			
			")";
	
	public static final String TABLE_LANGS = "langs";
	public static final String COLUMN_LANGSLANGID   = "langId";
	public static final String COLUMN_LANGSLANGSHORTNAME = "langShortName";
	public static final String COLUMN_LANGSLANGNAME = "langName";
	
	private static final String TABLE_CREATE_LANGS = 
			"CREATE TABLE " + TABLE_LANGS + "(" +
			COLUMN_LANGSLANGID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			COLUMN_LANGSLANGSHORTNAME + " TEXT, " +
			COLUMN_LANGSLANGNAME + " TEXT " +			
			")";
	
	public static final String TABLE_FAVORITES = "favorites";
	public static final String COLUMN_WORDID   = "wordId";
	
	private static final String TABLE_CREATE_FAVORITES = 
			"CREATE TABLE " + TABLE_FAVORITES + "(" +
			COLUMN_WORDID + " INTEGER PRIMARY KEY" +		
			")";
	
	public static final String TABLE_TYPES = "types";
	public static final String COLUMN_TYPEID   = "typeId";
	public static final String COLUMN_TYPENAME = "typeName";
	
	private static final String TABLE_CREATE_TYPES = 
			"CREATE TABLE " + TABLE_TYPES + "(" +
			COLUMN_TYPEID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			COLUMN_TYPENAME + " TEXT " +			
			")";
	
	public MyDBOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {		
		//Log.i(LOGTAG, TABLE_CREATE_CATEGORY);
		db.execSQL(TABLE_CREATE_CATEGORY);
		//db.execSQL(CREATE_INDEX_CATEGORY_IDLANG);		
		db.execSQL(TABLE_CREATE_WORDS);
		db.execSQL(TABLE_CREATE_LANGS);
		db.execSQL(TABLE_CREATE_FAVORITES);
		db.execSQL(TABLE_CREATE_TYPES);
		//Log.i(LOGTAG, "Tables has been created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORDS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LANGS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CREATE_FAVORITES);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TYPES);
		onCreate(db);
		//Log.i(LOGTAG, "Database has been upgraded from " + oldVersion + " to " + newVersion);
	}

}

package com.xagnhay.kirmancki.db;

import java.util.ArrayList;
import java.util.List;

import com.xagnhay.kirmancki.model.Category;
import com.xagnhay.kirmancki.model.Words;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDataSource {

	public static final String LOGTAG="KIRMANCKI DataSource";
	
	SQLiteOpenHelper dbhelper;
	SQLiteDatabase database;
	
	private static final String[] allColumns = {
		MyDBOpenHelper.COLUMN_CATID,
		MyDBOpenHelper.COLUMN_CATNAME,
		MyDBOpenHelper.COLUMN_CATDESC,
		MyDBOpenHelper.COLUMN_CATLANGID};
	
	private static final String[] allWordsColumns = {
		MyDBOpenHelper.COLUMN_WORDSID,
		MyDBOpenHelper.COLUMN_WORDSCATID,
		MyDBOpenHelper.COLUMN_WORDSGROUPID,
		MyDBOpenHelper.COLUMN_WORDSLANGID,
		MyDBOpenHelper.COLUMN_WORDSTEXT};
	
	public MyDataSource(Context context) {
		dbhelper = new MyDBOpenHelper(context);
	}
	
	public void open() {
		Log.i(LOGTAG, "Database opened");
		database = dbhelper.getWritableDatabase();
	}

	public void close() {
		Log.i(LOGTAG, "Database closed");		
		dbhelper.close();
	}
	
	public Category create(Category category) {
		ContentValues values = new ContentValues();
		values.put(MyDBOpenHelper.COLUMN_CATNAME, category.getCatName());
		values.put(MyDBOpenHelper.COLUMN_CATDESC, category.getCatDesc());		
		values.put(MyDBOpenHelper.COLUMN_CATLANGID, category.getCatlangId());
		values.put(MyDBOpenHelper.COLUMN_CATID, category.getCatId());
		long insertid = database.insert(MyDBOpenHelper.TABLE_CATEGORY, null, values);
		//category.setCatId(insertid);
		return category;
	}
	
	public Words create(Words words) {
		ContentValues values = new ContentValues();
		values.put(MyDBOpenHelper.COLUMN_WORDSLANGID, words.getLangId());
		values.put(MyDBOpenHelper.COLUMN_WORDSTEXT, words.getWordText());
		values.put(MyDBOpenHelper.COLUMN_WORDSGROUPID, words.getGroupId());
		values.put(MyDBOpenHelper.COLUMN_WORDSCATID, words.getCatId());
		values.put(MyDBOpenHelper.COLUMN_WORDSREMARKS, words.getWordRemarks());		
		values.put(MyDBOpenHelper.COLUMN_WORDSUSAGE, words.getWordUsage());
		values.put(MyDBOpenHelper.COLUMN_WORDSSOUND, words.getWordSound());		
		values.put(MyDBOpenHelper.COLUMN_WORDSIMAGE, words.getWordImage());
		values.put(MyDBOpenHelper.COLUMN_WORDSTYPE, words.getWordType());		
		values.put(MyDBOpenHelper.COLUMN_WORDSACTIVE, words.getWordActive());
		values.put(MyDBOpenHelper.COLUMN_WORDSCUSTOM, words.getWordCustom());
		long insertid = database.insert(MyDBOpenHelper.TABLE_WORDS, null, values);
		words.setWordId(insertid);
		return words;
	}
	
	public Words addWord(Words words) {
		ContentValues values = new ContentValues();
		values.put(MyDBOpenHelper.COLUMN_WORDSLANGID, words.getLangId());
		values.put(MyDBOpenHelper.COLUMN_WORDSGROUPID, words.getGroupId());
		values.put(MyDBOpenHelper.COLUMN_WORDSCATID, words.getCatId());		
		values.put(MyDBOpenHelper.COLUMN_WORDSTEXT, words.getWordText());
		values.put(MyDBOpenHelper.COLUMN_WORDSACTIVE, words.getWordActive());
		values.put(MyDBOpenHelper.COLUMN_WORDSCUSTOM, words.getWordCustom());		
//		values.put(KirmanckiDBOpenHelper.COLUMN_WORDSREMARKS, words.getWordRemarks());		
//		values.put(KirmanckiDBOpenHelper.COLUMN_WORDSUSAGE, words.getWordUsage());
//		values.put(KirmanckiDBOpenHelper.COLUMN_WORDSSOUND, words.getWordSound());		
//		values.put(KirmanckiDBOpenHelper.COLUMN_WORDSIMAGE, words.getWordImage());
//		values.put(KirmanckiDBOpenHelper.COLUMN_WORDSTYPE, words.getWordType());		
		long insertid = database.insert(MyDBOpenHelper.TABLE_WORDS, null, values);
		words.setWordId(insertid);
		return words;
	}
	
	public int updateWords(Words words) {
		ContentValues values = new ContentValues();
		values.put(MyDBOpenHelper.COLUMN_WORDSCATID, words.getCatId());
		values.put(MyDBOpenHelper.COLUMN_WORDSACTIVE, words.getWordActive());		
		values.put(MyDBOpenHelper.COLUMN_WORDSCUSTOM, words.getWordCustom());
		values.put(MyDBOpenHelper.COLUMN_WORDSGROUPID, words.getGroupId());
		values.put(MyDBOpenHelper.COLUMN_WORDSIMAGE, words.getWordImage());
		values.put(MyDBOpenHelper.COLUMN_WORDSLANGID, words.getLangId());
		values.put(MyDBOpenHelper.COLUMN_WORDSREMARKS, words.getWordRemarks());
		values.put(MyDBOpenHelper.COLUMN_WORDSSOUND, words.getWordSound());
		values.put(MyDBOpenHelper.COLUMN_WORDSTEXT, words.getWordText());
		values.put(MyDBOpenHelper.COLUMN_WORDSTYPE, words.getWordType());
		values.put(MyDBOpenHelper.COLUMN_WORDSUSAGE, words.getWordUsage());
		
		return database.update(MyDBOpenHelper.TABLE_WORDS, values, MyDBOpenHelper.COLUMN_WORDID + " = ?",
				new String[] { String.valueOf(words.getWordId()) });
	}
	
	public List<Category> findAll(String selection) {
		
		Cursor cursor = database.query(MyDBOpenHelper.TABLE_CATEGORY, allColumns, 
				selection, null, null, null, null);
				
		Log.i(LOGTAG, "Returned " + cursor.getCount() + " rows");
		List<Category> categories = cursorToList(cursor);
		return categories;
	}

	public List<Category> findFiltered(String selection, String orderBy) {
		
		Cursor cursor = database.query(MyDBOpenHelper.TABLE_CATEGORY, allColumns, 
				selection, null, null, null, orderBy);
				
		Log.i(LOGTAG, "Returned " + cursor.getCount() + " rows");
		List<Category> categories = cursorToList(cursor);
		return categories;
	}
	
	private List<Category> cursorToList(Cursor cursor) {
		List<Category> categories = new ArrayList<Category>();
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				Category category = new Category();
				category.setCatId(cursor.getLong(cursor.getColumnIndex(MyDBOpenHelper.COLUMN_CATID)));
				category.setCatName(cursor.getString(cursor.getColumnIndex(MyDBOpenHelper.COLUMN_CATNAME)));
				category.setCatDesc(cursor.getString(cursor.getColumnIndex(MyDBOpenHelper.COLUMN_CATDESC)));				
				category.setCatlangId(cursor.getLong(cursor.getColumnIndex(MyDBOpenHelper.COLUMN_CATLANGID)));
				categories.add(category);
			}
		}
		return categories;
	}
	
	public List<Words> listWords() {

		String query = "SELECT words.* FROM " +
				"words JOIN category ON " +
				"words.catId = category.catId and category.catlangid = 1";
		Cursor cursor = database.rawQuery(query, null);

		//Log.i(LOGTAG, "Words returned " + cursor.getCount() + " rows");

		List<Words> words = wordscursorToList(cursor);
		return words;
	}
	
	public int CountWords(long catID) {

		String query = "SELECT words.* FROM " +
				"words JOIN category ON " +
				"words.catId = category.catId and category.catId = " + catID;
		Cursor cursor = database.rawQuery(query, null);

		//Log.i(LOGTAG, "Words returned " + cursor.getCount() + " rows");

		return cursor.getCount();
	}	
	
	public Words FindTranslationWord(long translateToLanguage, long groupId) {

		String query = "SELECT words.* FROM words " +
				"where langid = " + translateToLanguage + " and groupId = " + groupId;
		Cursor cursor = database.rawQuery(query, null);
		
		Words w= new Words();
		
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {				
				w.setWordId(cursor.getLong(cursor.getColumnIndex(MyDBOpenHelper.COLUMN_WORDSID)));
				w.setCatId(cursor.getLong(cursor.getColumnIndex(MyDBOpenHelper.COLUMN_WORDSCATID)));
				w.setGroupId(cursor.getLong(cursor.getColumnIndex(MyDBOpenHelper.COLUMN_WORDSGROUPID)));
				w.setLangId(cursor.getLong(cursor.getColumnIndex(MyDBOpenHelper.COLUMN_WORDSLANGID)));				
				w.setWordText(cursor.getString(cursor.getColumnIndex(MyDBOpenHelper.COLUMN_WORDSTEXT)));				
			}
		}
		return w;
	}
	
	public List<Words> listFilteredWords(String selection, String orderBy) {
		
		Cursor cursor = database.query(MyDBOpenHelper.TABLE_WORDS, allWordsColumns, 
				selection, null, null, null, orderBy);

		List<Words> wordss = wordscursorToList(cursor);
		return wordss;
	}
	
	private List<Words> wordscursorToList(Cursor cursor) {
		List<Words> wordss = new ArrayList<Words>();
		 
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				Words w= new Words();
				w.setWordId(cursor.getLong(cursor.getColumnIndex(MyDBOpenHelper.COLUMN_WORDSID)));
				w.setCatId(cursor.getLong(cursor.getColumnIndex(MyDBOpenHelper.COLUMN_WORDSCATID)));
				w.setGroupId(cursor.getLong(cursor.getColumnIndex(MyDBOpenHelper.COLUMN_WORDSGROUPID)));
				w.setLangId(cursor.getLong(cursor.getColumnIndex(MyDBOpenHelper.COLUMN_WORDSLANGID)));				
				w.setWordText(cursor.getString(cursor.getColumnIndex(MyDBOpenHelper.COLUMN_WORDSTEXT)));				
//				w.setWordActive(cursor.getInt(cursor.getColumnIndex(KirmanckiDBOpenHelper.COLUMN_WORDSACTIVE)));
//				w.setWordCustom(cursor.getInt(cursor.getColumnIndex(KirmanckiDBOpenHelper.COLUMN_WORDSCUSTOM)));
//				w.setWordImage(cursor.getString(cursor.getColumnIndex(KirmanckiDBOpenHelper.COLUMN_WORDSIMAGE)));
//				w.setWordRemarks(cursor.getString(cursor.getColumnIndex(KirmanckiDBOpenHelper.COLUMN_WORDSREMARKS)));
//				w.setWordSound(cursor.getString(cursor.getColumnIndex(KirmanckiDBOpenHelper.COLUMN_WORDSSOUND)));	
//				w.setWordUsage(cursor.getString(cursor.getColumnIndex(KirmanckiDBOpenHelper.COLUMN_WORDSUSAGE)));
//				w.setWordType(cursor.getString(cursor.getColumnIndex(KirmanckiDBOpenHelper.COLUMN_WORDSTYPE)));
				wordss.add(w);
				
			}
		}
		return wordss;
	}
	
	public List<String> filterWordsForExpandableLV(String selection, String orderBy) {
		 
		ArrayList<String> list1 = new ArrayList<String>(); 
		 
		Cursor cursor = database.query(MyDBOpenHelper.TABLE_WORDS, allWordsColumns, 
				selection, null, null, null, orderBy);

		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				list1.add(cursor.getString(cursor.getColumnIndex(MyDBOpenHelper.COLUMN_WORDSTEXT)));
			}
		}
		return list1;
	}
	
}

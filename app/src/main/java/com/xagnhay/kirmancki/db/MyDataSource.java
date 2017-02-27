package com.xagnhay.kirmancki.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.xagnhay.kirmancki.model.Category;
import com.xagnhay.kirmancki.model.Quiz;
import com.xagnhay.kirmancki.model.W1d;
import com.xagnhay.kirmancki.model.W1m;
import com.xagnhay.kirmancki.model.Words;

import java.util.ArrayList;
import java.util.List;

import static com.xagnhay.kirmancki.R.raw.words;

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
		MyDBOpenHelper.COLUMN_WORDSTEXT,
		MyDBOpenHelper.COLUMN_WORDSACTIVE,
		MyDBOpenHelper.COLUMN_WORDSCUSTOM};

    private static final String[] allW1MColumns = {
            MyDBOpenHelper.COLUMN_W1LETTER};

    private static final String[] allW1DColumns = {
            MyDBOpenHelper.COLUMN_W1D_LETTER,
            MyDBOpenHelper.COLUMN_W1D_WL1,
            MyDBOpenHelper.COLUMN_W1D_WL2,
            MyDBOpenHelper.COLUMN_W1D_S1,
            MyDBOpenHelper.COLUMN_W1D_S2,
            MyDBOpenHelper.COLUMN_W1D_AUDIO
    };

	public MyDataSource(Context context) {
		dbhelper = new MyDBOpenHelper(context);
	}
	
	public void open() {
		//Log.i(LOGTAG, "Database opened");
		database = dbhelper.getWritableDatabase();
	}

	public void close() {
		//Log.i(LOGTAG, "Database closed");
		dbhelper.close();
	}
	
	public Category create(Category category) {
		ContentValues values = new ContentValues();
		values.put(MyDBOpenHelper.COLUMN_CATNAME, category.getCatName());
		values.put(MyDBOpenHelper.COLUMN_CATDESC, category.getCatDesc());		
		values.put(MyDBOpenHelper.COLUMN_CATLANGID, category.getCatLangId());
		values.put(MyDBOpenHelper.COLUMN_CATID, category.getCatId());
		long insertid = database.insert(MyDBOpenHelper.TABLE_CATEGORY, null, values);
		//category.setCatId(insertid);
		return category;
	}
	
	public Words create(Words words) {
		ContentValues values = new ContentValues();
		values.put(MyDBOpenHelper.COLUMN_WORDSLANGID, words.getLangId());
        values.put(MyDBOpenHelper.COLUMN_WORDSGROUPID, words.getGroupId());
        values.put(MyDBOpenHelper.COLUMN_WORDSCATID, words.getCatId());
		values.put(MyDBOpenHelper.COLUMN_WORDSTEXT, words.getWordText());
		//values.put(MyDBOpenHelper.COLUMN_WORDSREMARKS, words.getWordRemarks());
		//values.put(MyDBOpenHelper.COLUMN_WORDSUSAGE, words.getWordUsage());
		//values.put(MyDBOpenHelper.COLUMN_WORDSSOUND, words.getWordSound());
		//values.put(MyDBOpenHelper.COLUMN_WORDSIMAGE, words.getWordImage());
		//values.put(MyDBOpenHelper.COLUMN_WORDSTYPE, words.getWordType());
		values.put(MyDBOpenHelper.COLUMN_WORDSACTIVE, words.getWordActive());
		values.put(MyDBOpenHelper.COLUMN_WORDSCUSTOM, words.getWordCustom());
		long insertid = database.insert(MyDBOpenHelper.TABLE_WORDS, null, values);
		words.setWordId(insertid);
		return words;
	}

	public W1m create(W1m w1m) {
		ContentValues values = new ContentValues();
		values.put(MyDBOpenHelper.COLUMN_W1LETTER, w1m.getLetter());
		long insertid = database.insert(MyDBOpenHelper.TABLE_W1M, null, values);
		return w1m;
	}

	public W1d create(W1d w1d) {
		ContentValues values = new ContentValues();
		values.put(MyDBOpenHelper.COLUMN_W1D_LETTER, w1d.getLetter());
		values.put(MyDBOpenHelper.COLUMN_W1D_WL1, w1d.getWl1());
		values.put(MyDBOpenHelper.COLUMN_W1D_WL2, w1d.getWl2());
		values.put(MyDBOpenHelper.COLUMN_W1D_S1, w1d.getS1());
		values.put(MyDBOpenHelper.COLUMN_W1D_S2, w1d.getS2());
		values.put(MyDBOpenHelper.COLUMN_W1D_AUDIO, w1d.getAudio());
		long insertid = database.insert(MyDBOpenHelper.TABLE_W1D, null, values);
		return w1d;
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

    public void createBulkCategories(List<Category> allCategories){
        database.beginTransaction();
        try{
            for (Category c1 : allCategories) {
                create(c1);
            }
            database.setTransactionSuccessful();
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            database.endTransaction();
        }
    }

    public void createBulkWords(List<Words> allWords){
        database.beginTransaction();
        try{
            for (Words w1 : allWords) {
                //if(isWordsExist("wordText=\"" + w1.getWordText() + "\"") == 0 )
                create(w1);

            }
            database.setTransactionSuccessful();
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            database.endTransaction();
        }
    }

    public void createBulkGuideMaster(List<W1m> allW1ms){
        database.beginTransaction();
        try{
            for (W1m w1 : allW1ms) {
                create(w1);
            }
            database.setTransactionSuccessful();
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            database.endTransaction();
        }
    }

    public void createBulkGuideDetail(List<W1d> allW1ds){
        database.beginTransaction();
        try{
            for (W1d w1 : allW1ds) {
                create(w1);
            }
            database.setTransactionSuccessful();
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            database.endTransaction();
        }
    }

	public List<Category> findAll(String selection) {
		
		Cursor cursor = database.query(MyDBOpenHelper.TABLE_CATEGORY, allColumns, 
				selection, null, null, null, null);
				
		//Log.i(LOGTAG, "Returned categories: " + cursor.getCount() + " rows");
		List<Category> categories = cursorToList(cursor);
		return categories;
	}


	public List<Category> findFiltered(String selection, String orderBy) {
		
		Cursor cursor = database.query(MyDBOpenHelper.TABLE_CATEGORY, allColumns, 
				selection, null, null, null, orderBy);
				
		//Log.i(LOGTAG, "Returned " + cursor.getCount() + " rows");
		List<Category> categories = cursorToList(cursor);
		return categories;
	}

    //to fill spinner on quizactivity with sorted category names
	public List<String> listCategoriesSpinner(String selection, String orderBy) {

		ArrayList<String> listCat = new ArrayList<String>();

		Cursor cursor = database.query(MyDBOpenHelper.TABLE_CATEGORY, allColumns,
				selection, null, null, null, orderBy);

		//Log.i(LOGTAG, "Returned " + cursor.getCount() + " rows");
		if (cursor.getCount() != 0) {
			while (cursor.moveToNext()){
				listCat.add(cursor.getString(cursor.getColumnIndex(MyDBOpenHelper.COLUMN_CATNAME)));
			}
		}
		return listCat;
	}

    public int getCategoryIDforSpinner(String selection) {

        int newCatId = 0;
        Cursor cursor = database.query(MyDBOpenHelper.TABLE_CATEGORY, allColumns,
                selection, null, null, null, null);

        Log.i(LOGTAG, "Returned " + cursor.getCount() + " rows");
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()){
                newCatId = cursor.getInt(cursor.getColumnIndex(MyDBOpenHelper.COLUMN_CATID));
            }
        }
        return newCatId;
    }
	
	private List<Category> cursorToList(Cursor cursor) {
		List<Category> categories = new ArrayList<Category>();
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				Category category = new Category();
				category.setCatId(cursor.getLong(cursor.getColumnIndex(MyDBOpenHelper.COLUMN_CATID)));
				category.setCatName(cursor.getString(cursor.getColumnIndex(MyDBOpenHelper.COLUMN_CATNAME)));
				category.setCatDesc(cursor.getString(cursor.getColumnIndex(MyDBOpenHelper.COLUMN_CATDESC)));				
				category.setCatLangId(cursor.getLong(cursor.getColumnIndex(MyDBOpenHelper.COLUMN_CATLANGID)));
				categories.add(category);
			}
		}
		return categories;
	}
	
	public List<Words> listWords() {

		String query = "SELECT words.* FROM " +
				"words JOIN category ON " +
				"words.catId = category.catId and category.catlangid = 2";
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

		List<Words> wordsList = wordscursorToList(cursor);
		return wordsList;
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
				w.setWordActive(cursor.getInt(cursor.getColumnIndex(MyDBOpenHelper.COLUMN_WORDSACTIVE)));
				w.setWordCustom(cursor.getInt(cursor.getColumnIndex(MyDBOpenHelper.COLUMN_WORDSCUSTOM)));
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
				list1.add(cursor.getString(cursor.getColumnIndex(MyDBOpenHelper.COLUMN_WORDSTEXT))); // + "-" + cursor.getString(cursor.getColumnIndex(MyDBOpenHelper.COLUMN_WORDSGROUPID)));
			}
		}
		return list1;
	}

    public int isWordsExist(String selection) {

        //Aynı kayıttan tekrar yaratmamak için bu kullanılıyor.
        Cursor cursor = database.query(MyDBOpenHelper.TABLE_WORDS, allWordsColumns,
                selection, null, null, null, null);

        return cursor.getCount();
    }

    public int isCategoryExist(String selection) {

        //Aynı kayıttan tekrar yaratmamak için bu kullanılıyor.
        Cursor cursor = database.query(MyDBOpenHelper.TABLE_CATEGORY, allColumns,
                selection, null, null, null, null);

        return cursor.getCount();
    }

	public List<Quiz> getAllQuestion(String selection, String orderBy) {
		List<Quiz> quizQuestions = new ArrayList<>();
        //List<String> lq = new ArrayList<String>();
        //List<String> la = new ArrayList<String>();

		Cursor cursor = database.query(MyDBOpenHelper.TABLE_WORDS, allWordsColumns,
				selection, null, null, null, orderBy);
		while (cursor.moveToNext()) {
			Quiz quiz = new Quiz();
			quiz.setId(cursor.getInt(0));
			quiz.setQuestion(cursor.getString(1));
			quiz.setOption1(cursor.getString(2));
			quiz.setOption2(cursor.getString(3));
			quiz.setOption3(cursor.getString(4));
			quiz.setOption4(cursor.getString(5));
			quiz.setAnswer(cursor.getString(6));
			quizQuestions.add(quiz);
		}
		return quizQuestions;
	}

    public List<W1m> findAllGuideMaster(String selection) {

        List<W1m> w1ms = new ArrayList<>();

        Cursor cursor = database.query(MyDBOpenHelper.TABLE_W1M, allW1MColumns,
                selection, null, null, null, null);

        if(cursor.getCount() > 0){
            while (cursor.moveToNext()){
                W1m w1m = new W1m();
                w1m.setLetter(cursor.getString(cursor.getColumnIndex(MyDBOpenHelper.COLUMN_W1LETTER)));
                w1ms.add(w1m);
            }

        }
        //Log.i(LOGTAG, "Returned categories: " + cursor.getCount() + " rows");
        return w1ms;
    }

    public List<W1d> findAllGuideDetails(String selection) {

        List<W1d> w1ds = new ArrayList<>();

        Cursor cursor = database.query(MyDBOpenHelper.TABLE_W1D, allW1DColumns,
                selection, null, null, null, null);

        if(cursor.getCount() > 0){
            while (cursor.moveToNext()){
                W1d w1d = new W1d();
                w1d.setLetter(cursor.getString(cursor.getColumnIndex(MyDBOpenHelper.COLUMN_W1D_LETTER)));
                w1d.setWl1(cursor.getString(cursor.getColumnIndex(MyDBOpenHelper.COLUMN_W1D_WL1)));
                w1d.setWl2(cursor.getString(cursor.getColumnIndex(MyDBOpenHelper.COLUMN_W1D_WL2)));
                w1d.setS1(cursor.getString(cursor.getColumnIndex(MyDBOpenHelper.COLUMN_W1D_S1)));
                w1d.setS2(cursor.getString(cursor.getColumnIndex(MyDBOpenHelper.COLUMN_W1D_S2)));
                w1d.setAudio(cursor.getString(cursor.getColumnIndex(MyDBOpenHelper.COLUMN_W1D_AUDIO)));
                w1ds.add(w1d);
            }

        }
        //Log.i(LOGTAG, "Returned categories: " + cursor.getCount() + " rows");
        return w1ds;
    }


}

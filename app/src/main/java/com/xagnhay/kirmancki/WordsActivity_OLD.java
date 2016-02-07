package com.xagnhay.kirmancki;

import java.util.List;

import com.xagnhay.kirmancki.db.MyDataSource;
import com.xagnhay.kirmancki.model.Category;
import com.xagnhay.kirmancki.model.Words;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
//import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class WordsActivity_OLD extends ListActivity {
	private static final String LOGTAG = "WORDS";
	public int nativeLanguage = 1;
	public int translateToLanguage = 2;
	
	private List<Words> wordss;
	MyDataSource datasource;
	Category category;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		
		datasource = new MyDataSource(this);
		datasource.open();
		
		//Intent intent = getIntent();
		//Long id = intent.getLongExtra("CatID", 1);
		Bundle b = getIntent().getExtras();
		category = b.getParcelable(".model.Category");
		
		wordss = datasource.listFilteredWords("langid = " + nativeLanguage + " and catId = " + category.getCatId(), "wordText ASC");
		
		refreshDisplay();
		
	}
	
	public void refreshDisplay() {
		ArrayAdapter<Words> adapter = new ArrayAdapter<Words>(this, android.R.layout.simple_list_item_1, wordss);
		setListAdapter(adapter);		
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		Words w = wordss.get(position);
		//Words w2 = datasource.FindTranslationWord(translateToLanguage, w.getGroupId());
		if (w != null) {
			Intent intent = new Intent(this, WordsDetailActivity.class);
			//intent.putExtra("wordNative", w.getWordText());
			//intent.putExtra("wordTranslated", w2.getWordText());
			
			intent.putExtra(".model.Words", w);
			
			startActivity(intent);			
		}		
	}
	
}

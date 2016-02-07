package com.xagnhay.kirmancki;

import com.xagnhay.kirmancki.db.MyDataSource;
import com.xagnhay.kirmancki.model.Words;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class WordsDetailActivity extends Activity {

	public int translateToLanguage = 2;
	MyDataSource datasource;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_worddetail);
		
		datasource = new MyDataSource(this);
		datasource.open();
		
		Bundle b = getIntent().getExtras();
		Words wordNative = b.getParcelable(".model.Words");
		
		Words wordTranslate = datasource.FindTranslationWord(translateToLanguage, wordNative.getGroupId());
		
		TextView tv1 = (TextView) findViewById(R.id.textView1);
		TextView tv2 = (TextView) findViewById(R.id.textView2);

		//Intent intent = getIntent();
		//String wordNative = intent.getStringExtra("wordNative");
		//String wordTranslated = intent.getStringExtra("wordTranslated");		
		//tv1.setText(wordNative);
		//tv2.setText(wordTranslated);
		
		tv1.setText(wordNative.getWordText());
		tv2.setText(wordTranslate.getWordText());
	}

}

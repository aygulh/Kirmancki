package com.xagnhay.kirmancki;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.xagnhay.kirmancki.db.MyDataSource;
import com.xagnhay.kirmancki.db.MyDBOpenHelper;
import com.xagnhay.kirmancki.model.Category;
import com.xagnhay.kirmancki.model.Words;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

public class WordsActivity extends Activity {

	private static final String LOGTAG = "WORDS_DETAIL";
	public int nativeLanguage = 1;
	public int translateToLanguage = 2;
	
	MyDataSource datasource;
	Category category;
	
	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	List<String> listDataHeader;
	List<String> listDataDetail;
	HashMap<String, List<String>> listDataChild;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_explistview);

		// get the listview
		expListView = (ExpandableListView) findViewById(R.id.lvExp);

		datasource = new MyDataSource(this);
		datasource.open();
		
		//Intent intent = getIntent();
		//Long id = intent.getLongExtra("CatID", 1);
		Bundle b = getIntent().getExtras();
		category = b.getParcelable(".model.Category");
		
//		wordss = datasource.listFilteredWords("langid = " + nativeLanguage + " and catId = " + category.getCatId(), "wordText ASC");
		
//		Words wordTranslate = datasource.FindTranslationWord(translateToLanguage, wordNative.getGroupId());
		
		// preparing list data
		prepareListData();

		listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

		expListView.setAdapter(listAdapter);

		expListView.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				// Toast.makeText(getApplicationContext(),
				// "Group Clicked " + listDataHeader.get(groupPosition),
				// Toast.LENGTH_SHORT).show();
				return false;
			}
		});

		// Listview Group expanded listener
		expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int groupPosition) {
//				Toast.makeText(getApplicationContext(),
//						listDataHeader.get(groupPosition) + " Expanded",
//						Toast.LENGTH_SHORT).show();
			}
		});

		// Listview Group collasped listener
		expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

			@Override
			public void onGroupCollapse(int groupPosition) {
//				Toast.makeText(getApplicationContext(),
//						listDataHeader.get(groupPosition) + " Collapsed",
//						Toast.LENGTH_SHORT).show();

			}
		});

		// Listview on child click listener
		expListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
//				Toast.makeText(
//						getApplicationContext(),
//						listDataHeader.get(groupPosition)
//								+ " : "
//								+ listDataChild.get(
//										listDataHeader.get(groupPosition)).get(
//										childPosition), Toast.LENGTH_SHORT)
//						.show();
				return false;
			}
		});
	}
	
	/*
	 * Preparing the list data
	 */
	private void prepareListData() {
		
		listDataHeader = new ArrayList<String>();
		List<String> lh = new ArrayList<String>();
		List<String> ld = new ArrayList<String>();
		
		listDataChild = new HashMap<String, List<String>>();

		// Adding child data
		lh = datasource.filterWordsForExpandableLV("langid = " + nativeLanguage + " and catId = " + category.getCatId(), "groupId, langId");
		ld = datasource.filterWordsForExpandableLV("langid = " + translateToLanguage + " and catId = " + category.getCatId(), "groupId, langId");
		Log.i(LOGTAG, "number of list words:" + lh.size() + "number of translate details:" + ld.size());
		
		for (int i = 0; i <lh.size(); i++) {
			Log.i(LOGTAG, "words:" + lh.get(i));
			listDataHeader.add(lh.get(i));
		}
		for (int i = 0; i <ld.size(); i++) {
			Log.i(LOGTAG, "words detail:" + ld.get(i));
		}

		for (int i = 0; i < lh.size(); i++) {			
//			ld2.clear();
//			ld2.add(" -> " + ld.get(i));
//			ld2.add("-------");
			listDataChild.put(listDataHeader.get(i), getTranslate(ld, i)); // Header, Child data
			Log.i(LOGTAG, listDataHeader.get(i) + " -> " + ld.toString());
		}

	}
	
	public List<String> getTranslate(List<String> selection, int position) {
		 
		ArrayList<String> list1 = new ArrayList<String>(); 
		list1.add(selection.get(position));
		if (list1.size() <= 0) {
			list1.add("--");
		}
		return list1;
	}
}

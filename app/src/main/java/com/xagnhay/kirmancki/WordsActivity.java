package com.xagnhay.kirmancki;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.TextView;
import android.widget.Toast;

import com.xagnhay.kirmancki.db.MyDataSource;
import com.xagnhay.kirmancki.model.Category;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class WordsActivity extends Activity {

    public static final String LOGTAG = WordsActivity.class.getSimpleName();
    public static final String ANADIL="cb_preference";

    private Tracker mTracker;

	private int nativeLanguage = 1;
	private int translateToLanguage = 2;
	private boolean toSort;
	
	MyDataSource datasource;
	Category category;

	private SharedPreferences settings;

	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	List<String> listDataHeader;
	HashMap<String, List<String>> listDataChild;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_explistview);

		TextView tvCat = (TextView) findViewById(R.id.tvCategory);

        // [START shared_tracker]
        // Obtain the shared Tracker instance.
        KirmanckiApplication application = (KirmanckiApplication) getApplication();
        mTracker = application.getDefaultTracker();
        // [END shared_tracker]

        mTracker.setScreenName(WordsActivity.class.getSimpleName());
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        settings = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean prefValue=settings.getBoolean(ANADIL,true);

        if (prefValue) {
            nativeLanguage = 1;
            translateToLanguage = 2;
        } else
        {
            nativeLanguage = 2;
            translateToLanguage = 1;
        }

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

        //do not sort when category is about time and numbers
        switch ((int) category.getCatId()) {
            case 3: //numbers
                toSort = false;
                break;
            case 7:  //time
                toSort = false;
                break;
            default:
                toSort = true;
                break;
        }
		// preparing list data

		prepareListData();

		tvCat.setText(getResources().getText(R.string.lbl_category) + " : "  + category.getCatName());

		listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

		expListView.setAdapter(listAdapter);


		expListView.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				 //Toast.makeText(getApplicationContext(),
				 //"Group Clicked " + questionsList.get(groupPosition),
				 //Toast.LENGTH_SHORT).show();
				return false;
			}
		});

		// Listview Group expanded listener
		expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int groupPosition) {
//				Toast.makeText(getApplicationContext(),
//						questionsList.get(groupPosition) + " Expanded",
//						Toast.LENGTH_SHORT).show();
			}
		});

		// Listview Group collasped listener
		expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

			@Override
			public void onGroupCollapse(int groupPosition) {
//				Toast.makeText(getApplicationContext(),
//						questionsList.get(groupPosition) + " Collapsed",
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
//						questionsList.get(groupPosition)
//								+ " : "
//								+ optionsList.get(
//										questionsList.get(groupPosition)).get(
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
		//Log.i(LOGTAG, "number of list words:" + lh.size() + "number of translate details:" + ld.size());
		
		for (int i = 0; i <lh.size(); i++) {
			//Log.i(LOGTAG, "words:" + lh.get(i));
			listDataHeader.add(lh.get(i));
		}
		for (int i = 0; i <ld.size(); i++) {
			//Log.i(LOGTAG, "words detail:" + ld.get(i));
		}

		for (int i = 0; i < lh.size(); i++) {			
//			ld2.clear();
//			ld2.add(" -> " + ld.get(i));
//			ld2.add("-------");
			listDataChild.put(listDataHeader.get(i), getTranslate(ld, i)); // Header, Child data
			//Log.i(LOGTAG, questionsList.get(i) + " -> " + ld.toString());
		}
        if (toSort) {
            Collections.sort(listDataHeader);
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

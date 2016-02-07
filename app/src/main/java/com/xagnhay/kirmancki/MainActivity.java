package com.xagnhay.kirmancki;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.xagnhay.kirmancki.db.MyDataSource;
import com.xagnhay.kirmancki.model.Category;
import com.xagnhay.kirmancki.model.Words;
import com.xagnhay.kirmancki.xml.CategoriesPullParser;

public class MainActivity extends ListActivity {

	public static final String LOGTAG="WORDS_MAIN";
	
	public int nativelanguageid = 1;
	public int learninglanguageid = 2;
	
	private List<Category> categories;
	MyDataSource datasource;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
			
		datasource = new MyDataSource(this);
		datasource.open();

		refreshDisplay();		
		
	}

	public void refreshDisplay() {
		
		getLangCategories();
		
		ArrayAdapter<Category> adapter = new ArrayAdapter<Category>(this, 
				android.R.layout.simple_list_item_1, categories);
		setListAdapter(adapter);		
	}
	
	public void getLangCategories() {
		categories = datasource.findAll("catlangId = " + nativelanguageid);

		if (categories.size() == 0) {
			createData();
			categories = datasource.findAll("catlangId = " + nativelanguageid);
		}
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		Category category = categories.get(position);
		Log.i(LOGTAG, category.getCatId() + "-" + category.getCatName());
		if (datasource.CountWords(category.getCatId()) > 0) {
			Intent intent = new Intent(this, WordsActivity.class);
			//intent.putExtra("CatID", category.getCatId());
			intent.putExtra(".model.Category", category);
			
			startActivity(intent);			
		}
		else {
			Toast.makeText(getApplicationContext(),
			"�iye nedi!!....!",
			Toast.LENGTH_SHORT).show();			
		}
	}
	
	/**
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		
		case R.id.menu_settings:
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);			
			break;
			
		case R.id.menu_all:
			categories = datasource.findAll("catlangId = " + nativelanguageid);
			refreshDisplay();			
			break;
			
		case R.id.menu_cheap:
			categories = datasource.findFiltered("catId <= 2", "catId ASC");
			refreshDisplay();
			break;

		case R.id.menu_fancy:
			categories = datasource.findFiltered("catId >= 2", "catId DESC");
			refreshDisplay();			
			break;
			
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	 */
//	public void setPreference(View v) {
//		Log.i(LOGTAG, "Clicked set");
//		Intent intent = new Intent(this, SettingsActivity.class);
//		startActivity(intent);
//	}

	@Override
	protected void onResume() {
		super.onResume();
		datasource.open();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		datasource.close();
	}
	
	private void createData() {		
		
		CategoriesPullParser parser = new CategoriesPullParser();
		List<Category> categories = parser.parseXML(this);
		
		for (Category category : categories) {
			datasource.create(category);
		}
		
		//Order : languageId, groupId, categoryId, word,   Active, Custom
		//        long,       long,    long,       string, int,    int
		datasource.addWord(new Words(nativelanguageid,   1, 1, "Xer ama",1,1));
		datasource.addWord(new Words(nativelanguageid,   2, 1, "Kemere",1,1));
		datasource.addWord(new Words(nativelanguageid,   3, 2, "Z�mel p�s�ngerak este",1,1));
		datasource.addWord(new Words(nativelanguageid,   5, 2, "Aree x�rav�no. Weno neweno m�rd nebeno.",1,1));		
		datasource.addWord(new Words(nativelanguageid,   6, 2, "�erexino yeno, pule�ino asvarre",1,1));
		datasource.addWord(new Words(nativelanguageid,   7, 2, "Hata kare keno, kes�ke vejina dare ser.",1,1));
		datasource.addWord(new Words(nativelanguageid,   8, 2, "Her vas koka ho sero reweno.",1,1));
		datasource.addWord(new Words(nativelanguageid,   9, 2, "��kake kemera g�rse nane desra, heve kemera q�zek cero kene b�nera",1,1));
		datasource.addWord(new Words(nativelanguageid,  10, 2, "Cirano x�rav�n, ison keno wayire her �i.",1,1));
		datasource.addWord(new Words(nativelanguageid,  11, 2, "Desto ra�t desto �'ep �uno. Desto �'ep desto ra�t �uno.",1,1));
		datasource.addWord(new Words(nativelanguageid,  12, 2, "Dest Dest �une, dest ri �une.",1,1));
		datasource.addWord(new Words(nativelanguageid,  13, 2, "Her teyr zone hora waneno.",1,1));
		datasource.addWord(new Words(nativelanguageid,   4, 2, "Himetker�." + "\n" + "Xane X�z�r vo." + "\n" + "�ime Muz�r vo.",1,1));
		datasource.addWord(new Words(learninglanguageid, 1, 1, "Ho� geldin",1,1));
		datasource.addWord(new Words(learninglanguageid, 2, 1, "Ta�",1,1));
		datasource.addWord(new Words(learninglanguageid, 3, 2, "Kedide de b�y�k var",1,1));
		datasource.addWord(new Words(learninglanguageid, 4, 2, "Halla halla halla halla halla halla." + "\n" + "Zikr u duaz� ma." + "\n" + "Qurvan u niyaz� ma." + "\n" + "�erx u pervaz� ma." + "\n" + "Veng u vaz� ma." + "\n" + "Thom�r u saz� ma." + "\n" + "Sewda u avaz� ma." + "\n" + "�ero oli divan�de, tawuy� Haq�de qeym u qewul bo.",1,1));
		datasource.addWord(new Words(learninglanguageid, 5, 2, "Bozuk de�irmen gibi. Yedik�e doymuyor.",1,1));
		datasource.addWord(new Words(learninglanguageid, 6, 2, "D�ner dola��r, yulara dolan�r.",1,1));
		datasource.addWord(new Words(learninglanguageid, 7, 2, "Bir i� yapana kadar kaplumba�a a�aca ��kar.",1,1));
		datasource.addWord(new Words(learninglanguageid, 8, 2, "Her ot kendi k�k� �zerine ye�erir.",1,1));
		datasource.addWord(new Words(learninglanguageid, 9, 2, "Duvar yapmak i�in b�y�k ta�lar kullan�lsa da, alt�na sa�lamla�t�rmak i�in k���k ta� da koymak gerekir.",1,1));
		datasource.addWord(new Words(learninglanguageid, 10, 2, "K�t� kom�u insan� k�t� �ey sahibi yapar.",1,1));
		datasource.addWord(new Words(learninglanguageid, 11, 2, "Sa� el sol eli, sol el sa� eli y�kar.",1,1));
		datasource.addWord(new Words(learninglanguageid, 12, 2, "Eli eli, el y�z� y�kar.",1,1));
		datasource.addWord(new Words(learninglanguageid, 13, 2, "Her ku� kendi dilinde �ter.",1,1));
		
		Log.d("Reading: ", "Reading all words..");
        List<Words> ws = datasource.listWords();
        
		for (Words w : ws) {
			String log = "Id: "+w.getWordId()+"group: "+w.getGroupId()+" ,Word: " + w.getWordText();
			Log.i(LOGTAG, log);
		}
	}
	
}

package com.xagnhay.kirmancki;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.xagnhay.kirmancki.db.MyDBOpenHelper;
import com.xagnhay.kirmancki.db.MyDataSource;
import com.xagnhay.kirmancki.model.Category;
import com.xagnhay.kirmancki.model.Words;
import com.xagnhay.kirmancki.util.UIHelper;

import java.util.List;

public class MainActivity extends ListActivity {
    //private final String URL_TO_HIT = "http://192.168.13.191:8810/HAlarm.zip/categories_json.json";

	public static final String LOGTAG = MainActivity.class.getSimpleName();
    public static final String ANADIL="cb_preference";

    //private ProgressDialog pDialog;

    private SharedPreferences settings;

	public int nativelanguageid = 1;
	public int learninglanguageid = 2;

	private List<Category> categories;
    private List<Words> words;
	MyDataSource datasource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		datasource = new MyDataSource(this);
		datasource.open();

        // Showing progress dialog
        //pDialog = new ProgressDialog(this);
        //pDialog.setMessage(getResources().getText(R.string.msg_wait));
        //pDialog.setCancelable(false);

		settings = PreferenceManager.getDefaultSharedPreferences(this);

		refreshDisplay();

        //getLangWords(); //Aync task ile okuma için

	}

	public void refreshDisplay() {

        Boolean prefValue=settings.getBoolean(ANADIL,true);

        if (prefValue) {
            nativelanguageid = 1;
            learninglanguageid = 2;
        } else
        {
            nativelanguageid = 2;
            learninglanguageid = 1;
        }

        String txtComent = null;
        if (nativelanguageid == 1)
            txtComent = getResources().getText(R.string.msg_dil1).toString();
        else
            txtComent = getResources().getText(R.string.msg_dil2).toString();

        UIHelper.displayText(this, R.id.textView1, txtComent);

        getLangCategories();

		ArrayAdapter<Category> adapter = new ArrayAdapter<Category>(this,
				android.R.layout.simple_list_item_1, categories);
		setListAdapter(adapter);


	}

	public void getLangCategories() {
		categories = datasource.findAll("\"" + MyDBOpenHelper.COLUMN_CATLANGID + "\" = " + nativelanguageid);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		Category category = categories.get(position);

		if (datasource.CountWords(category.getCatId()) > 0) {
			Intent intent = new Intent(this, WordsActivity.class);
			//intent.putExtra("CatID", category.getCatId());
			intent.putExtra(".model.Category", category);

			startActivity(intent);
		}
		else {
			Toast.makeText(getApplicationContext(),
                    getResources().getText(R.string.msg_ctgry),
			Toast.LENGTH_SHORT).show();
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

            case R.id.menu_settings:
                Intent intent1 = new Intent(this, SettingsActivity.class);
                startActivity(intent1);
                break;

            case R.id.menu_update:
                Intent intent2 = new Intent(this, CheckUpdateActivity.class);
                startActivity(intent2);
                break;

            case R.id.menu_about:
                Intent intent3 = new Intent(this, AboutActivity.class);
                startActivity(intent3);
                break;

            default:
                break;
        }
		return super.onOptionsItemSelected(item);
	}


	@Override
	protected void onResume() {
		super.onResume();
		datasource.open();
        refreshDisplay();
	}

	@Override
	protected void onPause() {
		super.onPause();
		datasource.close();
	}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        datasource.close();
    }
}

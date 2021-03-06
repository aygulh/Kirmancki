package com.xagnhay.kirmancki;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.xagnhay.kirmancki.db.MyDBOpenHelper;
import com.xagnhay.kirmancki.db.MyDataSource;
import com.xagnhay.kirmancki.json.CategoriesJSONParser;
import com.xagnhay.kirmancki.json.WordsJSONParser;
import com.xagnhay.kirmancki.model.Category;
import com.xagnhay.kirmancki.model.Words;

import java.util.List;

/**
 * Created by hidir on 23.01.2017.
 */

public class CheckUpdateActivity extends Activity {

    //private final String URL_TO_HIT = "http://192.168.13.191:8810/HAlarm.zip/words_json.json";
    private ProgressDialog pDialog;
    private Tracker mTracker;

    public static final String LOGTAG = CheckUpdateActivity.class.getSimpleName();

    MyDataSource datasource;
    private List<Words> words;

    TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        KirmanckiApplication application = (KirmanckiApplication) getApplication();
        mTracker = application.getDefaultTracker();

        mTracker.setScreenName(CheckUpdateActivity.class.getSimpleName());
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        // Showing progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setMessage(getResources().getText(R.string.msg_wait));
        pDialog.setCancelable(false);

        datasource = new MyDataSource(this);
        datasource.open();

        tv1 = (TextView) findViewById(R.id.tvUpdate);

        final Button btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {

                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Update Data")
                        .setAction("Güncelleme Kontrol")
                        .build());

                tv1.setText(getResources().getText(R.string.msg_serverstart));

                GetWordsFromUrl myTask = new GetWordsFromUrl();
                myTask.execute(getResources().getString(R.string.url_words));

                GetCategoryFromUrl myCatTask = new GetCategoryFromUrl();
                myCatTask.execute(getResources().getString(R.string.url_category));

                tv1.append(getResources().getText(R.string.msg_serverend));
            }
        });
    }

    /**
     * Async task class to get words json by making HTTP call
     */
    private class GetWordsFromUrl extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
            pDialog.show();
            tv1.append(getResources().getText(R.string.msg_words));
        }

        @Override
        protected String doInBackground(String... params) {
            HttpHandler sh = new HttpHandler();

            List<Words> words = null;

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(params[0]);

            if (jsonStr != null) {
                WordsJSONParser parser = new WordsJSONParser();
                words = parser.parseWJSON(jsonStr);
            }
            if(words.size() != 0){
                datasource.createBulkWords(words);
            }
            return null;

        }

        @Override
        protected void onPostExecute(final String result) {
            //super.onPostExecute(result);

            //tv1.append("\n" + "Rehber veritabanı güncellendi.. \n");
        }
    }

    /**
     * Async task class to get category json by making HTTP call
     */
    private class GetCategoryFromUrl extends AsyncTask<String, String, String > {

        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
            tv1.append(getResources().getText(R.string.msg_category));
        }

        @Override
        protected String doInBackground(String... params) {
            HttpHandler sh = new HttpHandler();

            List<Category> categories = null;

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(params[0]);

            if (jsonStr != null) {
                CategoriesJSONParser parser = new CategoriesJSONParser();
                categories = parser.parseJSON(jsonStr);
            }
            if(categories.size() != 0){
                datasource.createBulkCategories(categories);
            }
            return null;

        }

        @Override
        protected void onPostExecute(final String result) {
            //super.onPostExecute(result);
            //tv1.append("\n" + "Kategori güncelleme tamamlandı...\n");

            if (pDialog.isShowing())
                pDialog.dismiss();
        }
    }

}

package com.xagnhay.kirmancki;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.xagnhay.kirmancki.db.MyDBOpenHelper;
import com.xagnhay.kirmancki.db.MyDataSource;
import com.xagnhay.kirmancki.json.CategoriesJSONParser;
import com.xagnhay.kirmancki.json.WordsJSONParser;
import com.xagnhay.kirmancki.model.Category;
import com.xagnhay.kirmancki.model.Words;

import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

/**
 * Created by hidir on 01.02.2017.
 */

public class WelcomeActivity extends Activity {

    public static final String ANADIL="cb_preference";

    private SharedPreferences settings;
    private boolean okExit = false;
    public int nativelanguageid = 1;
    public int learninglanguageid = 2;

    private List<Category> categories;
    MyDataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        datasource = new MyDataSource(this);
        datasource.open();

        settings = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean prefValue=settings.getBoolean(ANADIL,true);

        if (prefValue) {
            nativelanguageid = 1;
            learninglanguageid = 2;
        } else
        {
            nativelanguageid = 2;
            learninglanguageid = 1;
        }

        okExit = false;

        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        okExit = false;
                        getLangCategories();
                        getLangWords();
                    }
                } finally {
                    finish();

                    okExit = true;

                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        };

        Thread thread = new Thread(r);
        thread.start();
    }

    public void getLangCategories() {
        categories = datasource.findAll("\"" + MyDBOpenHelper.COLUMN_CATLANGID + "\" = " + nativelanguageid);

        if (categories.size() == 0) {
            createCategoryData();
            categories = datasource.findAll("\"" + MyDBOpenHelper.COLUMN_CATLANGID + "\" = " + nativelanguageid);
        }

    }

    public void getLangWords() {
        int iNumberofExistingWords = datasource.isWordsExist("1=1");

        if (iNumberofExistingWords == 0) {
            createWordData();
        }

    }

    @Override
    public void onBackPressed() {
        if (! okExit) {
            new AlertDialog.Builder(this)
                    //.setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(R.string.msg_alerttitle)
                    .setMessage(R.string.msg_alertmessage)
                    .setPositiveButton(R.string.msg_alertyes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }

                    })
                    .setNegativeButton(R.string.msg_alertno, null)
                    .show();
        }
    }

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

    @Override
    protected void onDestroy() {
            super.onDestroy();
            datasource.close();
    }

    private void createCategoryData() {

        List<Category> categories = getCategoryJSONfromFile(R.raw.categories_json);
        //if (categories.size() != 0) {
        //    for (Category category : categories) {
        //        datasource.create(category);
        //    }
        //}
        datasource.createBulkCategories(categories);
    }

    private void createWordData() {

        List<Words> words = getWordsJSONfromFile(R.raw.words_json);
        //if (words.size() != 0) {
        //   for (Words aword : words) {
        //      datasource.create(aword);
        //   }
        //}
        datasource.createBulkWords(words);
    }

    private List<Category> getCategoryJSONfromFile (int resID) {
        InputStream is = getResources().openRawResource(resID);

        Scanner scanner = new Scanner(is);

        StringBuilder builder = new StringBuilder();

        while (scanner.hasNextLine()) {
            builder.append(scanner.nextLine());
        }

        CategoriesJSONParser parser = new CategoriesJSONParser();
        List<Category> categories = parser.parseJSON(builder.toString());

        return categories;
    }

    private List<Words> getWordsJSONfromFile (int resID) {
        InputStream is = getResources().openRawResource(resID);

        Scanner scanner = new Scanner(is);

        StringBuilder builder = new StringBuilder();

        while (scanner.hasNextLine()) {
            builder.append(scanner.nextLine());
        }

        WordsJSONParser parser = new WordsJSONParser();
        List<Words> words = parser.parseWJSON(builder.toString());

        return words;
    }

}

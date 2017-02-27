package com.xagnhay.kirmancki;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.xagnhay.kirmancki.db.MyDBOpenHelper;
import com.xagnhay.kirmancki.db.MyDataSource;
import com.xagnhay.kirmancki.model.Quiz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;


/**
 * Created by hidir on 09.02.2017.
 */

public class QuizActivity extends Activity {

    public static final String ANADIL="cb_preference";
    public static final String SCORE="cb_score";

    Button b1, b2, b3, b4;
    TextView tvQuestion, tvScore, tvSorulan;
    Spinner spinner;

    int score;
    int totalQuestions = 0;
    Quiz quiz;
    Random r;

    int qid;
    int ifalse;

    private int nativeLanguage = 1;
    private int translateToLanguage = 2;

    private Tracker mTracker;

    MyDataSource datasource;

    private SharedPreferences settings;

    List<String> questionsList;
    //HashMap<String, List<String>> optionsList;
    List<Quiz> quizList;

    List<String> categoryList;
    ArrayAdapter<String> adapter;
    String selectedCategory;
    int selcatId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // [START shared_tracker]
        // Obtain the shared Tracker instance.
        KirmanckiApplication application = (KirmanckiApplication) getApplication();
        mTracker = application.getDefaultTracker();
        // [END shared_tracker]

        mTracker.setScreenName(QuizActivity.class.getSimpleName());
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        quizList = new ArrayList<>();
        categoryList = new ArrayList<>();

        qid    = 0;
        score  = 0;
        ifalse = 0;
        selectedCategory = "";
        selcatId = 0;


        tvQuestion = (TextView) findViewById(R.id.tvQuestion);
        b1 = (Button) findViewById(R.id.btnOptA);
        b2 = (Button) findViewById(R.id.btnOptB);
        b3 = (Button) findViewById(R.id.btnOptC);
        b4 = (Button) findViewById(R.id.btnOptD);
        tvScore = (TextView) findViewById(R.id.score);
        tvSorulan = (TextView) findViewById(R.id.iFalse);
        spinner = (Spinner) findViewById(R.id.spinner);

        datasource = new MyDataSource(this);
        datasource.open();

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

        //create sorted category list for spinner to select category
        categoryList = datasource.listCategoriesSpinner(MyDBOpenHelper.COLUMN_CATLANGID + "=" + nativeLanguage, MyDBOpenHelper.COLUMN_CATNAME );

        adapter=new ArrayAdapter<>(this, R.layout.spinner_item,categoryList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(1);

        selectedCategory = spinner.getItemAtPosition(0).toString();  //set the first spinner item
        selcatId = datasource.getCategoryIDforSpinner(MyDBOpenHelper.COLUMN_CATLANGID + "=" + nativeLanguage + " AND " + MyDBOpenHelper.COLUMN_CATNAME + "=\"" + selectedCategory + "\"");

        //prepareQuizData();


        spinner.setOnItemSelectedListener(new OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedCategory = parent.getItemAtPosition(position).toString();
                selcatId = datasource.getCategoryIDforSpinner(MyDBOpenHelper.COLUMN_CATLANGID + "=" + nativeLanguage + " AND " + MyDBOpenHelper.COLUMN_CATNAME + "=\"" + selectedCategory + "\"");

                quizList.clear();
                qid = 0;
                score = 0;
                ifalse = 0;

                prepareQuizData();
                getNextQ();
                //quiz = quizList.get(qid);
                view();

                //Toast.makeText(getBaseContext(), selectedCategory+" is selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //quiz = quizList.get(qid);
        //view();
    }

    public void view() {
        tvQuestion.setText(quiz.getQuestion());
        b1.setText(quiz.getOption1());
        b2.setText(quiz.getOption2());
        b3.setText(quiz.getOption3());
        b4.setText(quiz.getOption4());

        String correctSting = String.format(getResources().getText(R.string.msg_correct).toString(), score);
        String wrongSting = String.format(getResources().getText(R.string.msg_wrong).toString(), ifalse);

        tvScore.setText(correctSting);
        tvSorulan.setText(wrongSting);
    }

    public void getNextQ() {
        r  = new Random();
        qid = r.nextInt(totalQuestions);
        quiz = quizList.get(qid);
    }

    public void btnOptA(View view) {

        if (b1.getText().toString().equals(quiz.getAnswer())){
            score+=1;
        }
        else {
            ifalse +=1;
            Toast.makeText(QuizActivity.this, getResources().getText(R.string.msg_toast), Toast.LENGTH_SHORT).show();
        }
        getNextQ();
        view();
    }
    public void btnOptB(View view) {

        if (b2.getText().toString().equals(quiz.getAnswer())){
            score+=1;
        }
        else {
            ifalse +=1;
            Toast.makeText(QuizActivity.this, getResources().getText(R.string.msg_toast), Toast.LENGTH_SHORT).show();
        }
        getNextQ();
        view();
    }
    public void btnOptC(View view) {

        if (b3.getText().toString().equals(quiz.getAnswer())){
            score+=1;
        }
        else {
            ifalse +=1;
            Toast.makeText(QuizActivity.this, getResources().getText(R.string.msg_toast), Toast.LENGTH_SHORT).show();
        }
        getNextQ();
        view();
    }
    public void btnOptD(View view) {

        if (b4.getText().toString().equals(quiz.getAnswer())){
            score+=1;
        }
        else {
            ifalse +=1;
            Toast.makeText(QuizActivity.this, getResources().getText(R.string.msg_toast), Toast.LENGTH_SHORT).show();
        }
        getNextQ();
        view();
    }

    private void prepareQuizData() {

        questionsList = new ArrayList<>();
        List<String> optionsList = new ArrayList<>();
        List<String> answerList = new ArrayList<>();

        // Adding child data
        questionsList = datasource.filterWordsForExpandableLV("langid = " + nativeLanguage + " and catId = " + selcatId, "groupId, langId");
        answerList    = datasource.filterWordsForExpandableLV("langid = " + translateToLanguage + " and catId = " + selcatId, "groupId, langId");

        totalQuestions = questionsList.size();

        //TODO: optionslist bir class a alınıp aşağıdaki döngü içerisinde tekrar sıralanabilir
        for (int i = 0; i <answerList.size(); i++) {
            optionsList.add(answerList.get(i));
        }

        for (int i = 0; i < questionsList.size(); i++) {
            Quiz q = new Quiz();

            List<String> tmpAns = new ArrayList<>();

            String tmpStr = optionsList.get(i);
            optionsList.remove(i);  //create options list excluding the answer. Answer located in the same index as the question
            Collections.shuffle(optionsList);

            tmpAns.add(answerList.get(i));
            tmpAns.add(optionsList.get(0));
            tmpAns.add(optionsList.get(1));
            tmpAns.add(optionsList.get(2));
            Collections.shuffle(tmpAns); //Shuffle it so each time answer will be in another option

            q.setQuestion(questionsList.get(i));
            q.setAnswer(answerList.get(i));
            q.setOption1(tmpAns.get(0));
            q.setOption2(tmpAns.get(1));
            q.setOption3(tmpAns.get(2));
            q.setOption4(tmpAns.get(3));

            optionsList.add(tmpStr);
            tmpStr = "";
            tmpAns.clear();

            quizList.add(q);
        }
        //Collections.sort(questionsList);  //sort list alphabetically
    }

}

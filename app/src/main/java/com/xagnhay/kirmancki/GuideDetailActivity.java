package com.xagnhay.kirmancki;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.xagnhay.kirmancki.db.MyDataSource;
import com.xagnhay.kirmancki.model.W1d;
import com.xagnhay.kirmancki.model.W1m;

import java.util.List;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by hidir on 24.02.2017.
 */

public class GuideDetailActivity extends Activity {
    MyDataSource datasource;
    W1m w2;
    List<W1d> w1dList;

    ListView list;
    private Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidedetail);

        // [START shared_tracker]
        // Obtain the shared Tracker instance.
        KirmanckiApplication application = (KirmanckiApplication) getApplication();
        mTracker = application.getDefaultTracker();
        // [END shared_tracker]

        mTracker.setScreenName(GuideDetailActivity.class.getSimpleName());
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        datasource = new MyDataSource(this);
        datasource.open();

        Bundle b = getIntent().getExtras();
        w2 = b.getParcelable(".model.W1m");

        w1dList = datasource.findAllGuideDetails("w1d.letter = \"" + w2.getLetter() + "\"");

        populateListView();
        registerClickCallBack();

    }

    protected void registerClickCallBack() {
        ListView list = (ListView) findViewById(R.id.lvGuideDetail);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {

                W1d w1 = w1dList.get(position);


                try {
                    AssetFileDescriptor afd =  getAssets().openFd(w1.getAudio());
                    MediaPlayer mp = new MediaPlayer();
                    mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                    afd.close();
                    mp.prepare();
                    mp.start();
                } catch (Exception ex) {
                    //Toast.makeText(GuideDetailActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void populateListView(){
        ArrayAdapter<W1d> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.lvGuideDetail);
        list.setAdapter(adapter);
    }

    private class MyListAdapter extends ArrayAdapter<W1d> {

        public MyListAdapter(){
            super(GuideDetailActivity.this, R.layout.activity_guidedetailrow, w1dList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.activity_guidedetailrow, parent, false);
            }

            W1d currentW1d = w1dList.get(position);

            TextView tvW1 = (TextView) itemView.findViewById(R.id.tvWord1);
            TextView tvW2 = (TextView) itemView.findViewById(R.id.tvWord2);
            TextView tvS1 = (TextView) itemView.findViewById(R.id.tvSentence1);
            TextView tvS2 = (TextView) itemView.findViewById(R.id.tvSentence2);

            tvW1.setText(currentW1d.getWl1());
            tvW2.setText(currentW1d.getWl2());
            tvS1.setText(currentW1d.getS1());
            tvS2.setText(currentW1d.getS2());

            return itemView;
        }
    }
}

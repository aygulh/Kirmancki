package com.xagnhay.kirmancki;

import android.app.Activity;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by hidir on 23.02.2017.
 */

public class GuideActivity extends Activity {

    private List<W1d> myGuide = new ArrayList<>();
    MyDataSource datasource;

    private List<W1m> w1master_list;
    private W1m w1master;

    private List<W1d> w1detail_list;
    private W1d w1detail;

    private Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        // [START shared_tracker]
        // Obtain the shared Tracker instance.
        KirmanckiApplication application = (KirmanckiApplication) getApplication();
        mTracker = application.getDefaultTracker();
        // [END shared_tracker]

        mTracker.setScreenName(GuideActivity.class.getSimpleName());
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        datasource = new MyDataSource(this);
        datasource.open();

        getGuideMaster();
        getGuideDetails();
        populateListView();
        registerClickCallBack();

    }

    protected void registerClickCallBack() {
        ListView list = (ListView) findViewById(R.id.lvGuide);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                if (position > 0) {

                    int newposition = position-1;
                    W1m w2 = w1master_list.get(newposition);

                    Intent intent = new Intent(GuideActivity.this, GuideDetailActivity.class);
                    intent.putExtra(".model.W1m", w2);

                    startActivity(intent);
                }
            }
        });
    }

    public void getGuideMaster() {
        w1master_list = datasource.findAllGuideMaster("1=1");
        if(w1master_list.size() == 0){
            populateGuideMaster();
            w1master_list = datasource.findAllGuideMaster("1=1");
        }
    }

    public void getGuideDetails() {
        w1detail_list = datasource.findAllGuideDetails("1=1");
        if(w1detail_list.size() == 0){
            populateGuideDetail();
            w1detail_list = datasource.findAllGuideDetails("1=1");
        }
    }

    private void populateListView(){
        ArrayAdapter<W1m> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.lvGuide);

        View header = getLayoutInflater().inflate(R.layout.activity_guideheader, null);

        list.addHeaderView(header);

        list.setAdapter(adapter);
    }

    private class MyListAdapter extends ArrayAdapter<W1m> {

        public MyListAdapter(){
            super(GuideActivity.this, R.layout.activity_guideitemrow, w1master_list);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.activity_guideitemrow, parent, false);
            }

            W1m currentW1m = w1master_list.get(position);

            TextView tvLet = (TextView) itemView.findViewById(R.id.tvLetter);

            tvLet.setText(currentW1m.getLetter());

            return itemView;
        }
    }

    private void populateGuideMaster() {
        List<W1m> myGuideMaster = new ArrayList<>();
        myGuideMaster.add(new W1m("C-c"));
        myGuideMaster.add(new W1m("Ch"));
        myGuideMaster.add(new W1m("Çh"));
        myGuideMaster.add(new W1m("E-e"));
        myGuideMaster.add(new W1m("Ph"));
        myGuideMaster.add(new W1m("Q-q"));
        myGuideMaster.add(new W1m("Th"));
        myGuideMaster.add(new W1m("W-w"));
        myGuideMaster.add(new W1m("X-x"));

        datasource.createBulkGuideMaster(myGuideMaster);
    }

    private void populateGuideDetail(){
        myGuide.add(new W1d("C-c","Cıl","[Yatak]","Sonde cılde qumera","[Akşam yatakta yatarız]","voices/cildekumera.mp3"));
        myGuide.add(new W1d("C-c","Ca","[Yer]","Cae ho teng bi","[Yeri dardı]","voices/caehotengbi.mp3"));
        myGuide.add(new W1d("C-c","Cire ","[Kendisine]","Kes cire wayiren n(i)ekerdene","[Kimse kendisine sahip çıkmıyordu]","voices/cirewayirennekerdene.mp3"));
        myGuide.add(new W1d("Ch","Cha","[Yer]","Wusarke ame her cha beno qewe","[İlkbahar gelince her yer yeşillenir]","voices/herchabenoqewe.mp3"));
        myGuide.add(new W1d("Çh","Çhor","[Dört]","Ma Çhor bırayime","[Biz dört kardeşiz]","voices/herchabenoqewe.mp3"));
        myGuide.add(new W1d("Çh","Çhim","[Göz]","Çhim buriye ho rındeke","[Gözü kaşı güzel] ","voices/cimburiyehorindeke.mp3"));
        myGuide.add(new W1d("E-e","Des (Dyes)","[Duvar]","Ma des vırazeme","[Biz duvar yapıyoruz]","voices/desvirazeme.mp3"));
        myGuide.add(new W1d("E-e","Cer","[Aşağı]","Manga davacer şiye","[İnek aşağı doğru gitti]","voices/mangadavacersiye.mp3"));
        myGuide.add(new W1d("E-e","N(i)e","[Hayır]","Mı vake n(i)e, ez nin","[Ben hayır dedim, ben gelmiyorum]","voices/mivakeneeznin.mp3"));
        myGuide.add(new W1d("E-e","Be","[Gel]","Be şime","[Gel gidelim]","voices/besime.mp3"));
        myGuide.add(new W1d("E-e","Me","[Gelme]","Mıra vake teyna me","[Bana yalnız gelme dedi]","voices/miravaketeyname.mp3"));
        myGuide.add(new W1d("Ph","Phonc","[Beş]","Phonc çey(çiey) te(tie) lewedere","[Beş ev yan yanadır]","voices/phoncceytelewedere.mp3"));
        myGuide.add(new W1d("Q-q","Qalık","[Dede]","Qalıke mı heştay seredero","[Dedem seksen yaşındadır]","voices/qalikemihestayserdero.mp3"));
        myGuide.add(new W1d("Q-q","Qan","[Eski]","Biyo qan","[Eskimiş]","voices/biyoqan.mp3"));
        myGuide.add(new W1d("Q-q","Qer","[Sağır]","Kokımo, biyo qer","[Yaşlıdır, sağır olmuş]","voices/kokimobiyoqer.mp3"));
        myGuide.add(new W1d("Q-q","Qewe","[Yeşil]","Wusarke ame daru ber velike dano. Her cha beno qewe","[İlkbahar gelince ağaçlar çiçek açar, her yer yeşillenir]","voices/benoqewe.mp3"));
        myGuide.add(new W1d("Q-q","Qız","[Küçük]","Bırao qız hona neamo","[Küçük kardeş henüz gelmemiş]","voices/biraoqizneamo.mp3"));
        myGuide.add(new W1d("Th","Thae","[Birşey]","Thae ma çino","[Bir şeyimiz yok]","voices/taemacino.mp3"));
        myGuide.add(new W1d("Th","Tha","[Bura]","Be tha","[Buraya gel]","voices/betha.mp3"));
        myGuide.add(new W1d("Th","Thol","[Boş]","Qusxane biyo thol","[Tencere boşalmış]","voices/qusxanebiyothol.mp3"));
        myGuide.add(new W1d("W-w","Çewt","[Eğri]","D(i)es çewt vıraşto","[Duvarı eğri yapmışlar] ","voices/descewtvirasto.mp3"));
        myGuide.add(new W1d("W-w","Hewn","[Uyku]","Kerğe hewne hode qut diyo","[Tavuk rüyasında yem görmüş]","voices/kergehewnehodequtdiyo.mp3"));
        myGuide.add(new W1d("W-w","Wae","[Kız kardeş]","Way bıray tede telewede gureene","[Kız ve erkek kardeşler hepsi birarada çalışıyorlar]","voices/waybiraytelewedegureene.mp3"));
        myGuide.add(new W1d("W-w","Wayir","[Sahip]","Kes cire wayiren n(i)ekerdene","[Kimse kendisine sahip çıkmıyordu]","voices/kescirewayirennekerdene.mp3"));
        myGuide.add(new W1d("W-w","Wue","[Su]","Ake biya tesan wue simena","[Susadığında su içersin]","voices/akebiyatesanwuesimena.mp3"));
        myGuide.add(new W1d("W-w","Wes","[İyi-hoş]","Mı vijer di, wes bi","[Dün gördüm, iyiydi]","voices/mivijerdiwesbi.mp3"));
        myGuide.add(new W1d("W-w","Weşiye","[İyilik-sağlık]","Heq weşiye todoro","[Allah sana sağlık versin]","voices/heqwesiyetodoro.mp3"));
        myGuide.add(new W1d("W-w","Wazen","[İstiyorum]","Ez nezon çı wazen","[Ne istediğimi bilmiyorum]","voices/eznezonciwazen.mp3"));
        myGuide.add(new W1d("X-x","Xal","[Dayı]","Xalve xalcıniyamı dewede nisenore","[Dayım ve dayımın eşi köyde oturuyorlar]","voices/xalvecalciniyamidewede.mp3"));
        myGuide.add(new W1d("X-x","Xıravın","[Kötü]","Mordemo xıravınra qeso xıravın vejino","[Kötü adamdan kötü söz çıkar]","voices/mordemoxiravin.mp3"));
        myGuide.add(new W1d("X-x","Xırt","[Sağlam, güçlü]","Mordemo xırt","[Sağlam adam]","voices/mordemoxirt.mp3"));
        myGuide.add(new W1d("X-x","Xori","[Derin]","Golo be-bino, zaf xoriyo","[Dipsiz göldür, çok derindir]","voices/golobebinozafxoriyo.mp3"));

        datasource.createBulkGuideDetail(myGuide);
    }
}

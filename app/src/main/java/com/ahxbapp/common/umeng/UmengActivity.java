package com.ahxbapp.common.umeng;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ahxbapp.xjjsd.application.MyApp;
//import com.umeng.analytics.MobclickAgent;


//import com.google.android.gms.analytics.HitBuilders;
//import com.google.android.gms.analytics.Tracker;

/**
 * Created by gravel on 14-10-9.
 */
public class UmengActivity extends AppCompatActivity {

//    private Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 //       MobclickAgent.openActivityDurationTrack(false);

        MyApp application = (MyApp) getApplication();
//        mTracker = application.getDefaultTracker();
    }

    @Override
    public void onResume() {
        super.onResume();
  //      MobclickAgent.onPageStart(getClass().getSimpleName());
    //    MobclickAgent.onResume(this);

        MyApp.setMainActivityState(true);

//        mTracker.setScreenName(getClass().getName());
//        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public void onPause() {
        super.onPause();
       // MobclickAgent.onPageEnd(getClass().getSimpleName());
        //MobclickAgent.onPause(this);

        MyApp.setMainActivityState(false);
    }

    protected void umengEvent(String s, String param) {

        //MobclickAgent.onEvent(this, s, param);
    }
}

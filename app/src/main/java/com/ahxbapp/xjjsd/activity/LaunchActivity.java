package com.ahxbapp.xjjsd.activity;

import com.ahxbapp.common.Global;
import com.ahxbapp.common.ui.BaseActivity;
import com.ahxbapp.xjjsd.R;
import com.ahxbapp.xjjsd.utils.PrefsUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * 启动页
 */
@EActivity(R.layout.activity_launch)
public class LaunchActivity extends BaseActivity {

    @AfterViews
    void init() {
        String token = PrefsUtil.getString(this, Global.TOKEN);
        if (token == null) {
            JPushInterface.setAlias(LaunchActivity.this, "0", new TagAliasCallback() {
                @Override
                public void gotResult(int i, String s, Set<String> set) {
                }
            });
            JPushInterface.resumePush(LaunchActivity.this);
        }
        LoanMainActivity_.intent(LaunchActivity.this).start();
//        LoginActivity_.intent(this).background(null).start();
//        if(PrefsUtil.getString(this, Global.TOKEN)==null){
//            LoginActivity_.intent(LaunchActivity.this).start();
//        }else{
//            MainActivity_.intent(LaunchActivity.this).start();
//        }
        finish();
    }


}
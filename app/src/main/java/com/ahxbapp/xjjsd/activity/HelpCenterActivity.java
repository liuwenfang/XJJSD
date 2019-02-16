package com.ahxbapp.xjjsd.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ahxbapp.common.Global;
import com.ahxbapp.common.ui.BaseActivity;
import com.ahxbapp.xjjsd.R;
import com.ahxbapp.xjjsd.api.APIManager;
import com.ahxbapp.xjjsd.utils.PrefsUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * 帮助中心
 */
@EActivity(R.layout.activity_help_center)
public class HelpCenterActivity extends BaseActivity {
    @ViewById
    RelativeLayout rlQuest, rlLiuYan;
    @ViewById
    ImageButton mToolbarLeftIB;
    @ViewById
    TextView mToolbarTitleTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    void init() {
        mToolbarLeftIB.setImageResource(R.mipmap.back);
        mToolbarTitleTV.setText("帮助中心");
    }

    @Click
    void mToolbarLeftIB() {
        finish();
    }

    /**
     * 常见问题
     */
    @Click
    void rlQuest() {
        FAQActivity_.intent(this).start();
    }

    /**
     * 客服
     */
    @Click
    void rlKeFu() {
        WebActivity_.intent(this).url(APIManager.requestKeFuUrl).title("联系客服").start();
//        KeFuActivity_.intent(this).start();
    }

    /**
     * 留言
     */
    @Click
    void rlLiuYan() {
        String token = PrefsUtil.getString(this, Global.TOKEN);
        if (TextUtils.isEmpty(token)) {
            LoginActivity_.intent(this).startForResult(1000);
        } else {
            WordActivity_.intent(this).start();
        }
    }
}

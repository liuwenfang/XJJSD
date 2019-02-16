package com.ahxbapp.xjjsd.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ahxbapp.common.Global;
import com.ahxbapp.common.ui.BaseActivity;
import com.ahxbapp.xjjsd.R;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;


/**
 * Created by admin on 2019/1/25.
 */
public class RepaymentActivity extends BaseActivity {


    WebView repayment_web;
    int  LoanlogID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repayment);
        repayment_web= (WebView) findViewById(R.id.repayment_web);
        Intent intent= getIntent();
        LoanlogID = intent.getIntExtra("LoanlogID",LoanlogID);
        setWebview();
    }

    private void setWebview() {
        WebSettings webSettings = repayment_web.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setSupportZoom(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setGeolocationEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        repayment_web.loadUrl(Global.HOST+"home/llsub?id=" + LoanlogID);

        repayment_web.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(request.getUrl().toString());
                }
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
    }

}

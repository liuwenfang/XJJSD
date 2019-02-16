package com.ahxbapp.xjjsd.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ahxbapp.common.Global;
import com.ahxbapp.common.ui.BaseActivity;
import com.ahxbapp.xjjsd.R;
import com.ahxbapp.xjjsd.model.CatModel;
import com.ahxbapp.xjjsd.utils.MyToast;


/**
 * Created by admin on 2019/1/25.
 */

public class CatWebActivity extends BaseActivity {
    private CatModel catModel;
    private WebView webview_cat;


    private ImageButton mToolbarLeftIB;

    private TextView mToolbarTitleTV;

    private Button exit_btn;
    Intent intent;
  //  private final Handler handler = new Handler();//只有用handel才能修改android控件的值

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat);
        intent = getIntent();
        catModel = (CatModel) intent.getSerializableExtra("catModel");
        webview_cat = (WebView) findViewById(R.id.webview_cat);
        mToolbarLeftIB = (ImageButton) findViewById(R.id.mToolbarLeftIB);
        mToolbarTitleTV = (TextView) findViewById(R.id.mToolbarTitleTV);
        exit_btn = (Button) findViewById(R.id.exit_btn);
        setWebview();
        init();

        mToolbarLeftIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });


    }


    void init() {

        mToolbarLeftIB.setImageResource(R.mipmap.back);
        mToolbarTitleTV.setText("借款");


        //IOHelper.totalFileSize()

    }

    @Override
    protected void setActionBarTitle(int title) {
        super.setActionBarTitle(title);
    }

    @SuppressLint("JavascriptInterface")
    private void setWebview() {
        WebSettings webSettings = webview_cat.getSettings();
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
        webview_cat.loadUrl(Global.HOST + "home/ExtendLoanDetail" +
                "?LoanId=" + catModel.getLoanId() +
                "&TermId=+" + catModel.getTermId() +
                "&Applyfee=" + catModel.getApplyfee() +
                "&Interest=" + catModel.getInterest() +
                "&Userfee=" + catModel.getUserfee() +
                "&token=" + catModel.getToken() +
                "&Identifier=" + catModel.getIdentifier());

        webview_cat.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(request.getUrl().toString());
                }
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);


            }
        });

        webview_cat.addJavascriptInterface(new JSHook(this), "android");

    }

    public class JSHook {
        private Context mContext;

        JSHook(Context c)   {
            mContext = c;
        }

        @JavascriptInterface
        public void LoanSubResult(String json) {

            Log.e("dddddddddddddd", "dcccccccccccccccc" +catModel.getInterest());

        }

        @JavascriptInterface
        public void LoanSubResult2(String json) {
            String toast = json.substring(8);

            MyToast.showToast(CatWebActivity.this, "" + toast, 3000);

//            Log.e("dddddddddddddd", "dcfcfffcccc" + json.toString());
//            intent = new Intent(CatWebActivity.this, LoanMainActivity.class);
//            startActivity(intent);
            finish();


        }
    }

}
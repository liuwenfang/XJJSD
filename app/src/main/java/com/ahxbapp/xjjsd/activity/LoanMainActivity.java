package com.ahxbapp.xjjsd.activity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ahxbapp.common.Global;
import com.ahxbapp.common.inter.BackHandledInterface;
import com.ahxbapp.common.ui.BaseActivity;
import com.ahxbapp.common.ui.BaseWebFragment;
import com.ahxbapp.xjjsd.R;
import com.ahxbapp.xjjsd.adapter.MainAdapter;
import com.ahxbapp.xjjsd.api.APIManager;
import com.ahxbapp.xjjsd.dialog.PromptDialog;
import com.ahxbapp.xjjsd.fragment.ExchangeFragment;
import com.ahxbapp.xjjsd.fragment.ExchangeFragment_;
import com.ahxbapp.xjjsd.fragment.LoanFragment;
import com.ahxbapp.xjjsd.fragment.LoanFragment_;
import com.ahxbapp.xjjsd.fragment.LoanSuperFragment;
import com.ahxbapp.xjjsd.fragment.LoanSuperFragment_;
import com.ahxbapp.xjjsd.fragment.PersonFragment;
import com.ahxbapp.xjjsd.fragment.PersonFragment_;
import com.ahxbapp.xjjsd.model.BaseDataListModel;
import com.ahxbapp.xjjsd.model.CommonEnity;
import com.ahxbapp.xjjsd.model.VersionModel;
import com.ahxbapp.xjjsd.utils.BanbenVersion;
import com.ahxbapp.xjjsd.utils.BaseAppUtils;
import com.ahxbapp.xjjsd.utils.NoScrollViewPager;
import com.ahxbapp.xjjsd.utils.PrefsUtil;
import com.ahxbapp.xjjsd.utils.StatusBarUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * 首页
 */
@EActivity(R.layout.main_activity)
public class LoanMainActivity extends BaseActivity implements BackHandledInterface {

    public static final String TAG = LoanMainActivity.class.getSimpleName();
    @ViewById
    RadioGroup group;
    @ViewById
    RadioButton borrowings, certification, loanSuper, my; //借贷  兑现  贷超  我的
    @ViewById
    NoScrollViewPager main_VP;

    private long oldTime;
    private long nowTime;

    private LoanFragment loanFragment;   //借款
    //    private CertificationFragment certificationFragment;   //认证
    private ExchangeFragment exchangeFragment;   //兑现
    private LoanSuperFragment superFragment;   //容易借
    private PersonFragment myFragment;       //我的
    private List<Fragment> fragmentList;

    public static boolean isStart = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //判断是否更新新版本
//        detectionVersion();
        isStart = true;
        detectionVersion();

        StatusBarUtils.translucent(this);
        EventBus.getDefault().register(this);
    }

    //登录成功
    public void onEvent(CommonEnity event) {
        if (event.getType().equals("loginSuccess") || event.getType().equals("logOut")) {
            finish();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        isStart = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        //判断是否更新新版本
    }

    @AfterViews
    void init() {
//        PlatformConfig.setWeixin(Constant.WEIXIN_APPID, Constant.WEIXIN_APPSECRET);
//        PlatformConfig.setSinaWeibo(Constant.SINA_APPID, Constant.SINA_APPKEY);
//        PlatformConfig.setQQZone(Constant.QQ_APPID, Constant.QQ_APPKEY);
//        Config.REDIRECT_URL = "http://sns.whalecloud.com/sina2/callback";

        loanFragment = LoanFragment_.builder().build();
        exchangeFragment = ExchangeFragment_.builder().build();
        superFragment = LoanSuperFragment_.builder().build();
        myFragment = PersonFragment_.builder().build();

        fragmentList = new ArrayList<>();
        fragmentList.add(loanFragment);
        fragmentList.add(exchangeFragment);
        fragmentList.add(superFragment);
        fragmentList.add(myFragment);
        main_VP.setAdapter(new MainAdapter(getSupportFragmentManager(), fragmentList));
        main_VP.setCurrentItem(0);
        main_VP.setOffscreenPageLimit(3);
        borrowings.setTextColor(getResources().getColor(R.color.nav_blue));
        borrowings.setChecked(true);
        //点击时改变字体颜色
        main_VP.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                resetColor();
                switch (position) {
                    case 0:
                        borrowings.setTextColor(getResources().getColor(R.color.nav_blue));
                        borrowings.setChecked(true);
                        certification.setChecked(false);
                        my.setChecked(false);
                        loanSuper.setChecked(false);
                        break;

                    case 1:
                        certification.setTextColor(getResources().getColor(R.color.nav_blue));
                        certification.setChecked(true);
                        borrowings.setChecked(false);
                        my.setChecked(false);
                        loanSuper.setChecked(false);
                        break;

                    case 2:
                        loanSuper.setTextColor(getResources().getColor(R.color.nav_blue));
                        loanSuper.setChecked(true);
                        borrowings.setChecked(false);
                        my.setChecked(false);
                        certification.setChecked(false);
                        break;
                    case 3:
                        my.setTextColor(getResources().getColor(R.color.nav_blue));
                        my.setChecked(true);
                        borrowings.setChecked(false);
                        certification.setChecked(false);
                        loanSuper.setChecked(false);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        requestSuper();
    }

    private void requestSuper() {
        APIManager.getInstance().requestLoanSuperInfo(this, new APIManager.APIManagerInterface.baseBlock() {
            @Override
            public void Success(Context context, JSONObject response) {
                try {
                    loanSuper.setVisibility(View.VISIBLE);
                    String url = response.getString("url");
                    superFragment.setUrl(url);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void Failure(Context context, JSONObject response) {
                loanSuper.setVisibility(View.GONE);
            }
        });
    }

    private VersionModel versionModel;
    private PromptDialog promptDialog;

    /**
     * 版本检测
     */
    public void detectionVersion() {
        APIManager.getInstance().get_version(this, new APIManager.APIManagerInterface.common_object() {
            @Override
            public void Success(Context context, Object model) {
                BaseDataListModel<VersionModel> mm = (BaseDataListModel<VersionModel>) model;
                if (mm.getResult() == 1) {
                    versionModel = mm.getModel();
                    String verName = getVerName(context);
                    if (BanbenVersion.compareVersion(verName, versionModel.getVerSionNO()) == -1) {
                        if (promptDialog == null) {
                            showUpdateDialg();
                        } else if (!promptDialog.isShowing()) {
                            promptDialog.show();
                        }
                    }
                }
            }

            @Override
            public void Failure(Context context, JSONObject response) {

            }
        });

    }

    /**
     * 版本检测
     */
    private void showUpdateDialg() {
        promptDialog = new PromptDialog(LoanMainActivity.this);
        promptDialog.setCancelable(false);
        promptDialog.tvPrompt.setText("版本过低无法使用");
        promptDialog.tvTitle.setText("请更新到最新版本才能借款哦\n温馨提示：若立即更新出现问题，请卸载后去应用宝重新下载");
        promptDialog.tvCancle.setVisibility(View.GONE);
        promptDialog.tvConfirm.setText("立即更新");
        promptDialog.tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptDialog.dismiss();
                String apkPath = versionModel.getAddr();
//                                        DownloadAppUtils.downloadForAutoInstall(LoanMainActivity.this, apkPath, "现金急速贷", "更新现金急速贷");
                BaseAppUtils
                        .downloadFile(LoanMainActivity.this,
                                apkPath,
                                "现金急速贷",
                                "正在下载"
                                        + BaseAppUtils
                                        .getApplicationName(LoanMainActivity.this)
                                        + "中…");
            }
        });
        promptDialog.show();
    }

    /**
     * 获取软件版本名称
     */
    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().getPackageInfo("com.ahxbapp.xjjsd", 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {

        }
        return verName;
    }


    //radiobutton字体重置颜色
    private void resetColor() {
        borrowings.setTextColor(getResources().getColor(R.color.home_font));
        certification.setTextColor(getResources().getColor(R.color.home_font));
        loanSuper.setTextColor(getResources().getColor(R.color.home_font));
        my.setTextColor(getResources().getColor(R.color.home_font));
    }

    //RadioButton跳转监听

    /**
     * 借款
     */
    @Click
    void borrowings() {
        main_VP.setCurrentItem(0, false);
        //loanFragment.loadLoanData();
    }

    /**
     * 认证
     */
    @Click
    void certification() {
        String token = PrefsUtil.getString(this, Global.TOKEN);
        if (token == null) {
            certification.setChecked(false);
            borrowings.setChecked(true);
            LoginActivity_.intent(this).startForResult(1000);
            return;
        }
        main_VP.setCurrentItem(1, false);
    }

    @Click
    void loanSuper() {
        String token = PrefsUtil.getString(this, Global.TOKEN);
        if (token == null) {
            loanSuper.setChecked(false);
            borrowings.setChecked(true);
            LoginActivity_.intent(this).startForResult(1000);
            return;
        }
        main_VP.setCurrentItem(2, false);
    }

    /**
     * 我
     */
    @Click
    void my() {
        String token = PrefsUtil.getString(this, Global.TOKEN);
        if (token == null) {
            my.setChecked(false);
            borrowings.setChecked(true);
            LoginActivity_.intent(this).startForResult(1000);
            return;
        }
        main_VP.setCurrentItem(3, false);
        myFragment.loadView();
    }

    /**
     * fragment返回设置
     */
//    @Override
//    public void onBackPressed() {
//        nowTime = System.currentTimeMillis();
//        if (nowTime - oldTime < 2000) {
//            this.finish();
//            System.exit(0);
//        } else {
//            Toast.makeText(getApplicationContext(), "再按一次就退出了哦", Toast.LENGTH_SHORT).show();
//            oldTime = nowTime;
//        }
//    }

    @Override
    public void setSelectedFragment(BaseWebFragment selectedFragment) {
        this.superFragment = (LoanSuperFragment) selectedFragment;
    }

    @Override
    public void onBackPressed() {
        if(superFragment == null || !superFragment.onBackPressed()){
            nowTime = System.currentTimeMillis();
            if (nowTime - oldTime < 2000) {
                this.finish();
                System.exit(0);
            } else {
                Toast.makeText(getApplicationContext(), "再按一次就退出了哦", Toast.LENGTH_SHORT).show();
                oldTime = nowTime;
            }
        }
    }

}

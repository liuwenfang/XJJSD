package com.ahxbapp.xjjsd.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.ahxbapp.common.Global;
import com.ahxbapp.common.ui.BaseFragment;
import com.ahxbapp.common.util.DensityUtil;
import com.ahxbapp.xjjsd.R;
import com.ahxbapp.xjjsd.activity.CashOrderDetailActivity_;
import com.ahxbapp.xjjsd.activity.CatWebActivity;
import com.ahxbapp.xjjsd.activity.FirstCerActivity_;
import com.ahxbapp.xjjsd.activity.LoginActivity_;
import com.ahxbapp.xjjsd.activity.MessageActivity_;
import com.ahxbapp.xjjsd.activity.PayActivity_;
import com.ahxbapp.xjjsd.activity.RepaymentActivity;
import com.ahxbapp.xjjsd.activity.SingedActivity_;
import com.ahxbapp.xjjsd.activity.StartBorrowingsActivity_;
import com.ahxbapp.xjjsd.activity.WebActivity_;
import com.ahxbapp.xjjsd.activity.WordActivity_;
import com.ahxbapp.xjjsd.adapter.SuperLoanAdapter;
import com.ahxbapp.xjjsd.adapter.WxKefuAdapter;
import com.ahxbapp.xjjsd.api.APIManager;
import com.ahxbapp.xjjsd.decoration.DividerDecoration;
import com.ahxbapp.xjjsd.decoration.DividerGridItemDecoration;
import com.ahxbapp.xjjsd.decoration.FullyLinearLayoutManager;
import com.ahxbapp.xjjsd.dialog.PromptDialog;
import com.ahxbapp.xjjsd.dialog.SuperDialog;
import com.ahxbapp.xjjsd.event.UserEvent;
import com.ahxbapp.xjjsd.model.AuthenModel;
import com.ahxbapp.xjjsd.model.BaseDataListModel;
import com.ahxbapp.xjjsd.model.CatModel;
import com.ahxbapp.xjjsd.model.CommonEnity;
import com.ahxbapp.xjjsd.model.MessageModel;
import com.ahxbapp.xjjsd.model.MoneyModel;
import com.ahxbapp.xjjsd.model.MyBannerModel;
import com.ahxbapp.xjjsd.model.SuperLoanModel;
import com.ahxbapp.xjjsd.model.WxModel;
import com.ahxbapp.xjjsd.utils.DensityUtils;
import com.ahxbapp.xjjsd.utils.GlideImageLoader;
import com.ahxbapp.xjjsd.utils.MyToast;
import com.ahxbapp.xjjsd.utils.PrefsUtil;
import com.ahxbapp.xjjsd.utils.ScreenSizeUtils;
import com.ahxbapp.xjjsd.utils.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.bumptech.glide.Glide;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.switfpass.pay.MainApplication;
import com.switfpass.pay.activity.PayPlugin;
import com.switfpass.pay.bean.RequestMsg;
import com.switfpass.pay.handle.PayHandlerManager;
import com.switfpass.pay.utils.MD5;
import com.switfpass.pay.utils.SignUtils;
import com.switfpass.pay.utils.Util;
import com.switfpass.pay.utils.XmlUtils;
import com.xiaosu.DataSetAdapter;
import com.xiaosu.VerticalRollingTextView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.zhouyou.view.seekbar.SignSeekBar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import de.greenrobot.event.EventBus;

/**
 * Created by Jayzhang on 16/10/17.
 * 首页 借款 还款
 */
@EFragment(R.layout.home)
public class LoanFragment extends BaseFragment implements PullToRefreshScrollView.OnRefreshListener, View.OnTouchListener {

    @ViewById
    Button statusBtn;
    @ViewById
    TextView LeftTV, TitleTV;
    @ViewById
    ImageView RightIB, RightIB1, ivSpeed;

    @ViewById
    RelativeLayout status_Scroll, nav_rela;
    @ViewById
    PullToRefreshScrollView scroll_status, scroll_none;

    @ViewById
    TextView one_text, two_text, three_text;
    //, four_text, five_text
    @ViewById
    TextView mTitle1, mTitle2, mTitle3;
    //, mTitle4, mTitle5
    @ViewById
    TextView mContent1, mContent2, mContent3;

    //, mContent4, mContent5
    @ViewById
    RecyclerView wxRecyclerView;
    @ViewById
    RecyclerView recyclerView;
    @ViewById
    LinearLayout llRecyclerView, llWx, llSuperLoan;
    @ViewById
    View viewXian2;

    @ViewById
    Banner mBanner;
    @ViewById
    VerticalRollingTextView rollingView;
    @ViewById
    TextView tvMoney, tvDay, tvApply, tvDayMoney;
    @ViewById
    SignSeekBar mSignSeekBar1, mSignSeekBar2;


    boolean isJiekuan = true;

    int jkStatus = 0;

    int jkID = 0;

    int isShenFen = 0;

    String limitDay = "1";

    int passStatus = 0;

    //    private MoneyListAdapter moneyListAdapter;
    private List<WxModel> wxModels;
    private WxKefuAdapter wxKefuAdapter;
    private RelativeLayout parentView;

    private SuperLoanAdapter superLoanAdapter;
    private List<SuperLoanModel> superLoanModels;
    private boolean isGetData = false;
    private int state = 0;
    int result = 0;
    double Interest = 0.00;
    int Userfee = 0;
    double Applyfee = 0.00;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        // 调用下，防止收到上次登陆账号的通知

    }

    @Override
    public void onPause() {
        isGetData = false;
        super.onPause();
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onResume() {
        if (!isGetData) {
            //   这里可以做网络请求或者需要的数据刷新操作
          onRefresh(scroll_none);
            isGetData = true;
        }
        super.onResume();
    }

    @AfterViews
    void initLoanView() {
        requestBanner();
        loadLoanData();
        isFrist = true;
        initWxView();
        initSuperLoanView();
        parentView = (RelativeLayout) ivSpeed.getParent();
        ivSpeed.setOnTouchListener(this);
        initRandom();
//        ivSpeed.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        recyclerView.setLayoutManager(new FullyLinearLayoutManager(getContext()));
//        recyclerView.addItemDecoration(new DividerDecoration(getContext(),DividerItemDecoration.VERTICAL));
        recyclerView.addItemDecoration(new DividerDecoration(getContext().getResources().getColor(R.color.transparent), DensityUtils.dip2px(getContext(), 5)));
        // DensityUtils.dip2px(getContext(), 5)
        recyclerView.setAdapter(superLoanAdapter);
        mSignSeekBar1.setOnProgressChangedListener(new SignSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(SignSeekBar signSeekBar, int progress, float progressFloat, boolean fromUser) {
                int each = 9000 / 90;
                tvMoney.setText((1000 + each * progress) + "");
            }

            @Override
            public void getProgressOnActionUp(SignSeekBar signSeekBar, int progress, float progressFloat) {

            }

            @Override
            public void getProgressOnFinally(SignSeekBar signSeekBar, int progress, float progressFloat, boolean fromUser) {

            }
        });
        mSignSeekBar2.setOnProgressChangedListener(new SignSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(SignSeekBar signSeekBar, int progress, float progressFloat, boolean fromUser) {
                tvDay.setText(progress + "");
            }

            @Override
            public void getProgressOnActionUp(SignSeekBar signSeekBar, int progress, float progressFloat) {

            }

            @Override
            public void getProgressOnFinally(SignSeekBar signSeekBar, int progress, float progressFloat, boolean fromUser) {

            }
        });
    }

    private String speedUrl = "";
    private boolean isPaySpeed;
    private boolean isPaySpeedClick;
    private boolean isSignClick;//签约
    private PromptDialog wxDialog;

    private void showWxDialog(final String num) {
        if (wxDialog == null) {
            wxDialog = new PromptDialog(getContext());
            wxDialog.tvPrompt.setText("微信客服账号");
            wxDialog.tvConfirm.setText("复制");
            wxDialog.tvCancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    wxDialog.dismiss();
                }
            });
            wxDialog.tvConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    wxDialog.dismiss();
                    ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    // 将文本内容放到系统剪贴板里。
                    cm.setText(num);
                    Toast.makeText(getContext(), "复制成功", Toast.LENGTH_LONG).show();
                }
            });
        }
        wxDialog.tvTitle.setText(num);
        wxDialog.show();
    }

    private void initSuperLoanView() {

        superLoanModels = new ArrayList<>();
        superLoanAdapter = new SuperLoanAdapter(getContext(), superLoanModels);

        final String token = PrefsUtil.getString(getContext(), Global.TOKEN);
        superLoanAdapter.setWXClickListener(new SuperLoanAdapter.onWXClickListener() {
            @Override
            public void onApplyItemClick(int pos) {
                final SuperLoanModel model = superLoanModels.get(pos);
                if (model.getStatus() == 0) {

                    final Dialog dialog = new Dialog(getContext(), R.style.NormalDialogStyle);
                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    View view = inflater.inflate(R.layout.layout_dialogwx, null);
                    RelativeLayout rlKonw = (RelativeLayout) view.findViewById(R.id.rl_yes);
                    final TextView tvNumber = (TextView) view.findViewById(R.id.tv_number);
                    Button btn_copy = (Button) view.findViewById(R.id.btn_copy);
                    tvNumber.setText(model.getWx());


                    btn_copy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                            // 创建普通字符型ClipData
                            ClipData mClipData = ClipData.newPlainText("Label", model.getWx());
                            // 将ClipData内容放到系统剪贴板里。
                            cm.setPrimaryClip(mClipData);
                            MyToast.showToast(getContext(), "复制成功", 3000);
                        }
                    });
                    dialog.setContentView(view);
                    //使得点击对话框外部不消失对话框
                    dialog.setCanceledOnTouchOutside(false);
                    //设置对话框的大小
                    view.setMinimumHeight((int) (ScreenSizeUtils.getInstance(getContext()).getScreenHeight() * 0.23f));
                    Window dialogWindow = dialog.getWindow();
                    WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                    lp.width = (int) (ScreenSizeUtils.getInstance(getContext()).getScreenWidth() * 0.75f);
                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    lp.gravity = Gravity.CENTER;
                    dialogWindow.setAttributes(lp);
                    dialog.show();

                    dialog.getWindow().setGravity(Gravity.CENTER);//可以设置显示的位置
                    rlKonw.setOnClickListener(new View.OnClickListener() {

                        @SuppressLint("WrongConstant")
                        @Override
                        public void onClick(View arg0) {
                            dialog.dismiss();
                        }
                    });
                }
            }
        });


        superLoanAdapter.setonItemClickListener(new SuperLoanAdapter.onItemClickListener() {
            @Override
            public void onApplyItemClick(int pos) {
                final SuperLoanModel model = superLoanModels.get(pos);

                if (model.getStatus() == 5) {

                    final Dialog dialog = new Dialog(getContext(), R.style.NormalDialogStyle);
                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    View view = inflater.inflate(R.layout.layout_dialog_refund, null);
                    final ImageView iv_yinhangka = (ImageView) view.findViewById(R.id.iv_yinhangka);
                    final ImageView iv_weixin = (ImageView) view.findViewById(R.id.iv_weixin);

                    RelativeLayout rl_weixin = (RelativeLayout) view.findViewById(R.id.rl_weixin);
                    RelativeLayout rl_bankCard = (RelativeLayout) view.findViewById(R.id.rl_bankCard);
                    LinearLayout ll_woyaoRefund = (LinearLayout) view.findViewById(R.id.ll_woyaoRefund);


                    dialog.setContentView(view);
                    //使得点击对话框外部不消失对话框
                    dialog.setCanceledOnTouchOutside(true);
                    //设置对话框的大小
                    view.setMinimumHeight((int) (ScreenSizeUtils.getInstance(getContext()).getScreenHeight() * 0.23f));
                    Window dialogWindow = dialog.getWindow();
                    WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                    lp.width = (int) (ScreenSizeUtils.getInstance(getContext()).getScreenWidth() * 0.75f);
                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    lp.gravity = Gravity.CENTER;
                    dialogWindow.setAttributes(lp);
                    dialog.show();

                    dialog.getWindow().setGravity(Gravity.CENTER);//可以设置显示的位置


                    rl_weixin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            state = 1;
                            iv_weixin.setBackgroundResource(R.mipmap.yuan);
                            iv_yinhangka.setBackgroundResource(R.mipmap.weigou);
                        }
                    });
                    rl_bankCard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            state = 2;
                            iv_weixin.setBackgroundResource(R.mipmap.weigou);
                            iv_yinhangka.setBackgroundResource(R.mipmap.yuan);
                        }
                    });
                    ll_woyaoRefund.setOnClickListener(new View.OnClickListener() {
                        @SuppressLint("WrongConstant")
                        @Override
                        public void onClick(View v) {

                            if (state == 1) {
                                Toast.makeText(getContext(), "请联系微信客服还款。", 5000).show();
//                                                                    Intent i = new Intent(MainActivity.this, WXActivity.class);
//                                                                    i.putExtra("token", token);
//                                                                    startActivity(i);
                                dialog.dismiss();

                            } else if (state == 2) {
                                Intent i = new Intent(getContext(), RepaymentActivity.class);
                                i.putExtra("LoanlogID", model.getLoanlogID());
                                startActivity(i);
                                dialog.dismiss();
                            } else if (state == 0) {
                                Toast.makeText(getContext(), "请选择还款方式", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                } else {

                    Intent intent = new Intent(getActivity(), CatWebActivity.class);

                    CatModel catModel = new CatModel();

                    catModel.setLoanId(model.getLoanId());
                    catModel.setTermId(model.getTermId());
                    catModel.setToken(token);
                    catModel.setIdentifier(model.getIdentifier());
                    catModel.setInterest(model.getInterest());
                    catModel.setUserfee(model.getUserfees());
                    catModel.setApplyfee(model.getApplyfees());
                    intent.putExtra("catModel", catModel);
                    startActivity(intent);
//                startActivity(new Intent(getActivity(), CatWebActivity.class));
//                APIManager.getInstance().getBorrowing(getContext(), model.getLoanId(), model.getTermId(),
//                        token, model.getIdentifier()
//                        , Interest, Userfee, Applyfee, new APIManager.APIManagerInterface.baseBlock() {
//
//                            @Override
//                            public void Success(Context context, JSONObject response) {
//
//                            }
//
//                            @Override
//                            public void Failure(Context context, JSONObject response) {
//                                Toast.makeText(context, "=================", Toast.LENGTH_LONG).show();
//                            }
//                        });

//                Intent intent = new Intent();
////                intent.setData(Uri.parse(superLoanModels.get(pos).getUrl()));//Url 就是你要打开的网址
//                intent.setAction(Intent.ACTION_VIEW);
//                getContext().startActivity(intent); //启动浏览器
//////                WebActivity_.intent(getContext()).url(superLoanModels.get(pos).getUrl()).title("").start();
                }
            }
        });
        recyclerView.setLayoutManager(new FullyLinearLayoutManager(getContext()));
//        recyclerView.addItemDecoration(new DividerDecoration(getContext(),DividerItemDecoration.VERTICAL));
//        recyclerView.addItemDecoration(new DividerDecoration(getContext().getResources().getColor(R.color.transparent), DensityUtils.dip2px(getContext(), 5)));
        // DensityUtils.dip2px(getContext(), 5)
        recyclerView.setAdapter(superLoanAdapter);
    }


    private void initWxView() {
        wxModels = new ArrayList<>();
        wxKefuAdapter = new WxKefuAdapter(getContext(), wxModels);
        wxKefuAdapter.setonItemClickListener(new WxKefuAdapter.onItemClickListener() {
            @Override
            public void onOneItemClick(int position) {
                showWxDialog(wxModels.get(position).getWxCode());
            }

        });
        wxRecyclerView.addItemDecoration(new DividerGridItemDecoration(getContext(), DensityUtils.dip2px(getContext(), 5),
                R.color.transparent));
        wxRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        wxRecyclerView.setAdapter(wxKefuAdapter);
    }

    private boolean isFrist;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isFrist) {
            loadLoanData();

            initSuperLoanView();
            scroll_status.setOnRefreshListener(this);
            scroll_none.setOnRefreshListener(this);
            //注册handler，接受调wftsdk通知消息。
            PayHandlerManager.registerHandler(PayHandlerManager.PAY_H5_RESULT, handler);
        }
    }

    //通知
    public void onEvent(UserEvent.changeStatus status) {
        loadLoanData();

        initSuperLoanView();
    }

    //通知
    public void onEvent(CommonEnity enity) {
        if (enity.getType().equals("sign")) {
            isSignClick = true;
            mHandler.sendEmptyMessageDelayed(310, 3 * 1000);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //获取加载哪个视图的数据
    public void loadLoanData() {

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        scroll_status.setOnRefreshListener(this);
        scroll_none.setOnRefreshListener(this);


        ivSpeed.setVisibility(View.GONE);
        isPaySpeedClick = false;
        isSignClick = false;
        String token = PrefsUtil.getString(getActivity(), Global.TOKEN);
        if (token == null) {
            showJieKuanView();
            scroll_status.onRefreshComplete();
            scroll_none.onRefreshComplete();
        } else {
            //判断是否有未完成的订单
            showDialogLoading();
            APIManager.getInstance().Member_isNoComplate(getActivity(), new APIManager.APIManagerInterface.baseBlock() {
                @Override
                public void Success(Context context, JSONObject response) {
                    hideProgressDialog();
                    scroll_status.onRefreshComplete();
                    scroll_none.onRefreshComplete();
                    try {
                        int result = response.getInt("result");
                        if (result == 1) {
                            //0待审核 1待确认银行卡  2待确认身份证  3待签约  4放款 5待还款  6已完成  7已取消 8审核失败
                            passStatus = response.getInt("Status");
                            int IsShowDC = response.getInt("IsShowDC");
                            //测试改了0 原本是1
                            if (IsShowDC == 1) {//1 显示贷超
                                //设置默认滚动到底部
                                llSuperLoan.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.VISIBLE);
                                //  recyclerView.addItemDecoration(new DividerDecoration(R.color.person_text, LinearLayoutManager.VERTICAL));
                                getSuperLoanList();

                            } else {
                                llSuperLoan.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.GONE);
                            }
                            if (passStatus == 8) {
                                //未通过审核
                                hideProgressDialog();
                                limitDay = response.getString("LimitDay");
                                isHuanKuan = false;
                                message = "";
                                coupon = "优惠券：0张可用";
                                showJieKuanView();
                                requestLoanSuperUrl();
                                return;
                            }

                            if (passStatus == 9) {
                                //身份认证没通过
                                hideProgressDialog();
                                isHuanKuan = false;
                                message = "";
                                coupon = "优惠券：0张可用";
                                showJieKuanView();
                                return;
                            }

                            int loanID = response.getInt("LoanlogID");
                            if (loanID > 0) {
                                jkID = loanID;
                                getDeatilStatusWithLoanID(loanID);
                            } else if (loanID == 0) {
                                //判断是否已经身份认证
                                isShenFen = response.getInt("IsID2");
                                showJieKuanView();
                                requestActivity();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void Failure(Context context, JSONObject response) {
//                    if (moneyListAdapter != null) {
//                        isHuanKuan = false;
//                        message = "";
//                        coupon = "优惠券：0张可用";
//                        moneyListAdapter.setHuanKuan(isHuanKuan, message, coupon);
//                    } else {
//                        initData();
//                    }
                    hideProgressDialog();
                    scroll_status.onRefreshComplete();
                    scroll_none.onRefreshComplete();
                }
            });
        }
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 310) {
                //查询快速通道
                getDeatilStatusWithLoanID(jkID);
            }
        }
    };

    private void requestLoanSuperUrl() {
        APIManager.getInstance().requestLoanSuperUrl(getActivity(), new APIManager.APIManagerInterface.requestMobile() {
            @Override
            public void Success(Context context, String url) {
//                showButtomToast(url);
                showSuperDialog(url);
            }

            @Override
            public void Failure(Context context, String msg) {
            }
        });
    }

    private SuperDialog superDialog;

    private void showSuperDialog(String url) {
        if (superDialog == null) {
            superDialog = new SuperDialog(getContext(), url);
        }
        superDialog.show();
    }

    /**
     * 活动
     */
    private void requestActivity() {
        APIManager.getInstance().requestActivityInfo(getActivity(), new APIManager.APIManagerInterface.baseBlock() {
            @Override
            public void Success(Context context, JSONObject response) {
                try {
                    final String url = response.getString("url");
                    String imgUrl = response.getString("imgUrl");
                    int imgSize = response.getInt("imgSize");
                    ViewGroup.LayoutParams layoutParams = ivSpeed.getLayoutParams();
                    layoutParams.height = DensityUtil.dip2px(getContext(), imgSize);
                    layoutParams.width = DensityUtil.dip2px(getContext(), imgSize);
                    ivSpeed.setLayoutParams(layoutParams);
                    Glide.with(context).load(imgUrl).into(ivSpeed);
                    ivSpeed.setVisibility(View.VISIBLE);
//                    ivSpeed.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            WebActivity_.intent(getContext()).url(url).title("").start();
//                        }
//                    });
                    speedUrl = url;
                    isPaySpeed = false;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void Failure(Context context, JSONObject response) {

            }
        });
    }


    private String orderNO, backM, orderid;
    private int isVip; //  0 不是VIP  1 是

    //查看借款状态
    void getDeatilStatusWithLoanID(final int loanID) {
        showDialogLoading();
        APIManager.getInstance().LoanDetail1(getActivity(), String.valueOf(loanID), new APIManager.APIManagerInterface.baseBlock() {
            @Override
            public void Success(Context context, JSONObject response) {
                hideProgressDialog();
                try {
                    int status = response.getJSONObject("data").getInt("Status");
                    isVip = response.getJSONObject("data").getInt("isVip");
                    //String repayTime = response.getJSONObject("data").getString("RepayTime");
                    if (status < 5) {
                        isHuanKuan = false;
                        message = "";
                        coupon = "优惠券：0张可用";
                        showStatusView(status, loanID);
                        if (isSignClick) { //点击签约
                            if (status == 0) {//待审核
                                EventBus.getDefault().post(new CommonEnity<>("signSuccess"));
                            } else if (status == 3) {//待签约
                                mHandler.sendEmptyMessageDelayed(310, 3 * 1000);
                            }
                        }
                    }
                    if (status == 5) {
                        message = "当前借款：" + response.getJSONObject("data").getString("BackM") + "元";
                        isHuanKuan = true;
                        coupon = "借款编号：" + response.getJSONObject("data").getString("LoanNO");
                        orderNO = response.getJSONObject("data").getString("LoanNO");
                        orderid = response.getJSONObject("data").getString("ID");
                        backM = response.getJSONObject("data").getString("BackM");
                        tvDayMoney.setText(message);
                        tvDayMoney.setVisibility(View.VISIBLE);
                        tvApply.setText("立即还款");
                        showHuanKuanView();
                    }
                    if (isPaySpeedClick) {//点击快速审核
                        if (isVip == 1) {//如果是VIP  快速审核页面关闭
                            EventBus.getDefault().post(new CommonEnity<>("isVip"));
                        } else {
                            mHandler.sendEmptyMessageDelayed(310, 3 * 1000);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void Failure(Context context, JSONObject response) {
//                if (moneyListAdapter != null) {
//                    isHuanKuan = false;
//                    message = "";
//                    coupon = "优惠券：0张可用";
//                    moneyListAdapter.setHuanKuan(isHuanKuan, message, coupon);
//                } else {
//                    initData();
//                }
                hideProgressDialog();
            }
        });
    }

    /**
     * 快速审核费收取功能  jiemianzhanshi
     */
    private void requestLoanLogKssh(int loanID) {
        APIManager.getInstance().requestLoanLogKssh(getContext(), loanID, new APIManager.APIManagerInterface.baseBlock() {
            @Override
            public void Success(Context context, JSONObject response) {
                try {
                    final String url = response.getString("url");
                    String imgUrl = response.getString("imgUrl");
                    int imgSize = response.getInt("imgSize");
                    ViewGroup.LayoutParams layoutParams = ivSpeed.getLayoutParams();
                    layoutParams.height = DensityUtil.dip2px(getContext(), imgSize);
                    layoutParams.width = DensityUtil.dip2px(getContext(), imgSize);
                    ivSpeed.setLayoutParams(layoutParams);
                    Glide.with(context).load(imgUrl).into(ivSpeed);
                    ivSpeed.setVisibility(View.VISIBLE);
//                    ivSpeed.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            WebActivity_.intent(getContext()).url(url).title("").start();
//                        }
//                    });
                    speedUrl = url;
                    isPaySpeed = true;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void Failure(Context context, JSONObject response) {

            }
        });
    }

    //改变状态和按钮状态

    //显示借款视图
    void showJieKuanView() {
//        showDialogLoading();
        status_Scroll.setVisibility(View.GONE);
        nav_rela.setVisibility(View.GONE);
        tvDayMoney.setVisibility(View.GONE);
        scroll_none.setVisibility(View.VISIBLE);
        isJiekuan = true;
        isHuanKuan = false;
        LeftTV.setVisibility(View.GONE);
        tvApply.setText("立即借款");
        message = "";
        coupon = "优惠券：0张可用";
        initData();
    }

    private int count;
    private boolean isHuanKuan;
    private String message = "";
    private String coupon = "";
    private List<MoneyModel> moneyModels;

    private void initData() {
//        if (moneyListAdapter == null) {
//            moneyModels = new ArrayList<>();
//            moneyModels.add(new MoneyModel(0, 1000, false));
//            moneyModels.add(new MoneyModel(0, 3000, true));
//            moneyListAdapter = new MoneyListAdapter(getContext(), moneyModels);
//            moneyListAdapter.setonItemClickListener(new MoneyListAdapter.onItemClickListener() {
//                @Override
//                public void onHuanKuanItemClick(View v) {
//                    if (huanKuanDialog == null) {
//                        showHuanKuanDialog();
//                    } else {
//                        huanKuanDialog.show();
//                    }
//                }
//
//                @Override
//                public void onOneItemClick(View view) {
//                    String token = PrefsUtil.getString(getActivity(), Global.TOKEN);
//                    if (token == null) {
//                        LoginActivity_.intent(LoanFragment.this).startForResult(1000);
//                        return;
//                    }
//                    if (passStatus == 5) {
//                        return;
//                    } else if (passStatus != 8) {
//                        requestState();
//                    } else {
//                        if (Integer.parseInt(limitDay) == 0) {
//                            showLitmitDialog("您的申请借款审核未通过，详情请看系统消息！");
//                        } else {
//                            showLitmitDialog("您的申请借款审核未通过，请" + limitDay + "天后再次尝试！");
//                        }
//                    }
//                }
//            });
//            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//            recyclerView.setAdapter(moneyListAdapter);
//            moneyListAdapter.setHuanKuan(isHuanKuan, message, coupon);
//        } else {
//            moneyListAdapter.setHuanKuan(isHuanKuan, message, coupon);
//        }
//        String token = PrefsUtil.getString(getActivity(), Global.TOKEN);
//        if (token != null) {
//            APIManager.getInstance().getLoanCount(getContext(), new APIManager.APIManagerInterface.resultListener() {
//                @Override
//                public void Success(Context context, String data) {
//                    count = Integer.parseInt(data);
//                    moneyListAdapter.setCount(count);
//                }
//
//                @Override
//                public void Failure(Context context, JSONObject response) {
//                }
//            });
//        } else {
//            moneyListAdapter.setCount(0);
//        }
//        hideProgressDialog();
    }

    private PromptDialog litmitDialog;

    private void showLitmitDialog(String msg) {
        if (litmitDialog == null) {
            litmitDialog = new PromptDialog(getContext());
        }
        litmitDialog.tvCancle.setVisibility(View.GONE);
        litmitDialog.viewXian.setVisibility(View.GONE);
        litmitDialog.tvTitle.setText(msg);
        litmitDialog.tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                litmitDialog.dismiss();
            }
        });
        litmitDialog.show();
    }

    /**
     * 获取状态  是否已实名认证
     */
    private void requestState() {
        showDialogLoading();
        APIManager.getInstance().home_getAuthenStatus(getContext(), 0, new APIManager.APIManagerInterface.common_object() {
            @Override
            public void Success(Context context, Object model) {
                hideProgressDialog();
                AuthenModel authenModel = (AuthenModel) model;
                if (authenModel.getIsID2() == 1) {
                    StartBorrowingsActivity_.intent(LoanFragment.this).start();
                } else {
                    FirstCerActivity_.intent(LoanFragment.this).tag(2).start();
                }
            }

            @Override
            public void Failure(Context context, JSONObject response) {
                hideProgressDialog();
            }
        });
    }

    private AlertDialog huanKuanDialog;

    private void showHuanKuanDialog() {
        huanKuanDialog = new AlertDialog.Builder(getActivity()).create();
        huanKuanDialog.show();
        Window window = huanKuanDialog.getWindow();
        window.setContentView(R.layout.payway);
        final Button cancel = (Button) window.findViewById(R.id.cancel_Button);
        final Button ok = (Button) window.findViewById(R.id.OK_Button);
        final RelativeLayout bank_Rela = (RelativeLayout) window.findViewById(R.id.bank_Rela);
        final RelativeLayout zfb_Rela = (RelativeLayout) window.findViewById(R.id.zfb_Rela);
        final RelativeLayout wx_Rela = (RelativeLayout) window.findViewById(R.id.wx_Rela);
        final CheckBox zfb_Box = (CheckBox) window.findViewById(R.id.zfb_Box);
        final CheckBox wx_Box = (CheckBox) window.findViewById(R.id.wx_Box);
        final CheckBox bank_Box = (CheckBox) window.findViewById(R.id.bank_Box);

        zfb_Rela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zfb_Box.setChecked(true);
                wx_Box.setChecked(false);
                bank_Box.setChecked(false);
            }
        });
        bank_Box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bank_Box.setChecked(true);
                wx_Box.setChecked(false);
                zfb_Box.setChecked(false);
            }
        });
        bank_Rela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bank_Box.setChecked(true);
                wx_Box.setChecked(false);
                zfb_Box.setChecked(false);
            }
        });
        zfb_Box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zfb_Box.setChecked(true);
                wx_Box.setChecked(false);
                bank_Box.setChecked(false);
            }
        });
        wx_Rela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wx_Box.setChecked(true);
                zfb_Box.setChecked(false);
                bank_Box.setChecked(false);
            }
        });
        wx_Box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wx_Box.setChecked(true);
                bank_Box.setChecked(false);
                zfb_Box.setChecked(false);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                huanKuanDialog.dismiss();
            }
        });
        //还款
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                huanKuanDialog.dismiss();
                long time = System.currentTimeMillis();//long now = android.os.SystemClock.uptimeMillis();
                SimpleDateFormat format = new SimpleDateFormat("HHmmss");
                Date d1 = new Date(time);
                String t1 = format.format(d1);
                Log.e("msg", t1);
//                orderNO = orderNO + t1;
                if (wx_Box.isChecked() == true) {
                    //  PayWebActivity_.intent(getActivity()).flag(2).noOrder(orderNO).backM(backM).startForResult(100);
                    WebActivity_.intent(getContext()).url(Global.HOST + "Home/wx?token=" + PrefsUtil.getString(getContext(), Global.TOKEN)).start();
                } else if (zfb_Box.isChecked() == true) {
                    requestAliPay(orderid);
                    //威富通
                    //new GetPrepayIdTask().execute();
                    //51支付
//                    WebActivity_.intent(getContext()).url(Global.HOST + "Home/Code?token=" + PrefsUtil.getString(getContext(), Global.TOKEN)).start();
                    //     PayWebActivity_.intent(getActivity()).flag(1).noOrder(orderNO).backM(backM).startForResult(100);
                } else if (bank_Box.isChecked() == true) {
                    WebActivity_.intent(getContext()).url(APIManager.requestBankPay + "?id=" + orderid).start();
//                    requestBankState(orderid);
                }
            }
        });
    }


    private void requestAliPay(String orderNO) {
        showDialogLoading();
        APIManager.getInstance().requestAliPay(orderNO, getContext(), new APIManager.APIManagerInterface.aliPay() {

            @Override
            public void Success(Context context, String prepayId, String ordernumber) {
                hideProgressDialog();
                PayActivity_.intent(LoanFragment.this).prepayId(prepayId).ordernumber(ordernumber).startForResult(1000);
            }

            @Override
            public void Failure(Context context, String response) {
                showButtomToast(response);
                hideProgressDialog();
            }
        });
    }

    /**
     * 判断银行卡还款方式
     */
    private void requestBankState(final String orderNO) {
        showDialogLoading();
        APIManager.getInstance().requestBankState(getContext(), new APIManager.APIManagerInterface.aliPay() {

            @Override
            public void Success(Context context, String url, String ordernumber) {
                hideProgressDialog();
                WebActivity_.intent(getContext()).url(url).title("银行卡还款").start();
            }

            @Override
            public void Failure(Context context, String response) {
                hideProgressDialog();
                requestBankPay(orderNO);
            }
        });
    }

    private void requestBankPay(String orderNO) {
        showDialogLoading();
        APIManager.getInstance().requestBankPay(orderNO, getContext(), new APIManager.APIManagerInterface.aliPay() {

            @Override
            public void Success(Context context, String prepayId, String ordernumber) {
                hideProgressDialog();
                JSONObject obj;
                try {
                    obj = new JSONObject(prepayId);
                    String ret_code = obj.getString("ret_code");
                    if (ret_code.equals("0000")) {
                        showButtomToast(obj.getString("ret_msg"));
                        loadLoanData();
                        initSuperLoanView();
                    } else {
                        showButtomToast(obj.getString("ret_msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void Failure(Context context, String response) {
                showButtomToast(response);
                hideProgressDialog();
            }
        });
    }

    @OnActivityResult(1000)
    void PayActivity() {
        loadLoanData();

        initSuperLoanView();
    }

    //显示状态视图
    void showStatusView(int status, int loanID) {
        statusBtn.setTextColor(getResources().getColor(R.color.white));
        wxKefuAdapter.notifyDataSetChanged();
        haveMessage();
        jkStatus = status;
        scroll_none.setVisibility(View.GONE);
        llWx.setVisibility(View.GONE);
        status_Scroll.setVisibility(View.VISIBLE);
        nav_rela.setVisibility(View.VISIBLE);
//        LeftTV.setVisibility(View.VISIBLE);
        RightIB.setVisibility(View.VISIBLE);
        RightIB1.setVisibility(View.VISIBLE);
        if (status == 3) {
            //签约
            one_text.setBackgroundResource(R.drawable.text_blue);
            mTitle1.setTextColor(getResources().getColor(R.color.nav_blue));
            mContent1.setTextColor(getResources().getColor(R.color.detail_cash));
            statusBtn.setText("签约");
            statusBtn.setBackground(getResources().getDrawable(R.drawable.btn_cut));
            statusBtn.setClickable(true);

            two_text.setBackgroundResource(R.drawable.text_grey);
            mTitle2.setTextColor(getResources().getColor(R.color.font_grey2));
            mContent2.setTextColor(getResources().getColor(R.color.font_grey2));
            three_text.setBackgroundResource(R.drawable.text_grey);
            mTitle3.setTextColor(getResources().getColor(R.color.font_grey2));
            mContent3.setTextColor(getResources().getColor(R.color.font_grey2));
        } else {
            statusBtn.setBackground(getResources().getDrawable(R.drawable.btn_cut_gray));
            statusBtn.setClickable(false);
            if (status == 0) {
                //审核
                mContent1.setText("签约成功，若审核通过，及时放款");
                two_text.setBackgroundResource(R.drawable.text_blue);
                mTitle2.setTextColor(getResources().getColor(R.color.nav_blue));
                mContent2.setTextColor(getResources().getColor(R.color.detail_cash));
                three_text.setBackgroundResource(R.drawable.text_grey);
                mTitle3.setTextColor(getResources().getColor(R.color.font_grey2));
                mContent3.setTextColor(getResources().getColor(R.color.font_grey2));
                llWx.setVisibility(View.VISIBLE);
                if (isVip == 1) {
                    statusBtn.setTextColor(getResources().getColor(R.color.text_red));
                    statusBtn.setText("您已进入VIP快速审核中");
                } else {
                    statusBtn.setText("正在审核中");
                }
                if (wxModels.size() == 0) {
                    requestWxCode();
                    requestWxTxt();
                }
                requestLoanLogKssh(loanID);
            } else {
                //放款
                statusBtn.setText("正在放款中");
                mContent2.setText("审核通过，款项正在路上");
                three_text.setBackgroundResource(R.drawable.text_blue);
                mTitle3.setTextColor(getResources().getColor(R.color.nav_blue));
                mContent3.setTextColor(getResources().getColor(R.color.detail_cash));
            }
        }
    }

    /**
     * 审核中的提示语
     */
    private void requestWxTxt() {
        APIManager.getInstance().requestWxTxt(getContext(), new APIManager.APIManagerInterface.requestMobile() {
            @Override
            public void Success(Context context, String msg) {
                if (!StringUtils.isEmpty(msg)) {
                    mContent2.setText(msg);
                }
            }

            @Override
            public void Failure(Context context, String msg) {

            }
        });
    }

    /**
     * 微信客服
     */
    private void requestWxCode() {
        APIManager.getInstance().requestHomeWxKefu(getContext(), new APIManager.APIManagerInterface.common_object() {
            @Override
            public void Success(Context context, Object model) {
                if (model != null) {
                    BaseDataListModel<WxModel> wxModelBaseDataListModel = (BaseDataListModel<WxModel>) model;
                    if (wxModelBaseDataListModel.getResult() == 1 && wxModelBaseDataListModel.getDatalist() != null
                            && wxModelBaseDataListModel.getDatalist().size() > 0) {
                        wxModels.addAll(wxModelBaseDataListModel.getDatalist());
                    }
                }
                wxKefuAdapter.notifyDataSetChanged();
                ViewGroup.LayoutParams layoutParams = viewXian2.getLayoutParams();
                int size;
                if (wxModels.size() % 3 == 0) {
                    size = wxModels.size() / 3;
                } else {
                    size = wxModels.size() / 3 + 1;
                }
                int line = size * DensityUtils.dip2px(getContext(), 35);
                int space = (size - 1) * DensityUtils.dip2px(getContext(), 5);
                layoutParams.height = DensityUtils.dip2px(getContext(), 35) + line + space;
                viewXian2.setLayoutParams(layoutParams);
            }

            @Override
            public void Failure(Context context, JSONObject response) {

            }
        });
    }


    //显示还款视图
    void showHuanKuanView() {
        status_Scroll.setVisibility(View.GONE);
        nav_rela.setVisibility(View.GONE);
        scroll_none.setVisibility(View.VISIBLE);
//        if (moneyListAdapter == null) {
//            initData();
//        } else {
//            moneyListAdapter.setHuanKuan(isHuanKuan, message, coupon);
//        }
//        LeftTV.setVisibility(View.VISIBLE);
        RightIB.setVisibility(View.VISIBLE);
        RightIB1.setVisibility(View.VISIBLE);
        haveMessage();
        isJiekuan = false;
    }

    //是否有未读消息
    void haveMessage() {
        showBlackLoading();
        APIManager.getInstance().getMessage(getActivity(), pageIndex, pageSize, new APIManager.APIManagerInterface.common_object() {
            @Override
            public void Success(Context context, Object model) {
                hideProgressDialog();
                BaseDataListModel<MessageModel> mm = (BaseDataListModel<MessageModel>) model;
                for (int i = 0; i < mm.getDatalist().size(); i++) {
                    MessageModel messageModel = mm.getDatalist().get(i);
                    if (messageModel.getIsRead() == 0) {
                        RightIB.setBackground(getResources().getDrawable(R.mipmap.rednews));
                        RightIB1.setBackground(getResources().getDrawable(R.mipmap.rednews));
                        return;
                    }
                }
                RightIB.setBackground(getResources().getDrawable(R.mipmap.news));
                RightIB1.setBackground(getResources().getDrawable(R.mipmap.news));
            }

            @Override
            public void Failure(Context context, JSONObject response) {
                hideProgressDialog();
            }
        });
    }

    //贷超列表
    void getSuperLoanList() {
        if (superLoanModels.size() == 0)
            APIManager.getInstance().getSuperLoanList(getActivity(), new APIManager.APIManagerInterface.common_object() {
                @Override
                public void Success(Context context, Object model) {
                    if (superLoanModels.size() > 0)
                        return;
                    hideProgressDialog();
                    BaseDataListModel<SuperLoanModel> mm = (BaseDataListModel<SuperLoanModel>) model;
                    if (mm.getDatalist() != null && mm.getDatalist().size() > 0) {
                        superLoanModels.addAll(mm.getDatalist());
                    }
//                    superLoanAdapter.setOffscreenPageLimit(2);
                    superLoanAdapter.notifyDataSetChanged();
                    scroll_none.post(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            scroll_none.getRefreshableView().fullScroll(ScrollView.FOCUS_DOWN);
                        }
                    });
                }

                @Override
                public void Failure(Context context, JSONObject response) {
                    hideProgressDialog();
                }
            });
    }

    @Click
    void statusBtn() {
//        if (jkStatus==1){
//            //确认银行卡
//            CardActivity_.intent(this).LoanID(jkID).startForResult(100);
//        }
//        if (jkStatus==2){
//            //身份认证
//            IDPhotoActivity_.intent(this).loanlogID(jkID).startForResult(100);
//        }
        if (jkStatus == 3) {
            //签约
            SingedActivity_.intent(this).LoanID(jkID).startForResult(100);
        }
    }

    @OnActivityResult(100)
    void change() {
        loadLoanData();

        initSuperLoanView();
    }

    @Click
    void LeftTV() {
        CashOrderDetailActivity_.intent(getActivity()).loanLog(jkID).start();
    }

    @Click
    void tvApply() {
        String token = PrefsUtil.getString(getActivity(), Global.TOKEN);
        if (token == null) {//未登录
            LoginActivity_.intent(LoanFragment.this).startForResult(1000);
            return;
        }
        //0待审核 1待确认银行卡  2待确认身份证  3待签约  4放款 5待还款  6已完成  7已取消 8审核失败
        if (passStatus == 5) {
            if (huanKuanDialog == null) {
                showHuanKuanDialog();
            } else {
                huanKuanDialog.show();
            }
        } else if (passStatus != 8) {
            requestState();
        } else {
            if (Integer.parseInt(limitDay) == 0) {
                showLitmitDialog("您的申请借款审核未通过，详情请看系统消息！");
            } else {
                showLitmitDialog("您的申请借款审核未通过，请" + limitDay + "天后再次尝试！");
            }
        }
    }

    @Click
    void RightIB() {
        String token = PrefsUtil.getString(getActivity(), Global.TOKEN);
        if (TextUtils.isEmpty(token)) {
            LoginActivity_.intent(this).startForResult(1000);
        } else {
            MessageActivity_.intent(getActivity()).start();
        }
    }

    @Click
    void RightIB1() {
        String token = PrefsUtil.getString(getActivity(), Global.TOKEN);
        if (TextUtils.isEmpty(token)) {
            LoginActivity_.intent(this).startForResult(1000);
        } else {
            MessageActivity_.intent(getActivity()).start();
        }
    }

    @Click
    void LeftIB() {
        String token = PrefsUtil.getString(getActivity(), Global.TOKEN);
        if (TextUtils.isEmpty(token)) {
            LoginActivity_.intent(this).startForResult(1000);
        } else {
            WordActivity_.intent(this).start();
        }
    }

    private class GetPrepayIdTask extends AsyncTask<Void, Void, Map<String, String>> {

        private ProgressDialog dialog;

        private String accessToken;

        public GetPrepayIdTask(String accessToken) {
            this.accessToken = accessToken;
        }

        public GetPrepayIdTask() {
        }

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(getContext(), "提示", "正在获取预支付订单..");
        }

        @Override
        protected void onPostExecute(Map<String, String> result) {
            if (dialog != null) {
                dialog.dismiss();
            }
            if (result == null) {
                Toast.makeText(getContext(), "获取prepayid失败", Toast.LENGTH_LONG).show();
            } else {
                if (result.get("status").equalsIgnoreCase("0")) // 成功
                {

                    Toast.makeText(getContext(), "获取prepayid成功", Toast.LENGTH_LONG).show();
                    RequestMsg msg = new RequestMsg();
                    msg.setTokenId(result.get("token_id"));

                    //支付宝wap
                    msg.setTokenId(result.get("token_id"));
                    msg.setTradeType(MainApplication.PAY_NEW_ZFB_WAP);
                    PayPlugin.unifiedH5Pay(getActivity(), msg);


                } else {
                    Toast.makeText(getContext(), "获取prepayid失败", Toast.LENGTH_LONG)
                            .show();
                }

            }

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected Map<String, String> doInBackground(Void... params) {
            // 统一预下单接口
            String url = "https://pay.swiftpass.cn/pay/gateway";

            String entity = getParams();

            Log.d(TAG, "doInBackground, url = " + url);
            Log.d(TAG, "doInBackground, entity = " + entity);

            byte[] buf = Util.httpPost(url, entity);
            if (buf == null || buf.length == 0) {
                return null;
            }
            String content = new String(buf);
            Log.d(TAG, "doInBackground, content = " + content);
            try {
                return XmlUtils.parse(content);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            }
        }

    }

    private String genNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    private String genOutTradNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss");
        Date date = new Date();
        String key = format.format(date);

        java.util.Random r = new java.util.Random();
        key += r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    String payOrderNo;

    /**
     * 组装参数
     * <功能详细描述>
     *
     * @return
     * @see [类、类#方法、类#成员]
     */
    private String getParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("body", "现金急速贷还款"); // 商品名称
        params.put("service", "unified.trade.pay"); // 支付类型
        params.put("version", "1.0"); // 版本
        params.put("mch_id", "101590023318"); // 威富通商户号
        //        params.put("mch_id", mchId.getText().toString()); // 威富通商户号
        params.put("notify_url", " "); // 后台通知url
        params.put("nonce_str", genNonceStr()); // 随机数
        payOrderNo = genOutTradNo();
        params.put("out_trade_no", orderNO);
        //params.put("out_trade_no", payOrderNo); //订单号
        params.put("mch_create_ip", "127.0.0.1"); // 机器ip地址
        float f = Float.parseFloat(backM);
        params.put("total_fee", "" + (int) (f * 100)); // 总金额
        params.put("limit_credit_pay", "1"); // 是否限制信用卡支付， 0：不限制（默认），1：限制
        String sign = createSign("25fac09b79fea6c6264c6e817e77e8de", params); // 01133be809cd03a4726e8b861b58ad7a  威富通密钥

        params.put("sign", sign); // sign签名

        return XmlUtils.toXml(params);
    }

    public String createSign(String signKey, Map<String, String> params) {
        StringBuilder buf = new StringBuilder((params.size() + 1) * 10);
        SignUtils.buildPayParams(buf, params, false);
        buf.append("&key=").append(signKey);
        String preStr = buf.toString();
        String sign = "";
        // 获得签名验证结果
        try {
            sign = MD5.md5s(preStr).toUpperCase();
        } catch (Exception e) {
            sign = MD5.md5s(preStr).toUpperCase();
        }
        return sign;
    }

    public void buildPayParams(StringBuilder sb, Map<String, String> payParams, boolean encoding) {
        List<String> keys = new ArrayList<String>(payParams.keySet());
        Collections.sort(keys);
        for (String key : keys) {
            sb.append(key).append("=");
            if (encoding) {
                sb.append(urlEncode(payParams.get(key)));
            } else {
                sb.append(payParams.get(key));
            }
            sb.append("&");
        }
        sb.setLength(sb.length() - 1);
    }

    public String urlEncode(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (Throwable e) {
            return str;
        }
    }

    //    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data)
//    {
//
//        if (data == null)
//        {
//            return;
//        }
//
//        String respCode = data.getExtras().getString("resultCode");
//        if (!TextUtils.isEmpty(respCode) && respCode.equalsIgnoreCase("success"))
//        {
//            //标示支付成功
//            Toast.makeText(PayMainActivity.this, "支付成功", Toast.LENGTH_LONG).show();
//        }
//        else
//        { //其他状态NOPAY状态：取消支付，未支付等状态
//            Toast.makeText(PayMainActivity.this, "未支付", Toast.LENGTH_LONG).show();
//        }
//
//        super.onActivityResult(requestCode, resultCode, data);
//
//    }
    String TAG = "LoanFragment";

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {

        loadLoanData();
        initSuperLoanView();

    }

    public Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case PayHandlerManager.PAY_H5_FAILED: //失败，原因如有（商户未开通[pay.weixin.wappay]支付类型）等
                    Log.i(TAG, "" + msg.obj);
                    break;
                case PayHandlerManager.PAY_H5_SUCCES: //成功
                    Log.i(TAG, "" + msg.obj);
                    break;

                default:
                    break;
            }
        }
    };


    private int lastX;
    private int lastY;
    private int downX;
    private int downY;

    private int maxRight;
    private int maxBottom;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //得到事件的坐标
        int eventX = (int) event.getRawX();
        int eventY = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //得到父视图的right/bottom
                if (maxRight == 0) {//保证只赋一次值
                    maxRight = parentView.getRight();
                    maxBottom = parentView.getBottom();
                }
                //第一次记录lastX/lastY
                lastX = downX = eventX;
                lastY = downY = eventY;
                break;
            case MotionEvent.ACTION_UP:
                if (Math.abs(eventX - downX) < 10 && Math.abs(eventY - downY) < 10) {
                    WebActivity_.intent(getContext()).url(speedUrl).title("").start();
                    if (isPaySpeed) {
                        isPaySpeedClick = true;
                        mHandler.sendEmptyMessageDelayed(310, 3 * 1000);
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                //计算事件的偏移
                int dx = eventX - lastX;
                int dy = eventY - lastY;
                //根据事件的偏移来移动imageView
                int left = ivSpeed.getLeft() + dx;
                int top = ivSpeed.getTop() + dy;
                int right = ivSpeed.getRight() + dx;
                int bottom = ivSpeed.getBottom() + dy;
                //限制left >=0
                if (left < 0) {
                    right += -left;
                    left = 0;
                }
                //限制top
                if (top < 0) {
                    bottom += -top;
                    top = 0;
                }
                //限制right <=maxRight
                if (right > maxRight) {
                    left -= right - maxRight;
                    right = maxRight;
                }
                //限制bottom <=maxBottom
                if (bottom > maxBottom) {
                    top -= bottom - maxBottom;
                    bottom = maxBottom;
                }
                ivSpeed.layout(left, top, right, bottom);
                //再次记录lastX/lastY
                lastX = eventX;
                lastY = eventY;
                break;
            default:
                break;
        }
        return true;//所有的motionEvent都交给imageView处理
    }

    private List<String> bannerUrls;

    /**
     *
     * */
    private void initBanner() {
        bannerUrls = new ArrayList<>();
        for (int i = 0; i < bannerModels.size(); i++) {
            bannerUrls.add(bannerModels.get(i).getPic_2xUrl());
//            bannerUrls.add("http://img.910ok.com/" + bannerModels.get(i).getUrl());
        }
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (bannerModels.get(position).getLinkUrl() != null) {
                    WebActivity_.intent(getContext()).url(bannerModels.get(position).getLinkUrl()).title("").start();
                }
            }
        });
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        mBanner.setImageLoader(new GlideImageLoader());
        mBanner.setBannerAnimation(Transformer.Default);
        mBanner.isAutoPlay(true);
        mBanner.setDelayTime(3000);
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        mBanner.setImages(bannerUrls);
        mBanner.start();
    }

    private List<MyBannerModel> bannerModels;

    private void requestBanner() {
        bannerModels = new ArrayList<>();
        APIManager.getInstance().requestBanner(getActivity(), new APIManager.APIManagerInterface.aliPay() {
            @Override
            public void Success(Context context, String banner, String ordernumber) {
                if (banner != null) {
                    List<MyBannerModel> models = JSONArray.parseArray(banner, MyBannerModel.class);
                    if (models != null && models.size() > 0) {
                        bannerModels.addAll(models);
                        initBanner();
                    }
                }
            }

            @Override
            public void Failure(Context context, String msg) {
                showButtomToast(msg);
            }
        });
    }

    private void initRandom() {
        List<String> msgList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 15; i++) {
            String msg = "";
            for (int j = 0; j < 4; j++) {
                int num = random.nextInt(9);
                msg = msg + num + "";
            }
            int money = 0;
            if (i % 2 == 0) {
                money = 500;
            } else {
                money = 1000;
            }
            msgList.add("尾号" + msg + "，成功借款" + money + "元");
        }
        rollingView.setDataSetAdapter(new DataSetAdapter<String>(msgList) {
            @Override
            protected String text(String s) {
                return s;
            }
        });
        // 开始滚动
        rollingView.run();
    }
}

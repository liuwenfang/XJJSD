package com.ahxbapp.xjjsd.fragment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ahxbapp.common.Global;
import com.ahxbapp.common.ImageLoadTool;
import com.ahxbapp.common.ui.BaseFragment;
import com.ahxbapp.xjjsd.R;
import com.ahxbapp.xjjsd.activity.ConsummateInfoActivity_;
//import com.ahxbapp.xjjsd.activity.CouponCashActivity_;
import com.ahxbapp.xjjsd.activity.FirstCerActivity_;
import com.ahxbapp.xjjsd.activity.HelpCenterActivity_;
import com.ahxbapp.xjjsd.activity.LoginActivity_;
import com.ahxbapp.xjjsd.activity.MessageActivity_;
import com.ahxbapp.xjjsd.activity.OrderListActivity_;
import com.ahxbapp.xjjsd.activity.PersonDataActivity_;
import com.ahxbapp.xjjsd.activity.SetWebActivity_;
import com.ahxbapp.xjjsd.activity.SettingActivity_;
import com.ahxbapp.xjjsd.activity.WordActivity_;
import com.ahxbapp.xjjsd.api.APIManager;
import com.ahxbapp.xjjsd.model.User;
import com.ahxbapp.xjjsd.utils.PrefsUtil;
import com.ahxbapp.xjjsd.utils.VersionUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by Jayzhang on 16/10/17.
 */
@EFragment(R.layout.person)
public class PersonFragment extends BaseFragment {

    @ViewById
    RelativeLayout login_Rela, message_Rela, perData_Rela, MyBorrowings_Rela, law_Rela, FAQ_Rela, word_Rela;
    @ViewById
    ImageView setting_btn, img_head, shimingImage;
    @ViewById
    TextView login_text, banben_text, tvDisCountP;

    String code;

//    @Click
//    void login_text() {
//        showButtomToast("点击测试");
//    }

    @AfterViews
    void init() {
        //创建/打开 数据库
        SQLiteDatabase db = Connector.getDatabase();
        loadView();

        try {
            code = VersionUtils.getVersionName(getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        banben_text.setText("当前版本" + code);
    }

    public void loadView() {
        //加载视图
        String token = PrefsUtil.getString(getActivity(), Global.TOKEN);
        if (TextUtils.isEmpty(token)) {
            //头像
            Bitmap bit = BitmapFactory.decodeResource(getResources(), R.mipmap.avatar);
            img_head.setImageBitmap(bit);
            login_text.setText(R.string.per_nologin_text);
            shimingImage.setVisibility(View.GONE);
            tvDisCountP.setText("0");
        } else {
            //加载本地
            loadLocal();
            //下载网络数据  个人信息
            loadData();
            //优惠券信息
            loadDisMsg();
        }
    }

    /**
     * 获取优惠券信息
     */
    private void loadDisMsg() {
        APIManager.getInstance().getCouponCount(getContext(), new APIManager.APIManagerInterface.resultListener() {
            @Override
            public void Success(Context context, String data) {
                tvDisCountP.setText("0");
//                tvDisCountP.setText(data);
            }

            @Override
            public void Failure(Context context, JSONObject response) {

            }
        });
    }

    private User user;

    /**
     * 网络请求个人信息
     */
    //下载数据
    void loadData() {
        APIManager.getInstance().user_getUserInfo(getActivity(), new APIManager.APIManagerInterface.common_object() {
            @Override
            public void Success(Context context, Object model) {
                //用户信息
                user = (User) model;
                //存储数据库
                if (user != null) {
                    User user1 = DataSupport.find(User.class, user.getUid());
                    if (user1 == null) {
                        user.save();
                        PrefsUtil.setString(context, "user_id", String.valueOf(user.getUid()));
                        JPushInterface.setAlias(getContext(), String.valueOf(user.getID()), new TagAliasCallback() {
                            @Override
                            public void gotResult(int i, String s, Set<String> set) {
                            }
                        });
                        JPushInterface.resumePush(getContext());
                        Log.e("1", "添加2--------->> user" + user.getID());
                    } else {
                        user.update(user.getUid());
                        Log.e("1", "修改2-------->> user" + user.getID());
                    }
                }

                //加载页面数据

                shimingImage.setVisibility(View.VISIBLE);
                if (user.getTrueName() != null && !user.getTrueName().equals("")) {
                    Bitmap bit = BitmapFactory.decodeResource(getResources(), R.mipmap.yishim);
                    shimingImage.setImageBitmap(bit);
                    login_text.setText(user.getTrueName());
                } else {
                    Bitmap bit = BitmapFactory.decodeResource(getResources(), R.mipmap.weism);
                    shimingImage.setImageBitmap(bit);
                    login_text.setText(user.getNickName());
                }
//                login_text.setText(user.getNickName());
                ImageLoader.getInstance().displayImage(user.getHead(), img_head, ImageLoadTool.options);
            }

            @Override
            public void Failure(Context context, JSONObject response) {

            }
        });
    }

    /**
     * 本地个人信息
     */
    void loadLocal() {
        User localUser = DataSupport.findFirst(User.class);
        if (localUser != null) {
            //加载页面数据
            shimingImage.setVisibility(View.VISIBLE);
            if (localUser.getTrueName() != null&& !user.getTrueName().equals("")) {
                Bitmap bit = BitmapFactory.decodeResource(getResources(), R.mipmap.yishim);
                shimingImage.setImageBitmap(bit);
                login_text.setText(localUser.getTrueName());
            } else {
                Bitmap bit = BitmapFactory.decodeResource(getResources(), R.mipmap.weism);
                shimingImage.setImageBitmap(bit);
                login_text.setText(localUser.getNickName());
            }
            ImageLoader.getInstance().displayImage(localUser.getHead(), img_head, ImageLoadTool.options);
        }
    }

    @OnActivityResult(1000)
    void LoginActivity() {
        loadView();
    }

    @OnActivityResult(2000)
    void UserInfoActivity() {
        loadView();
    }

    @OnActivityResult(3000)
    void SettingActivity() {
        loadView();
    }

    /**
     * 个人信息（昵称与头像）
     */
    @Click
    void login_Rela() {
        String token = PrefsUtil.getString(getActivity(), Global.TOKEN);
        if (TextUtils.isEmpty(token)) {
            LoginActivity_.intent(this).startForResult(1000);
        } else {
            if (user != null && user.getTrueName() != null&& !user.getTrueName().equals("")) {
//                PersonDataActivity_.intent(this).startForResult(2000);
            } else {
                FirstCerActivity_.intent(this).start();
            }
        }
    }

    /**
     * 系统消息
     */
    @Click
    void message_Rela() {
        String token = PrefsUtil.getString(getActivity(), Global.TOKEN);
        if (TextUtils.isEmpty(token)) {
            LoginActivity_.intent(this).startForResult(1000);
        } else {
            MessageActivity_.intent(this).start();
        }
    }

    /**
     * 个人资料
     */
    @Click
    void perData_Rela() {
        String token = PrefsUtil.getString(getActivity(), Global.TOKEN);
        if (TextUtils.isEmpty(token)) {
            LoginActivity_.intent(this).startForResult(1000);
        } else {
            User localUser = DataSupport.findFirst(User.class);
            if (localUser.getTrueName() != null&& !user.getTrueName().equals("")) {
                PersonDataActivity_.intent(this).start();
            } else {
                FirstCerActivity_.intent(this).tag(2).start();
            }
        }
    }

    /**
     * 完善资料
     */
    @Click
    void rlPrefectData() {
        String token = PrefsUtil.getString(getActivity(), Global.TOKEN);
        if (TextUtils.isEmpty(token)) {
            LoginActivity_.intent(this).startForResult(1000);
        } else {
            if (user != null && user.getTrueName() != null&& !user.getTrueName().equals("")) {
                ConsummateInfoActivity_.intent(this).start();
            } else {
                FirstCerActivity_.intent(this).tag(2).start();
            }
        }
    }

    /**
     * 我的借款
     */
    @Click
    void MyBorrowings_Rela() {
        String token = PrefsUtil.getString(getActivity(), Global.TOKEN);
        if (TextUtils.isEmpty(token)) {
            LoginActivity_.intent(this).startForResult(1000);
        } else {
            OrderListActivity_.intent(this).start();
        }
    }

    /**
     * 法律责任
     */
    @Click
    void law_Rela() {
        SetWebActivity_.intent(this).flag(4).start();
    }

    /**
     * 常见问题 帮助中心...
     */
    @Click
    void FAQ_Rela() {
//        FAQActivity_.intent(this).start();
        HelpCenterActivity_.intent(this).start();
    }

    /**
     * 留言
     */
    @Click
    void word_Rela() {
        String token = PrefsUtil.getString(getActivity(), Global.TOKEN);
        if (TextUtils.isEmpty(token)) {
            LoginActivity_.intent(this).startForResult(1000);
        } else {
            WordActivity_.intent(this).start();
        }
    }

    /**
     * 优惠券
     */
    @Click
    void rlCoupon() {
//        CouponCashActivity_.intent(this).price(-1).startForResult(1000);
    }

    /**
     * 设置
     */
    @Click
    void setting_btn() {
        SettingActivity_.intent(this).startForResult(3000);
    }

    /**
     * 设置
     */
    @Click
    void rlSet() {
        SettingActivity_.intent(this).startForResult(3000);
    }
}

package com.ahxbapp.xjjsd.activity;

import android.Manifest;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ahxbapp.common.ui.BaseActivity;
import com.ahxbapp.xjjsd.R;
import com.ahxbapp.xjjsd.api.APIManager;
import com.ahxbapp.xjjsd.dialog.LoadDialog;
import com.ahxbapp.xjjsd.model.AuthenModel;
import com.ahxbapp.xjjsd.model.ContactsInfo;
import com.ahxbapp.xjjsd.utils.GetContactsInfo1;
import com.ahxbapp.xjjsd.utils.PermissionUtils;
import com.google.gson.Gson;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;
import org.json.JSONObject;

import java.util.List;

/**
 * 认证
 */
@EActivity(R.layout.activity_consummate_info)
public class ConsummateInfoActivity extends BaseActivity implements View.OnClickListener {
    @ViewById
    ImageButton mToolbarLeftIB;
    @ViewById
    TextView mToolbarTitleTV; //title
    @ViewById
    LinearLayout llGuarantee;
    @ViewById
    TextView tvId1, tvContent, tvZM, tvPhone1, tvId2, tvPhone2, tvGuarantee;
    @ViewById
    Button btId1, btContent, btZM, btPhone1, btId2, btPhone2;

    private Context mContext;


    private List<ContactsInfo> contactsInfoList;

    @AfterViews
    void initLogic() {
        mContext = this;
        mToolbarTitleTV.setText("认证");
        mToolbarLeftIB.setImageResource(R.mipmap.back);
        btId1.setOnClickListener(this);
        btContent.setOnClickListener(this);
        btZM.setOnClickListener(this);
        btPhone1.setOnClickListener(this);
        btId2.setOnClickListener(this);
        btPhone2.setOnClickListener(this);
        mToolbarLeftIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        requestGuarantee();

        GetContactsInfo1 getContactsInfo = new GetContactsInfo1(this);
        //获取通讯录好友列表ContactsInfoList
        contactsInfoList = getContactsInfo.getLocalContactsInfos();
    }


    // 认证
    private AuthenModel authenModel;

    @Override
    public void onClick(View v) {
        if (authenModel == null) {
            showMiddleToast("数据异常，请重新进入。");
            finish();
            return;
        } else if (authenModel.getIsID2() == 0) {
            FirstCerActivity_.intent(mContext).tag(2).startForResult(100);
            return;
        }
        switch (v.getId()) {
            //  个人认证
            case R.id.btId1:
                if (authenModel.getIsID() == 1) {
                    return;
                }
                if (authenModel.getIsID() == 0)
                    IDPhotoActivity_.intent(mContext).type(0).startForResult(100);
                break;
            case R.id.btContent:
                if (authenModel.getIsContact() == 1) {
                    return;
                }

                if (authenModel.getIsID() == 1) {
                    checkPermission();
                } else {
                    showButtomToast("请先认证上一步");
                }
                break;
            case R.id.btZM:
                if (authenModel.getIsZM() == 1) {
                    return;
                }
                if (authenModel.getIsContact() == 1) {
                    ZMActivity_.intent(mContext).startForResult(100);
                } else {
                    showButtomToast("请先认证上一步");
                }
                break;
            case R.id.btPhone1:
                if (authenModel.getIsMobile() == 1) {
                    return;
                }
                if (authenModel.getIsZM() == 1) {
                    if (contactsInfoList == null) {
                        showButtomToast("请打开获取手机通讯录权限!");
                        return;
                    }
                    showLoad();
                    updateDirectory();
                } else {
                    showButtomToast("请先认证上一步");
                }
                break;
            //  担保人认证
            case R.id.btId2:
                if (authenModel.getIsId_dbr() == 1) {
                    return;
                }
                if (authenModel.getIsMobile() == 1) {
                    IDPhotoActivity_.intent(mContext).type(1).startForResult(100);
                } else {
                    showButtomToast("请先认证上一步");
                }
                break;
            case R.id.btPhone2:
                if (authenModel.getIsMobile_dbr() == 1) {
                    return;
                }
                if (authenModel.getIsId_dbr() == 1) {
                    requestMobile(1);
                } else {
                    showButtomToast("请先认证上一步");
                }
                break;
        }
    }

    /**
     * 判断权限
     */
    private void checkPermission() {
        String[] permissions = new String[]{Manifest.permission.GET_ACCOUNTS};
        PermissionUtils.requestPermissions(mContext, 310, permissions, new PermissionUtils.OnPermissionListener() {
            @Override
            public void onPermissionGranted() {
                ContactPersonActivity_.intent(mContext).startForResult(100);
            }

            @Override
            public void onPermissionDenied() {
                showButtomToast("请打开获取手机通讯录权限!");
            }
        });
    }

    private LoadDialog loadDialog;

    private void showLoad() {
        if (loadDialog == null) {
            loadDialog = new LoadDialog(this);
        }
        loadDialog.startAnmation();
        loadDialog.show();
    }

    private void updateDirectory() {
        String directory = new Gson().toJson(contactsInfoList);
        APIManager.getInstance().getPhoneDirectory(this, directory, "", "", new APIManager.APIManagerInterface.getContact_back() {
            @Override
            public void Success(Context context, int result, String message) {
                requestMobile(0);
            }

            @Override
            public void Failure(Context context, JSONObject response) {
                loadDialog.dismiss();
            }
        });
    }

    //  isId 身份证认证
    private void initView() {
        // 身份证认证
        if (authenModel.getIsID() == 1) {
            Drawable top = getResources().getDrawable(R.mipmap.list_authentication_2color);
            btId1.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
            tvId1.setText("已认证");
            tvId1.setTextColor(getResources().getColor(R.color.nav_blue));
        }
        // 联系人认证
        if (authenModel.getIsContact() == 1) {
            Drawable top = getResources().getDrawable(R.mipmap.img_lianxiren_selected);
            btContent.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
            tvContent.setText("已认证");
            tvContent.setTextColor(getResources().getColor(R.color.nav_blue));
        }
        // 芝麻信用
        if (authenModel.getIsZM() == 1) {
            Drawable top = getResources().getDrawable(R.mipmap.list_authentication_4color);
            btZM.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
            tvZM.setText("已认证");
            tvZM.setTextColor(getResources().getColor(R.color.nav_blue));
        }
        // 手机号
        if (authenModel.getIsMobile() == 1) {
            Drawable top = getResources().getDrawable(R.mipmap.list_authentication_7color);
            btPhone1.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
            tvPhone1.setText("已认证");
            tvPhone1.setTextColor(getResources().getColor(R.color.nav_blue));
        }
        //  身份证  (担保人)
        if (authenModel.getIsId_dbr() == 1) {
            Drawable top = getResources().getDrawable(R.mipmap.list_authentication_2color);
            btId2.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
            tvId2.setText("已认证");
            tvId2.setTextColor(getResources().getColor(R.color.nav_blue));
        }
        //  手机号  (担保人)
        if (authenModel.getIsMobile_dbr() == 1) {
            Drawable top = getResources().getDrawable(R.mipmap.list_authentication_7color);
            btPhone2.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
            tvPhone2.setText("已认证");
            tvPhone2.setTextColor(getResources().getColor(R.color.nav_blue));
        }
    }

    //获取手机号认证的网页链接  0 个人 1 担保人
    private void requestMobile(int type) {
        APIManager.getInstance().requestMobile(mContext, type, new APIManager.APIManagerInterface.requestMobile() {
            @Override
            public void Success(Context context, String url) {
                loadDialog.dismiss();
                WebActivity_.intent(mContext).url(url).title("手机号认证").startForResult(100);
            }

            @Override
            public void Failure(Context context, String msg) {
                loadDialog.dismiss();
            }
        });
    }

    /**
     * 判断担保人是否显示
     */
    private void requestGuarantee() {
        APIManager.getInstance().requestGuarantee(mContext, new APIManager.APIManagerInterface.requestGuarantee() {
            @Override
            public void Success(Context context, boolean isShow, String guarantee) {
                if (isShow) {
                    tvGuarantee.setText(guarantee);
                    llGuarantee.setVisibility(View.VISIBLE);
                } else {
                    llGuarantee.setVisibility(View.GONE);
                }
            }

            @Override
            public void Failure(Context context, String msg) {

            }
        });
    }

    /**
     * 获取认证状态
     */
    private void requestAuthenStatus() {
        showDialogLoading();
        APIManager.getInstance().home_getAuthenStatus(mContext, 0, new APIManager.APIManagerInterface.common_object() {
            @Override
            public void Success(Context context, Object object) {
                authenModel = (AuthenModel) object;
                hideProgressDialog();
                if (authenModel != null) {
                    initView();
                }
            }

            @Override
            public void Failure(Context context, JSONObject response) {
                hideProgressDialog();
            }
        });
    }

    @OnActivityResult(100)
    void ActivityResult() {

    }

    @Override
    public void onResume() {
        super.onResume();
        requestAuthenStatus();
    }
}

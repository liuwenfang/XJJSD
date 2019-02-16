package com.ahxbapp.xjjsd.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ahxbapp.common.SaveFragmentPagerAdapter;
import com.ahxbapp.common.third.WechatTab;
import com.ahxbapp.common.ui.BaseActivity;
import com.ahxbapp.xjjsd.R;
import com.ahxbapp.xjjsd.fragment.CashOrderFragment;
import com.ahxbapp.xjjsd.fragment.CashOrderFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * 我的借款列表
 */
@EActivity(R.layout.activity_cash_order)
public class OrderListActivity extends BaseActivity {
    @ViewById
    ImageButton mToolbarLeftIB;
    @ViewById
    TextView mToolbarTitleTV;
    @ViewById
    WechatTab mTabs;
    @ViewById
    ViewPager mViewPager;

    String[] fragmentTitles;

    CashOrderFragment fragment;

    @AfterViews
    void init() {
        mToolbarLeftIB.setImageResource(R.mipmap.back);
        mToolbarTitleTV.setText("我的借款");


        fragmentTitles = getResources().getStringArray(R.array.cash_order);
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
        mViewPager.setPageMargin(pageMargin);
        mTabs.setViewPager(mViewPager);
    }

    @Click
    void mToolbarLeftIB() {
        finish();
    }

    class MyPagerAdapter extends SaveFragmentPagerAdapter {

        protected MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            fragment = CashOrderFragment_.builder().build();
            int orderStatus;
            if (position < 2) {
                orderStatus = position - 1;
            } else {
                orderStatus = position + 3;
            }
            fragment.setOrderStatus(orderStatus);
            saveFragment(fragment);
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitles[position];
        }

        @Override
        public int getCount() {
            return fragmentTitles.length;
        }
    }
}

package com.ahxbapp.xjjsd.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.ahxbapp.xjjsd.R;
import com.ahxbapp.xjjsd.adapter.common.CommonAdapter;
import com.ahxbapp.xjjsd.adapter.common.ViewHolder;
import com.ahxbapp.xjjsd.model.CouponCashModel;

import java.util.List;

/**
 * Created by Admin on 2016/10/25.
 * Page
 */
public class CashCouponAdapter extends CommonAdapter<CouponCashModel> {

    public CashCouponAdapter(Context context, List<CouponCashModel> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, CouponCashModel couponCashModel) {
        holder.setText(R.id.mMoneyTV, couponCashModel.getMoney() + "");
        holder.setText(R.id.mFullmoneyTV, couponCashModel.getFullmoney() + "");
        holder.setText(R.id.mExplainTV, couponCashModel.getDes());
        holder.setText(R.id.mDayTV, couponCashModel.getEndTime());
        View viewBg = holder.getView(R.id.mCouponView);
        TextView overTV = holder.getView(R.id.mOverTV);
        if (couponCashModel.getIsOver() == 1) {
            viewBg.setBackground(context.getResources().getDrawable(R.drawable.coupon_bg1));
            overTV.setVisibility(View.VISIBLE);
        } else {
            overTV.setVisibility(View.GONE);
            viewBg.setBackground(context.getResources().getDrawable(R.drawable.coupon_bg));
        }
    }
}

package com.ahxbapp.xjjsd.adapter;

import android.content.Context;

import com.ahxbapp.xjjsd.R;
import com.ahxbapp.xjjsd.adapter.common.CommonAdapter;
import com.ahxbapp.xjjsd.adapter.common.ViewHolder;
import com.ahxbapp.xjjsd.model.CouponModel;

import java.util.List;

/**
 * Created by xp on 16/9/6.
 */
public class CouponAdapter extends CommonAdapter<CouponModel> {
    public CouponAdapter(Context context, List<CouponModel> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, CouponModel couponModel) {
        holder.setText(R.id.tv_money,"￥"+couponModel.getMoney());
        holder.setText(R.id.tv_limit,"满"+couponModel.getFullmoney()+"元可用");
        holder.setText(R.id.tv_title,"现金券");
        holder.setText(R.id.tv_desc,couponModel.getDes());
        holder.setText(R.id.tv_date,"有效期至: "+couponModel.getEndTime());
    }
}

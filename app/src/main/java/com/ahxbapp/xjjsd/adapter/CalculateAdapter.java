package com.ahxbapp.xjjsd.adapter;

import android.content.Context;

import com.ahxbapp.xjjsd.R;
import com.ahxbapp.xjjsd.adapter.common.CommonAdapter;
import com.ahxbapp.xjjsd.adapter.common.ViewHolder;
import com.ahxbapp.xjjsd.model.CartModel;

import java.util.List;

/**
 * Created by xp on 16/9/5.
 */
public class CalculateAdapter extends CommonAdapter<CartModel> {
    public CalculateAdapter(Context context, List<CartModel> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, CartModel cartModel) {
        holder.setImageUrl(R.id.img_cover, cartModel.getThumbnail());
        holder.setText(R.id.tv_name, cartModel.getTitle());
        holder.setText(R.id.tv_num,Integer.toString(cartModel.getNum()));
        holder.setText(R.id.tv_size,cartModel.getSize());
        holder.setText(R.id.tv_price,"￥"+Float.toString(cartModel.getPrice()));
    }
}

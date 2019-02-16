package com.ahxbapp.xjjsd.adapter;

import android.content.Context;

import com.ahxbapp.xjjsd.R;
import com.ahxbapp.xjjsd.adapter.common.CommonAdapter;
import com.ahxbapp.xjjsd.adapter.common.ViewHolder;
import com.ahxbapp.xjjsd.model.DesignModel;

import java.util.List;

/**
 * Created by xp on 16/8/29.
 */
public class DesignAdapter extends CommonAdapter<DesignModel> {
    ViewHolder.ViewHolderInterface.common_click click_block1,click_block2,click_block3;
    public DesignAdapter(Context context, List<DesignModel> datas, int layoutId,ViewHolder.ViewHolderInterface.common_click block1,ViewHolder.ViewHolderInterface.common_click block2,ViewHolder.ViewHolderInterface.common_click block3) {
        super(context, datas, layoutId);
        click_block1 = block1;
        click_block2 = block2;
        click_block3 = block3;
    }

    @Override
    public void convert(ViewHolder holder, DesignModel designModel) {
        holder.setImageUrl(R.id.img_cover,designModel.getThumbnail());
        holder.setText(R.id.txt_name, designModel.getTitle());
        holder.setText(R.id.txt_date,designModel.getAddDate());
        holder.clickButton(R.id.btn_edit, click_block1);
        holder.clickButton(R.id.btn_delete,click_block2);
        holder.clickButton(R.id.btn_addCart,click_block3);
    }
}

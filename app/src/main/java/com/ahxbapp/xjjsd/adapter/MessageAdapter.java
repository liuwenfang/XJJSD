package com.ahxbapp.xjjsd.adapter;

import android.content.Context;

import com.ahxbapp.xjjsd.R;
import com.ahxbapp.xjjsd.adapter.common.CommonAdapter;
import com.ahxbapp.xjjsd.adapter.common.ViewHolder;
import com.ahxbapp.xjjsd.model.MessageModel;

import java.util.List;

/**
 * Created by xp on 16/8/30.
 */
public class MessageAdapter extends CommonAdapter<MessageModel> {

    ViewHolder.ViewHolderInterface.common_click checkClick;
    public MessageAdapter(Context context, List<MessageModel> datas, int layoutId) {
        super(context, datas, layoutId);

    }

    @Override
    public void convert(ViewHolder holder, MessageModel messageModel) {

        if (messageModel.getIsRead()==0){
            holder.setBackGround(R.id.tv_title,context.getResources().getDrawable(R.drawable.shape_top_30));
        }else {
            holder.setBackGround(R.id.tv_title,context.getResources().getDrawable(R.drawable.shape_gray_5));
        }
        holder.setText(R.id.tv_title,messageModel.getTitle());
        holder.setText(R.id.tv_date, messageModel.getAddTime());
        holder.setText(R.id.tv_content, messageModel.getContent());

    }
}

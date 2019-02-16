package com.ahxbapp.xjjsd.adapter;

import android.content.Context;

import com.ahxbapp.xjjsd.R;
import com.ahxbapp.xjjsd.adapter.common.CommonAdapter;
import com.ahxbapp.xjjsd.adapter.common.ViewHolder;
import com.ahxbapp.xjjsd.model.SendMethod;

import java.util.List;

/**
 * Created by xp on 16/9/6.
 */
public class SendAdapter extends CommonAdapter<SendMethod> {
    public SendAdapter(Context context, List<SendMethod> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, SendMethod sendMethod) {
        holder.setText(R.id.send_id,sendMethod.getName());
    }
}

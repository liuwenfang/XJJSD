package com.ahxbapp.xjjsd.adapter;

import android.content.Context;

import com.ahxbapp.xjjsd.R;
import com.ahxbapp.xjjsd.adapter.common.CommonAdapter;
import com.ahxbapp.xjjsd.adapter.common.ViewHolder;
import com.ahxbapp.xjjsd.model.ContactsInfo;

import java.util.List;

/**
 * Created by zzx on 2017/9/2 0002.
 */

public class MailAdapter extends CommonAdapter<ContactsInfo> {

    public MailAdapter(Context context, List<ContactsInfo> datas) {
        super(context, datas, R.layout.layout_mail);
    }

    @Override
    public void convert(ViewHolder holder, ContactsInfo contactsInfo) {
        holder.setText(R.id.tvMainAdapter, contactsInfo.getName() + "   " + contactsInfo.getPhone());
    }
}

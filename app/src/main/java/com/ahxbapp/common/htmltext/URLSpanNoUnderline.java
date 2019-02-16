package com.ahxbapp.common.htmltext;

import android.content.Context;
import android.text.TextPaint;
import android.text.style.URLSpan;
import android.view.View;

import com.ahxbapp.common.Global;

/**
 * Created by gravel on 15/1/12.
 * 用来解析 url 以跳转到不同的界面
 */
public class URLSpanNoUnderline extends URLSpan {

    public static final String PATTERN_URL_MESSAGE = "^(?:https://[\\w.]*)?/user/messages/history/([\\w-]+)$";
    private int color;

    public URLSpanNoUnderline(String url, int color) {
        super(url);
        this.color = color;
    }

    public static String createMessageUrl(String globalKey) {
        return Global.HOST + "/user/messages/history/" + globalKey;
    }

    public static void openActivityByUri(Context context, String uriString, boolean newTask) {
        openActivityByUri(context, uriString, newTask, true);
    }


    public static boolean openActivityByUri(Context context, String uriString, boolean newTask, boolean defaultIntent) {
        return openActivityByUri(context, uriString, newTask, defaultIntent, false);
    }

    public static boolean openActivityByUri(Context context, String uri, boolean newTask, boolean defaultIntent, boolean share) {


        return false;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setUnderlineText(false);
        ds.setColor(color);
    }

    public static String generateAbsolute(String jumpUrl) {
        if (jumpUrl == null) {
            return "";
        }

        String url = jumpUrl.replace("/u/", "/user/")
                .replace("/p/", "/project/");

        if (url.startsWith("/")) {
            url = Global.HOST_API + url;
        } else if (url.startsWith(Global.HOST) && !url.startsWith(Global.HOST_API)) {
            url = url.replace(Global.HOST, Global.HOST_API);
        }

        return url;
    }

    @Override
    public void onClick(View widget) {
        openActivityByUri(widget.getContext(), getURL(), false);
    }
}

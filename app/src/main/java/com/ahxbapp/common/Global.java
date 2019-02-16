package com.ahxbapp.common;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.text.style.QuoteSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;

import com.ahxbapp.common.htmltext.GrayQuoteSpan;
import com.ahxbapp.common.htmltext.URLSpanNoUnderline;
import com.ahxbapp.xjjsd.application.MyApp;
import com.ahxbapp.xjjsd.utils.PrefsUtil;
import com.readystatesoftware.viewbadger.BadgeView;

import org.json.JSONObject;
import org.xml.sax.XMLReader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by gravel.
 * 放一些公共的全局方法
 */
public class Global {
    public static final String aliPayHtml = "http://apk910okinfo.910ok.com/" + "pay.html?prepay_id=";
//        public static final String aliPayHtml = "http://192.168.1.109:20504/" + "pay.html?prepay_id=";
    public static final String aliPayResultHtml = "http://apk910okinfo.910ok.com/" + "successpay.html?ordernumber=";
//        public static final String aliPayResultHtml = "http://192.168.1.109:20504/" + "successpay.html?ordernumber=";
//    public static final String DEFAULT_HOST = "http://192.168.1.109:20504/";
//    public static final String DEFAULT_HOST = "http://ts.910ok.com/";//测试服
    public static final String DEFAULT_HOST = "http://apk910okinfo.910ok.com/";//正式服
    public static final String DEFAULT_IMAGE_HOST = "http://img.910ok.com//";
    public static final SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd EEE");
    public static final SimpleDateFormat mDateYMDHH = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private static final String IMAGE_URL_SCAL = "%s?imageMogr2/thumbnail/!%s";
    private static final SimpleDateFormat sFormatToday = new SimpleDateFormat("今天 HH:mm");
    private static final SimpleDateFormat sFormatThisYear = new SimpleDateFormat("MM/dd HH:mm");
    private static final SimpleDateFormat sFormatOtherYear = new SimpleDateFormat("yy/MM/dd HH:mm");
    private static final SimpleDateFormat sFormatMessageToday = new SimpleDateFormat("今天");
    private static final SimpleDateFormat sFormatMessageThisYear = new SimpleDateFormat("MM/dd");
    private static final SimpleDateFormat sFormatMessageOtherYear = new SimpleDateFormat("yy/MM/dd");
    public static String HOST = DEFAULT_HOST;
    public static String HOST_API = HOST;
    public static String TOKEN = "token";

    /**
     * 语音文件存放目录
     */
    public static String sVoiceDir;
    public static SimpleDateFormat DateFormatTime = new SimpleDateFormat("HH:mm");
    public static SimpleDateFormat MonthDayFormatTime = new SimpleDateFormat("MMMdd日");
    public static SimpleDateFormat WeekFormatTime = new SimpleDateFormat("EEE");
    public static SimpleDateFormat NextWeekFormatTime = new SimpleDateFormat("下EEE");
    public static SimpleDateFormat LastWeekFormatTime = new SimpleDateFormat("上EEE");
    public static Html.TagHandler tagHandler = new Html.TagHandler() {
        @Override
        public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
            if (tag.toLowerCase().equals("code") && !opening) {
                output.append("\n\n");
            }
        }
    };
    public static DecimalFormat df = new DecimalFormat("#.00");
    private static SimpleDateFormat DayFormatTime = new SimpleDateFormat("yyyy-MM-dd");

    public static String dayFromTime(long time) {
        return DayFormatTime.format(time);
    }

    public static long longFromDay(String day) throws ParseException {
        final String format = "yyyy-MM-dd";
        final SimpleDateFormat sd = new SimpleDateFormat(format);
        return sd.parse(day).getTime();
    }

    public static boolean isEmptyContainSpace(EditText edit) {
        return edit.getText().toString().replace(" ", "").replace("　", "").isEmpty();
    }

    public static String dayCount(long time) {
        return DayFormatTime.format(time);
    }

    public static void errorLog(Exception e) {
        if (e == null) {
            return;
        }

        e.printStackTrace();
        Log.e("", "" + e);
    }

    public static String encodeInput(String at, String input) {
        if (at == null || at.isEmpty()) {
            return input;
        }

        return String.format("@%s %s", at, input);
    }

    public static String encodeUtf8(String s) {
        try {
            return URLEncoder.encode(s, "utf-8");
        } catch (Exception e) {
            return "";
        }
    }

    public static String decodeUtf8(String s) {
        try {
            return URLDecoder.decode(s, "utf-8");
        } catch (Exception e) {
            return "";
        }
    }

    public static boolean isImageUri(String s1) {
        s1 = s1.toLowerCase();
        return s1.endsWith(".png")
                || s1.endsWith(".jpg")
                || s1.endsWith(".jpeg")
                || s1.endsWith(".bmp")
                || s1.endsWith(".gif");
    }

    public static void syncCookie(Context context) {


    }

    public static void copy(Context context, String content) {
//        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
//        String url = HtmlContent.parseReplaceHtml(content);
//        cmb.setText(url);url
    }

    public static String replaceAvatar(JSONObject json) {
        return replaceHeadUrl(json, "avatar");
    }

    public static String getErrorMsg(JSONObject jsonObject) {
        String s = "";
        try {
            s = jsonObject.getString("message");
        } catch (Exception e) {
            Global.errorLog(e);
        }

        return s;
    }

    // 用于头像，有些头像是 “/static/fruit_avatar/Fruit-12.png”
    public static String replaceHeadUrl(JSONObject json, String name) {
        String s = json.optString(name);
        return translateStaticIcon(s);
    }

    private static String translateStaticIcon(String s) {
        if (s.indexOf("/static/") == 0) {
            return Global.HOST + s;
        }

        return s;
    }

    public static void popSoftkeyboard(Context ctx, View view, boolean wantPop) {
        InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (wantPop) {
            view.requestFocus();
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        } else {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static String makeSmallUrl(ImageView view, String url) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        int max = Math.max(lp.height, lp.width);
        if (max > 0) {
            return makeSmallUrlSquare(url, max);
        }

        return url;
    }

    public static boolean canCrop(String url) {
        return url.startsWith("http") && (!url.contains("/thumbnail/"));
    }

    public static String makeSmallUrlSquare(String url, int widthPix) {
        if (canCrop(url)) {
            String parma = "";
            if (!url.contains("?imageMogr2/")) {
                parma = "?imageMogr2/";
            }
            return String.format("%s%s/!%dx%d", url, parma, widthPix, widthPix);
        } else {
            return url;
        }
    }

    public static String makeLargeUrl(String url) {
        final int MAX = 4096; // ImageView显示的图片不能大于这个数
        return String.format(IMAGE_URL_SCAL, url, MAX);
    }

    private static String intToString(int length) {
        String width;
        if (length > 0) {
            width = String.valueOf(length);
        } else {
            width = "";
        }

        return width;
    }

    public static int dpToPx(int dpValue) {
        return (int) (dpValue * MyApp.sScale + 0.5f);
    }

    public static int dpToPx(double dpValue) {
        return (int) (dpValue * MyApp.sScale + 0.5f);
    }

    public static int pxToDp(float pxValue) {
        return (int) (pxValue / MyApp.sScale + 0.5f);
    }

    public static Spannable changeHyperlinkColor(String content) {
        return Global.changeHyperlinkColor(content, null, null);
    }

    public static Spannable changeHyperlinkColor(String content, int linkColor) {
        return changeHyperlinkColor(content, null, tagHandler, linkColor);
    }

    public static Spannable changeHyperlinkColor(String content, int color, MyImageGetter imageGetter) {
        return Global.changeHyperlinkColor(content, imageGetter, null);
    }

    public static Spannable changeHyperlinkColor(String content, Html.ImageGetter imageGetter, Html.TagHandler tagHandler) {
        return changeHyperlinkColor(content, imageGetter, tagHandler, 0xFF3BBD79);
    }


    public static Spannable changeHyperlinkColorMaopao(String content, Html.ImageGetter imageGetter, Html.TagHandler tagHandler, AssetManager assetManager) {
        Spannable s = changeHyperlinkColor(content, imageGetter, tagHandler, 0xFF3BBD79);
        return spannToGif(s, assetManager);
    }

    public static Spannable changeHyperlinkColor(String content, Html.ImageGetter imageGetter, Html.TagHandler tagHandler, int color) {
        Spannable s = (Spannable) Html.fromHtml(content, imageGetter, tagHandler);
        return getCustomSpannable(color, s);
    }

    public static Spannable recentMessage(String content, Html.ImageGetter imageGetter, Html.TagHandler tagHandler) {
        String parse = HtmlContent.parseToText(content);

        Spannable s = (Spannable) Html.fromHtml(parse, imageGetter, null);
        return getCustomSpannable(0xff999999, s);
    }

    static public void cropImageUri(StartActivity activity, Uri uri, Uri outputUri, int outputX, int outputY, int requestCode) {
        try {
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(uri, "image/*");
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputX", outputX);
            intent.putExtra("outputY", outputY);
            intent.putExtra("scale", true);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
            intent.putExtra("return-data", false);
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            intent.putExtra("noFaceDetection", true);
            activity.startActivityForResult(intent, requestCode);
        } catch (Exception e) {
            Global.errorLog(e);
        }
    }

    private static Spannable getCustomSpannable(int color, Spannable s) {
        URLSpan[] urlSpan = s.getSpans(0, s.length(), URLSpan.class);
        for (URLSpan span : urlSpan) {
            int start = s.getSpanStart(span);
            int end = s.getSpanEnd(span);
            s.removeSpan(span);
            span = new URLSpanNoUnderline(span.getURL(), color);
            s.setSpan(span, start, end, 0);
        }

        QuoteSpan quoteSpans[] = s.getSpans(0, s.length(), QuoteSpan.class);
        for (QuoteSpan span : quoteSpans) {
            int start = s.getSpanStart(span);
            int end = s.getSpanEnd(span);
            s.removeSpan(span);
            GrayQuoteSpan grayQuoteSpan = new GrayQuoteSpan();
            s.setSpan(grayQuoteSpan, start, end, 0);
        }

        return s;
    }

    private static Spannable spannToGif(Spannable s, AssetManager assetManager) {
        ImageSpan[] imageSpans = s.getSpans(0, s.length(), ImageSpan.class);

        final String[] gifEmojiName = new String[]{
                "festival-emoji-01.gif",
                "festival-emoji-02.gif",
                "festival-emoji-03.gif",
                "festival-emoji-04.gif",
                "festival-emoji-05.gif",
                "festival-emoji-06.gif",
                "festival-emoji-07.gif",
                "festival-emoji-08.gif",
        };

        for (ImageSpan imageSpan : imageSpans) {
            int start = s.getSpanStart(imageSpan);
            int end = s.getSpanEnd(imageSpan);

            String imageSource = imageSpan.getSource();
            for (String endString : gifEmojiName) {
                if (imageSource.endsWith(endString)) {
                    try {
//                        GifDrawable gifDrawable = new GifDrawable(assetManager, endString);
//                        DrawableTool.zoomDrwable(gifDrawable, true);
//                        gifDrawable.setLoopCount(100);
//                        GifImageSpan gifImageSpan = new GifImageSpan(gifDrawable);
//                        s.removeSpan(imageSpan);
//                        s.setSpan(gifImageSpan, start, end, 0);
                    } catch (Exception e) {
                        Global.errorLog(e);
                    }
                }
            }
        }

        return s;
    }

    static public String readTextFile(Context context, String assetFile) throws IOException {
        InputStream inputStream = context.getAssets().open(assetFile);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte buf[] = new byte[1024];
        int len;
        while ((len = inputStream.read(buf)) != -1) {
            outputStream.write(buf, 0, len);
        }
        outputStream.close();
        inputStream.close();

        return outputStream.toString();
    }

    static public String readTextFile(File file) {
        try {
            FileInputStream is = new FileInputStream(file);
            return readTextFile(is);
        } catch (Exception e) {
            Global.errorLog(e);
        }

        return "";
    }

    static public String readTextFile(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte buf[] = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();

        } catch (IOException e) {
            Global.errorLog(e);
        }

        return outputStream.toString();
    }

    public static void initWebView(WebView webView) {
        webView.getSettings().setJavaScriptEnabled(true);

        // 防止webview滚动时背景变成黑色
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            webView.setBackgroundColor(0x00000000);
        } else {
            webView.setBackgroundColor(Color.argb(1, 0, 0, 0));
        }

        webView.getSettings().setDefaultTextEncodingName("UTF-8");
    }

    static public void setWebViewContent(WebView webView, String tempate, String content) {
        setWebViewContent(webView, tempate, "${webview_content}", content);
    }

    static public void setWebViewContent(WebView webView, String tempate, String replaceString, String content) {
//        Context context = webView.getContext();
//        Global.initWebView(webView);
//        webView.setWebViewClient(new MaopaoDetailActivity.CustomWebViewClient(context, content));
//        try {
//            syncCookie(webView.getContext());
//            String bubble = readTextFile(context.getAssets().open(tempate));
//            webView.loadDataWithBaseURL(Global.HOST, bubble.replace(replaceString, content), "text/html", "UTF-8", null);
//        } catch (Exception e) {
//            Global.errorLog(e);
//        }
    }

    static public void setWebViewContent(WebView webView, String tempate, String replaceString,
                                         String content, String replaceComment, String comment) {
//        Context context = webView.getContext();
//        Global.initWebView(webView);
//        webView.setWebViewClient(new MaopaoDetailActivity.CustomWebViewClient(context, content));
//        try {
//            syncCookie(webView.getContext());
//            String bubble = readTextFile(context.getAssets().open(tempate));
//            webView.loadDataWithBaseURL(Global.HOST, bubble.replace(replaceString, content).replace(replaceComment, comment), "text/html", "UTF-8", null);
//        } catch (Exception e) {
//            Global.errorLog(e);
//        }
    }

    public static boolean isGif(String uri) {
        return uri.toLowerCase().endsWith(".gif");
    }

    // 通过文件头来判断是否gif
    public static boolean isGifByFile(File file) {
        try {
            int length = 10;
            InputStream is = new FileInputStream(file);
            byte[] data = new byte[length];
            is.read(data);
            String type = getType(data);
            is.close();

            if (type.equals("gif")) {
                return true;
            }
        } catch (Exception e) {
            Global.errorLog(e);
        }

        return false;
    }

    private static String getType(byte[] data) {
        String type = "";
        if (data[1] == 'P' && data[2] == 'N' && data[3] == 'G') {
            type = "png";
        } else if (data[0] == 'G' && data[1] == 'I' && data[2] == 'F') {
            type = "gif";
        } else if (data[6] == 'J' && data[7] == 'F' && data[8] == 'I'
                && data[9] == 'F') {
            type = "jpg";
        }
        return type;
    }

    private static String getDay(long time, boolean showToday) {
        Calendar calendarToday = Calendar.getInstance();
        calendarToday.set(calendarToday.get(Calendar.YEAR), calendarToday.get(Calendar.MONTH), calendarToday.get(Calendar.DAY_OF_MONTH), 0, 0, 0);

        final long oneDay = 1000 * 3600 * 24;
        long today = calendarToday.getTimeInMillis();
        long tomorrow = today + oneDay;
        long tomorrowNext = tomorrow + oneDay;
        long tomorrowNextNext = tomorrowNext + oneDay;
        long yesterday = today - oneDay;
        long lastYesterday = yesterday - oneDay;

        if (time >= today) {
            if (tomorrow > time) {
                if (showToday) {
                    return "今天";
                } else {
                    return "";
                }
            } else if (tomorrowNext > time) {
                return "明天";
            } else if (tomorrowNextNext > time) {
                return "后天";
            }
        } else {
            if (time > yesterday) {
                return "昨天";
            } else if (time > lastYesterday) {
                return "前天";
            }
        }

        return null;
    }

    private static String getWeek(long time) {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        today.set(Calendar.HOUR, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        final long oneWeek = 1000 * 60 * 60 * 24 * 7;

        long weekBegin = today.getTimeInMillis();
        long nextWeekBegin = weekBegin + oneWeek;
        long nextnextWeekBegin = nextWeekBegin + oneWeek;
        long lastWeekBegin = weekBegin - oneWeek;

        if (time >= weekBegin) {
            if (nextWeekBegin > time) {
                return WeekFormatTime.format(time);
            } else if (nextnextWeekBegin > time) {
                return NextWeekFormatTime.format(time);
            }
        } else {
            if (time > lastWeekBegin) {
                return LastWeekFormatTime.format(time);
            }
        }
        return null;
    }

    public static String getDataDetail(long timeInMillis) {
        String dataString = getDay(timeInMillis, true);
        if (dataString == null) {
            dataString = getWeek(timeInMillis);
            if (dataString == null) {
                dataString = MonthDayFormatTime.format(timeInMillis);
            }
        }
        return dataString;
    }

    public static String getTimeDetail(long timeInMillis) {
//        String dataString = getDay(timeInMillis, false);
//        if (dataString == null) {
//            dataString = getWeek(timeInMillis);
//            if (dataString == null) {
//                dataString = MonthDayFormatTime.format(timeInMillis);
//            }
//        }
//
//        return String.format("%s %s", dataString, DateFormatTime.format(new Date(timeInMillis)));
        return dayToNow(timeInMillis, true);
    }

    public static String dayToNowCreate(long time) {
        return "创建于 " + Global.dayToNow(time);
    }

    public static String dayToNow(long time) {
        return dayToNow(time, true);
    }

    public static String dayToNow(long time, boolean displayHour) {
        long nowMill = System.currentTimeMillis();

        long minute = (nowMill - time) / 60000;
        if (minute < 60) {
            if (minute <= 0) {
                return Math.max((nowMill - time) / 1000, 1) + "秒前"; // 由于手机时间的原因，有时候会为负，这时候显示1秒前
            } else {
                return minute + "分钟前";
            }
        }

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(time);
        int year = calendar.get(GregorianCalendar.YEAR);
        int month = calendar.get(GregorianCalendar.MONTH);
        int day = calendar.get(GregorianCalendar.DAY_OF_MONTH);

        calendar.setTimeInMillis(nowMill);
        Long timeObject = new Long(time);
        if (calendar.get(GregorianCalendar.YEAR) != year) { // 不是今年
            SimpleDateFormat sFormatOtherYear = displayHour ? Global.sFormatOtherYear : Global.sFormatMessageOtherYear;
            return sFormatOtherYear.format(timeObject);
        } else if (calendar.get(GregorianCalendar.MONTH) != month
                || calendar.get(GregorianCalendar.DAY_OF_MONTH) != day) { // 今年
            SimpleDateFormat sFormatThisYear = displayHour ? Global.sFormatThisYear : Global.sFormatMessageThisYear;
            return sFormatThisYear.format(timeObject);
        } else { // 今天
            SimpleDateFormat sFormatToday = displayHour ? Global.sFormatToday : Global.sFormatMessageToday;
            return sFormatToday.format(timeObject);
        }
    }

    public static String dayToNow(long time, String template) {
        return String.format(template, dayToNow(time));
    }

    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activityNetwork = mConnectivityManager.getActiveNetworkInfo();
            return activityNetwork != null && activityNetwork.getType() == ConnectivityManager.TYPE_WIFI;
        }
        return false;
    }

    public static boolean isConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 显示文件大小,保留两位
     */
    public static String HumanReadableFilesize(double size) {
        String[] units = new String[]{"B", "KB", "MB", "GB", "TB", "PB"};
        double mod = 1024.0;
        int i = 0;
        while (size >= mod) {
            size /= mod;
            i++;
        }
        //return Math.round(size) + units[i];
        return df.format(size) + " " + units[i];
    }

    public static void setBadgeView(BadgeView badge, int count) {
        if (count > 0) {
            String countString = count > 99 ? "99+" : ("" + count);
            badge.setText(countString);
            badge.setVisibility(View.VISIBLE);
        } else {
            badge.setVisibility(View.INVISIBLE);
        }
    }

    public static void start2FAActivity(Activity activity) {
        Intent intent;
//        if (AccountInfo.loadAuthDatas(activity).isEmpty()) {
//            intent = new Intent(activity, Login2FATipActivity.class);
//        } else {
//            intent = new Intent(activity, AuthListActivity.class);
//        }
//
//        activity.startActivity(intent);
    }

    public static String fmtMoney(Object obj) {
        if (obj == null) {
            return "--";
        }
        String str = obj + "";
        str = str.replaceAll("(\\.[^0]{0,2}).*", "$1").replaceAll("\\.$", "");
        return str;
    }

    public static String fmtDistance(Object obj) {
        Double dou = 0.00;
        if (obj != null)
            dou = Double.valueOf((Double) obj);
        if (dou < 100) {
            return "100m内";
        } else if (dou >= 100 && dou < 1000) {
            return fmtMoney(obj) + "m";
        } else {
            return fmtMoney(dou / 1000) + "km";
        }
    }

    public static String getImgUrlWithWidth(String str, float width) {
        if (str == null) {
            return "";
        }
        width = width * 2;
        width = (width % 50 == 0 ? width / 50 : width / 50 + 1) * 50;
        String host = str.replaceAll("(http://)([^/]*)/.*", "$1$2");
        String url = str.replaceAll("(http://)([^/]*)/(.*)", "$3");
        return host + "/XBAPP/" + url + "?w=" + width;
    }


    public static void amotView(View v) {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(v, "scaleX", 1, 1.1f, 1, 1.05f, 1),
                ObjectAnimator.ofFloat(v, "scaleY", 1, 1.1f, 1, 1.05f, 1)
        );
        set.setDuration(500).start();
    }

    public static String fmtPhone(Object obj) {
        if (obj == null) {
            return "--";
        }
        String str = obj + "";
        str = str.replaceAll("^(\\d{3}).*(\\d{4})$", "$1****$2");
        return str;
    }

    public static class MessageParse {
        public String text = "";
        public ArrayList<String> uris = new ArrayList<>();
        public boolean isVoice;
        public String voiceUrl;
        public int voiceDuration;
        public int played;
        public int id;

        public String toString() {
            String s = "text " + text + "\n";
            for (int i = 0; i < uris.size(); ++i) {
                s += uris.get(i) + "\n";
            }
            return s;
        }
    }

    public static boolean isEmail(String email) {
        if (null == email || "".equals(email)) return false;
        //Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }
}

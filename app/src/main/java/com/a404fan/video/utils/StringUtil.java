package com.a404fan.video.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.Spanned;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 林墨
 * @time 18/12/14  20:17
 * @desc 字符串工具类
 */
public class StringUtil {

    /**
     * 安全设置TextView文本
     * @param textView
     * @param text
     * @param defText
     */
    public static void safeSetText(TextView textView, String text, String defText){
        if(!TextUtils.isEmpty(text)  && !"null".equals(text)){
            textView.setText(text);
        }else {
            if(!isNotNull(defText)){
                defText = "";
            }
            textView.setText(defText);
        }
    }

    public static void safeSetText(TextView textView, Spanned html, String defText){
        if(!TextUtils.isEmpty(html)  && !"null".equals(html)){
            textView.setText(html);
        }else {
            if(!isNotNull(defText)){
                defText = "";
            }
            textView.setText(defText);
        }
    }

    /**
     * 安全设置TextView文本
     * @param textView
     * @param text
     */
    public static void safeSetText(TextView textView, String text){
        safeSetText(textView, text, "");
    }


    /**
     * 当String为null,"" ,"null" 时，返回false
     * @param destStr
     * @return
     */
    public static boolean isNotNull(String destStr ){
        return !TextUtils.isEmpty(destStr) && !"null".equals(destStr);
    }


    /**
     *  将字符串以指定字符编码进行URL编码
     * @param str
     * @param encode
     * @return
     */
    public static String urlEncoder(String str, String encode){
        try {
            return URLEncoder.encode(str, encode);
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 从字符串中提取数字
     * @param text
     * @return
     */
    public static int extractNum(String text){
        return Integer.valueOf(extractNumStr(text.trim()));
    }

    public static String extractNumStr(String text){
        text=text.trim();
        StringBuilder str2= new StringBuilder();
        if(!"".equals(text)) {
            for (int i = 0; i < text.length(); i++) {
                if (text.charAt(i) >= 48 && text.charAt(i) <= 57) {
                    str2.append(text.charAt(i));
                }
            }
        }
        return str2.toString();
    }

    /**
     * 获取所有满足正则表达式的字符串
     * @param str 需要被获取的字符串
     * @param regex 正则表达式
     * @return 所有满足正则表达式的字符串
     */
    public static List<String> getAllSatisfyStr(String str, String regex) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        List<String> allSatisfyStr = new ArrayList<>();
        if (regex == null || regex.isEmpty()) {
            allSatisfyStr.add(str);
            return allSatisfyStr;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            allSatisfyStr.add(matcher.group());
        }
        return allSatisfyStr;
    }

    public static String unicodeDecode(String unicodeStr) {
        if (unicodeStr == null) {
            return null;
        }
        StringBuffer retBuf = new StringBuffer();
        int maxLoop = unicodeStr.length();
        for (int i = 0; i < maxLoop; i++) {
            if (unicodeStr.charAt(i) == '\\') {
                if ((i < maxLoop - 5) && ((unicodeStr.charAt(i + 1) == 'u') || (unicodeStr.charAt(i + 1) == 'U')))
                    try {
                        retBuf.append((char) Integer.parseInt(unicodeStr.substring(i + 2, i + 6), 16));
                        i += 5;
                    } catch (NumberFormatException localNumberFormatException) {
                        retBuf.append(unicodeStr.charAt(i));
                    }
                else
                    retBuf.append(unicodeStr.charAt(i));
            } else {
                retBuf.append(unicodeStr.charAt(i));
            }
        }
        return retBuf.toString();
    }

    /**
     * 复制文本到粘贴板
     * @param context       上下文
     * @param text          文本内容
     */
    public static void copyToClipboard(Context context, String text){
        if(context == null){
            return;
        }
        if(!isNotNull(text)){
            Toast.makeText(context, "复制文本不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if(cm == null){
            Toast.makeText(context, "复制失败", Toast.LENGTH_SHORT).show();
            return;
        }
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", "" + text);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
        Toast.makeText(context, "已复制到粘贴板", Toast.LENGTH_SHORT).show();
    }
}

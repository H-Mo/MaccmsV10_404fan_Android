package com.a404fan.video.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.PhoneNumberUtils;
import android.view.inputmethod.InputMethodManager;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author 林墨
 * @time 17/6/13  14:51
 * @desc 应用相关的工具方法
 */
public class AppUtils {

    /**
     * 调用拨号界面
     * @param phone 电话号码
     */
    public static void call(String phone, Context context) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    /**
     * 调起系统发短信功能
     * @param phoneNumber
     * @param message
     */
    public static void doSendSMSTo(String phoneNumber,String message, Context context){
        if(PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)){
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"+phoneNumber));
            intent.putExtra("sms_body", message);
            context.startActivity(intent);
        }
    }


    /**
     * 打开QQ聊天
     * @param context   上下文
     * @param qq        QQ号码(需开通QQ推广功能)
     */
    public static void qqTalk(Context context, String qq){
        String qqUrl = "mqqwpa://im/chat?chat_type=wpa&uin=" + qq + "&version=1";
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(qqUrl)));
    }

    /**
     * 隐藏软键盘
     * @param activity  活动界面
     */
    public static void closeSoftInput(Activity activity){
        if(activity == null){
            return;
        }
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm != null){
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0) ;
        }
    }


    public static String getValue(String value){
        return getVale(value, "");
    }

    public static String getVale(String value, String def){
        if(value == null || "".equals(value) || "null".equals(value) || "NULL".equals(value)){
            return def;
        }
        return value;
    }


    /**
     * 保留小数点后两位
     * @param d     浮点数
     * @return
     */
    public static String getDoubleTwo(double d){
        return String .format("%.2f", d);
    }


    /**
     * 使用默认的浏览器打开网页URL
     * @param context
     * @param url
     */
    public static void openWebUrl(Context context, String url){
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        context.startActivity(intent);
    }

    /****************
     *
     * 发起添加群流程。群号：次元茶舍(721800134) 的 key 为： P1N32VTB_Z7OF8o7T7Q9H9obEUu7sLFh
     * 调用 joinQQGroup(P1N32VTB_Z7OF8o7T7Q9H9obEUu7sLFh) 即可发起手Q客户端申请加群 次元茶舍(721800134)
     *
     * @param key 由官网生成的key
     * @return 返回true表示呼起手Q成功，返回fals表示呼起失败
     ******************/
    public static boolean joinQQGroup(Context context, String key) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            // 未安装手Q或安装的版本不支持
            return false;
        }
    }

    /**
     * RecyclerView 移动到当前位置，
     *
     * @param manager   设置RecyclerView对应的manager
     * @param mRecyclerView  当前的RecyclerView
     * @param n  要跳转的位置
     */
    public static void MoveToPosition(LinearLayoutManager manager, RecyclerView mRecyclerView, int n) {


        int firstItem = manager.findFirstVisibleItemPosition();
        int lastItem = manager.findLastVisibleItemPosition();
        if (n <= firstItem) {
            mRecyclerView.scrollToPosition(n);
        } else if (n <= lastItem) {
            int top = mRecyclerView.getChildAt(n - firstItem).getTop();
            mRecyclerView.scrollBy(0, top);
        } else {
            mRecyclerView.scrollToPosition(n);
        }

    }
}

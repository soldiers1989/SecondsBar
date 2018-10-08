package com.fx.secondbar.receive;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.btten.bttenlibrary.util.LogUtil;

import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * function:极光推送广播接收者
 * author: frj
 * modify date:
 * modify date:
 */

public class JPushReceive extends BroadcastReceiver
{
    private static final String TAG = "JPushReceive";

    private NotificationManager nm;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (null == nm)
        {
            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        Bundle bundle = intent.getExtras();

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction()))
        {
            LogUtil.d(TAG, "JPush用户注册成功");

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction()))
        {
            LogUtil.d(TAG, "接受到推送下来的自定义消息");

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction()))
        {
            LogUtil.d(TAG, "接受到推送下来的通知");

            receivingNotification(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction()))
        {
            LogUtil.d(TAG, "用户点击打开了通知");
            if (isApplicationRunning(context))
            {
                openNotification(context, bundle);
            } else
            {
                String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
                LogUtil.e("receive", extras);
                Bundle b = new Bundle();

//                /*
//                    4.20修改，推送点击不再传递资讯实体信息值
//                 */
////                b.putParcelable("activity_num", advisoryBean);
//                b.putInt("activity_num", advisoryBean.getId());
//                b.putInt("activity_str", advisoryBean.getType_id());
//                //跳转至主界面，再在主界面做判断
//                Intent i = new Intent(Intent.ACTION_MAIN);
//                i.addCategory(Intent.CATEGORY_LAUNCHER);
//                i.putExtras(b);
//                ComponentName cn = new ComponentName(context.getPackageName(), context.getPackageName() + ".ui.transition.AcTransition");
//                i.setComponent(cn);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(i);


            }
        } else
        {
            LogUtil.d(TAG, "Unhandled intent - " + intent.getAction());
        }
    }

    /**
     * 接收到推送通知
     *
     * @param context
     * @param bundle
     */
    private void receivingNotification(Context context, Bundle bundle)
    {
        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        LogUtil.d(TAG, " title : " + title);
        String message = bundle.getString(JPushInterface.EXTRA_ALERT);
        LogUtil.d(TAG, "message : " + message);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        LogUtil.d(TAG, "extras : " + extras);

//        AdvisoryBean advisoryBean = buildAdvisory(extras);
//        Intent intent = new Intent(Constants.FILTER_ADVISORY_MSG);
//        intent.putExtra("bean", advisoryBean);
//        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    /**
     * 点击消息通知
     *
     * @param context 上下文
     * @param bundle
     */
    private void openNotification(Context context, Bundle bundle)
    {
        if (context == null)
        {
            return;
        }
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        LogUtil.i(TAG, "openNotification,extra:" + extras);
        if (!TextUtils.isEmpty(extras))
        {
//            String[] account = SharePreferenceUtils.getAccount();
//            if (account == null)
//            {
//                Intent intent = new Intent(context, AcLogin.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                context.startActivity(intent);
//                return;
//            }
//            AdvisoryBean advisoryBean = buildAdvisory(extras);
//            if (advisoryBean != null)
//            {
//                Intent intent;
//                if (Constants.TYPE_SUGGEST == advisoryBean.getType_id() || Constants.TYPE_COMPLAINTS == advisoryBean.getType_id())
//                {
//                    intent = new Intent(context, AcSuggestDetail.class);
//                } else
//                {
////                    intent = new Intent(context, AcAdvisoryDetail.class);
//                    intent = new Intent(context, AcNewAdvisoryDetail.class);
//                }
//                Bundle b = new Bundle();
////                b.putParcelable("activity_num", advisoryBean);
//                b.putInt("activity_num", advisoryBean.getId());
//                b.putInt("activity_str", advisoryBean.getType_id());
//                intent.putExtras(b);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
//                LogUtil.i(TAG, "jump to detail");
//            } else
//            {
//                LogUtil.i(TAG, "advisoryBean is   null");
//            }
        }
    }

//    /**
//     * 通过附加信息，构建资讯信息实体
//     *
//     * @param extras 附加信息
//     * @return
//     */
//    private AdvisoryBean buildAdvisory(String extras)
//    {
//        try
//        {
//            Gson gson = new Gson();
//            AdvisoryBean advisoryBean = gson.fromJson(extras, AdvisoryBean.class);
//            return advisoryBean;
//        } catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//        return null;
//    }

    /**
     * 判断应用是否在运行
     *
     * @param context
     * @return
     */
    private boolean isApplicationRunning(Context context)
    {
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        for (ActivityManager.RunningTaskInfo info : list)
        {
            if (info.topActivity.getPackageName().equals(
                    context.getPackageName())
                    && info.baseActivity.getPackageName().equals(
                    context.getPackageName()))
            {
                return true;
            }
        }
        return false;
    }

}

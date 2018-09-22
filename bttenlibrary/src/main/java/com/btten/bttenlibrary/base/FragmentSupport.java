package com.btten.bttenlibrary.base;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.btten.bttenlibrary.util.DialogUtil;
import com.btten.bttenlibrary.util.ResourceHelper;

import java.util.Map;

public abstract class FragmentSupport extends Fragment implements
        OnItemClickListener, OnClickListener
{

    /**
     * 界面传整形值中，key的值
     */
    protected static final String KEY = "activity_num";

    /**
     * 界面传字符串类型的值时，key的值
     */
    protected static final String KEY_STR = "activity_str";

    /**
     * 每页数据条目，分页中使用
     */
    protected static final int PAGE_NUM = 10;

    /**
     * 起始页
     */
    protected static final int PAGE_START = 1;

    /**
     * 多次点击超时时间
     */
    protected static final int MULTIPLE_CLICK_TIMEOUT = 300;

    /**
     * 用于保存上次点击的View
     */
    protected View tempView;

    /**
     * 用于保存最后一次点击时间
     */
    protected long lastClickTime;

    /**
     * 进度对话框
     */
    protected Dialog loadDialog;

    /**
     * 进度对话框加载提示
     */
    private static final String DIALOG_MSG = "数据加载中…";

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        initView();
        initListener();
        initData();
    }

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 初始化监听事件
     */
    protected abstract void initListener();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 通过资源Id查找控件
     *
     * @param id
     * @param <T> 返回继承至View的对象
     * @return
     */
    protected <T extends View> T findView(int id)
    {
        return (T) getView().findViewById(id);
    }

    @Override
    public void onDestroy()
    {
        if (loadDialog != null && loadDialog.isShowing())
        {
            loadDialog.dismiss();
        }
        super.onDestroy();
    }

    /**
     * 获取输入框的字符串
     *
     * @param textView
     * @return
     */
    protected String getTextView(TextView textView)
    {
        if (textView == null)
        {
            return "";
        }
        return textView.getText().toString().trim();
    }

    /**
     * 获取输入框的字符串 当输入框无内容时，使用默认值代替
     *
     * @param editText
     * @param defaultText 默认文本
     * @return
     */
    protected String getEditText(EditText editText, String defaultText)
    {
        if (editText == null)
        {
            return defaultText;
        }
        if (TextUtils.isEmpty(editText.getText().toString().trim()))
        {
            return defaultText;
        }
        return editText.getText().toString().trim();
    }

    /**
     * 给Map集合设置参数
     *
     * @param map    map数组
     * @param params 参数
     * @param value  参数值
     */
    protected void setMapParams(Map<String, String> map, String params,
                                String value)
    {
        if (map == null)
        {
            return;
        }
        if (params != null)
        {
            if (!TextUtils.isEmpty(value))
            {
                map.put(params, value);
            }
        }
    }

    /**
     * 给Map集合设置参数
     *
     * @param map    map对象
     * @param params 参数集合
     * @param values 参数值集合
     */
    protected void setMapParams(Map<String, String> map, String[] params,
                                String[] values)
    {
        if (map == null)
        {
            return;
        }
        if (params == null || values == null)
        {
            return;
        }
        int length = 0;
        if (params.length > values.length)
        {
            length = values.length;
        } else
        {
            length = params.length;
        }
        for (int i = 0; i < length; i++)
        {
            setMapParams(map, params[i], values[i]);
        }
    }

    /**
     * @param clazz    要跳转的类
     * @param isFinish 表示是否关闭当前的Activity true表示关闭当前的Activity
     * @param bundle   传输的参数
     * @author：Frj 功能:Activity跳转的方法 使用方法：
     */
    protected void jump(Class<?> clazz, Bundle bundle, boolean isFinish)
    {
        Intent intent = new Intent(getActivity(), clazz);
        if (bundle != null)
        {
            intent.putExtras(bundle);
        }
        getActivity().startActivity(intent);
        // 屏幕右边进，屏幕内从左边移出 动画
        getActivity().overridePendingTransition(
                ResourceHelper.getInstance(
                        getActivity().getApplicationContext()).getAnimId(
                        "right_in_anim"),
                ResourceHelper.getInstance(
                        getActivity().getApplicationContext()).getAnimId(
                        "left_out_anim"));
        if (isFinish)
        {
            getActivity().finish();
        }
    }

    /**
     * @param clazz    要填转到的类
     * @param param    参数
     * @param isFinish 是否关闭当前Activity
     * @author：Frj 功能:传输一个String类型的参数，跳转到下一个Activity 使用方法：
     */
    protected void jump(Class<?> clazz, String param, boolean isFinish)
    {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_STR, param);
        jump(clazz, bundle, isFinish);
    }

    /**
     * @param clazz    要填转到的类
     * @param param    参数
     * @param isFinish 是否关闭当前Activity
     * @author：Frj 功能:传输一个整形的参数跳转到下一个Activity 使用方法：
     */
    protected void jump(Class<?> clazz, int param, boolean isFinish)
    {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY, param);
        jump(clazz, bundle, isFinish);
    }

    /**
     * @param clazz    要填转到的类
     * @param isFinish 是否关闭当前Activity
     * @author：Administrator 功能:跳转界面，无参数传输 使用方法：
     */
    protected void jump(Class<?> clazz, boolean isFinish)
    {
        jump(clazz, "", isFinish);
    }

    /**
     * @param clazz 要填转到的类
     * @param param 参数
     * @author：Frj 功能:传输一个String类型的参数，跳转到下一个Activity(不关闭当前的Activity) 使用方法：
     */
    protected void jump(Class<?> clazz, String param)
    {
        jump(clazz, param, false);
    }

    /**
     * @param paramClass 跳转到的页面
     * @author：Frj 功能:跳转Activity（不finish当前页） 使用方法：
     */
    protected void jump(Class<?> paramClass)
    {
        jump(paramClass, "", false);
    }

    /**
     * 带回调结果的跳转
     *
     * @param paramClass  要跳转到的Activity
     * @param requestCode 请求码
     */
    protected void jump(Class<?> paramClass, int requestCode)
    {
        jump(paramClass, null, requestCode);
    }

    /**
     * 带回调结果的跳转
     *
     * @param paramClass  要跳转到的Activity
     * @param bundle      参数
     * @param requestCode 请求码
     */
    protected void jump(Class<?> paramClass, Bundle bundle, int requestCode)
    {
        Intent intent = new Intent(getActivity(), paramClass);
        if (bundle != null)
        {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
        // 屏幕右边进，屏幕内从左边移出 动画
        getActivity().overridePendingTransition(
                ResourceHelper.getInstance(
                        getActivity().getApplicationContext()).getAnimId(
                        "right_in_anim"),
                ResourceHelper.getInstance(
                        getActivity().getApplicationContext()).getAnimId(
                        "left_out_anim"));
    }

    @Override
    public void onClick(View v)
    {
        if (isFastDoubleClick(v))
        {
            return;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id)
    {
        if (isFastDoubleClick(view))
        {
            return;
        }
    }

    /**
     * @param view 触发点击事件的View
     * @return 如果小于设定的超时时间（MULTIPLE_CLICK_TIMEOUT值），那么返回true
     * @author：frj 功能:判断两次点击间隔是否小于设定的超时时间，如果小于设定的超时时间，那么返回true 使用方法：
     */
    protected boolean isFastDoubleClick(View view)
    {
        return isFastDoubleClick(MULTIPLE_CLICK_TIMEOUT, view);
    }

    /**
     * @param interval 时间间隔，单位为毫秒
     * @param view     触发点击事件的View
     * @return 如果小于指定的间隔，那么返回true
     * @author：frj 功能:判断两次点击间隔是否小于指定的间隔，如果小于指定的间隔，那么返回true 使用方法：
     */
    protected boolean isFastDoubleClick(long interval, View view)
    {
        if (view != tempView)
        { // 如果两次点击的控件不一样，那么不验证
            tempView = view;
            return false;
        }
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < interval)
        {
            return true;
        }
        lastClickTime = time;
        tempView = null;
        return false;
    }

    /**
     * @param context
     * @param msg     提示语
     * @author：Frj 功能:显示进度对话框，使用自定义的提示语 使用方法：
     */

    protected void showDialog(Context context, String msg)
    {
        if (TextUtils.isEmpty(msg))
        {
            msg = DIALOG_MSG;
        }
        if (loadDialog != null && loadDialog.isShowing())
        {
            loadDialog.dismiss();
            loadDialog = null;
        }
        loadDialog = DialogUtil.createLoadingDialog(context, msg);
        // sdk14以后，不设置为false，当显示对话框的时候，点击屏幕会被取消
        loadDialog.setCanceledOnTouchOutside(false);
        loadDialog.show();
    }

    /**
     * @param context
     * @author：Frj 功能:显示进度对话框，使用默认的提示语 使用方法：
     */
    protected void showDialog(Context context)
    {
        showDialog(context, "");
    }

    /**
     * 清除fragment 对象
     */
    @Override
    public void onDestroyView()
    {
        Fragment parentFragment = getParentFragment();
        FragmentManager manager;
        try
        {
            if (getActivity() instanceof ActivitySupport)
            {
                if (((ActivitySupport) getActivity()).isDestroy())
                {
                    super.onDestroyView();
                    return;
                }
            }
            if (parentFragment != null)
            {
                // If parent is another fragment, then this fragment is nested
                manager = parentFragment.getChildFragmentManager();
                if (manager != null)
                {
                    manager.beginTransaction().remove(this).commitAllowingStateLoss();
                }
            } else
            {// This fragment is placed into activity
                if (getActivity() != null)
                {
                    manager = getActivity().getSupportFragmentManager();
                    if (manager != null)
                    {
                        manager.beginTransaction().remove(this).commitAllowingStateLoss();
                    }
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        super.onDestroyView();
    }
}

package com.btten.bttenlibrary.base;

import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.btten.bttenlibrary.R;
import com.btten.bttenlibrary.application.BtApplication;
import com.btten.bttenlibrary.base.permission.PermissionHelper;
import com.btten.bttenlibrary.base.permission.callback.OnPermissionCallback;
import com.btten.bttenlibrary.util.DisplayUtil;
import com.btten.bttenlibrary.util.ResourceHelper;

import java.util.List;
import java.util.Map;

import rx.subscriptions.CompositeSubscription;

/**
 * 功能：FragmentActivity的基类，实现了页面的跳转的方法和各种监听事件
 */
public abstract class ActivitySupport extends AppCompatActivity implements OnClickListener, OnItemClickListener, OnPermissionCallback
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
     * 分页时，每页的数据量
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
     * 权限设置界面请求码
     */
    protected static final int REQUEST_SETTING = 0x200;

    /**
     * 权限请求帮助
     */
    protected PermissionHelper mPermissionHelper;

    /**
     * 用于保存上次点击的View
     */
    protected View tempView;

    /**
     * 用于保存最后一次点击时间
     */
    protected long lastClickTime;

    /**
     * 表示是否Destroy
     */
    private boolean isDestroy = false;

    /**
     * 代表当前Activity的所有订阅信息
     */
    protected CompositeSubscription mCompositeSubscription;

    /**
     * 需要申请的权限数组
     */
    protected String[] permissionArrays;

    /**
     * res值，表示需要申请的权限对应的提示说明
     */
    @IntegerRes
    protected int[] permissionInfoTips;

    //表示是否初始化
    private boolean isInit = false;

    private Bundle savedInstanceState;

    @Override
    protected void onCreate(Bundle arg0)
    {
        super.onCreate(arg0);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        BtApplication.getApplication().addActivity(this);
//        StatusBarUtil.setStatusBarLightMode(getWindow());
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //设置沉浸式
        setTranslucentStatus(true);
        savedInstanceState = arg0;
        //设置布局
        setContentView(getLayoutResId());
        permissionInit();
        savedInstanceState = null;
    }

    /**
     * 优先初始化权限信息
     */
    private void permissionInit()
    {
        permissionArrays = getPermissionArrays();
        permissionInfoTips = getPermissionInfoTips();

        /*
            表示无需权限
         */
        if (permissionArrays == null || permissionArrays.length == 0)
        {
            init();
        } else
        {
            doPermissionCheck();
        }
    }

    /**
     * 初始化
     */
    private void init()
    {
        isInit = true;
        //初始化控件
        initView();
        //初始化监听事件
        initListener();
        mCompositeSubscription = new CompositeSubscription();
        //初始化数据
        initData();
    }

    @Override
    public void setSupportActionBar(@Nullable Toolbar toolbar)
    {
        if (isFitWindow() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            if (toolbar != null)
            {
                ViewGroup.LayoutParams params = toolbar.getLayoutParams();
                params.height += DisplayUtil.getStatusBarHeight(this);
                toolbar.setPadding(0, DisplayUtil.getStatusBarHeight(this), 0, 0);
            }
        }
        super.setSupportActionBar(toolbar);
    }

    /**
     * 控制是否设置沉浸式状态栏
     *
     * @return true表示设置沉浸式，false反之。
     */
    protected boolean isFitWindow()
    {
        return true;
    }

    /**
     * 返回当前Activity布局文件的id
     *
     * @return
     */
    protected abstract int getLayoutResId();

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
     * 获取需要申请的权限的数组集
     *
     * @return
     */
    protected abstract String[] getPermissionArrays();

    /**
     * 获取需要申请的权限对应的提示语的资源id数组集
     *
     * @return
     */
    protected abstract int[] getPermissionInfoTips();

    /**
     * 获取SaveInstanceState对象
     *
     * @return
     */
    protected Bundle getSavedInstanceState()
    {
        return savedInstanceState;
    }

    /**
     * 权限检查
     */
    private void doPermissionCheck()
    {
        mPermissionHelper = PermissionHelper.getInstance(this);
        mPermissionHelper
                .setForceAccepting(true) // default is false. its here so you know that it exists.
                .request(permissionArrays);
    }

    /**
     * 设置沉浸式
     *
     * @param on
     */
    protected void setTranslucentStatus(boolean on)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (on)
            {
                winParams.flags |= bits;
            } else
            {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
        }
    }

    /**
     * 通过资源Id查找控件
     *
     * @param id
     * @param <T> 返回继承至View的对象
     * @return
     */
    protected <T extends View> T findView(int id)
    {
        return (T) findViewById(id);
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
    protected void setMapParams(Map<String, String> map, String params, String value)
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
    protected void setMapParams(Map<String, String> map, String[] params, String[] values)
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
        Intent intent = new Intent(this, clazz);
        if (bundle != null)
        {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        // 屏幕右边进，屏幕内从左边移出 动画
        overridePendingTransition(ResourceHelper.getInstance(getApplicationContext()).getAnimId("right_in_anim"),
                ResourceHelper.getInstance(getApplicationContext()).getAnimId("left_out_anim"));
        if (isFinish)
        {
            this.finish();
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
     * @author：Frj 功能:跳转界面，无参数传输 使用方法：
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
        Intent intent = new Intent(this, paramClass);
        if (bundle != null)
        {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
        // 屏幕右边进，屏幕内从左边移出 动画
        overridePendingTransition(R.anim.right_in_anim,
                R.anim.left_out_anim);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev)
    {
        if (ev.getAction() == MotionEvent.ACTION_DOWN)
        {

            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();

            if (isShouldHideInput(v, ev))
            {
                hideSoftInput(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v
     * @param event
     * @return
     */
    protected boolean isShouldHideInput(View v, MotionEvent event)
    {
        if (v != null && (v instanceof EditText))
        {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom)
            {
                // 点击EditText的事件，忽略它。
                return false;
            } else
            {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 隐藏软件盘
     *
     * @param token
     */
    public void hideSoftInput(IBinder token)
    {
        if (token != null)
        {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 判断服务是否正在运行
     *
     * @param mContext
     * @param className 服务类名（绝对路径的+类名）
     * @return true表示正在运行
     */
    public boolean isServiceRunning(Context mContext, String className)
    {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager.getRunningServices(100);
        if (!(serviceList.size() > 0))
        {
            return false;
        }
        for (int i = 0; i < serviceList.size(); i++)
        {
            if (serviceList.get(i).service.getClassName().equals(className) == true)
            {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }

    /**
     * 判断是否是被回收了
     *
     * @return
     */
    public boolean isDestroy()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
        {
            return isDestroy || isFinishing() || isDestroyed();
        } else
        {
            return isDestroy || isFinishing();
        }
    }


    @Override
    public void onPermissionGranted(@NonNull String[] permissionName)
    {
        //权限点击允许
//        String lastPermission = permissionName[permissionName.length - 1];
//        if (lastPermission.equals(permissionArrays[permissionArrays.length - 1]))
//        {
//            init();
//        } else
//        {
//            doPermissionCheck();
//        }
        if (isInit)
        {
            return;
        }
        for (int i = 0; i < permissionArrays.length; i++)
        {
            if (!mPermissionHelper.isPermissionGranted(permissionArrays[i]))
            {
                doPermissionCheck();
                return;
            }
        }
        init();
    }

    @Override
    public void onPermissionDeclined(@NonNull String[] permissionName)
    {
    }

    @Override
    public void onPermissionPreGranted(@NonNull String permissionsName)
    {
//        init();
    }

    @Override
    public void onPermissionNeedExplanation(@NonNull String permissionName)
    {
        mPermissionHelper.requestAfterExplanation(permissionName);
    }

    @Override
    public void onPermissionReallyDeclined(@NonNull String permissionName)
    {

        String permissionTips = "";
        for (int i = 0, length = permissionArrays.length; i < length; i++)
        {
            if (permissionArrays[i].equals(permissionName))
            {
                if (i < permissionInfoTips.length)
                {
                    permissionTips = getString(permissionInfoTips[i]);
                    break;
                }
            }
        }
        if (TextUtils.isEmpty(permissionTips))
        {
            permissionTips = getString(R.string.should_permission_default_tips);
        }
        new AlertDialog.Builder(this).setCancelable(false).setMessage(permissionTips).setPositiveButton("确定", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                //禁止权限
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.parse("package:" + getPackageName());
                intent.setData(uri);
                startActivityForResult(intent, REQUEST_SETTING);
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                finish();
            }
        }).create().show();

    }

    @Override
    public void onNoPermissionNeeded()
    {
        init();
    }

    /**
     * 记得手动重写这个方法
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        mPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
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
     * 根据当前的焦点对象隐藏软键盘
     */
    protected void hideSoftByFocus()
    {
        // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
        View v = getCurrentFocus();
        if (v instanceof EditText)
        {
            hideSoftInput(v.getWindowToken());
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }

    @Override
    protected void onDestroy()
    {
        hideSoftByFocus();
        BtApplication.getApplication().removeActivity(this);
        isDestroy = true;
        //退订所有订阅以避免内存泄露
        if (mCompositeSubscription != null)
        {
            mCompositeSubscription.unsubscribe();
        }
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_SETTING == requestCode && RESULT_OK == resultCode)
        {
            //重新执行权限检查
            doPermissionCheck();
        }
    }
}

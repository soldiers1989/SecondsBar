package com.btten.bttenlibrary.application;

import android.app.Activity;
import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;
import android.util.Log;

import com.btten.bttenlibrary.base.config.ABaseConfig;
import com.btten.bttenlibrary.http.HttpGetData;
import com.btten.bttenlibrary.util.LogUtil;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.Stack;

public abstract class BtApplication extends Application
{

    private static BtApplication mApplication; // 单例模式
    // 标识是否是测试版
    protected boolean isDebug = true;

    private Stack<WeakReference<Activity>> activities; // Activity栈集合

    protected ABaseConfig mAbaseConfig;    //基础配置


    @Override
    public void onCreate()
    {
        super.onCreate();
        mApplication = this;
        activities = new Stack<>();
        setDebug();
        setBaseConfig();
        HttpGetData.initConfig(mAbaseConfig);
    }

    /**
     * 创建根目录文件夹
     */
    public static void createRootDir()
    {
        String path = Environment.getExternalStorageDirectory().getPath()
                + File.separator + getApplication().mAbaseConfig.getBaseRootDir();
        File dir = new File(path);
        if (!dir.exists())
        {
            boolean isResult = dir.mkdir();
            if (!isResult)
            {
                LogUtil.e("create Root Dir was fail");
            }
        }
//        if (!getApplication().isDebug)
//        {
//            // 获取应用程序奔溃信息
//            CrashHandler.getInstance(getApplication().mAbaseConfig.getBaseRootDir()).init(getApplication().getApplicationContext());
//        }
    }

    /**
     * 单例模式，获取BTApplication的实例
     *
     * @return Application实例对象
     */
    public static BtApplication getApplication()
    {
        return mApplication;
    }

    /**
     * 设置基础的配置类
     */
    protected abstract void setBaseConfig();


    /**
     * 获取当前是否是测试版
     *
     * @return true表示当前是测试版。
     */
    public boolean isDebug()
    {
        return isDebug;
    }

    /**
     * 标识是正式版还是测试版
     */
    protected abstract void setDebug();

    /**
     * 添加Activity
     *
     * @param activity activity对象
     */
    public void addActivity(Activity activity)
    {
        if (activity != null)
        {
            activities.add(new WeakReference<>(activity));
        }
    }

    /**
     * 移除Activity
     *
     * @param activity activity对象
     */
    public void removeActivity(Activity activity)
    {
        if (activity != null)
        {
            for (int i = 0; i < activities.size(); i++)
            {
                if (activities.get(i) != null && activity == activities.get(i).get())
                {
                    activities.remove(i);
                    break;
                }
            }
        }
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public int getVersion()
    {
        PackageManager manager = getPackageManager();
        PackageInfo info;
        try
        {
            info = manager.getPackageInfo(getPackageName(), 0);
            Log.i("info", "" + info.versionCode);
            return info.versionCode;
        } catch (NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 退出程序
     */
    public void exit()
    {
        for (int i = 0; i < activities.size(); i++)
        {
            if (activities.get(i) != null && activities.get(i).get() != null)
            {
                activities.get(i).get().finish();
            }
        }
        System.exit(0);
    }
}

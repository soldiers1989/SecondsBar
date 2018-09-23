package com.fx.secondbar.util;

import android.content.Context;

import com.btten.bttenlibrary.util.LogUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * @author zk
 * <p>
 * 本类 采用泛型 广泛接受 ArrayList 直接做缓存，即是开发中经常用到的 bean；
 * 使用注意： 传进来的 ArrayList 所绑定的 bean，一定要是 已经继承 Serializable 或者Parcelable接口的；
 * 使用文本流做缓存。
 * @date 2017/10/12  13:51
 * @describe TODO
 * @project
 */

public class DataCacheUtils
{

    /**
     * 定义一些你项目里面的 缓存文件名字 ，自定义，不要也没关系，调用函数再传入也行
     */
    private static String DataCache = "Data_Cache_File";

    private static final String TAG = "DataCacheUtils";
    /**
     * 文件名，用户信息保存的文件的文件名
     */
    public static final String FILE_USERINFO = "userinfo";

    /**
     * 文件名，配置信息保存的文件的文件名
     */
    public static final String FILE_CONFIG_INFO = "configinfo";

    /**
     * 保存 一组 数据
     *
     * @param ctx       上下文
     * @param data      种子
     * @param cacheName 缓存文件名
     */
    public static <T> void saveListCache(Context ctx, ArrayList<T> data, String cacheName)
    {
        new DataCache<T>().saveGlobal(ctx, data, cacheName);
    }

    /**
     * 保存 一组 数据
     *
     * @param ctx       上下文
     * @param data      种子
     * @param cacheName 缓存文件名
     */
    public static <T> void saveListCache(Context ctx, T data, String cacheName)
    {
        ArrayList arrayList = new ArrayList<T>(1);
        arrayList.add(data);
        new DataCache<T>().saveGlobal(ctx, arrayList, cacheName);
    }

    /**
     * 直接根据 缓存文件名获取
     */
    public static <T> ArrayList<T> loadListCache(Context ctx, String cacheName)
    {
        return new DataCache<T>().loadGlobal(ctx, cacheName);
    }

    /**
     * @param <T> 数据缓存 save or load
     */
    static class DataCache<T>
    {
        public void save(Context ctx, ArrayList<T> data, String name)
        {
            save(ctx, data, name, "");
        }

        public void saveGlobal(Context ctx, ArrayList<T> data, String name)
        {
            save(ctx, data, name, DataCache);
        }

        private void save(Context ctx, ArrayList<T> data, String name, String folder)
        {
            if (ctx == null)
            {
                return;
            }
            File file;
            if (!folder.isEmpty())
            {
                File fileDir = new File(ctx.getCacheDir() + File.separator + Constants.ROOT_DIR, folder);
                if (!fileDir.exists() || !fileDir.isDirectory())
                {
                    fileDir.mkdirs();
                }
                file = new File(fileDir, name);
            } else
            {
                file = new File(ctx.getCacheDir() + File.separator + Constants.ROOT_DIR, name);
            }
            if (file.exists())
            {
                file.delete();
            }
            LogUtil.e(TAG, file.getAbsolutePath());
            try
            {
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
                oos.writeObject(data);
                oos.close();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        public ArrayList<T> load(Context ctx, String name)
        {
            return load(ctx, name, "");
        }

        public ArrayList<T> loadGlobal(Context ctx, String name)
        {
            return load(ctx, name, DataCache);
        }

        private ArrayList<T> load(Context ctx, String name, String folder)
        {

            ArrayList<T> data = null;
            if (ctx == null)
            {
                return new ArrayList<T>();
            }
            File file;
            if (!folder.isEmpty())
            {
                File fileDir = new File(ctx.getCacheDir() + File.separator + Constants.ROOT_DIR, folder);
                if (!fileDir.exists() || !fileDir.isDirectory())
                {
                    fileDir.mkdirs();
                }
                file = new File(fileDir, name);
            } else
            {
                file = new File(ctx.getCacheDir() + File.separator + Constants.ROOT_DIR, name);
            }
            LogUtil.e(TAG, "file " + file.getAbsolutePath());
            if (file.exists())
            {
                try
                {
                    LogUtil.d(TAG, "write object");
                    ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                    data = (ArrayList<T>) ois.readObject();
                    ois.close();
                } catch (Exception e)
                {
                    LogUtil.d(TAG, e.toString());
                }
            }
            if (data == null)
            {     /** 如果没有 */
                LogUtil.e(TAG, "data == null");
                data = new ArrayList<T>();
            }
            return data;
        }
    }

}
package com.btten.bttenlibrary.util;

import java.lang.reflect.Field;

import android.content.Context;

/**
 * 通过反射获取资源Id帮助类
 */
public class ResourceHelper {

	private static ResourceHelper mResource = null;

	private static String mPackagename = null;

	private static Class<?> mLayout = null;
	private static Class<?> mDrawable = null;
	private static Class<?> mID = null;
	private static Class<?> mString = null;
	private static Class<?> mAttr = null;
	private static Class<?> mAnim = null;
	private static Class<?> mStyleable = null;
	private static Class<?> mStyle = null;
	private static Class<?> mDimen = null;
	private static Class<?> mColor = null;

	/**
	 * 获取管理对象
	 * 
	 * @param context
	 *            可用ApplicationContext
	 * @return
	 */
	public static ResourceHelper getInstance(Context context) {
		if (mResource == null) {
			mPackagename = (mPackagename == null ? context.getPackageName() : mPackagename);
			mResource = new ResourceHelper(mPackagename);
		}
		return mResource;
	}

	/**
	 * 单例模式 构造方法
	 * 
	 * @param packageName
	 *            包名
	 */
	private ResourceHelper(String packageName) {
		/*
		 * $表示内部类
		 */
		try {
			mLayout = Class.forName(packageName + ".R$layout");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			mDrawable = Class.forName(packageName + ".R$drawable");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			mID = Class.forName(packageName + ".R$id");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			mString = Class.forName(packageName + ".R$string");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			mAttr = Class.forName(packageName + ".R$attr");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			mAnim = Class.forName(packageName + ".R$anim");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			mStyleable = Class.forName(packageName + ".R$styleable");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			mStyle = Class.forName(packageName + ".R$style");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			mDimen = Class.forName(packageName + ".R$dimen");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			mColor = Class.forName(packageName + ".R$color");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 通过资源名称获取资源Id
	 * 
	 * @param classType
	 *            资源类型
	 * @param resourceName
	 *            资源名称
	 * @return 资源Id
	 */
	private int getResourceId(Class<?> classType, String resourceName) {
		if (classType == null) {
			throw new IllegalArgumentException(
					"ResClass is not initialized. Please make sure you have added neccessary resources. Also make sure you have "
							+ mPackagename + ".R$* configured in obfuscation. field=" + resourceName);
		}
		try {
			Field field = classType.getField(resourceName);
			return field.getInt(resourceName);
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.e("ResourceHelper",
					"Error getting resource. Make sure you have copied all resources (res/) from SDK to your project.");
		}
		return -1;
	}

	/**
	 * 通过资源名称获取资源Id数组
	 * 
	 * @param classType
	 *            资源类型
	 * @param resourceName
	 *            资源名称
	 * @return 资源Id数组
	 */
	private int[] getResourceIds(Class<?> classType, String resourceName) {
		if (classType == null) {
			throw new IllegalArgumentException(
					"ResClass is not initialized. Please make sure you have added neccessary resources. Also make sure you have "
							+ mPackagename + ".R$* configured in obfuscation. field=" + resourceName);
		}
		try {
			Field field = classType.getField(resourceName);
			return (int[]) field.get(resourceName);
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.e("ResourceHelper",
					"Error getting resource. Make sure you have copied all resources (res/) from SDK to your project.");
		}
		return new int[] { -1 };
	}

	/**
	 * 获取图片资源id
	 * 
	 * @param resourceName
	 *            图片名称
	 * @return
	 */
	public int getDrawableId(String resourceName) {
		return getResourceId(mDrawable, resourceName);
	}

	/**
	 * 获取样式文件资源id
	 * 
	 * @param resourceName
	 *            资源名称
	 * @return
	 */
	public int getLayoutId(String resourceName) {
		return getResourceId(mLayout, resourceName);
	}

	/**
	 * 通过id名称获取id值
	 * 
	 * @param resourceName
	 *            id名称
	 * @return
	 */
	public int getId(String resourceName) {
		return getResourceId(mID, resourceName);
	}

	/**
	 * 获取文本字符串资源Id值
	 * 
	 * @param resourceName
	 *            文本Id名称
	 * @return
	 */
	public int getStringId(String resourceName) {
		return getResourceId(mString, resourceName);
	}

	/**
	 * 获取属性文件资源Id值
	 * 
	 * @param resourceName
	 *            属性文件名称
	 * @return
	 */
	public int getAttrId(String resourceName) {
		return getResourceId(mAttr, resourceName);
	}

	/**
	 * 获取动画文件资源Id
	 * 
	 * @param resourceName
	 *            动画文件名称
	 * @return
	 */
	public int getAnimId(String resourceName) {
		return getResourceId(mAnim, resourceName);
	}

	/**
	 * 获取样式文件资源Id
	 * 
	 * @param resourceName
	 *            样式文件名称
	 * @return
	 */
	public int getStyleId(String resourceName) {
		return getResourceId(mStyle, resourceName);
	}

	/**
	 * 获取自定义属性资源Id
	 * 
	 * @param resourceName
	 * 
	 * @return
	 */
	public int getStyleableId(String resourceName) {
		return getResourceId(mStyleable, resourceName);
	}

	/**
	 * 获取自定义属性资源Id组
	 * 
	 * @param resourceName
	 *            自定义属性名称
	 * @return
	 */
	public int[] getStyleableIds(String resourceName) {
		return getResourceIds(mStyleable, resourceName);
	}

	/**
	 * 获取Dimension属性资源Id
	 * 
	 * @param resourceName
	 *            Dimension名称
	 * @return
	 */
	public int getDimenId(String resourceName) {
		return getResourceId(mDimen, resourceName);
	}

	/**
	 * 获取Color属性资源Id
	 * 
	 * @param resourceName
	 *            Color名称
	 * @return
	 */
	public int getColorId(String resourceName) {
		return getResourceId(mColor, resourceName);
	}
}

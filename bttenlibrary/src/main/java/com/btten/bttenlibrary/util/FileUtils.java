package com.btten.bttenlibrary.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.btten.bttenlibrary.application.BtApplication;

import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

/**
 * 文件操作类 Created by Nereo on 2015/4/8.
 */
public class FileUtils {

	/**
	 * 保存的文件夹名称
	 */
	public static String SAVE_FOLDER = "/btten_library/";

	/**
	 * 写入操作时，每次写入大小
	 */
	public final static int WRITE_SEG_SIZE = 1024;

	/**
	 * 创建临时文件（主要用于保存拍照图片）
	 * 
	 * @param context
	 * @return
	 */
	public static File createTmpFile(Context context) {

		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			// 已挂载

			/*
			 * 由于某些ROM禁用了功能目录的使用，所以造成拍照后点击确定无法返回的问题。此处替换为自定义目录
			 */
			String path = Environment.getExternalStorageDirectory().getPath() + SAVE_FOLDER;
			File pic = new File(path);
			if (!pic.exists()) {
				pic.mkdirs();
			}
			// File pic = Environment
			// .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
			String fileName = "multi_image_" + timeStamp + "";
			File tmpFile = new File(pic, fileName + ".jpg");
			return tmpFile;
		} else {
			File cacheDir = context.getCacheDir();
			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
			String fileName = "multi_image_" + timeStamp + "";
			File tmpFile = new File(cacheDir, fileName + ".jpg");
			return tmpFile;
		}
	}

	/**
	 * 获取本地文件保存地址
	 * 
	 * @return
	 */
	public static String getNativePath() {
		String path = Environment.getExternalStorageDirectory().getPath() + SAVE_FOLDER;
		return path;
	}

	/**
	 * 通过文件获取Uri
	 *
	 * @param context
	 * @param file
	 * @return
	 */
	public static Uri getUriForFile(Context context, File file)
	{
		if (context == null || file == null)
		{
			throw new NullPointerException();
		}
		Uri uri;
		if (Build.VERSION.SDK_INT >= 24)
		{
			uri = FileProvider.getUriForFile(context.getApplicationContext(), context.getPackageName() + ".FileProvider", file);
		} else
		{
			uri = Uri.fromFile(file);
		}
		return uri;
	}

	/**
	 * 获取文件大小
	 * 
	 * @param path
	 *            文件路径
	 * @return 以Kb为单位的大小
	 */
	public static long getFileSize(String path) {
		if (TextUtils.isEmpty(path)) {
			return 0;
		}
		File file = new File(path);
		return getFileSize(file);
	}

	/**
	 * 获取文件大小
	 * 
	 * @param file
	 *            文件
	 * @return 以Kb为单位的大小
	 */
	public static long getFileSize(File file) {
		if (file == null) {
			return 0;
		}
		try {
			return file.length() / 1024;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 获取文件名称
	 * 
	 * @param file
	 *            文件
	 * @return 文件名称
	 */
	public static String getFileName(File file) {
		if (file == null) {
			return "";
		}
		return file.getName();
	}

	/**
	 * 获取文件名称
	 * 
	 * @param path
	 *            文件路径
	 * @return 文件名称
	 */
	public static String getFileName(String path) {
		if (TextUtils.isEmpty(path)) {
			return "";
		}
		int separatorIndex = path.lastIndexOf(File.separator);
		return (separatorIndex < 0) ? path : path.substring(separatorIndex + 1, path.length());
	}

	/**
	 * 在SD卡上创建文件
	 * 
	 * @throws IOException
	 */
	public static File creatSDFile(String fileName) throws IOException {
		File file = new File(Environment.getExternalStorageDirectory() + SAVE_FOLDER + fileName);
		if (file.exists()) {
			file.delete();
		}
		file.createNewFile();
		return file;
	}

	/**
	 * 在SD卡上创建目录
	 * 
	 * @param dirName
	 */
	public static File creatSDDir(String dirName) {
		File dir = new File(Environment.getExternalStorageDirectory() + SAVE_FOLDER + dirName);
		dir.mkdir();
		return dir;
	}

	/**
	 * 判断SD卡上的文件夹是否存在
	 */
	public static boolean isFileExist(String fileName) {
		File file = new File(Environment.getExternalStorageDirectory() + SAVE_FOLDER + fileName);
		return file.exists();
	}

	/**
	 * 将一个InputStream里面的数据写入到SD卡中
	 * 
	 * @param path
	 *            文件路径
	 * @param fileName
	 *            文件名
	 * @param input
	 *            输入文件流
	 * @return
	 */
	public static File write2SDFromInput(String path, String fileName, InputStream input) {
		File file = null;
		OutputStream output = null;
		try {
			creatSDDir(path);
			file = creatSDFile(path + File.separator + fileName);
			output = new FileOutputStream(file);
			byte buffer[] = new byte[WRITE_SEG_SIZE];
			int readsize = 0;
			while ((readsize = input.read(buffer)) != -1) {
				output.write(buffer, 0, readsize);
			}
			output.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return file;
	}
}

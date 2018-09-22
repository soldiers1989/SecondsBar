package com.btten.bttenlibrary.util;

import com.btten.bttenlibrary.application.BtApplication;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 对话框工具类
 * 
 * 包含： 默认进度对话框、按钮提示框
 */
public class DialogUtil {

	/**
	 * 得到自定义的progressDialog
	 * 
	 * @param context
	 * @param msg
	 * @return
	 */
	public static Dialog createLoadingDialog(Context context, String msg) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(ResourceHelper.getInstance(BtApplication.getApplication().getApplicationContext())
				.getLayoutId("loading_dialog_"), null);// 得到加载view
		LinearLayout layout = (LinearLayout) v.findViewById(ResourceHelper
				.getInstance(BtApplication.getApplication().getApplicationContext()).getId("dialog_view"));// 加载布局
//		// main.xml中的ImageView
//		final ImageView spaceshipImage = (ImageView) v.findViewById(
//				ResourceHelper.getInstance(BtApplication.getApplication().getApplicationContext()).getId("img"));
//		TextView tipTextView = (TextView) v.findViewById(ResourceHelper
//				.getInstance(BtApplication.getApplication().getApplicationContext()).getId("tipTextView"));// 提示文字
//		// 加载动画
//		final Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(context, ResourceHelper
//				.getInstance(BtApplication.getApplication().getApplicationContext()).getAnimId("loading_dialog_anim"));
//		// 使用ImageView显示动画
//		spaceshipImage.startAnimation(hyperspaceJumpAnimation);
//		tipTextView.setText(msg);// 设置加载信息

		final Dialog loadingDialog = new Dialog(context, ResourceHelper
				.getInstance(BtApplication.getApplication().getApplicationContext()).getStyleId("loading_dialog"));// 创建自定义样式dialog

		loadingDialog.setOnShowListener(new OnShowListener() {

			@Override
			public void onShow(DialogInterface dialog) {
				/*
				 * 主要解决对话框再次显示时 动画不执行的问题
				 */
				// 使用ImageView显示动画
//				spaceshipImage.startAnimation(hyperspaceJumpAnimation);
			}
		});

		// loadingDialog.setCancelable(false);// 不可以用“返回键”取消
		loadingDialog.setContentView(layout,
				new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));// 设置布局
		return loadingDialog;
	}

	/**
	 * 
	 * @author：Frj 功能:创建一个提示对话框
	 * @param context
	 *            上下文
	 * @param msg
	 *            提示内容
	 * @param positive_str
	 *            确定键内容
	 * @param cancel_str
	 *            取消键内容
	 * @return
	 */
	public static Dialog createTipsDialog(Context context, String msg, String positive_str, String cancel_str,
			OnClickListener listener) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(ResourceHelper.getInstance(BtApplication.getApplication().getApplicationContext())
				.getLayoutId("tips_dialog"), null);// 得到加载view
		TextView tv_tips = (TextView) v.findViewById(
				ResourceHelper.getInstance(BtApplication.getApplication().getApplicationContext()).getId("tv_tips"));
		Button btn_posive = (Button) v.findViewById(
				ResourceHelper.getInstance(BtApplication.getApplication().getApplicationContext()).getId("posive"));
		Button btn_cancel = (Button) v.findViewById(
				ResourceHelper.getInstance(BtApplication.getApplication().getApplicationContext()).getId("cancel"));

		tv_tips.setText(msg);
		btn_posive.setText(cancel_str);

		btn_cancel.setText(positive_str);

		btn_posive.setTag(cancel_str);

		btn_cancel.setTag(positive_str);

		btn_posive.setOnClickListener(listener);
		btn_cancel.setOnClickListener(listener);

		final Dialog dialog = new Dialog(context, ResourceHelper
				.getInstance(BtApplication.getApplication().getApplicationContext()).getStyleId("tips_dialog"));
		dialog.setContentView(v, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		return dialog;
	}

	/**
	 * 只带有确定按钮的提示框
	 * 
	 * @param context
	 * @param msg
	 *            提示信息
	 * @param listener
	 *            点击监听事件
	 * @return
	 */
	public static Dialog createTipsDialog(Context context, String msg, OnClickListener listener) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(ResourceHelper.getInstance(BtApplication.getApplication().getApplicationContext())
				.getLayoutId("dialog_tips"), null);// 得到加载view
		TextView tv_msg = (TextView) v.findViewById(
				ResourceHelper.getInstance(BtApplication.getApplication().getApplicationContext()).getId("tv_msg"));
		Button btn_confirm = (Button) v.findViewById(ResourceHelper
				.getInstance(BtApplication.getApplication().getApplicationContext()).getId("btn_confirm"));

		tv_msg.setText(msg);
		final Dialog dialog = new Dialog(context, ResourceHelper
				.getInstance(BtApplication.getApplication().getApplicationContext()).getStyleId("loading_dialog"));
		if (listener != null) {
			btn_confirm.setTag(dialog);
			btn_confirm.setOnClickListener(listener);
		} else {
			btn_confirm.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
		}
		dialog.setContentView(v, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		return dialog;
	}

	/**
	 * 只带有确定按钮的提示框 点击按钮 默认取消对话框
	 * 
	 * @param context
	 * @param msg
	 *            提示信息
	 * @return
	 */
	public static Dialog createTipsDialog(Context context, String msg) {
		return createTipsDialog(context, msg, null);
	}
}

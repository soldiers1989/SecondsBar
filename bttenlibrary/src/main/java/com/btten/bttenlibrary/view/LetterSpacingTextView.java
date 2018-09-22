package com.btten.bttenlibrary.view;

import com.btten.bttenlibrary.application.BtApplication;
import com.btten.bttenlibrary.util.ResourceHelper;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ScaleXSpan;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 功能：重写TextView 实现可以控制字间距
 * 
 * 使用：与TextView使用一致，调用setLetterSpacing(float
 * letterSpacing)即可设置间隔，在setText(CharSequence text)之前调用。
 */
public class LetterSpacingTextView extends TextView {
	/**
	 * 正常的字间距
	 */
	public static final float NORMAL = 1.0f;
	/**
	 * 较小的字间距
	 */
	public static final float SMALL = 0.8f;
	/**
	 * 较大的字间距
	 */
	public static final float BIG = 1.2f;

	private float letterSpacing = NORMAL; // 字间距
	private CharSequence originalText = "";

	public LetterSpacingTextView(Context context) {
		super(context);
	}

	public LetterSpacingTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray typedArray = context.obtainStyledAttributes(attrs,
				ResourceHelper.getInstance(BtApplication.getApplication().getApplicationContext())
						.getStyleableIds("letterspacing_textview"));
		letterSpacing = typedArray
				.getFloat(ResourceHelper.getInstance(BtApplication.getApplication().getApplicationContext())
						.getStyleableId("letterspacing_textview_word_space"), NORMAL);

		typedArray.recycle();
	}

	public LetterSpacingTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public float getLetterSpacing() {
		return letterSpacing;
	}

	@Override
	public void setLetterSpacing(float letterSpacing) {
		this.letterSpacing = letterSpacing;
	}

	@Override
	public void setText(CharSequence text, BufferType type) {
		originalText = text;
		applyLetterSpacing();
	}

	@Override
	public CharSequence getText() {
		return originalText;
	}

	/**
	 * 
	 * @author：Frj 功能:应用字间距 使用方法：
	 */
	private void applyLetterSpacing() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < originalText.length(); i++) {
			builder.append(originalText.charAt(i));
			if (i + 1 < originalText.length()) {
				builder.append("\u00A0");
			}
		}
		SpannableString finalText = new SpannableString(builder.toString());
		if (builder.toString().length() > 1) {
			for (int i = 1; i < builder.toString().length(); i += 2) {
				finalText.setSpan(new ScaleXSpan((letterSpacing) / 10), i, i + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
		}
		super.setText(finalText, BufferType.SPANNABLE);
	}
}

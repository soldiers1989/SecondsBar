package com.fx.secondbar.ui.date;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.btten.bttenlibrary.util.ShowToast;
import com.fx.secondbar.R;

/**
 * function:添加话题对话框
 * author: frj
 * modify date: 2018/10/11
 */
public class FragmentAddTopicDialog extends DialogFragment implements View.OnClickListener
{

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

    private EditText edInput;
    private OnAddTopicListener onAddTopicListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.Dialog_Normal);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.dialog_add_topic, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        setCancelable(true);
        super.onActivityCreated(savedInstanceState);
        init();
    }

    /**
     * 初始化
     */
    private void init()
    {
        getView().findViewById(R.id.btn_confirm).setOnClickListener(this);
        edInput = getView().findViewById(R.id.ed_input);
    }

    public OnAddTopicListener getOnAddTopicListener()
    {
        return onAddTopicListener;
    }

    public void setOnAddTopicListener(OnAddTopicListener onAddTopicListener)
    {
        this.onAddTopicListener = onAddTopicListener;
    }

    @Override
    public void onClick(View v)
    {
        if (isFastDoubleClick(v))
        {
            return;
        }
        switch (v.getId())
        {
            case R.id.btn_confirm:
                if (edInput != null)
                {
                    String text = edInput.getText().toString().trim();
                    if (TextUtils.isEmpty(text))
                    {
                        ShowToast.showToast("请输入标签");
                        return;
                    }
                    if (onAddTopicListener != null)
                    {
                        onAddTopicListener.finishInput(text);
                    }
                    hideSoftInput(edInput.getWindowToken());
                    edInput.setText("");
                    dismiss();
                }

                break;
        }
    }

    /**
     * 隐藏软件盘
     *
     * @param token 焦点控件的token
     */
    public void hideSoftInput(IBinder token)
    {
        if (token != null)
        {
            InputMethodManager im = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (im != null)
            {
                im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    /**
     * @param view 触发点击事件的View
     * @return 如果小于指定的间隔，那么返回true
     * @author：frj 功能:判断两次点击间隔是否小于指定的间隔，如果小于指定的间隔，那么返回true 使用方法：
     */
    protected boolean isFastDoubleClick(View view)
    {
        if (view != tempView)
        { // 如果两次点击的控件不一样，那么不验证
            tempView = view;
            return false;
        }
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < MULTIPLE_CLICK_TIMEOUT)
        {
            return true;
        }
        lastClickTime = time;
        tempView = null;
        return false;
    }

    /**
     * 添加话题监听
     */
    public interface OnAddTopicListener
    {
        void finishInput(String topic);
    }
}

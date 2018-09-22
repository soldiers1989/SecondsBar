package com.btten.bttenlibrary.ui.img;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.btten.bttenlibrary.R;
import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.util.DisplayUtil;
import com.btten.bttenlibrary.util.ResourceHelper;

import java.io.File;
import java.util.ArrayList;

/**
 * 多图选择 Created by Nereo on 2015/4/7.
 * <p/>
 * 请使用startActivityForResult启动， 传递参数（均为可选）：
 * ConstantValue.EXTRA_SELECT_COUNT（最大图片选择次数，int类型，默认9 ）、
 * ConstantValue.EXTRA_SELECT_MODE（图片选择模式，默认多选 多选为：ConstantValue.MODE_MULTI）、
 * ConstantValue.EXTRA_SHOW_CAMERA（是否显示相机，Boolean类型，默认显示 值为true ）、
 * ConstantValue.EXTRA_DEFAULT_SELECTED_LIST（当选择模式为多选时有效，表示选择集合，ArrayList
 * <String>集合）
 * <p/>
 * 返回： RESULT_CANCELED（未选择，直接返回）、 RESULT_OK（已选择）
 * <p/>
 * 结果参数（为RESULT_OK时有效）：
 * ConstantValue.EXTRA_RESULT（选择结果，返回为 ArrayList<String> 图片路径集合 ）
 */
public class MultiImageSelectorActivity extends ActivitySupport implements MultiImageSelectorFragment.Callback
{

    private ArrayList<String> resultList = new ArrayList<>();
    private Toolbar toolbar;
    private ImageView img_toolbar_left;
    private Button mSubmitButton;
    private int mDefaultCount;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId()
    {
        return ResourceHelper.getInstance(getApplicationContext()).getLayoutId("activity_selector");
    }

    @Override
    protected void initView()
    {
        img_toolbar_left = findView(R.id.img_toolbar_left);
        toolbar = findView(R.id.toolbar);
        // 完成按钮
        mSubmitButton = findView(R.id.btn_finish);
        if (resultList == null || resultList.size() <= 0)
        {
            mSubmitButton.setText("完成");
            mSubmitButton.setEnabled(false);
        } else
        {
            mSubmitButton.setText("完成");
            mSubmitButton.setEnabled(true);
        }
        mSubmitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (resultList != null && resultList.size() > 0)
                {
                    // 返回已选择的图片数据
                    Intent data = new Intent();
                    data.putStringArrayListExtra(ConstantValue.EXTRA_RESULT, resultList);
                    setResult(RESULT_OK, data);
                    finish();
                }
            }
        });
        img_toolbar_left.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        setSupportActionBar(toolbar);
        initToolBar();
    }

    @Override
    protected void initListener()
    {
    }

    @Override
    protected void initData()
    {
        Intent intent = getIntent();
        mDefaultCount = intent.getIntExtra(ConstantValue.EXTRA_SELECT_COUNT, 9);
        int mode = intent.getIntExtra(ConstantValue.EXTRA_SELECT_MODE, ConstantValue.MODE_MULTI);
        boolean isShow = intent.getBooleanExtra(ConstantValue.EXTRA_SHOW_CAMERA, true);
        if (mode == ConstantValue.MODE_MULTI && intent.hasExtra(ConstantValue.EXTRA_DEFAULT_SELECTED_LIST))
        {
            resultList = intent.getStringArrayListExtra(ConstantValue.EXTRA_DEFAULT_SELECTED_LIST);
        }

        Bundle bundle = new Bundle();
        bundle.putInt(ConstantValue.EXTRA_SELECT_COUNT, mDefaultCount);
        bundle.putInt(ConstantValue.EXTRA_SELECT_MODE, mode);
        bundle.putBoolean(ConstantValue.EXTRA_SHOW_CAMERA, isShow);
        bundle.putStringArrayList(ConstantValue.EXTRA_DEFAULT_SELECTED_LIST, resultList);

        getSupportFragmentManager().beginTransaction()
                .add(ResourceHelper.getInstance(getApplicationContext()).getId("image_grid"),
                        Fragment.instantiate(this, MultiImageSelectorFragment.class.getName(), bundle))
                .commit();
    }

    /**
     * 初始化Toolbar高度
     */
    private void initToolBar()
    {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) toolbar.getLayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            int statusBarHeight = DisplayUtil.getStatusBarHeight(this);
            params.height += statusBarHeight;
            toolbar.setPadding(0, statusBarHeight, 0, 0);
        }
    }

    @Override
    protected String[] getPermissionArrays()
    {
        return new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
    }

    @Override
    protected int[] getPermissionInfoTips()
    {
        return new int[]{ResourceHelper.getInstance(getApplicationContext()).getStringId("permission_write_external_storage_tips")};
    }

    @Override
    public void onSingleImageSelected(String path)
    {
        Intent data = new Intent();
        resultList.add(path);
        data.putStringArrayListExtra(ConstantValue.EXTRA_RESULT, resultList);
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void onImageSelected(String path)
    {
        if (!resultList.contains(path))
        {
            resultList.add(path);
        }
        // 有图片之后，改变按钮状态
        if (resultList.size() > 0)
        {
//			mSubmitButton.setText("完成(" + resultList.size() + "/" + mDefaultCount + ")");

            mSubmitButton.setText("完成");
            if (!mSubmitButton.isEnabled())
            {
                mSubmitButton.setEnabled(true);
            }
        }
    }

    @Override
    public void onImageUnselected(String path)
    {
        if (resultList.contains(path))
        {
            resultList.remove(path);
//            mSubmitButton.setText("完成(" + resultList.size() + "/" + mDefaultCount + ")");
            mSubmitButton.setText("完成");
        } else
        {
//            mSubmitButton.setText("完成(" + resultList.size() + "/" + mDefaultCount + ")");
            mSubmitButton.setText("完成");
        }
        // 当为选择图片时候的状态
        if (resultList.size() == 0)
        {
            mSubmitButton.setText("完成");
            mSubmitButton.setEnabled(false);
        }
    }

    @Override
    public void onCameraShot(File imageFile)
    {
        if (imageFile != null)
        {
            // 通知系统更新图库
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(imageFile)));
            Intent data = new Intent();
            resultList.add(imageFile.getAbsolutePath());
            data.putStringArrayListExtra(ConstantValue.EXTRA_RESULT, resultList);
            setResult(RESULT_OK, data);
            finish();
        }
    }

}

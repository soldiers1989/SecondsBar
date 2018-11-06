package com.fx.secondbar.ui.date;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.base.bean.ResponseBean;
import com.btten.bttenlibrary.ui.img.ConstantValue;
import com.btten.bttenlibrary.ui.img.MultiImageSelectorActivity;
import com.btten.bttenlibrary.util.BitmapUtil;
import com.btten.bttenlibrary.util.DateUtils;
import com.btten.bttenlibrary.util.DensityUtil;
import com.btten.bttenlibrary.util.DisplayUtil;
import com.btten.bttenlibrary.util.LogUtil;
import com.btten.bttenlibrary.util.ShowToast;
import com.btten.bttenlibrary.util.SpaceDecorationUtil;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fx.secondbar.R;
import com.fx.secondbar.http.HttpManager;
import com.fx.secondbar.util.Constants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import me.next.tagview.TagCloudView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * function:发布约吧
 * author: frj
 * modify date: 2018/11/5
 */
public class AcSendDate extends ActivitySupport implements FragmentAddTopicDialog.OnAddTopicListener
{
    /**
     * 选择地理位置请求码
     */
    private static final int REQUEST_CODE_LOCATION = 111;

    /**
     * 最多图片选取张数
     */
    private static final int MAX_IMG_COUNT = 9;

    private EditText ed_title;
    private EditText ed_input;
    private TextView tv_limit;
    private RecyclerView recyclerView;
    private TextView tv_location;
    private EditText ed_cost;
    private EditText ed_people_count;
    private EditText ed_date;
    private EditText ed_timelength;
    private TextView tv_add_topic;
    private TagCloudView tag_cloud_view;

    private AdAddImg adapter;
    private List<String> topics;
    private FragmentAddTopicDialog addTopicDialog;
    private ArrayList<String> selectImgs;

    private ProgressDialog dialog;

    private TimePickerView timePickerView;

    private String lat;
    private String lng;

    @Override
    protected int getLayoutResId()
    {
        return R.layout.ac_send_date;
    }

    @Override
    protected void initView()
    {
        ed_title = findView(R.id.ed_title);
        ed_input = findView(R.id.ed_input);
        tv_limit = findView(R.id.tv_limit);
        recyclerView = findView(R.id.recyclerView);
        tv_location = findView(R.id.tv_location);
        ed_cost = findView(R.id.ed_cost);
        ed_people_count = findView(R.id.ed_people_count);
        ed_date = findView(R.id.ed_date);
        tv_add_topic = findView(R.id.tv_add_topic);
        tag_cloud_view = findView(R.id.tag_cloud_view);
        ed_timelength = findView(R.id.ed_timelength);

        findView(R.id.ib_back).setOnClickListener(this);
        findView(R.id.tv_publish).setOnClickListener(this);
        Toolbar toolbar = findView(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    protected void initListener()
    {
        tv_location.setOnClickListener(this);
        ed_date.setOnClickListener(this);
        tv_add_topic.setOnClickListener(this);
    }

    @Override
    protected void initData()
    {
        initDialog();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.addItemDecoration(SpaceDecorationUtil.getDecoration(DensityUtil.dip2px(this, 8), false, false, false));
        adapter = new AdAddImg(this, calImgItemWidth());
        adapter.addData(new AdAddImg.Entity(AdAddImg.Entity.TYPE_ADD));
        adapter.bindToRecyclerView(recyclerView);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener()
        {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position)
            {
                AdAddImg adAddImg = (AdAddImg) adapter;
                if (adAddImg != null && adAddImg.getItem(position) != null)
                {
                    String path = adAddImg.getItem(position).getPath();
                    if (!TextUtils.isEmpty(path))
                    {
                        selectImgs.remove(path);
                    }
                    adAddImg.remove(position);
                    if (adAddImg.getItemCount() < MAX_IMG_COUNT)
                    {
                        if (AdAddImg.Entity.TYPE_ADD != adAddImg.getItem(adAddImg.getItemCount() - 1).getItemType())
                        {
                            adAddImg.addData(new AdAddImg.Entity(AdAddImg.Entity.TYPE_ADD));
                        }
                    }
                }
            }
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position)
            {
                AdAddImg adAddImg = (AdAddImg) adapter;
                if (adAddImg == null || adAddImg.getItem(position) == null)
                {
                    return;
                }
                //新增
                if (AdAddImg.Entity.TYPE_ADD == adAddImg.getItem(position).getItemType())
                {
                    Bundle bundle = new Bundle();
                    bundle.putInt(ConstantValue.EXTRA_TITLE_COLOR, Color.WHITE);
                    bundle.putStringArrayList(ConstantValue.EXTRA_DEFAULT_SELECTED_LIST, selectImgs);
                    jump(MultiImageSelectorActivity.class, bundle, ConstantValue.REQUEST_CODE);
                } else
                {   //预览

                }
            }
        });

        topics = new ArrayList<>();
        tag_cloud_view.setTags(topics);
        selectImgs = new ArrayList<>(MAX_IMG_COUNT);

        ed_input.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                tv_limit.setText(String.format(getString(R.string.send_dynamic_limit), s.length()));
            }
        });
    }

    /**
     * 初始化对话框
     */
    private void initDialog()
    {
        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.progress_tips));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);
    }

    /**
     * 计算图片项宽度
     *
     * @return 图片项宽度
     */
    private int calImgItemWidth()
    {
        int width = DisplayUtil.getRealScreenSize(this).widthPixels;
        //计算出RecycleView的宽度
        width -= DensityUtil.dip2px(this, 15) * 2;
        width -= DensityUtil.dip2px(this, 8) * 3;
        return width / 4;
    }

    /**
     * 创建日期选择器
     */
    private TimePickerView createTimePicker()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR) + 1, calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        return new TimePickerBuilder(this, new OnTimeSelectListener()
        {
            @Override
            public void onTimeSelect(Date date, View v)
            {
                if (ed_date != null)
                {
                    String time = DateUtils.DateToStr(date, "yyyy-MM-dd");
                    ed_date.setText(time);
                }
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "时", "分", "")
                .setCancelText("取消")
                .setSubmitText("确定")
                .setCancelColor(Color.parseColor("#999999"))
                .setSubmitColor(getResources().getColor(R.color.font_yellow_color))
                .setTextColorOut(Color.parseColor("#999999"))
                .setTextColorCenter(Color.parseColor("#333333"))
                .setRangDate(Calendar.getInstance(), calendar)
                .setDate(Calendar.getInstance())
                .setTitleSize(18)
                .setContentTextSize(15)
                .setLineSpacingMultiplier(2.0f)
                .isCyclic(true)
                .isCenterLabel(true)
                .build();
    }

    /**
     * 发布
     */
    private void publish()
    {
        if (VerificationUtil.requiredFieldValidator(this, new View[]{ed_title, ed_input, ed_cost, ed_people_count, ed_date, ed_timelength}, new String[]{"请输入邀约主题", "请输入邀约内容", "请输入约见费用", "请输入约见人数", "请选择约见日期", "请输入约见时长"}))
        {
            if (!getTextView(tv_location).equals("请选择约见位置"))
            {
                //压缩图片
                compressFile(selectImgs);
            } else
            {
                ShowToast.showToast("请选择约见位置");
            }
        }
    }

    /**
     * 压缩文件
     *
     * @param imgs
     */
    private void compressFile(List<String> imgs)
    {
        if (imgs == null)
        {
            submit(null);
            return;
        }
        final List<File> files = new ArrayList<>(imgs.size());
        Observable.from(imgs).map(new Func1<String, File>()
        {

            @Override
            public File call(String s)
            {
                byte[] bytes = BitmapUtil.decodeThumbBitmapByteForFile(s, BitmapUtil.DEFAULT_WIDTH, BitmapUtil.DEFAULT_HEIGHT, true);
                File dir = new File(getCacheDir() + File.separator + Constants.ROOT_DIR);
                if (!dir.exists())
                {
                    dir.mkdirs();
                }
                File file = new File(dir, String.valueOf(System.currentTimeMillis()));
                try
                {
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(bytes);
                    fos.flush();
                    fos.close();
                    return file;
                } catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
                return null;
            }
        }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<File>()
        {
            @Override
            public void onCompleted()
            {
                LogUtil.e("onCompleted");
                submit(files);
            }

            @Override
            public void onError(Throwable e)
            {
                if (dialog != null)
                {
                    dialog.dismiss();
                }
                e.printStackTrace();
                ShowToast.showToast("文件读写失败");
            }

            @Override
            public void onNext(File file)
            {
                if (file != null)
                {
                    files.add(file);
                }
            }
        });
    }

    /**
     * 提交
     *
     * @param files
     */
    private void submit(List<File> files)
    {
        String tag = "";
        if (VerificationUtil.getSize(topics) > 0)
        {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < topics.size(); i++)
            {
                sb.append(topics.get(i));
                if (i != topics.size() - 1)
                {
                    sb.append("，");
                }
            }
            tag = sb.toString();
        }
        if (dialog == null)
        {
            initDialog();
        }
        dialog.show();
        HttpManager.publishDate(files, getTextView(ed_title), getTextView(ed_date), getTextView(tv_location), lng, lat, tag, getTextView(ed_input), getTextView(ed_people_count), getTextView(ed_timelength), getTextView(ed_cost), new Subscriber<ResponseBean>()
        {
            @Override
            public void onCompleted()
            {

            }

            @Override
            public void onError(Throwable e)
            {
                if (isDestroy())
                {
                    return;
                }
                if (dialog != null)
                {
                    dialog.dismiss();
                }
                e.printStackTrace();
                ShowToast.showToast(HttpManager.checkLoadError(e));
            }

            @Override
            public void onNext(ResponseBean responseBean)
            {
                if (isDestroy())
                {
                    return;
                }
                if (dialog != null)
                {
                    dialog.dismiss();
                }
                ShowToast.showToast("发布成功");
                finish();
            }
        });
    }


    @Override
    protected String[] getPermissionArrays()
    {
        return new String[0];
    }

    @Override
    protected int[] getPermissionInfoTips()
    {
        return new int[0];
    }

    @Override
    public void finishInput(String topic)
    {
        topics.add(topic);
        tag_cloud_view.setTags(topics);
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
            case R.id.ib_back:
                finish();
                break;
            case R.id.tv_publish:
                publish();
                break;
            case R.id.tv_location:
                jump(AcSearchLocation.class, REQUEST_CODE_LOCATION);
                break;
            case R.id.ed_date:
                if (timePickerView == null)
                {
                    timePickerView = createTimePicker();
                }
                timePickerView.show();
                break;
            case R.id.tv_add_topic:
                if (addTopicDialog == null)
                {
                    addTopicDialog = new FragmentAddTopicDialog();
                    addTopicDialog.setOnAddTopicListener(this);
                }
                if (!addTopicDialog.isVisible())
                {
                    addTopicDialog.show(getSupportFragmentManager(), FragmentAddTopicDialog.class.getSimpleName());
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (ConstantValue.REQUEST_CODE == requestCode && RESULT_OK == resultCode)
        {
            if (data == null)
            {
                return;
            }
            ArrayList<String> list = data.getStringArrayListExtra(ConstantValue.EXTRA_RESULT);
            selectImgs.clear();
            selectImgs.addAll(list);
            //最多选取9张照片
            int size = VerificationUtil.getSize(list);
            //清除现有显示
            adapter.getData().clear();
            //重新添加新的资源
            for (int i = 0; i < size; i++)
            {
                AdAddImg.Entity entity = new AdAddImg.Entity(AdAddImg.Entity.TYPE_IMG, list.get(i));
                adapter.getData().add(entity);
            }
            if (size < MAX_IMG_COUNT)
            {
                adapter.getData().add(new AdAddImg.Entity(AdAddImg.Entity.TYPE_ADD));
            }
            adapter.notifyDataSetChanged();
        } else if (REQUEST_CODE_LOCATION == requestCode && RESULT_OK == resultCode)
        {
            if (data == null)
            {
                return;
            }
            String address = data.getStringExtra("address");
            lat = data.getStringExtra("lat");
            lng = data.getStringExtra("lng");
            if (!TextUtils.isEmpty(address))
            {
                tv_location.setText(address);
            }
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }
}

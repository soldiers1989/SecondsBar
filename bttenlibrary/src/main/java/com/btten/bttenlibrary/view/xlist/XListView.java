/**
 * @file XListView.java
 * @package me.maxwin.view
 * @create Mar 18, 2012 6:28:41 PM
 * @author Maxwin
 * @description An ListView support (a) Pull down to refresh, (b) Pull up to load more.
 * Implement IXListViewListener, and see stopRefresh() / stopLoadMore().
 */
package com.btten.bttenlibrary.view.xlist;


import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.btten.bttenlibrary.R;


/**
 * 下拉刷新、上拉加载更多ListView
 *
 */
public class XListView extends ListView implements OnScrollListener
{

    private float mLastY = -1; // save event y
    private Scroller mScroller; // used for scroll back
    private OnScrollListener mScrollListener; // user's scroll listener

    // the interface to trigger refresh and load more.
    private IXListViewListener mListViewListener;

    // -- header view
    private XListViewHeader mHeaderView;
    // header view content, use it to calculate the Header's height. And hide it
    // when disable pull refresh.
    private RelativeLayout mHeaderViewContent;
    private TextView mHeaderTimeView;
    private int mHeaderViewHeight; // header view's height
    private boolean mEnablePullRefresh = true;
    private boolean mPullRefreshing = false; // is refreashing.

    // -- footer view
    private XListViewFooter mFooterView;
    private boolean mEnablePullLoad;
    private boolean mPullLoading;
    private boolean mIsFooterReady = false;

    // total list items, used to detect is at the bottom of listview.
    private int mTotalItemCount;

    // for mScroller, scroll back from header or footer.
    private int mScrollBack;
    private final static int SCROLLBACK_HEADER = 0;
    private final static int SCROLLBACK_FOOTER = 1;

    private final static int SCROLL_DURATION = 400; // scroll back duration
    private final static int PULL_LOAD_MORE_DELTA = 50; // when pull up >= 50px
    // at bottom, trigger
    // load more.
    private final static float OFFSET_RADIO = 1.8f; // support iOS like pull
    // feature.

    /**
     * 监控的高度，当滑动过该高度后，触发监听事件
     */
    private int mMonitoringRange = 0;

    private OnMonitoringListener mOnMonitoringListener;

    private GestureDetector mGestureDetector; // 手势监听

    /**
     * @param context
     */
    public XListView(Context context)
    {
        super(context);
        initWithContext(context);
    }

    public XListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initWithContext(context);
    }

    public XListView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        initWithContext(context);
    }

    private void initWithContext(Context context)
    {
        mScroller = new Scroller(context, new DecelerateInterpolator());
        // XListView need the scroll event, and it will dispatch the event to
        // user's listener (as a proxy).
        super.setOnScrollListener(this);

        // init header view
        mHeaderView = new XListViewHeader(context);
        mHeaderViewContent = (RelativeLayout) mHeaderView.findViewById(R.id.xlistview_header_content);
        mHeaderTimeView = (TextView) mHeaderView.findViewById(R.id.xlistview_header_time);
        addHeaderView(mHeaderView);

        // init footer view
        mFooterView = new XListViewFooter(context);

        // init header height
        mHeaderView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener()
        {
            @Override
            public void onGlobalLayout()
            {
                mHeaderViewHeight = mHeaderViewContent.getHeight();
                getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
        setFadingEdgeLength(0);
        mGestureDetector = new GestureDetector(getContext(), new YScrollDetector());
    }

    @Override
    public void setAdapter(ListAdapter adapter)
    {
        // make sure XListViewFooter is the last footer view, and only add once.
        if (mIsFooterReady == false)
        {
            mIsFooterReady = true;
            addFooterView(mFooterView);
        }
        super.setAdapter(adapter);
    }

    /**
     * enable or disable pull down refresh feature.
     *
     * @param enable
     */
    public void setPullRefreshEnable(boolean enable)
    {
        mEnablePullRefresh = enable;
        if (!mEnablePullRefresh)
        { // disable, hide the content
            mHeaderViewContent.setVisibility(View.INVISIBLE);
        } else
        {
            mHeaderViewContent.setVisibility(View.VISIBLE);
        }
    }

    /**
     * enable or disable pull up load more feature.
     *
     * @param enable
     */
    public void setPullLoadEnable(boolean enable)
    {
        mEnablePullLoad = enable;
        if (!mEnablePullLoad)
        {
            mFooterView.hide();
            mFooterView.setOnClickListener(null);
        } else
        {
            mPullLoading = false;
            mFooterView.show();
            mFooterView.setState(XListViewFooter.STATE_NORMAL);
            mFooterView.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    startLoadMore();
                }
            });
        }
    }

    /**
     * stop refresh, reset header view.
     */
    public void stopRefresh()
    {
        if (mPullRefreshing == true)
        {
            mPullRefreshing = false;
            resetHeaderHeight();
        }
    }

    /**
     * stop load more, reset footer view.
     */
    public void stopLoadMore()
    {
        if (mPullLoading == true)
        {
            mPullLoading = false;
            mFooterView.setState(XListViewFooter.STATE_NORMAL);
        }
    }

    /**
     * set last refresh time
     *
     * @param time
     */
    public void setRefreshTime(String time)
    {
        mHeaderTimeView.setText(time);
    }

    private void invokeOnScrolling()
    {
        if (mScrollListener instanceof OnXScrollListener)
        {
            OnXScrollListener l = (OnXScrollListener) mScrollListener;
            l.onXScrolling(this);
        }
    }

    private void updateHeaderHeight(float delta)
    {
        mHeaderView.setVisiableHeight((int) delta + mHeaderView.getVisiableHeight());
        if (mEnablePullRefresh && !mPullRefreshing)
        { // 未处于刷新状态，更新箭头
            if (mHeaderView.getVisiableHeight() > mHeaderViewHeight)
            {
                mHeaderView.setState(XListViewHeader.STATE_READY);
            } else
            {
                mHeaderView.setState(XListViewHeader.STATE_NORMAL);
            }
        }
        setSelection(0); // scroll to top each time
    }

    /**
     * reset header view's height.
     */
    private void resetHeaderHeight()
    {
        int height = mHeaderView.getVisiableHeight();
        if (height == 0) // not visible.
            return;
        // refreshing and header isn't shown fully. do nothing.
        if (mPullRefreshing && height <= mHeaderViewHeight)
        {
            return;
        }
        int finalHeight = 0; // default: scroll back to dismiss header.
        // is refreshing, just scroll back to show all the header.
        if (mPullRefreshing && height > mHeaderViewHeight)
        {
            finalHeight = mHeaderViewHeight;
        }
        mScrollBack = SCROLLBACK_HEADER;
        mScroller.startScroll(0, height, 0, finalHeight - height, SCROLL_DURATION);
        // trigger computeScroll
        invalidate();
    }

    private void updateFooterHeight(float delta)
    {
        int height = mFooterView.getBottomMargin() + (int) delta;
        if (mEnablePullLoad && !mPullLoading)
        {
            if (height > PULL_LOAD_MORE_DELTA)
            { // height enough to invoke load
                // more.
                mFooterView.setState(XListViewFooter.STATE_READY);
            } else
            {
                mFooterView.setState(XListViewFooter.STATE_NORMAL);
            }
        }
        mFooterView.setBottomMargin(height);

        // setSelection(mTotalItemCount - 1); // scroll to bottom
    }

    private void resetFooterHeight()
    {
        int bottomMargin = mFooterView.getBottomMargin();
        if (bottomMargin > 0)
        {
            mScrollBack = SCROLLBACK_FOOTER;
            mScroller.startScroll(0, bottomMargin, 0, -bottomMargin, SCROLL_DURATION);
            invalidate();
        }
    }

    /**
     * 开始加载
     */
    public void startLoadMore()
    {
        if (!mPullLoading)
        {
            mPullLoading = true;
            mFooterView.setState(XListViewFooter.STATE_LOADING);
            if (mListViewListener != null)
            {
                mListViewListener.onLoadMore();
            }
        }
    }

    /**
     * 开始刷新
     */
    public void startRefresh()
    {
        updateHeaderHeight(mHeaderViewHeight);
        refresh();
    }

    /**
     * 开始刷新
     */
    private void refresh()
    {
        mPullRefreshing = true;
        mHeaderView.setState(XListViewHeader.STATE_REFRESHING);
        if (mListViewListener != null)
        {
            mListViewListener.onRefresh();
        }
    }

    public boolean ismPullRefreshing()
    {
        return mPullRefreshing;
    }

    public boolean ismPullLoading()
    {
        return mPullLoading;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        if (mLastY == -1)
        {
            mLastY = ev.getRawY();
        }

        switch (ev.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                mLastY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float deltaY = ev.getRawY() - mLastY;
                mLastY = ev.getRawY();
                if (getFirstVisiblePosition() == 0 && (mHeaderView.getVisiableHeight() > 0 || deltaY > 0))
                {
                    // the first item is showing, header has shown or pull down.
                    if (mEnablePullRefresh)
                    {
                        updateHeaderHeight(deltaY / OFFSET_RADIO);
                        invokeOnScrolling();
                    }
                } else if (getLastVisiblePosition() == mTotalItemCount - 1
                        && (mFooterView.getBottomMargin() > 0 || deltaY < 0))
                {
                    // last item, already pulled up or want to pull up.
                    if (mEnablePullLoad)
                    {
                        updateFooterHeight(-deltaY / OFFSET_RADIO);
                    }
                }
                break;
            default:
                mLastY = -1; // reset
                if (getFirstVisiblePosition() == 0)
                {
                    // invoke refresh
                    if (mEnablePullRefresh && mHeaderView.getVisiableHeight() > mHeaderViewHeight)
                    {
                        // mPullRefreshing = true;
                        // mHeaderView.setState(XListViewHeader.STATE_REFRESHING);
                        // if (mListViewListener != null) {
                        // mListViewListener.onRefresh();
                        // }
                        refresh();
                    }
                    resetHeaderHeight();
                }
                if (getLastVisiblePosition() == mTotalItemCount - 1)
                {
                    // invoke load more.
                    if (mEnablePullLoad && mFooterView.getBottomMargin() > PULL_LOAD_MORE_DELTA)
                    {
                        startLoadMore();
                    }
                    resetFooterHeight();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void computeScroll()
    {
        if (mScroller.computeScrollOffset())
        {
            if (mScrollBack == SCROLLBACK_HEADER)
            {
                mHeaderView.setVisiableHeight(mScroller.getCurrY());
            } else
            {
                mFooterView.setBottomMargin(mScroller.getCurrY());
            }
            postInvalidate();
            invokeOnScrolling();
        }
        super.computeScroll();
    }

    @Override
    public void setOnScrollListener(OnScrollListener l)
    {
        mScrollListener = l;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState)
    {
        if (mScrollListener != null)
        {
            mScrollListener.onScrollStateChanged(view, scrollState);
        }
    }

    /**
     * 上一次顶点
     */
    private int beforeTop = 0;
    // 上一个显示的项的位置
    private int beforeVisibleItem = 1;

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
    {
        mTotalItemCount = totalItemCount;
        if (mScrollListener != null)
        {
            mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
        if (mOnMonitoringListener != null)
        {
            if (firstVisibleItem == 1)
            {
                View childAt = getChildAt(0);
                if (childAt != null)
                {
                    // int top = Math.abs(childAt.getTop());
                    int top = -childAt.getTop();
                    if (top > mMonitoringRange)
                    {
                        // 判断下滑还是上滑
                        boolean downScroll = top > beforeTop;
                        mOnMonitoringListener.onMonitoring(false, downScroll, top - mMonitoringRange);
                    } else
                    {
                        mOnMonitoringListener.onMonitoring(true, (top > beforeTop ? true : false), 0);
                    }
                    beforeTop = top;
                }
            } else if (firstVisibleItem > 1 && beforeVisibleItem != firstVisibleItem)
            {
                beforeVisibleItem = firstVisibleItem;
                mOnMonitoringListener.onMonitoring(false, true, 255);
            }
        }
    }

    public void setXListViewListener(IXListViewListener l)
    {
        mListViewListener = l;
    }

    /**
     * 设置监听高度
     *
     * @param mMonitoringRange
     *            高度范围
     */
    public void setMonitoringRange(int mMonitoringRange)
    {
        this.mMonitoringRange = mMonitoringRange;
    }

    /**
     * 设置监听滑入指定范围内的监听器
     *
     * @param mListener
     */
    public void setOnMonitoringListener(OnMonitoringListener mListener)
    {
        this.mOnMonitoringListener = mListener;
    }

    /**
     * you can listen ListView.OnScrollListener or this one. it will invoke
     * onXScrolling when header/footer scroll back.
     */
    public interface OnXScrollListener extends OnScrollListener
    {
        public void onXScrolling(View view);
    }

    /**
     * 监听是否滑动过指定的高度
     *
     * @author frj
     *
     */
    public static interface OnMonitoringListener
    {

        /**
         *
         * @param state
         *            true表示正在指定的高度范围内；false反之。
         * @param downScroll
         *            表示是否是向下滑，true表示向下；false表示向上。
         * @param distance
         *            超出指定高度范围的距离
         */
        void onMonitoring(boolean state, boolean downScroll, int distance);
    }

    /**
     * 判断当前是左右滑动还是上下滑动（根据左右和上下滑动距离判断，哪个距离更大，就表示哪个是滑动）
     *
     * @author frj
     *
     */
    private class YScrollDetector extends SimpleOnGestureListener
    {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
        {

            if (Math.abs(distanceY) >= Math.abs(distanceX))
            {
                return true;
            }
            return false;
        }
    }

    // /**
    // * implements this interface to get refresh/load more event.
    // */
    // public interface IXListViewListener {
    // public void onRefresh();
    //
    // public void onLoadMore();
    // }
}

package com.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.OverScroller;

public class ScrollLayout extends ViewGroup {
    private GestureDetector mGesture;
    private String TAG = "ScrollLayout";
    private int screenWidth;
    private OverScroller mScroller;
    private int screenHeight;
    private VelocityTracker mVelocityTracker;
    private int maxFlingVelocity;

    public ScrollLayout(Context context) {
        super(context);
        init();
    }

    public ScrollLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    private void init() {
        maxFlingVelocity = ViewConfiguration.get(getContext()).getScaledMaximumFlingVelocity();
        mScroller = new OverScroller(getContext());
        WindowManager manager = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        screenWidth = outMetrics.widthPixels;
        screenHeight = outMetrics.heightPixels;
        mGesture = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                scrollBy(0, (int) distanceY);
                return true;
            }
        });
    }

    public ScrollLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int top = 0;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            int measuredHeight = child.getMeasuredHeight();
            int measuredWidth = child.getMeasuredWidth();
            child.layout(0, top, measuredWidth, top + measuredHeight);
            top += measuredHeight;
        }
    }

    //    match_parent—>EXACTLY。怎么理解呢？match_parent就是要利用父View给我们提供的所有剩余空间，而父View剩余空间是确定的，也就是这个测量模式的整数里面存放的尺寸。
//
//    wrap_content—>AT_MOST。怎么理解：就是我们想要将大小设置为包裹我们的view内容，那么尺寸大小就是父View给我们作为参考的尺寸，只要不超过这个尺寸就可以啦，具体尺寸就根据我们的需求去设定。
//
//    固定尺寸（如100dp）—>EXACTLY。用户自己指定了尺寸大小，我们就不用再去干涉了，当然是以指定的大小为主啦。
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        //如果宽高都是包裹内容
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            //我们将高度设置为所有子View的高度相加，宽度设为子View中最大的宽度
            int height = getTotleHeight();
            int width = getMaxChildWidth();
            setMeasuredDimension(width, height);

        } else if (heightMode == MeasureSpec.AT_MOST) {//如果只有高度是包裹内容
            //宽度设置为ViewGroup自己的测量宽度，高度设置为所有子View的高度总和
            setMeasuredDimension(widthSize, getTotleHeight());
        } else if (widthMode == MeasureSpec.AT_MOST) {//如果只有宽度是包裹内容
            //宽度设置为子View中宽度最大的值，高度设置为ViewGroup自己的测量值
            setMeasuredDimension(getMaxChildWidth(), heightSize);

        } else {
            setMeasuredDimension(widthSize, heightSize);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
        mGesture.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
            mVelocityTracker.computeCurrentVelocity(1000, maxFlingVelocity);
            float velocityX = mVelocityTracker.getXVelocity(event.getPointerId(0));
            float velocityY = mVelocityTracker.getYVelocity(event.getPointerId(0));
            completeMove(-velocityX, -velocityY);
            if (mVelocityTracker != null) {
                mVelocityTracker.recycle();
                mVelocityTracker = null;
            }

            if (getScrollY() < 0) {
                mScroller.startScroll(0, getScrollY(), 0, -getScrollY());
                invalidate();
            }
            View lastChild = getChildAt(getChildCount() - 1);
            int bottomY = (int) (lastChild.getY() + lastChild.getMeasuredHeight() - screenHeight);
            if (getScrollY() > bottomY) {
                mScroller.startScroll(0, getScrollY(), 0, bottomY - getScrollY());
                invalidate();
            }
        }
        return true;
    }

    private void completeMove(float v, float velocityY) {
        View lastChild = getChildAt(getChildCount() - 1);
        int bottomY = (int) (lastChild.getY() + lastChild.getMeasuredHeight() - screenHeight);
        mScroller.fling(0, getScrollY(), 0, (int) (velocityY ), 0, getMeasuredWidth(), 0, bottomY);
        invalidate();
    }


    /***
     * 获取子View中宽度最大的值
     */
    private int getMaxChildWidth() {
        int childCount = getChildCount();
        int maxWidth = 0;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (childView.getMeasuredWidth() > maxWidth)
                maxWidth = childView.getMeasuredWidth();

        }

        return maxWidth;
    }

    /***
     * 将所有子View的高度相加
     **/
    private int getTotleHeight() {
        int childCount = getChildCount();
        int height = 0;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            height += childView.getMeasuredHeight();

        }

        return height;
    }

    @Override
    public void computeScroll() {
        //判断滚动时候停止
        if (mScroller.computeScrollOffset()) {
            //滚动到指定的位置
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            //这句话必须写，否则不能实时刷新
            postInvalidate();
        }
    }
}
